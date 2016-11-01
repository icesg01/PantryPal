package com.example.stan.testinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Second extends AppCompatActivity {

    ArrayList<String> newList = new ArrayList<>();

    ArrayAdapter adapter;
    ListView newListView;
    DataBaseHelperClass myDbHelper;
    String name;
    boolean inserted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_list_groceries);

        newListView = (ListView) findViewById(R.id.mobile_first);
        myDbHelper = new DataBaseHelperClass(this);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Pantry Pal </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

         name = i.getStringExtra("name");
      String price =  i.getStringExtra("price");
        String fullPath = name + "\n price:" + price;
        String[] splitMe = {name,price};
        String n = "";
        String w = "";


        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to creates database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }





        if(savedInstanceState != null) {

            inserted = savedInstanceState.getBoolean("entered");
        }

else {

            inserted = false;


        }





        if(inserted == false) {
            myDbHelper.insertProduce(name,price);

            inserted = true;
        }

        myDbHelper.getUserNameFromDB(newList);
            adapter = new myAdapter(this,R.layout.activity_list_ttem,newList,myDbHelper,splitMe);

            newListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
adapter.getItem(0);

        ImageButton newImage = (ImageButton)findViewById(R.id.imageButton);

        newImage.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                Intent i = new Intent(Second.this,listGroceries.class);

                startActivity(i);


            }
        });



        newListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);












//        inputz.setBackgroundResource(R.drawable.backtext);





    }



    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putStringArrayList("newList",newList);
        outState.putBoolean("entered",inserted);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        newList =(ArrayList<String>) savedInstanceState.getStringArrayList("newList");
        inserted = savedInstanceState.getBoolean("entered");




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

               Intent newIntent = new Intent(Second.this,listGroceries.class);
                startActivity(newIntent);
                return true;




            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public String getName(){
        return name;
    }






}
