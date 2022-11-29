package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.QMember;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {

    public MemberSearchImpl() {
        super(Member.class);
    }

    @Override
    public Page<Member> search(Pageable pageable) {
        QMember member = QMember.member;
        JPQLQuery<Member> query = from(member);

        this.getQuerydsl().applyPagination(pageable, query).fetch();
        List<Member> list = query.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
