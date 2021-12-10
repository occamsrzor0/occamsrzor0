package com.example.realestatesearchapp.Parser;

import com.example.realestatesearchapp.ListingType;
import com.example.realestatesearchapp.PropertyType;

import java.util.*;

public class Parser {

    public static Filter parse(String sentence) throws UnrecognizedTokenException, UnexpectedTokenException {
        Tokenizer tokenizer=new Tokenizer(sentence);
        Filter filter=new Filter();
        while (true){
            parseTerm(tokenizer,filter);
            if(tokenizer.hasNext()){
                Token or=tokenizer.next();
                if(or.tokenType!=TokenType.OR){
                    throw new UnexpectedTokenException(or.tokenType.toString());
                }
            }else {
                break;
            }
        }
        return filter;
    }

    private static void parseTerm(Tokenizer tokenizer, Filter filter) throws UnrecognizedTokenException, UnexpectedTokenException {
        if (tokenizer.hasNext()) {
            Token token = tokenizer.next();
            if (token.tokenType == TokenType.BRAC_LEFT) {
                parseCompareTerm(tokenizer, filter);
            } else if (token.tokenType == TokenType.SQ_BRAC_LEFT) {
                parseIncludeTerm(tokenizer, filter);
            } else {
                throw new UnexpectedTokenException(token.tokenType.toString());
            }
        }
    }

    private static void parseCompareTerm(Tokenizer tokenizer, Filter filter) throws UnrecognizedTokenException, UnexpectedTokenException {
        Token type = tokenizer.next();
        Token colon = tokenizer.next();

        if (type.tokenType == TokenType.BEDROOM && colon.tokenType == TokenType.COLON) {
            Map<TokenType, Integer> comparison = parseComparison(tokenizer);
            filter.setBedroomL(comparison.getOrDefault(TokenType.LARGER, null));
            filter.setBedroomR(comparison.getOrDefault(TokenType.LESS, null));
            filter.setBedroomEqual(comparison.getOrDefault(TokenType.EQUALS, null));
        } else if (type.tokenType == TokenType.PRICE && colon.tokenType == TokenType.COLON) {
            Map<TokenType, Integer> comparison = parseComparison(tokenizer);
            filter.setPriceL(comparison.getOrDefault(TokenType.LARGER, null));
            filter.setPriceR(comparison.getOrDefault(TokenType.LESS, null));
            filter.setPriceEqual(comparison.getOrDefault(TokenType.EQUALS, null));
        } else {
            throw new UnexpectedTokenException(type.tokenType + ", " + colon.tokenType);
        }
    }

    private static Map<TokenType, Integer> parseComparison(Tokenizer tokenizer) throws UnrecognizedTokenException, UnexpectedTokenException {
        Map<TokenType, Integer> map = new HashMap<>();
        while (true) {
            Token sign = tokenizer.next();
            Token value = tokenizer.next();
            if (value.tokenType != TokenType.INT) {
                throw new UnexpectedTokenException(value.tokenType.toString());
            }
            switch (sign.tokenType) {
                case LARGER:
                    map.put(TokenType.LARGER, value.intValue);
                    break;
                case EQUALS:
                    map.put(TokenType.EQUALS, value.intValue);
                    break;
                case LESS:
                    map.put(TokenType.LESS, value.intValue);
                    break;
                default:
                    throw new UnexpectedTokenException(sign.tokenType.toString());

            }
            Token after = tokenizer.next();
            if (after.tokenType == TokenType.BRAC_RIGHT) {
                return map;
            }
        }
    }

    private static void parseIncludeTerm(Tokenizer tokenizer, Filter filter) throws UnrecognizedTokenException, UnexpectedTokenException {
        Token type = tokenizer.next();
        Token colon = tokenizer.next();

        if (type.tokenType == TokenType.LISTING_TYPE && colon.tokenType == TokenType.COLON) {
            List<Token> list = parseInclude(tokenizer);
            list.forEach(token -> filter.addListingType(ListingType.valueOf(token.tokenType.name())));
        } else if (type.tokenType == TokenType.PROPERTY_TYPE && colon.tokenType == TokenType.COLON) {
            List<Token> list = parseInclude(tokenizer);
            list.forEach(token -> filter.addPropertyType(PropertyType.valueOf(token.tokenType.name())));
        } else if (type.tokenType == TokenType.LOCATION && colon.tokenType == TokenType.COLON) {
            List<Token> list = parseInclude(tokenizer);
            list.forEach(token -> filter.addLocation(token.stringValue));
        } else {
            throw new UnexpectedTokenException(type.tokenType + ", " + colon.tokenType);
        }
    }

    static Set<TokenType> includeTermValueType = new HashSet<TokenType>() {{
        add(TokenType.TOWNH);
        add(TokenType.TSHAR);
        add(TokenType.APTHI);
        add(TokenType.APTLO);
        add(TokenType.DETCO);

        add(TokenType.NL);
        add(TokenType.ML);
        add(TokenType.OL);
        add(TokenType.EL);
        add(TokenType.ERSL);
        add(TokenType.STRING);
    }};

    private static List<Token> parseInclude(Tokenizer tokenizer) throws UnrecognizedTokenException, UnexpectedTokenException {
        List<Token> list = new ArrayList<>();
        while (true) {
            Token value = tokenizer.next();
            if (!includeTermValueType.contains(value.tokenType)) {
                throw new UnexpectedTokenException(value.tokenType.toString());
            }
            list.add(value);

            Token after = tokenizer.next();
            if (after.tokenType == TokenType.SQ_BRAC_RIGHT) {
                return list;
            }
        }
    }
}
