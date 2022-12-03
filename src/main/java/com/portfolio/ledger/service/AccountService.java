package com.portfolio.ledger.service;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.exception.NotOwnerException;

import java.util.Map;

public interface AccountService {
    Long register(AccountDTO accountDTO);
    PageResponseDTO<AccountDTO> getList(PageRequestDTO pageRequestDTO);
    AccountDTO get(Long ano);
    void modify(AccountDTO accountDTO) throws NotOwnerException;
    void remove(AccountDTO accountDTO) throws NotOwnerException;

    Map<String, Double> getTotalPrice();
}
