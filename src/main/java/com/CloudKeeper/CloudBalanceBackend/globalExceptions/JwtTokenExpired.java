package com.CloudKeeper.CloudBalanceBackend.globalExceptions;

public class JwtTokenExpired extends RuntimeException {
    public JwtTokenExpired(String message) {
        super(message);
    }
}

