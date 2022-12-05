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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Double> totalPrice = accountService.getTotalPrice();

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("totalPrice", totalPrice);
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/modify")
    public String modify(@Valid AccountDTO accountDTO,
                         BindingResult bindingResult,
                         PageRequestDTO pageRequestDTO,
                         Principal principal,
                         RedirectAttributes redirectAttributes) {
        log.info("...........................POST MODIFY...........................");
        log.info("USER NAME : " + principal.getName());

        accountDTO.setWriter(principal.getName());

        if(bindingResult.hasErrors()) {
            log.info(".................HAS ERRORS.................");

            Map<String, Object> errors = new HashMap<>();

            errors.put("errorType", "BindingException");
            errors.put("message", "유효하지 않은 값입니다.");
            errors.put("time", "" + System.currentTimeMillis());

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/table/modify?ano=" + accountDTO.getAno() + "&" + pageRequestDTO.getLink();
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
