package com.portfolio.ledger.controller;

import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.ReplyDTO;
import com.portfolio.ledger.exception.NotOwnerException;
import com.portfolio.ledger.security.dto.MemberSecurityDTO;
import com.portfolio.ledger.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping(value = "/list/{ano}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("ano") Long ano, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfAccount(ano, pageRequestDTO);

        return responseDTO;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@RequestBody @Valid ReplyDTO replyDTO,
                                                      BindingResult bindingResult,
                                                      Authentication authentication) throws BindException {
        log.info("..................................POST..................................");
        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        replyDTO.setUid(((MemberSecurityDTO)authentication.getPrincipal()).getUid());

        Long rno = replyService.register(replyDTO);

        Map<String, Long> result = new HashMap<>();
        result.put("rno", rno);

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno, Authentication authentication) throws NotOwnerException {

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(rno)
                .uid(((MemberSecurityDTO)authentication.getPrincipal()).getUid())
                .build();

        replyService.remove(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);

        return resultMap;
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno,
                                    @RequestBody @Valid ReplyDTO replyDTO,
                                    BindingResult bindingResult,
                                    Authentication authentication) throws NotOwnerException, BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        replyDTO.setRno(rno);
        replyDTO.setUid(((MemberSecurityDTO)authentication.getPrincipal()).getUid());

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);

        return resultMap;
    }
}
