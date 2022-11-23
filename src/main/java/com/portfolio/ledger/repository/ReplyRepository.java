package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select r from Reply r where r.account.ano = :ano")
    Page<Reply> listOfAccount(Long ano, Pageable pageable);
}
