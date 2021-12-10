package com.example.realestatesearchapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Generator {
	private PropertyCollection gen_total_properties;
	ArrayList<Property> generated_properties = new ArrayList<>();
	public static Random random = new Random();
	public static String get(String[] array) {
		int rnd = random.nextInt(array.length);
		return array[rnd];
	}
	public int getNumber() {
		return random.nextInt();
	}
	private static PropertyType checkProperty_type(String str) {
		PropertyType pt =null;
		switch (str){
			case"TOWNH" :
				pt = PropertyType.TOWNH;
				break;
			case "APTLO":
				pt = PropertyType.APTLO;
				break;
			case "APTHI" :
				pt = PropertyType.APTHI;
				break;
			case "DETCO" :
				pt = PropertyType.DETCO;
				break;
			case "TSHAR" :
				pt = PropertyType.TSHAR;
				break;
			default:
				break;
		}
		return pt;
	}
	private static ListingType checkListing_type(String str) {
		ListingType lt = null;
		switch (str){
			case"EL" :
				lt = ListingType.EL;
				break;
			case "ERSL" :
				lt = ListingType.ERSL;
				break;
			case "OL" :
				lt = ListingType.OL;
				break;
			case "NL" :
				lt = ListingType.NL;
				break;
			case "ML" :
				lt = ListingType.ML;
				break;
			default:
				break;
		}
		return lt;
	}
	public static int getNumberBetween(final int min, final int max) {

		if (max < min) {
			throw new IllegalArgumentException(String.format("Minimum must be less than minimum (min=%d, max=%d)", min, max));
	        }
		if (max == min) {
			return min;
	        }

		return min + random.nextInt(max - min);
	}

	public String getLocation(){
	 	return get(Location_values.getCities());
		  }

	public PropertyType getPropertyType(){
		return PropertyCollection.checkProperty_type(get(PropertyType.getProperty_types()));
	}

	public ListingType getListingType(){
		return PropertyCollection.checkListing_type(get(ListingType.getListing_types()));
	}
	public Boolean getRandomBoolean(){
		Random randombool = new Random();
		// get next next boolean value
		boolean value = randombool.nextBoolean();
		return value;
	}
/// createcollection() class will create 1000 data instances that update the generated_properties of the Generator class.
	public void createCollection() {
		Generator generator1 = new Generator();
		int counter =0;
		while(counter<1000){
			generated_properties.add(new Property(getNumberBetween(50, 2000),getNumberBetween(0, 10),generator1.getLocation(),generator1.getPropertyType(),generator1.getListingType(),generator1.getRandomBoolean()));
			counter++;
		}
//sorting function that let promoted properties get displayed first
		Collections.sort(this.generated_properties, new Comparator<Property>(){
			@Override
			public int compare(Property o1, Property o2) {
				int b1 = o1.getPromoted() ? 1 : 0;
				int b2 = o2.getPromoted() ? 1 : 0;
				return b2 - b1;
			}
		});
		this.gen_total_properties = new PropertyCollection(generated_properties);
	}

   
}
