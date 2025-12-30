package com.CloudKeeper.CloudBalanceBackend.globalExceptions;

public class InvalidEmailidOrPassword extends RuntimeException {
    public InvalidEmailidOrPassword(String message) {
        super(message);
    }
}
