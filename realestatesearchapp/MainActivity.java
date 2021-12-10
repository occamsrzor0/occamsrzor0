package com.example.realestatesearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatesearchapp.Parser.Filter;
import com.example.realestatesearchapp.Parser.Parser;
import com.example.realestatesearchapp.Parser.UnexpectedTokenException;
import com.example.realestatesearchapp.Parser.UnrecognizedTokenException;
import com.example.realestatesearchapp.tree.NullKeyException;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Property> items;
    Button s_button;
    EditText search;
    String word;
    Filter filter0 = null;
    Toolbar toolbar;
    private  PropertyCollection total_properties;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Generator generator = new Generator();
        generator.createCollection();
        File fileXML = new File(getApplicationContext().getFilesDir(), "data.xml");
        File fileJSON = new File(getApplicationContext().getFilesDir(), "data.json");
        File fileBespoke = new File(getApplicationContext().getFilesDir(), "data.bin");
        this.total_properties = new PropertyCollection(generator.generated_properties);
        this.total_properties.saveToXMLFile(fileXML);
        this.total_properties.saveToJsonFile(fileJSON);
        this.total_properties.saveToBespokeFile(fileBespoke);
        DataManager dataManager = new DataManager();
        PropertyCollection loadedCollectionXML = PropertyCollection.loadFromXMLFile(fileXML);
        PropertyCollection loadedCollectionJSON = PropertyCollection.loadFromXMLFile(fileJSON);
        PropertyCollection loadedCollectionBespoke = PropertyCollection.loadFromXMLFile(fileBespoke);
        try {
            dataManager.dataTree = dataManager.loadData(loadedCollectionXML.propertycollection);
        } catch (NullKeyException e) {
            e.printStackTrace();
        }
        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        s_button = findViewById(R.id.search_button);
        search = findViewById(R.id.t_search);
        items = new ArrayList<>();
        //items.add(total_properties.propertycollection.get(0));
        for (Property p:generator.generated_properties){
            items.add(p);
        }


        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word = search.getText().toString();
                if(!word.isEmpty()) {
                    try {
                        filter0 = Parser.parse(word);
                    } catch (UnrecognizedTokenException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error 01 : The following grammar is not exist", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (UnexpectedTokenException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error 02 : Grammar structure is not correct", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (StringIndexOutOfBoundsException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error 06 : This is not correct grammar", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    List<Property> a;
                    try {
                        a = dataManager.getPropertyListByFilter(filter0);
                        items.clear();
                        adapter.notifyDataSetChanged();
                        ///for loop
                        for (Property p : a)
                            items.add(p);
                        Collections.sort(items, (property, t1) -> {
                            if (property.getPrice() < t1.getPrice())
                                return -1;
                            else
                                return 0;
                        });
                    } catch (NullKeyException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error 03 : Please enter the grammar", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (NullPointerException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error 04 : Please enter the grammar again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Error 05 : Ops! Empty Input", Toast.LENGTH_LONG).show();
            }});




        recyclerView = findViewById(R.id.cycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);



    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}