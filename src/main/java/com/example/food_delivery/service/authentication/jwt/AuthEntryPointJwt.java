package com.example.food_delivery.service.authentication.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * EntryPoint for handling authentication errors.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


    /**
     * Method invoked whenever an authenticated user tries to acces a secured HTTP resource
     * available for authenticated users only.
     * @param request is the request sent by the user.
     * @param response is the answer to te request.
     * @param authException is the authentication exception that prevented the fulfillment of the
     *                     request.
     * @throws IOException when sending the error through the response fails.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}