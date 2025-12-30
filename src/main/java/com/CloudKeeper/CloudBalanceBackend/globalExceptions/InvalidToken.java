package com.CloudKeeper.CloudBalanceBackend.globalExceptions;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String message) {
        super(message);
    }
}
