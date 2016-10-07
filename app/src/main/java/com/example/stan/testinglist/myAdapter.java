package com.example.stan.testinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 9/17/2016.
 */
public class myAdapter extends ArrayAdapter<String> {

    private int layout;

    private ArrayList<String> thisList;
    DataBaseHelperClass myDbHelper;
    String fullPath;
    String[] split;
    String name;
    public myAdapter(Context context, int resource, ArrayList<String> objects,DataBaseHelperClass myDbHelper,String[] split) {
        super(context, resource, objects);
        layout = resource;
    this.split = split;
        thisList =  objects;
        this.myDbHelper = myDbHelper;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ContentHolder expectedListHolder = null;

        if(convertView == null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);
            final View thisView = convertView;
            final ContentHolder listHolder = new ContentHolder(split[0],split[1]);
            listHolder.setImage((ImageView) convertView.findViewById(R.id.thumbnail));

            listHolder.setTitle((TextView) convertView.findViewById(R.id.gg));
            listHolder.setBtn((Button) convertView.findViewById(R.id.btn));
            listHolder.setRemove((Button) convertView.findViewById(R.id.button));
            listHolder.setEdit((Button)convertView.findViewById(R.id.button2));
            listHolder.setRename((Button)convertView.findViewById(R.id.button3));

            listHolder.getBtn().setText("options");



            listHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                     aleternate();






                    }

                public void aleternate(){

                    if(listHolder.getEdit().getVisibility() == View.VISIBLE){
                        listHolder.getEdit().setVisibility(View.GONE);
                    }else{
                        listHolder.getEdit().setVisibility(View.VISIBLE);
                    }
                    if(listHolder.getRename().getVisibility() == View.VISIBLE){
                        listHolder.getRename().setVisibility(View.GONE);
                    }else{
                        listHolder.getRename().setVisibility(View.VISIBLE);
                    }


                    if(listHolder.getRemove().getVisibility() == View.VISIBLE){
                        listHolder.getRemove().setVisibility(View.GONE);
                    }else{
                        listHolder.getRemove().setVisibility(View.VISIBLE);
                    }



                }


                });

            listHolder.getRemove().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder newBuilder = new AlertDialog.Builder(getContext());
                    newBuilder.setMessage("Are you sure you want to delete this item");


               newBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {


                       Toast.makeText(getContext(),  myDbHelper.deleteRecord(getItem(position)),Toast.LENGTH_SHORT).show();


                       listHolder.btn.setBackgroundColor(Color.argb(1,0,56,158));


                       thisList.remove(getItem(position));

                       notifyDataSetChanged();

                   }
               });

                newBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                    AlertDialog alert = newBuilder.create();
                    alert.show();

                }
            });



                convertView.setTag(listHolder);


            }

           expectedListHolder = (ContentHolder) convertView.getTag();
          expectedListHolder.title.setText(getItem(position));


          return convertView;

    }

}
