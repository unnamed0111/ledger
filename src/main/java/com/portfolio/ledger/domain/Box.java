package com.portfolio.ledger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    @Id
    @Column(name = "bno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;
}
