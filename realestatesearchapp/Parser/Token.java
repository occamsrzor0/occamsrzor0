package com.example.realestatesearchapp.Parser;

public class Token {
    public TokenType tokenType;
    public int intValue;
    public String stringValue;

    public Token(TokenType tokenType, int value) {
        this.tokenType = tokenType;
        this.intValue = value;
    }

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.stringValue = value;
    }

    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
