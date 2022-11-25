package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.QAccount;
import com.portfolio.ledger.domain.QReply;
import com.portfolio.ledger.dto.AccountDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
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

    @Override
    public Map<String, Double> searchTotalPrice() {
        QAccount account = QAccount.account;

        // 매출 총합 구하기
        Double totalSalesPrice = getQuerydsl().createQuery()
                .select(account.price.multiply(account.amount).sum())
                .from(account)
                .where(account.snp.eq(AccountDTO.SYMBOL_SALES))
                .fetchOne();

        // 지출 총합 구하기
        Double totalPurchasePrice = getQuerydsl().createQuery()
                .select(account.price.multiply(account.amount).sum())
                .from(account)
                .where(account.snp.eq(AccountDTO.SYMBOL_PURCHASE))
                .fetchOne();

        Map<String, Double> result = new HashMap<>();
        result.put("totalSalesPrice", totalSalesPrice);
        result.put("totalPurchasePrice", totalPurchasePrice);

        return result;
    }
}
