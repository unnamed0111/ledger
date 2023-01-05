package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
