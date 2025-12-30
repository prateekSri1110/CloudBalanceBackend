package com.CloudKeeper.CloudBalanceBackend.globalExceptions;

public class ExistingAccountException extends RuntimeException {
    public ExistingAccountException(String message) {
        super(message);
    }
}
