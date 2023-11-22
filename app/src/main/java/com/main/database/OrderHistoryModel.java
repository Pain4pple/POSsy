package com.main.database;

public class OrderHistoryModel {
    private int Id;
    private String CusName;
    private double TotalOrdered;
    private String Date;
    private Boolean PaidByCredit;
    private double TotalCredit;

    public OrderHistoryModel(int id, String cusName, double totalOrdered, Boolean paidByCredit) {
        Id = id;
        CusName = cusName;
        TotalOrdered = totalOrdered;
        PaidByCredit = paidByCredit;
    }


    public OrderHistoryModel(int id, String cusName, double totalOrdered, Boolean paidByCredit, double totalCredit) {
        Id = id;
        CusName = cusName;
        TotalOrdered = totalOrdered;
        PaidByCredit = paidByCredit;
        TotalCredit = totalCredit;
    }

    public OrderHistoryModel(String cusName, double totalOrdered, String date, double totalCredit) {
        CusName = cusName;
        TotalOrdered = totalOrdered;
        Date = date;
        TotalCredit = totalCredit;
    }

    public OrderHistoryModel() {
    }

    @Override
    public String toString() {
        return "OrderHistoryModel{" +
                "Id=" + Id +
                ", CusName='" + CusName + '\'' +
                ", TotalOrdered=" + TotalOrdered +
                ", Date='" + Date + '\'' +
                ", PaidByCredit=" + PaidByCredit +
                ", TotalCredit=" + TotalCredit +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public double getTotalOrdered() {
        return TotalOrdered;
    }

    public void setTotalOrdered(double totalOrdered) {
        TotalOrdered = totalOrdered;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Boolean getPaidByCredit() {
        return PaidByCredit;
    }

    public void setPaidByCredit(Boolean paidByCredit) {
        PaidByCredit = paidByCredit;
    }

    public double getTotalCredit() {
        return TotalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        TotalCredit = totalCredit;
    }
}
