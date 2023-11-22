package com.main.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductModel {

    private int Id;
    private String Name;
    private double Value;
    private int Image;


    public ProductModel(int id, String name, double value, int image) {
        this.Id = id;
        this.Name = name;
        this.Value = value;
        this.Image = image;
    }

    public ProductModel() {
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Value=" + Value +
                ", Image='" + Image + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}