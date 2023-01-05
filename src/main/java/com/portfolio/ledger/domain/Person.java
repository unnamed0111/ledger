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
public class Person {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private Integer age;
}
