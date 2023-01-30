package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.QMember;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {

    public MemberSearchImpl() {
        super(Member.class);
    }

    @Override
    public Page<Member> search(Pageable pageable) {
        QMember member = QMember.member;
        JPQLQuery<Member> query = from(member);

//        this.getQuerydsl().applyPagination(pageable, query).fetch(); // member 별칭변경이 적용이 안됨

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        if (pageable.getSort().isSorted()) {
            List<OrderSpecifier> orders = new ArrayList<>();

            pageable.getSort().forEach(order -> {
                StringPath column = Expressions.stringPath(member, order.getProperty());
                orders.add(order.getDirection().isAscending() ? column.asc() : column.desc());
            });

            query.orderBy(orders.stream().toArray(OrderSpecifier[]::new));
        }

        log.info("***********MEMBER QUERY***********");
        log.info(query);

        List<Member> list = query.fetch();
        Long count = query.fetchCount();

        log.info("-------------------END QUERY-------------------");

        return new PageImpl<>(list, pageable, count);
    }
}
