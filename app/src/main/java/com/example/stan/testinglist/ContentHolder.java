package com.example.stan.testinglist;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
/**
 * Created by Stan on 9/19/2016.
 */
public class ContentHolder {

    ImageView image;
    TextView title;
    Button btn;
    Button remove;
    Button edit;
    Button rename;
    String price;
    String name;


    public ContentHolder(String name,String price){
        this.name = name;
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }


    public void setImage(ImageView image){
        this.image = image;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public Button getRemove() {
        return remove;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getRename() {
        return rename;
    }

    public void setRename(Button rename) {
        this.rename = rename;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public void setTitle(TextView textView){
        this.title = textView;
    }

    public void setBtn(Button btn){
        this.btn = btn;
    }

    public TextView getTitle(){
        return title;
    }


    public Button getBtn(){
        return btn;
    }

    public ImageView getImage(){
        return image;
    }
}
