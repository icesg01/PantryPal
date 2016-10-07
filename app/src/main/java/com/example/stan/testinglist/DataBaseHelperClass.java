package com.example.stan.testinglist;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperClass extends SQLiteOpenHelper{
    //The Android's default system path of your application database.
    private static String DB_PATH;
    // Data Base Name.
    private static final String DATABASE_NAME = "test.sqlite";
    // Data Base Version.
    private static final int DATABASE_VERSION = 3;
    // Table Names of Data Base.
    static final String TABLE_Name = "produce";

    public Context context;
    static SQLiteDatabase sqliteDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     * Parameters of super() are    1. Context
     *                              2. Data Base Name.


     *                              3. Cursor Factory.
     *                              4. Data Base Version.
     */
    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     * */
    public void createDataBase() throws IOException{
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if(databaseExist){
            // Do Nothing.
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        System.out.println("here it is right here " + databaseFile.getName());
        return databaseFile.exists();
    }

    public void clearRecords(){
        String result = "DELETE FROM produce";
        sqliteDataBase.execSQL(result);
    }

    public String deleteRecord(String fullPath){
        String result = "DELETE FROM produce WHERE fullpath=" + "'" + fullPath + "'" ;


        sqliteDataBase.execSQL(result);

        return result;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams

        System.out.println(myOutput.toString());

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;

        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    /**
     * Apply your methods and class to fetch data using raw or queries on data base using
     * following demo example code as:
     */
    public String getUserNameFromDB(ArrayList<String> list){
        String query = "select name,price from produce ORDER BY name ASC";
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        String userName = null;
        String price = null;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                do{
                    userName = cursor.getString(0);
                    price = cursor.getString(1);
                    list.add(userName + "\n price:" + price);
                }while (cursor.moveToNext());
            }
        }
        return userName;
    }

    public void insertProduce(String namee,String price){
        String fullPath = namee+ "\n price:" + price;
        String result = "insert into produce (name,price,fullpath) VALUES(" + "'" + namee + "', " + "'" + price + "'," + "'" + fullPath + "'" +  ")";
        sqliteDataBase.execSQL(result);

//        ContentValues values = new ContentValues();
//        values.put("name", namee);
//        sqliteDataBase.beginTransaction();
//        sqliteDataBase.insert("produce", null, values);
//        sqliteDataBase.setTransactionSuccessful();


    }

//
//    public void insertNewRow(){
//    String result = "ALTER TABLE produce ADD DEFAULT $2.99 FOR price";
//        sqliteDataBase.execSQL(result);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to write the create table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to write the update table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
        // We should not update it as requirements of application.
    }
}