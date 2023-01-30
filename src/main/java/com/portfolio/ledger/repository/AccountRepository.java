package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.repository.search.AccountSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountSearch {
    @EntityGraph(attributePaths = "member")
    @Query("SELECT a FROM Account a WHERE a.ano = :ano")
    Optional<Account> findWithMember(Long ano);
}
