package com.example.realestatesearchapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PropertyCollection {
    List<Property> propertycollection;

    public PropertyCollection() {

        this.propertycollection = new ArrayList<Property>();
    }
    public PropertyCollection(List<Property> propertycollection) {
        this.propertycollection = propertycollection;
    }

    public List<Property> getPropertycollection() {
        return propertycollection;
    }


    public void setPropertycollection(List<Property> propertycollection) {
        this.propertycollection = propertycollection;
    }
    public String toString() {
        return this.propertycollection.stream().map(book -> " - " + book.display() + "\n").collect(Collectors.joining());
    }
    public void saveToBespokeFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Property a : propertycollection) {
                String str = a.display();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveToJsonFile(File file){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter fw = new FileWriter(file)){
            gson.toJson(propertycollection, fw);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void saveToXMLFile(File file)
    {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder(); //create a new instance of DocumentBuilder
            Document d = db.newDocument(); //obtain new instance of a DOM document

            Element rootElement = d.createElement("Properties");//<People>
            d.appendChild(rootElement); //append the root to the document

            for(Property property : propertycollection)
            {
                Element propertyElement = d.createElement("Property");//

                Element priceElement = d.createElement("Price");
                priceElement.appendChild(d.createTextNode(Integer.toString(property.getPrice())));
                propertyElement.appendChild(priceElement);


                Element number_of_bedroomsElement = d.createElement("NumberOfBedrooms");//<FirstName> ... </FirstName>
                number_of_bedroomsElement.appendChild(d.createTextNode(Integer.toString(property.getNumber_of_bedroom())));//<FirstName> here goes firstname </FirstName>
                propertyElement.appendChild(number_of_bedroomsElement);//<Person id="1"><FirstName> here goes firstname </FirstName></Person>

                Element locationElement = d.createElement("Location");
                locationElement.appendChild(d.createTextNode(property.getLocation()));
                propertyElement.appendChild(locationElement);

                Element propertyTypeElement = d.createElement("PropertyType");
                propertyTypeElement.appendChild(d.createTextNode(property.getProperty_type().display()));
                propertyElement.appendChild(propertyTypeElement);

                Element listingTypeElement = d.createElement("ListingType");
                listingTypeElement.appendChild(d.createTextNode(property.getListing_type().display()));
                propertyElement.appendChild(listingTypeElement);

                Element promotedElement = d.createElement("Promoted");
                promotedElement.appendChild(d.createTextNode(Boolean.toString(property.getPromoted())));
                propertyElement.appendChild(promotedElement);

                rootElement.appendChild(propertyElement);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(d);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static PropertyCollection loadFromBespokeFile(File file) {
        ArrayList<Property> properties = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String record;

            while ((record = br.readLine()) != null) {
                String[] tokens = record.split(",");
                int price = Integer.parseInt(tokens[0].split("price=")[1]);
                int number_of_bedrooms = Integer.parseInt(tokens[1].split(" number_of_bedrooms=")[1]);
                String location = tokens[2].split("'")[1];
                PropertyType property_type = checkProperty_type(tokens[3].split(" property_type=")[1]);
                ListingType listing_type = checkListing_type(tokens[4].split(" listing_type=")[1]);
                Boolean promoted = Boolean.parseBoolean(tokens[5].split("promoted=")[1].replace("}",""));
                Property book_loaded = new Property(price, number_of_bedrooms, location, property_type,listing_type,promoted);
                properties.add(book_loaded);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyCollection propertyCollection = new PropertyCollection(properties);
        return propertyCollection;
    }
    public static PropertyCollection loadFromJSONFile(File file) {

        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<ArrayList<Property>>(){}.getType();
        try{
            jsonReader = new JsonReader(new FileReader(file));
        }catch (Exception e) {
            e.printStackTrace();
        }
        PropertyCollection pCollection = new PropertyCollection( gson.fromJson(jsonReader, CUS_LIST_TYPE));
        return pCollection;
    }


    public static PropertyCollection loadFromXMLFile(File file)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //list
        ArrayList<Property> lp = new ArrayList<Property>();
        PropertyCollection pxml = new PropertyCollection();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(file);
            d.getDocumentElement().normalize();
            NodeList nl = d.getElementsByTagName("Property");

            for(int i = 0; i < nl.getLength(); i++)
            {
                Node n = nl.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE) {
                    Element element		= (Element) n;
                    int price 		= Integer.parseInt(element.getElementsByTagName("Price").item(0).getTextContent());
                    int number_of_bedrooms = Integer.parseInt(element.getElementsByTagName("NumberOfBedrooms").item(0).getTextContent());
                    String location 	= element.getElementsByTagName("Location").item(0).getTextContent();
                    PropertyType property_type = checkProperty_type(element.getElementsByTagName("PropertyType").item(0).getTextContent());
                    ListingType listing_type = checkListing_type(element.getElementsByTagName("ListingType").item(0).getTextContent());
                    Boolean promoted = Boolean.parseBoolean(element.getElementsByTagName("Promoted").item(0).getTextContent());
                    Property p 	= new Property(price,number_of_bedrooms,location,property_type,listing_type,promoted);
                    lp.add(p);
                    pxml = new PropertyCollection(lp);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return pxml;
    }


    public static ListingType checkListing_type(String str) {
        ListingType lt = null;
        switch (str){
            case"Exclusive Listing" :
                lt = ListingType.EL;
                break;
            case "Exclusive Right to Sell Listing" :
                lt = ListingType.ERSL;
                break;
            case "Open Listing" :
                lt = ListingType.OL;
                break;
            case "Net listing" :
                lt = ListingType.NL;
                break;
            case "Multiple Listing" :
                lt = ListingType.ML;
                break;
            default:
                break;
        }
        return lt;
    }
    public static PropertyType checkProperty_type(String str) {
        PropertyType pt =null;
        switch (str){
            case"Townhouse" :
                pt = PropertyType.TOWNH;
                break;
            case "Apartment (Lowrise)":
                pt = PropertyType.APTLO;
                break;
            case "Apartment (High Rise)" :
                pt = PropertyType.APTHI;
                break;
            case "Detached Condominium" :
                pt = PropertyType.DETCO;
                break;
            case "Timeshare" :
                pt = PropertyType.TSHAR;
                break;
            default:
                break;
        }
        return pt;
    }


}

