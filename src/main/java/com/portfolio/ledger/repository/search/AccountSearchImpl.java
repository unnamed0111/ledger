package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.QAccount;
import com.portfolio.ledger.domain.QReply;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.AccountSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
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
    public Page<AccountDTO> searchList(Pageable pageable, AccountSearchDTO searchDTO) {
        QAccount    account = QAccount.account;
        QReply      reply   = QReply.reply;

        JPQLQuery<Account> query = from(account); // ... from account ...

        // AccountSearchDTO 기반으로 검색할 내역 좁히기
        if(searchDTO != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            // 날짜 시작
            if(searchDTO.getDateStart() != null) {
                booleanBuilder.and(account.date.goe(searchDTO.getDateStart()));
            }

            // 날짜 끝
            if (searchDTO.getDateEnd() != null) {
                booleanBuilder.and(account.date.loe(searchDTO.getDateEnd()));
            }

            // 제목 키워드 포함
            if(searchDTO.getTitle() != null && searchDTO.getTitle().length() > 0) {
                booleanBuilder.and(account.title.contains(searchDTO.getTitle()));
            }

            // 내용 키워드 포함
            if(searchDTO.getContent() != null && searchDTO.getContent().length() > 0) {
                booleanBuilder.and(account.content.contains(searchDTO.getContent()));
            }

            // 작성자 키워드 포함
            if (searchDTO.getWriter() != null && searchDTO.getWriter().length() > 0) {
                booleanBuilder.and(account.member.mid.contains(searchDTO.getWriter()));
            }

            // 수량 이상
            if(searchDTO.getAmountStart() != null && searchDTO.getAmountStart() > 0) {
                booleanBuilder.and(account.amount.goe(searchDTO.getAmountStart()));
            }

            // 수량 이하
            if(searchDTO.getAmountEnd() != null && searchDTO.getAmountStart() > 0) {
                booleanBuilder.and(account.amount.loe(searchDTO.getAmountEnd()));
            }

            // 가격 이상
            if(searchDTO.getPriceStart() != null && searchDTO.getPriceStart() > 0) {
                booleanBuilder.and(account.price.goe(searchDTO.getPriceStart()));
            }

            // 가격 이하
            if (searchDTO.getPriceEnd() != null && searchDTO.getPriceEnd() > 0) {
                booleanBuilder.and(account.price.loe(searchDTO.getPriceEnd()));
            }

            query.where(booleanBuilder);
        }

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
                        account.member.mid.as("writer"),
                        account.snp,
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

//        // 매출 총합 구하기
//        Double totalSalesPrice = getQuerydsl().createQuery()
//                .select(account.price.multiply(account.amount).sum())
//                .from(account)
//                .where(account.snp.eq(AccountDTO.SYMBOL_SALES))
//                .fetchOne();
//
//        // 지출 총합 구하기
//        Double totalPurchasePrice = getQuerydsl().createQuery()
//                .select(account.price.multiply(account.amount).sum())
//                .from(account)
//                .where(account.snp.eq(AccountDTO.SYMBOL_PURCHASE))
//                .fetchOne();

        NumberExpression<Double> sumOfSales = new CaseBuilder()
                .when(account.snp.eq(AccountDTO.SYMBOL_SALES))
                .then(account.price.multiply(account.amount))
                .otherwise((Double) null);

        NumberExpression<Double> sumOfPurchase = new CaseBuilder()
                .when(account.snp.eq(AccountDTO.SYMBOL_PURCHASE))
                .then(account.price.multiply(account.amount))
                .otherwise((Double) null);

        JPQLQuery<Tuple> query = getQuerydsl().createQuery();
        query
                .select(sumOfSales.sum(), sumOfPurchase.sum())
                .from(account);

        Tuple result = query.fetchOne();

        Double totalSalesPrice = result.get(0, Double.class);
        Double totalPurchasePrice = result.get(1, Double.class);

        Map<String, Double> totalPrice = new HashMap<>();
        totalPrice.put("totalSalesPrice", totalSalesPrice != null ? totalSalesPrice : 0);
        totalPrice.put("totalPurchasePrice", totalPurchasePrice != null ? totalPurchasePrice : 0);

        return totalPrice;
    }
}
