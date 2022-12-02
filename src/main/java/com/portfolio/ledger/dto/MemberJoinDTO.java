package com.portfolio.ledger.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
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
