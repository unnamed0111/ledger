package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.SampleDTO;
import com.portfolio.ledger.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("......................getList......................");

        PageResponseDTO<SampleDTO> responseDTO = sampleService.getList(pageRequestDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @PostMapping("/write")
    public String insert(@RequestParam(name = "text", required = true) String text,
                         @RequestParam(name = "title", required = true) String title) {
        log.info("......................insert......................");
        log.info(text);

        SampleDTO sampleDTO = SampleDTO.builder()
                .title(title)
                .text(text)
                .build();

        sampleService.register(sampleDTO);

        return "redirect:/sample/list";
    }
}
