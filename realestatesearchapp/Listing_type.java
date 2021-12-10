package com.example.realestatesearchapp;

import java.io.Serializable;

public enum Listing_type implements Serializable {
    EL("Exclusive Listing"),
    ERSL("Exclusive Right to Sell Listing"),
    OL("Open Listing"),
    NL("Net listing"),
    ML("Multiple Listing");

    String type_name;



    static String[] listing_types={"Exclusive Listing","Exclusive Right to Sell Listing","Open Listing","Net listing","Multiple Listing"};
    Listing_type(String name) {
        this.type_name = name;
    }

    public static String[] getListing_types() {
        return listing_types;
    }
    /**
     * Displays this genre as a human-readable name.
     *
     * @return The name of this genre type.
     *
     */
    public String display() {
        return this.type_name;
    }
    
}
