package com.main.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductDatabaseHelper extends SQLiteOpenHelper {


    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String COLUMN_PRODUCT_VALUE = "PRODUCT_VALUE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_IMAGE = "PRODUCT_IMAG";
    public static final String COLUMN_ID = "ID";

    public ProductDatabaseHelper(@Nullable Context context) {
        super(context, "productPOS.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PRODUCT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT_NAME + " TEXT, " + COLUMN_PRODUCT_VALUE + " DECIMAL(65, 2), " + COLUMN_PRODUCT_IMAGE + " VARCHAR(65535))";
        db.execSQL(createTableStatement);


    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addProd(ProductModel productModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT_NAME,productModel.getName());
        cv.put(COLUMN_PRODUCT_VALUE,productModel.getValue());
        cv.put(COLUMN_PRODUCT_IMAGE,productModel.getImage());

        long insert = db.insert(PRODUCT_TABLE, null, cv);
        if(insert==-1) {
            return false;
        }
        else{
            return true;
        }
    }
}
