package com.example.food_delivery.service.menu_pdf_generation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MenuPDFGenerationService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 36,
            Font.BOLD);
    private static final Font SMALL_BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    private static final String LOGO_PATH = "src/main/resources/img/menu.png";

   // private static final Logger LOGGER = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private static final String FILE_PATH_FORMAT = "Menus/Menu-%s.pdf";
    //private static final String LOGO_PATH = "src/main/resources/img/logo.png";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy_MM_dd-HH_mm_ss");

    public void createMenuPDF()  {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, new FileOutputStream(String.format(FILE_PATH_FORMAT,
                    LocalDateTime.now().format(DATE_TIME_FORMATTER))));
            document.open();
            addMetaData(document);
            addContent(document);
            //  addContent(document, client, product, order);
            document.close();
        } catch (DocumentException | IOException e) {
           // LOGGER.error(Throwables.getStackTraceAsString(e));
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Restaurant Menu");
        document.addSubject("menu");
    }

    private static void addContent(Document document)
            throws DocumentException, IOException {
        addPrefaceParagraph(document);
    }

    private static void addPrefaceParagraph(Document document) throws DocumentException, IOException {
        Paragraph preface = new Paragraph();
        Image image = Image.getInstance(LOGO_PATH);
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(100, 100);
        preface.add(image);
        addEmptyLine(preface, 1);
        Paragraph titlePara = new Paragraph("Menu", TITLE_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        preface.add(titlePara);
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
