package com.bankproject.hctbank.ExceptionHandling;

import lombok.Data;

@Data
public class CustomerIdNotFound extends Exception{
    private String reason;
    public CustomerIdNotFound(String msg,String reason)
    {
        super(msg);
        this.reason=reason;
    }
}
