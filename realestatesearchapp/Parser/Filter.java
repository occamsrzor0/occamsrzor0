package com.example.realestatesearchapp.Parser;

import com.example.realestatesearchapp.ListingType;
import com.example.realestatesearchapp.Property;
import com.example.realestatesearchapp.PropertyType;

import java.util.HashSet;
import java.util.Set;

public class Filter {
    public void setPriceL(Integer priceL) {
        this.priceL = priceL;
    }

    public void setPriceR(Integer priceR) {
        this.priceR = priceR;
    }

    public void setBedroomL(Integer bedroomL) {
        this.bedroomL = bedroomL;
    }

    public void setBedroomR(Integer bedroomR) {
        this.bedroomR = bedroomR;
    }


    public void setPriceEqual(Integer priceEqual) {
        this.priceEqual = priceEqual;
    }

    public void setBedroomEqual(Integer bedroomEqual) {
        this.bedroomEqual = bedroomEqual;
    }

    public Integer getPriceL() {
        return priceL;
    }

    public Integer getPriceR() {
        return priceR;
    }

    public Integer getPriceEqual() {
        return priceEqual;
    }

    public Integer getBedroomL() {
        return bedroomL;
    }

    public Integer getBedroomR() {
        return bedroomR;
    }

    public Integer getBedroomEqual() {
        return bedroomEqual;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public Set<PropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public Set<ListingType> getListingTypes() {
        return listingTypes;
    }

    Integer priceL;
    Integer priceR;
    Integer priceEqual;
    Integer bedroomL;
    Integer bedroomR;
    Integer bedroomEqual;
    Set<String> locations = new HashSet<>();
    Set<PropertyType> propertyTypes = new HashSet<>();
    Set<ListingType> listingTypes = new HashSet<>();

    public void addLocation(final String location) {
        this.locations.add(location);
    }

    public void addPropertyType(final PropertyType property_type) {
        this.propertyTypes.add(property_type);
    }

    public void addListingType(final ListingType listingType) {
        this.listingTypes.add(listingType);
    }
    public boolean filter(Property property){
        if(bedroomEqual!=null){
            if(property.getNumber_of_bedroom()!=bedroomEqual){
                return false;
            }
        }else {
            if(bedroomL!=null){
                if(property.getNumber_of_bedroom()<bedroomL){
                    return false;
                }
            }
            if(bedroomR!=null){
                if(property.getNumber_of_bedroom()>bedroomR){
                    return false;
                }
            }
        }

        if(this.propertyTypes.size()>0 && !propertyTypes.contains(property.getProperty_type())){
            return false;
        }

        if(this.listingTypes.size()>0 && !listingTypes.contains(property.getListing_type())){
            return false;
        }
        if(this.locations.size()>0 && !locations.contains(property.getLocation())){
            return false;
        }
        return true;
    }

}
