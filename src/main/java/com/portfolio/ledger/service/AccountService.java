package com.portfolio.ledger.service;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;

public interface AccountService {
    Long register(AccountDTO accountDTO);
    PageResponseDTO<AccountDTO> getList(PageRequestDTO pageRequestDTO);
    AccountDTO get(Long ano);
}
