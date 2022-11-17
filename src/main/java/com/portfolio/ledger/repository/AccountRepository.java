package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.repository.search.AccountSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountSearch {

}
