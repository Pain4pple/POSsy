package com.main.database;

public class OrderModel {
    private int Id;
    private String Name;
    private int Qty;
    private double Value;
    private double TotalValue;
    private int Image;


    public OrderModel(int id, String name, int qty, double value, double totalValue, int image) {
        this.Id = id;
        this.Name = name;
        this.Qty = qty;
        this.Value = value;
        this.TotalValue = totalValue;
        this.Image = image;
    }

    public OrderModel() {
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Qty=" + Qty +
                ", Value=" + Value +
                ", TotalValue=" + TotalValue +
                ", Image=" + Image +
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

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
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

    public double getTotalValue() {
        return TotalValue;
    }

    public void setTotalValue(double totalValue) {
        TotalValue = totalValue;
    }
}
