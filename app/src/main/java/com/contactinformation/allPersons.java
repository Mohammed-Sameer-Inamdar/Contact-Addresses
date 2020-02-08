package com.contactinformation;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class allPersons extends AppCompatActivity {

    MyDataBase handler;
    MainActivity mine;
    ArrayList<String> personList, phnlist;
    ArrayAdapter<String> arrayAdapter;
    private ListView plist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_persons);
        plist = findViewById(R.id.listall);

        personList = new ArrayList<String>();
        phnlist = new ArrayList<String>();

        handler = new MyDataBase(this);
        personList = handler.getData();
        arrayAdapter = new ArrayAdapter<String>(
                allPersons.this,
                R.layout.contact_items_listview,
                R.id.textView, personList
        );
        plist.setAdapter(arrayAdapter);

    }

    public void listall(View view) {
        personList = handler.getData();
        arrayAdapter = new ArrayAdapter<String>(
                allPersons.this,
                R.layout.contact_items_listview,
                R.id.textView, personList
        );
        plist.setAdapter(arrayAdapter);

    }
}
