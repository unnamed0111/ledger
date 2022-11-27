package com.portfolio.ledger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info(".....................................LOGIN GET.....................................");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("................user logout..................");
        }
    }

    // 로그인 POST방식은 SecurityConfig에서 로그인 경로를 지정해주면서 자동으로 생성됨 (혹은 개인이 다르게 구현가능)
}
