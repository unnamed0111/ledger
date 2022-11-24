package com.portfolio.ledger.service;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.ReplyDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    PageResponseDTO<ReplyDTO> getListOfAccount(Long ano, PageRequestDTO pageRequestDTO);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);
}
