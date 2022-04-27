package com.example.food_delivery.service.menu_pdf_generation;

import com.example.food_delivery.model.DTO.FoodDTO;
import com.example.food_delivery.model.Food;
import com.example.food_delivery.model.FoodCategory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class MenuPDFGenerationService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 36,
            Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLD);
    private static final Font SMALL_BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14);

    private static final String LOGO_PATH = "src/main/resources/img/menu.png";

   // private static final Logger LOGGER = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private static final String FILE_PATH_FORMAT = "Menus/Menu-%s.pdf";
    //private static final String LOGO_PATH = "src/main/resources/img/logo.png";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy_MM_dd-HH_mm_ss");

    public void createMenuPDF(String restaurantName,
                              Map<FoodCategory, List<FoodDTO>> categoriesToFoodList)  {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, new FileOutputStream(String.format(FILE_PATH_FORMAT,
                    LocalDateTime.now().format(DATE_TIME_FORMATTER))));
            document.open();
            addMetaData(document, restaurantName);
            addContent(document, restaurantName, categoriesToFoodList);
            document.close();
        } catch (DocumentException | IOException e) {
           // LOGGER.error(Throwables.getStackTraceAsString(e));
            e.printStackTrace();
        }
    }



    private void addMetaData(Document document, String restaurantName) {
        document.addTitle(String.format("%s's Menu", restaurantName));
        document.addSubject("menu");
    }

    private void addContent(Document document,
                            String restaurantName,
                            Map<FoodCategory, List<FoodDTO>> categoriesToFoodList)
            throws DocumentException, IOException {
        addPrefaceParagraph(document, restaurantName);

        for (FoodCategory foodCategory : categoriesToFoodList.keySet()) {
            addFoodCategorySection(document, foodCategory, categoriesToFoodList.get(foodCategory));
        }
    }

    private void addPrefaceParagraph(Document document, String restaurantName) throws DocumentException, IOException {
        Paragraph preface = new Paragraph();
        Image image = Image.getInstance(LOGO_PATH);
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(100, 100);
        preface.add(image);
        addEmptyLine(preface, 1);
        Paragraph titlePara = new Paragraph(String.format("%s's Menu", restaurantName), TITLE_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        preface.add(titlePara);
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    private void addFoodCategorySection(Document document,
                                        FoodCategory foodCategory,
                                        List<FoodDTO> foodList) throws DocumentException {
        document.newPage();

        Paragraph section = new Paragraph();

        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        section.add(dottedline);

        addEmptyLine(section, 1);

        Paragraph titlePara = new Paragraph(foodCategory.getName() + "s", SUBTITLE_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        section.add(titlePara);

        addEmptyLine(section, 1);

        document.add(section);

        for (FoodDTO foodDTO : foodList) {
            addFoodSubsection(document, foodDTO);
        }
    }

    private void addFoodSubsection(Document document, FoodDTO foodDTO) throws DocumentException {
        Paragraph section = new Paragraph();
        section.setIndentationLeft(40);

        Paragraph titlePara = new Paragraph(foodDTO.getName(), SMALL_BOLD_FONT);
        titlePara.setAlignment(Element.ALIGN_LEFT);
        section.add(titlePara);

        Paragraph pricePara = new Paragraph(String.format("Price: %s$", foodDTO.getPrice()), SMALL_FONT);
        pricePara.setAlignment(Element.ALIGN_LEFT);
        section.add(pricePara);

        if (foodDTO.getDescription() != null && !foodDTO.getDescription().isBlank()) {
            Paragraph descriptionPara = new Paragraph(String.valueOf(foodDTO.getDescription()),
                    SMALL_FONT);
            descriptionPara.setAlignment(Element.ALIGN_LEFT);
            section.add(descriptionPara);
        }

        addEmptyLine(section, 1);

        document.add(section);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
