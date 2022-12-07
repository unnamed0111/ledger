package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/table")
@RequiredArgsConstructor
@Log4j2
public class TableController {
    private final AccountService accountService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model, AccountDTO accountDTO) {
        log.info("........................GET ACCOUNT LIST........................");

        // 나중에 /register 를 REST API 형태로 분리하면 없애기
        if(model.containsAttribute("accountDTOBindingResult")) {
            model.addAttribute(
                    "org.springframework.validation.BindingResult.accountDTO",
                    model.asMap().get("accountDTOBindingResult")
            );
        }

        PageResponseDTO<AccountDTO> responseDTO = accountService.getList(pageRequestDTO);
        Map<String, Double> totalPrice = accountService.getTotalPrice();

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("totalPrice", totalPrice);
    }

    // 나중에 list 페이지에 의존하지 않도록 REST API 형태로 분리하기
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

            redirectAttributes.addFlashAttribute("accountDTOBindingResult",bindingResult);
            redirectAttributes.addFlashAttribute(accountDTO);
            redirectAttributes.addFlashAttribute(pageRequestDTO);

            return "redirect:/table/list";
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
    public void modifyGet(@RequestParam(name = "ano", required = true) Long ano, PageRequestDTO pageRequestDTO, Model model) {
        log.info("...........................GET MODIFY...........................");

        AccountDTO accountDTO = accountService.get(ano);

        model.addAttribute(accountDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/modify")
    public String modify(@Valid AccountDTO accountDTO,
                         BindingResult bindingResult,
                         PageRequestDTO pageRequestDTO,
                         Principal principal,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        log.info("...........................POST MODIFY...........................");
        log.info("USER NAME : " + principal.getName());

        accountDTO.setWriter(principal.getName());

        if(bindingResult.hasErrors()) {
            log.info(".................HAS ERRORS.................");
            log.info(bindingResult);

            model.addAttribute(accountDTO);

            return "table/modify";
        }

        try {
            accountService.modify(accountDTO);
        } catch (Exception e) {
            log.info("==================THIS USER IS NOT OWNER==================");

            redirectAttributes.addFlashAttribute("errors", e.getMessage());

            return "redirect:/table/read?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
        }

        // 정상 수정 됐을 때
        return "redirect:/table/read?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/remove")
    public String remove(AccountDTO accountDTO,
                         Principal principal,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes) {

        accountDTO.setWriter(principal.getName());

        try {
            accountService.remove(accountDTO);
        } catch (Exception e) {
            log.info("==================THIS USER IS NOT OWNER==================");

            redirectAttributes.addFlashAttribute("errors", e.getMessage());

            return "redirect:/table/read?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
        }

        return "redirect:/table/list";
    }
}
