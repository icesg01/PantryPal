package com.example.stan.testinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.webkit.HttpAuthHandler;
import android.widget.ArrayAdapter;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Stan on 10/14/2016.
 */
public class RemoteDataBase extends AsyncTask<String,Void,String> {
AlertDialog alertDialog;
    Context context;
    ArrayList<String> returnArray;
    String JSon;
    ArrayAdapter<String> adapter;
    RemoteDataBase (Context ctx,ArrayList<String> returnArray,ArrayAdapter<String> adapter){

        context = ctx;
        this.JSon = JSon;
        this.adapter = adapter;
        this.returnArray = returnArray;
    }


    @Override
    protected String doInBackground(String... params) {
        ArrayList<String> namePrice = new ArrayList<>();
        String type = params[0];

        String login_url = "http://www.sullens.net/~sice/PHP5/connectToAndroid.php";
        String groceryGetter = "http://www.sullens.net/~sice/PHP5/getGrocery.php";
        String getPrice = "http://www.sullens.net/~sice/PHP5/getPrice.php";

        if (type.equals("selectFromList")) {

            String name = params[1];
            try {
                URL url = new URL(getPrice);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";






                while ((line = bufferedReader.readLine()) != null) {
                    String newLine = line;
                    returnArray.add(newLine);
                    adapter.notifyDataSetChanged();
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return namePrice.get(0);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }









        if(type.equals("login")){
            String user_name = params[1];
            String password = params[2];
            try{
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                   namePrice.add(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return namePrice.get(0);


            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }



        }

        if (type.equals("grocery")){
            String name = params[1];

try{

            URL url = new URL(groceryGetter);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");


            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result = "";
            String line = "";

            int x = 0;
            StringBuilder sb = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){

                sb.append(line);
                namePrice.add(line);
                x++;
            }
    result = sb.toString();


    bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;


        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }





        }


        return null;

    }

    @Override


    protected void onPreExecute(){



    }

    @Override
    protected void onPostExecute(String result) {



    JSon = result;
        try {
            System.out.println(result);
            JSONObject jsonRootObject = new JSONObject(result);
            JSONArray jsonArray = jsonRootObject.optJSONArray("json");
            for(int x = 0; x < jsonArray.length();x++){
            JSONObject jsonObject = jsonArray.getJSONObject(x);
            String printme = jsonObject.optString("name") + "  " + jsonObject.optString("price");


            returnArray.add(printme);}
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
//    int currentPosition = 0;
//        if (result != null) {
//            if (result.length() > 1) {
//                for (int x = 0; x < result.length() - 2; x++) {
//                    if(result.substring(x,x+3).equals("end")){
//                        returnArray.add(result.substring(currentPosition,x));
//                        currentPosition = x + 3;
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//
//
//        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
