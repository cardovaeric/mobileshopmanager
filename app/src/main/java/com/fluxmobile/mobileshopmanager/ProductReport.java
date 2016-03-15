package com.fluxmobile.mobileshopmanager;


public class ProductReport
{
    private String name,date;
    private int sold,profit;

    public ProductReport(String name, String date, int sold, int profit) {
        this.name = name;
        this.date = date;
        this.sold = sold;
        this.profit = profit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return sold;
    }

    public void setQuantity(int sold) {
        this.sold = sold;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
