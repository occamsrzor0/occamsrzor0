package com.example.realestatesearchapp.Parser;

public class UnrecognizedTokenException extends Exception {
    private String token;

    public UnrecognizedTokenException(String token) {
        this.token = token;
    }

    @Override
    public String getMessage() {
        return "Unrecognized token found: " + this.token;
    }
}
