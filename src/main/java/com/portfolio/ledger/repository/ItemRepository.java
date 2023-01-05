package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
