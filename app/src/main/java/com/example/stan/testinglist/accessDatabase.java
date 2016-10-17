package com.example.stan.testinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Stan on 10/14/2016.
 */
public class accessDatabase extends AppCompatActivity {



protected void onCreate(Bundle savedInstanceState){

    super.onCreate(savedInstanceState);

    setContentView(R.layout.layout);





}

    public void onLogin(View view){


        String bob = "sice10";
        String password = "sice10";
        String type = "login";
        RemoteDataBase dataBase = new RemoteDataBase(this);


        dataBase.execute(type,bob,password);




    }



}
