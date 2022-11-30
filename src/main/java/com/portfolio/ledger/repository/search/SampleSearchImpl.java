package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.QSample;
import com.portfolio.ledger.domain.Sample;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class SampleSearchImpl extends QuerydslRepositorySupport implements SampleSearch {

    public SampleSearchImpl() {
        super(Sample.class);
    }

    // 동적으로 SQL 만들고 실행하기
    @Override
    public Page<Sample> search1(Pageable pageable) {
        QSample sample = QSample.sample; // Q도메인 객체

        JPQLQuery<Sample> query = from(sample); // select ... from sample

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // and/or 과 같은 연산자를 묶을 떄 사용(QueryDsl에서 지원함)
        booleanBuilder.or(sample.text.contains("8"));
        booleanBuilder.or(sample.text.contains("3"));
        query.where(booleanBuilder); // where (... or ...)

        query.where(sample.bno.gt(40L)); // where ~ and ... > ...

//        query.where(sample.text.contains("1")); // where text like ...
        this.getQuerydsl().applyPagination(pageable, query); // orderby ... limit ... 부모클래스인 QuerydslRepositorySupport가 지원하는 기능

        List<Sample> list = query.fetch(); // JPQLQuery 실행
        long count = query.fetchCount(); // JQPLQuery 카운트 실행

        return null;
    }

    @Override
    public Page<Sample> searchFiltered(String[] types, String keyword, Pageable pageable) {

        // 밑의 두가지 형식은 거의 고정
        QSample sample = QSample.sample;
        JPQLQuery<Sample> query = from(sample); // select ... from sample

        if((types != null && types.length > 0) && keyword != null) { // 해당하지 않으면 조건문 추가 안함
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types) {
                switch(type) {
                    case "t":
                        booleanBuilder.or(sample.text.contains(keyword));
                        break;
                        // 나중에 속성이 추가되면 더 추가할 것
                } // switch
            } // for

            query.where(booleanBuilder);
        } // if

        this.getQuerydsl().applyPagination(pageable, query);

        List<Sample> list = query.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Sample> searchAll(Pageable pageable) {
        QSample sample = new QSample("test_sample");
        JPQLQuery<Sample> query = from(sample); // select ... from sample test_sample

//        query.limit(pageable.getPageSize());
//        query.offset(pageable.getOffset());
//
//        if (pageable.getSort().isSorted()) {
//            List<OrderSpecifier> orders = new ArrayList<>();
//
//            pageable.getSort().forEach(order -> {
//                StringPath column = Expressions.stringPath(sample, order.getProperty());
//                orders.add(order.getDirection().isAscending() ? column.asc() : column.desc());
//            });
//
//            query.orderBy(orders.stream().toArray(OrderSpecifier[]::new));
//        }

        getQuerydsl().applyPagination(pageable, query); // 위 주석 코드와 같음

        log.info("*********SAMPLE QUERY**********");
        log.info(query.toString());

        List<Sample> list = query.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
