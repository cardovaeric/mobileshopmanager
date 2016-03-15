package com.fluxmobile.mobileshopmanager;


public class Product
{
    private String nama,date;
    private int hargamodal,hargajual,stock,sold,id;
    public Product(int id,String nama,int modal, int jual, int stock,int sold, String date)
    {
        this.id=id;
        this.nama=nama;
        this.hargajual=jual;
        this.hargamodal=modal;
        this.stock=stock;
        this.sold=sold;
        this.date=date;

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHargamodal() {
        return hargamodal;
    }

    public void setHargamodal(int hargamodal) {
        this.hargamodal = hargamodal;
    }

    public int getHargajual() {
        return hargajual;
    }

    public void setHargajual(int hargajual) {
        this.hargajual = hargajual;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId()
    {return id;}

}
