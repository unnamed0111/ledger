package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/table")
@RequiredArgsConstructor
@Log4j2
public class TableController {
    private final AccountService accountService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("........................GET ACCOUNT LIST........................");

        PageResponseDTO<AccountDTO> responseDTO = accountService.getList(pageRequestDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/register")
    public String register(@Valid AccountDTO accountDTO,
                           BindingResult bindingResult,
                           PageRequestDTO pageRequestDTO,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {
        log.info("...........................POST ACCOUNT LIST...........................");
        log.info("pageRequestDTO : " + pageRequestDTO);
        log.info(principal);

        if (bindingResult.hasErrors()) {
            log.info("...........................HAS ERRORS...........................");
            log.info("AccountDTO : " + accountDTO);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/table/list?" + pageRequestDTO.getLink();
        }

        accountDTO.setWriter(principal.getName());
        accountService.register(accountDTO);

        return "redirect:/table/list";
    }

    @GetMapping("/read")
    public void read(Long ano, PageRequestDTO pageRequestDTO, Model model) {
        log.info("...........................GET READ...........................");

        AccountDTO accountDTO = accountService.get(ano);
        log.info(accountDTO);

        model.addAttribute("dto", accountDTO);
    }

    @GetMapping("/modify")
    public void modifyGet(@RequestParam(name = "ano") Long ano, PageRequestDTO pageRequestDTO, Model model) {
        log.info("...........................GET MODIFY...........................");

        AccountDTO accountDTO = accountService.get(ano);

        model.addAttribute("dto", accountDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid AccountDTO accountDTO,
                         BindingResult bindingResult,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("...........................POST MODIFY...........................");

        if(bindingResult.hasErrors()) {
            log.info(".................HAS ERRORS.................");

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/table/modify?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
        }

        accountService.modify(accountDTO);

        // 정상 수정 됐을 때
        return "redirect:/table/read?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "ano") Long ano) {
        accountService.remove(ano);

        return "redirect:/table/list";
    }
}
