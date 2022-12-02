package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.MemberJoinDTO;
import com.portfolio.ledger.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info(".....................................LOGIN GET.....................................");
        log.info("logout : " + logout);

        if(logout != null) {
            log.info("................user logout..................");
        }
    }

    // 로그인 POST방식은 SecurityConfig에서 로그인 경로를 지정해주면서 자동으로 생성됨 (혹은 개인이 다르게 구현가능)

    @GetMapping("/join")
    public void joinGET() {
        log.info(".....................................JOIN GET.....................................");
    }

    @PostMapping("/join")
    public String join(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info(".....................................JOIN POST.....................................");
        log.info(memberJoinDTO);

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/member/join";
        }

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid existed");

            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/member/login"; // 나중에 안내문 페이지(or 회원가입 완료 페이지)를 만들어서 해당 화면에 result값과 메세지 전달
    }
}
