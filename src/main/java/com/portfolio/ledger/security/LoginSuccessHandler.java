package com.portfolio.ledger.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String referer = request.getHeader("referer");
        String referer = request.getParameter("from");

        if(referer.isEmpty()) {
            response.sendRedirect("/table/list");

            return;
        }

        response.sendRedirect(referer);
    }
}
