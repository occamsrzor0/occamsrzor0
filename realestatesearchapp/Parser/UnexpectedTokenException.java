package com.example.realestatesearchapp.Parser;

public class UnexpectedTokenException extends Exception {
    private String token;

    public UnexpectedTokenException(String token) {
        this.token = token;
    }

    @Override
    public String getMessage() {
        return "Unexpected token found: " + this.token;
    }
}
