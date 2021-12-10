package com.example.realestatesearchapp.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    public Tokenizer(String search_sentence) {
        this.search_sentence = search_sentence;
        this.pos = 0;
    }
    /*
    search_sentence = term, { '|' ,term};

    term = compare_term|include_term

    compare_term = '(', num_field, ':', comparison|{',',comparison},')'
    include_term = property_type_term|listing_type_term|location_term
    property_type='[',','property_type:', property_type,{',',property_type},']'
    listing_type='[',','listing_type:', listing_type,{',',listing_type},']'
    location='[',','location:', location,{',',location},']'

    location='"',character,{character|' '},'"'
    character='a'|'b'|'c'|'d'|'e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'m'|'n'|'o'|'p'|'q'|'r'|'s'|'t'|'u'|'v'|'w'|'x'|'y'|'z';

    comparison= '>' | '<' | '=', integer;

    num_field = 'bedroom' | 'price'
    property_type = 'TOWNH' | 'TSHAR' | 'APTHI' | 'APTLO' | 'DETCO'
    listing_type = 'NL' | 'ML' | 'OL' | 'EL' | 'ERSL'


    natural = '0' | (nzdigit, { digit }) ;
    nzdigit = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' ; digit = '0' | nzdigit ;
    integer = '0' | (['-'], nzdigit, { digit }) ;



    (bedroom:<5,>3,=6) | [property_type:TOWNH,TSHAR]|

    * */

    String search_sentence;
    int pos;

    Map<Character, TokenType> tokenTypeMap = new HashMap<Character, TokenType>() {{
        put('|', TokenType.OR);
        put(',', TokenType.COMMA);
        put('>', TokenType.LARGER);
        put('<', TokenType.LESS);
        put('=', TokenType.EQUALS);
        put('[', TokenType.SQ_BRAC_LEFT);
        put(']', TokenType.SQ_BRAC_RIGHT);
        put('(', TokenType.BRAC_LEFT);
        put(')', TokenType.BRAC_RIGHT);
        put(':', TokenType.COLON);
    }};

    List<String> words = new ArrayList<String>() {{
        add("bedroom");
        add("price");
        add("TOWNH");
        add("TSHAR");
        add("APTHI");
        add("APTLO");
        add("DETCO");
        add("NL");
        add("ML");
        add("OL");
        add("EL");
        add("ERSL");
        add("property_type");
        add("listing_type");
        add("location");
    }};

    public boolean hasNext() {
        for (int i = pos; i < search_sentence.length(); i++) {
            char c = search_sentence.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    public Token next() throws UnrecognizedTokenException {
        String token = "";
        boolean stringToken = false;
        for (; pos <= search_sentence.length(); pos++) {
            char c = search_sentence.charAt(pos);
            if (stringToken) {
                if (c == '"') {
                    pos++;
                    break;
                }
                token += c;
                continue;
            }
            if (Character.isWhitespace(c)) {
                if (token.equals(""))
                    continue;
                else
                    break;
            }

            if (c == '"') {
                if (!token.equals("")) {
                    break;
                }
                stringToken = true;
            }

            if (tokenTypeMap.containsKey(c)) {
                if (token.equals("")) {
                    pos++;
                    return new Token(tokenTypeMap.get(c));
                } else
                    break;
            }

            if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_') {
                token = token + c;
            }

        }
        if(stringToken){
            return new Token(TokenType.STRING,token);
        }
        else if (words.contains(token)) {
            return new Token(TokenType.valueOf(token.toUpperCase()));
        } else if (isNumerical(token)) {
            return new Token(TokenType.INT, Integer.parseInt(token));
        } else {
            throw new UnrecognizedTokenException(token);
        }
    }

    private boolean isNumerical(String token) {
        for (int i = 0; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
