package com.example.realestatesearchapp;

import com.example.realestatesearchapp.Parser.Filter;
import com.example.realestatesearchapp.Parser.Parser;
import com.example.realestatesearchapp.Parser.UnexpectedTokenException;
import com.example.realestatesearchapp.Parser.UnrecognizedTokenException;
import com.example.realestatesearchapp.tree.BinarySearchTree;
import com.example.realestatesearchapp.tree.NullKeyException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataManager {
    private static final Type PROPERTY_TYPE = new TypeToken<List<Property>>() {
    }.getType();
    public static BinarySearchTree<Integer, List<Property>> dataTree;


    //ListingType->PropertyType->Location->NumberOfBedroom->Price->NumberOfProperty
    public static BinarySearchTree<Integer, List<Property>> loadData(List<Property> list) throws NullKeyException {
        BinarySearchTree<Integer, List<Property>> bst = new BinarySearchTree<>();
       // Gson gson = new Gson();
       // List<Property> list = gson.fromJson(new JsonReader(fileReader), PROPERTY_TYPE);
        for (Property p : list) {
            addProperty(p, bst);
        }
        return bst;
    }

    public static void addProperty(Property property, BinarySearchTree<Integer, List<Property>> bst) throws NullKeyException {
        if (bst.search(property.getPrice()) == null) {
            List<Property> list = new ArrayList<>();
            list.add(property);
            bst.insert(property.getPrice(), list);
        } else {
            bst.search(property.getPrice()).add(property);
        }
    }

    public static void search() throws UnrecognizedTokenException, UnexpectedTokenException, NullKeyException {
        String mockSearchSentence0 = "(bedroom:<7,>3) | [listing_type:NL,ML] ";
        String mockSearchSentence1 = "(bedroom:<7,>3)";
        String mockSearchSentence2 = "(bedroom:=5) | (price:<1000) | [listing_type:OL]|[property_type:TOWNH,TSHAR] | [location:\"Smithville\",\"Ochlocknee\"]";
        Filter filter0 = Parser.parse(mockSearchSentence1);
        Filter filter1 = Parser.parse(mockSearchSentence1);
        Filter filter2 = Parser.parse(mockSearchSentence2);

        List<Property> properties0 = getPropertyListByFilter(filter0);
        List<Property> properties1 = getPropertyListByFilter(filter1);
        List<Property> properties2 = getPropertyListByFilter(filter2);
        System.out.println(properties0.toString());
        System.out.println(properties2.toString());
        System.out.println(properties2);
    }


    public static List<Property> getPropertyListByFilter(Filter filter) throws NullKeyException {
        if(filter.getPriceEqual()!=null){
            return dataTree.search(filter.getPriceEqual()).stream().filter(filter::filter).collect(Collectors.toList());
        }else {
            int l=filter.getPriceL()==null?Integer.MIN_VALUE:filter.getPriceL();
            int r=filter.getPriceR()==null?Integer.MAX_VALUE:filter.getPriceR();
                return dataTree.between(l,r).stream().flatMap(List::stream).filter(filter::filter).collect(Collectors.toList());
        }
    }

    private static void insertProperty(List<Property> list, Map<Integer, Integer> map, PropertyType propertyType, ListingType listingType, int bedroom, String location,Boolean promoted) {
        map.forEach((price, count) -> {
            for (int i = 0; i < count; i++) {
                list.add(new Property(price, bedroom, location, propertyType, listingType,promoted));
            }
        });
    }
}
