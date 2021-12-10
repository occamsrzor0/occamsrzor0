package com.example.realestatesearchapp;

import java.io.Serializable;
import java.util.Objects;

public class Property  implements Serializable {
    private int price;
    private int number_of_bedrooms;
    private String location;
    private PropertyType property_type;
    private ListingType listing_type;
    private Boolean promoted;

    public int getNumber_of_bedrooms() {
        return number_of_bedrooms;
    }

    public void setNumber_of_bedrooms(int number_of_bedrooms) {
        this.number_of_bedrooms = number_of_bedrooms;
    }

    public Boolean getPromoted() {
        return promoted;
    }

    public void setPromoted(Boolean promoted) {
        this.promoted = promoted;
    }

    public Property(int price, int number_of_bedroom, String location, PropertyType property_type, ListingType listing_type, Boolean promoted)  {
        this.price = price;
        this.number_of_bedrooms = number_of_bedroom;
        this.location = location;
        this.property_type = property_type;
        this.listing_type = listing_type;
        this.promoted = promoted;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber_of_bedroom() {
        return number_of_bedrooms;
    }

    public void setNumber_of_bedroom(int number_of_bedrooms) {
        this.number_of_bedrooms = number_of_bedrooms;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyType getProperty_type() {
        return property_type;
    }

    public void setProperty_type(PropertyType property_type) {
        this.property_type = property_type;
    }

    public ListingType getListing_type() {
        return listing_type;
    }

    public void setListing_type(ListingType listing_type) {
        this.listing_type = listing_type;
    }


    public String display() {
        return "Property{" +
                "price=" + price +
                ", number_of_bedrooms=" + number_of_bedrooms +
                ", location='" + location + '\'' +
                ", property_type=" + property_type.display() +
                ", listing_type=" + listing_type.display() +
                ", promoted="+ promoted.toString()+
                '}';
    }
    public String display_in_ui() {
        return "\n\n"+ property_type.display()+"\n\n"+
                "Price: $" + price +"\n\n"+
                "Number of Bedrooms: " + number_of_bedrooms +"\n\n"+
                "Location: " + location +"\n\n"+
                "listing Type: " + listing_type.display() +"\n\n"+
                "promoted="+ promoted.toString()
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return price == property.price &&
                number_of_bedrooms == property.number_of_bedrooms &&
                Objects.equals(location, property.location) &&
                property_type == property.property_type &&
                listing_type == property.listing_type &&
                Objects.equals(promoted, property.promoted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, number_of_bedrooms, location, property_type, listing_type, promoted);
    }
}
