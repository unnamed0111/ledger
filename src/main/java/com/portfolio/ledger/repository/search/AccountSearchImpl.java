package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.QAccount;
import com.portfolio.ledger.domain.QReply;
import com.portfolio.ledger.dto.AccountDTO;
import com.querydsl.core.types.Projections;
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
    public Page<AccountDTO> searchList(Pageable pageable) {
        QAccount    account = QAccount.account;
        QReply      reply   = QReply.reply;

        JPQLQuery<Account> query = from(account); // ... from account ...
        query.leftJoin(reply).on(reply.account.eq(account));

        query.groupBy(account);

        JPQLQuery<AccountDTO> dtoQuery = query.select(
                Projections.bean(
                        AccountDTO.class,
                        account.date,
                        account.ano,
                        account.title,
                        account.content,
                        account.price,
                        account.amount,
                        account.snp,
                        account.writer,
                        account.regDate,
                        reply.count().as("replyCount")
                )
        );

        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<AccountDTO> list = dtoQuery.fetch();
        Long count = dtoQuery.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
