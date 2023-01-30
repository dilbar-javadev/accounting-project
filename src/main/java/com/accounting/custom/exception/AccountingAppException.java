package com.accounting.custom.exception;

public class AccountingAppException extends RuntimeException{  // Ask why?

    public AccountingAppException(String message){
        super(message);
    }
}
