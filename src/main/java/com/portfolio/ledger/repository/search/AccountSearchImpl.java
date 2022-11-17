package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.QAccount;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class AccountSearchImpl extends QuerydslRepositorySupport implements AccountSearch {
    public AccountSearchImpl() {
        super(Account.class);
    }

    @Override
    public Page<Account> searchList(Pageable pageable) {
        QAccount account = QAccount.account;
        JPQLQuery<Account> query = from(account); // ... from account ...

        this.getQuerydsl().applyPagination(pageable, query);

        List<Account> list = query.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
