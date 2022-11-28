package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberSearch {
    Page<Member> search(Pageable pageable); // 기본 검색 기능
}
