package com.portfolio.ledger.exception;

public class NotOwnerException extends Exception {
    public NotOwnerException() {
        this("NOT OWNER");
    }

    public NotOwnerException(String message) {
        super(message);
    }
}

