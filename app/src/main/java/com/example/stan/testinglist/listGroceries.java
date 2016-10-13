package com.example.stan.testinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class listGroceries extends AppCompatActivity implements Serializable {
    EditText name;
    EditText price;
    int count = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_second);

        final Button sub = (Button)findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.Name);
        price = (EditText) findViewById(R.id.editText2);
        final Button image = (Button)findViewById(R.id.button4);

        final EditText priceText = (EditText) findViewById(R.id.editText2);
        final TextView errorReport = (TextView) findViewById(R.id.error);

        priceText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                final String regExp = "^\\$(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$";
                if(!priceText.getText().toString().matches(regExp)){
                    errorReport.setText("pleasse enter a valide price");
                    sub.setClickable(false);
                }else{

                    errorReport.setText(" ");
                    sub.setClickable(true);

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                final String regExp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$";
                if(!priceText.getText().toString().matches(regExp)){
                    errorReport.setText("pleasse enter a valide price");
                    sub.setClickable(false);
                }else{

                        errorReport.setText("");
                        sub.setClickable(true);

                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {





            }

        });

        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if(name.getText().toString().equals("")){
                    errorReport.setText("pleasse enter a name for the product");
                    sub.setClickable(false);
                }else{
                    count++;
                    if(count >= 2) {
                        errorReport.setText("");
                        sub.setClickable(true);
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

                if(name.getText().toString().equals("")){
                    errorReport.setText("pleasse enter a name for the product");
                    sub.setClickable(false);
                }else{
                    if(count == 2) {
                        errorReport.setText(" ");

                        sub.setClickable(true);
                    }
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {





            }

        });








        sub.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {







                Intent i = new Intent(listGroceries.this,Second.class);

                i.putExtra("name",name.getText().toString());
                i.putExtra("price",price.getText().toString());

                startActivity(i);

            }
        });




        sub.setClickable(false);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });


    }

    public void openCamera() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Toast.makeText(listGroceries.this, "got here above first if", Toast.LENGTH_SHORT).show();
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Toast.makeText(listGroceries.this, "got here above cursor", Toast.LENGTH_SHORT).show();
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                Toast.makeText(listGroceries.this, "got here above int columnindex", Toast.LENGTH_SHORT).show();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.newImage);
                Toast.makeText(listGroceries.this, "got here", Toast.LENGTH_SHORT).show();
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
        }catch (Exception e){
            Toast.makeText(listGroceries.this, "failure", Toast.LENGTH_SHORT).show();
        }

    }

}