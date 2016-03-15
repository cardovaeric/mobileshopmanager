package com.fluxmobile.mobileshopmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DBAdapter
{
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_TRANSACTIONS = "Transactions";


    static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE Products(productId INTEGER PRIMARY KEY," +
            "productName VARCHAR(50)," +
            "productModalPrice INTEGER," +
            "productSellPrice INTEGER," +
            "productStock INTEGER," +
            "productSold INTEGER," +
            "productDate DATE)";

    static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE Transactions (TransactionId INTEGER PRIMARY KEY, " +
            "transactionDate DATE , " +
            "productId INTEGER, " +
            "profit INTEGER, " +
            "quantity INTEGER)";

    static final String DELETE_ROWS = "DELETE FROM products";
    //static final String SELECT_REPORT = "SELECT  transactionDate,productName, quantity, productSellPrice,productModalPrice from Transactions a , Products b where a.productId = b.productId";
    static final String SELECT_REPORT = "SELECT  transactionDate, b.productName, quantity, profit from Transactions a, Products b WHERE a.productId = b.productId ORDER BY transactionId DESC";
    Context context;
    DBHelper helper;
    SQLiteDatabase db;

    public DBAdapter(Context context)
    {
        this.context = context;
        helper = new DBHelper(context);
    }

    public void open()
    {
        db = helper.getWritableDatabase();
    }
    public void close(){db.close();};
    public void insertProduct(String nama, int modal, int jual, int stock, int sold, Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues value = new ContentValues();
        value.put("productName", nama);
        value.put("productModalPrice", modal);
        value.put("productSellPrice", jual);
        value.put("productStock", stock);
        value.put("productSold", sold);
        value.put("productDate", dateFormat.format(date));
        db.insert(TABLE_PRODUCTS, null, value);
    }

    public Cursor selectReport()
    {
        Cursor cursor = db.rawQuery(SELECT_REPORT,null);
        return cursor;
    }
    public void insertTransaction(int productid, Date date, int quantity,int profit)
    {
        ContentValues value = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        value.put("productId", productid);
        value.put("transactionDate", dateFormat.format(date));
        value.put("quantity",quantity);
        value.put("profit",profit);
        db.insert(TABLE_TRANSACTIONS, null, value);
    }

    public Cursor selectStock(int product_id)
    {
        return db.query(TABLE_PRODUCTS,new String[]{"productStock"},"productId"+"=?",new String[]{product_id+""}, null, null, null, null);
    }

    public Cursor selectSold(int product_id)
    {
        return db.query(TABLE_PRODUCTS,new String[]{"productSold"},"productId"+"=?",new String[]{product_id+""}, null, null, null, null);
    }

    public Cursor selectSellPrice(int product_id)
    {
        return db.query(TABLE_PRODUCTS,new String[]{"productSellPrice"},"productId"+"=?",new String[]{product_id+""}, null, null, null, null);
    }

    public Cursor selectPurchasePrice(int product_id)
    {
        return db.query(TABLE_PRODUCTS,new String[]{"productModalPrice"},"productId"+"=?",new String[]{product_id+""}, null, null, null, null);
    }

    public void updateStockSold(int product_id,int stock,int sold)
    {
        ContentValues value = new ContentValues();
        value.put("productStock",stock);
        value.put("productSold",sold);
        db.update(TABLE_PRODUCTS,value,"productId" + " = ?", new String[]{product_id+""});

    }

    public void updateProduct(int product_id,String productName,int productModal, int productPrice,int stock)
    {
        ContentValues value = new ContentValues();
        value.put("productName", productName);
        value.put("productModalPrice", productModal);
        value.put("productSellPrice", productPrice);
        value.put("productStock", stock);
        db.update(TABLE_PRODUCTS,value,"productId" + " = ?", new String[]{product_id+""});
    }

    public void dropTable()
    {
        db.execSQL(DELETE_ROWS);
    }

    public void deleteProduct(int id)
    {
        db.delete(TABLE_PRODUCTS, "productId" + " = ? ",new String[]{id+""});
    }

    public void deleteAllReport()
    {
        db.delete(TABLE_TRANSACTIONS,null,null);
    }

    public Cursor getAllProducts()
    {
        return db.query(TABLE_PRODUCTS, new String[]{"productId","productName","productModalPrice","productSellPrice","productStock","productSold","productDate"}, null, null, null, null, null);
    }

    static class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context, DB_NAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(CREATE_TABLE_TRANSACTIONS);
            db.execSQL(CREATE_TABLE_PRODUCTS);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }

    }
}
