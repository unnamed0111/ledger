package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.ReplyDTO;
import com.portfolio.ledger.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@RequestBody @Valid ReplyDTO replyDTO,
                                                      BindingResult bindingResult) throws BindException {
        log.info("..................................POST..................................");
        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Long rno = replyService.register(replyDTO);

        Map<String, Long> result = new HashMap<>();
        result.put("rno", rno);

        return ResponseEntity.ok(result);
    }
}
