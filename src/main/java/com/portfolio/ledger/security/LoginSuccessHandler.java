package com.portfolio.ledger.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String referer = request.getHeader("referer");
        String referer = request.getParameter("from");

        log.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxREFERERxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        log.info("referer : " + referer);

        if(referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        }
    }
}
