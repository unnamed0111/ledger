package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountSearch {
    Page<Account> searchList(Pageable pageable); // 이후에 고급검색 기능 추가
}
