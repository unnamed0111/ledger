package com.portfolio.ledger.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sample extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 500, nullable = false)
    private String text;

    public void change(final String title, final String text) {
        this.title = title;
        this.text = text;
    }
}
