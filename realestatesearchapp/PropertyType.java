package com.example.realestatesearchapp;
import java.io.Serializable;

public enum PropertyType implements Serializable {

        TOWNH("Townhouse"),
        APTLO("Apartment (Lowrise)"),
        APTHI("Apartment (High Rise)"),
        DETCO("Detached Condominium"),
        TSHAR("Timeshare");
        String property_type_name;
        static String[] property_types={"Townhouse","Apartment (Lowrise)","Apartment (High Rise)","Detached Condominium","Timeshare"};

    public static String[] getProperty_types() {
        return property_types;
    }

    PropertyType(String name) {
            this.property_type_name = name;
        }

        /**
         * Displays this genre as a human-readable name.
         *
         * @return The name of this genre type.
         */
        public String display() {
            return this.property_type_name;
        }
    }


