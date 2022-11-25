package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AccountSearch {
    Page<AccountDTO> searchList(Pageable pageable); // 이후에 고급검색 기능 추가
    Map<String, Double> searchTotalPrice();
}
