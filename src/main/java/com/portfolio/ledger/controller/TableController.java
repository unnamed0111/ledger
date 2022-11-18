package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/register")
    public String register(AccountDTO accountDTO) {
        log.info("...........................POST ACCOUNT LIST...........................");

        accountService.register(accountDTO);

        return "redirect:/table/list";
    }
}
