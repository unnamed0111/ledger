package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.repository.search.MemberSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, MemberSearch {
    @EntityGraph(attributePaths = "roleSet")
    @Query("SELECT m FROM Member m WHERE m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);
}
