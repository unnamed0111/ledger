package com.portfolio.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDTO {

    @NotEmpty
    private String mid;

    @NotEmpty
    private String mpw;

    @Email
    private String email;

    private boolean del;
    private boolean social;
}
