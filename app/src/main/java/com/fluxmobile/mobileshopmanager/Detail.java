package com.fluxmobile.mobileshopmanager;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fluxmobile.mobilemarketmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detail extends ActionBarActivity {
    String  name,stock,purprise,recselprice,sold,date;
    int productid,currentStock,currentSold,currentPrice,currentModal;
    Typeface tf;
    TextView t1,t2,t3,t4,t5,t6;
    TextView tDialogStock,tDialogPrice,tDialogTotalPrice,tdialog1,tdialog2,tdialog3,tDialogName;
    TextView erase_txt_name,erase_txt_sure;
    Button erase_butt_erase,erase_butt_cancel;
    TextView edit_lbl_name,edit_lbl_purchase,edit_lbl_sell,edit_lbl_stock;
    EditText edit_name,edit_purchase,edit_sell,edit_stock;
    Button edit_cancel,edit_edit;
    EditText dialogInputQuantity;
    DBAdapter db;
    Button dialogSell,dialogCancel;
    Date datenotstring;
    String current="",current2="";
    LayoutInflater inflator;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffda531f")));

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        db = new DBAdapter(this);

        t1 = (TextView)findViewById(R.id.det_productName);
        t2 = (TextView)findViewById(R.id.det_purchaseprice);
        t3 = (TextView)findViewById(R.id.det_recselprice);
        t4 = (TextView)findViewById(R.id.det_stock);
        t5 = (TextView)findViewById(R.id.det_sold);
        t6 = (TextView)findViewById(R.id.det_date);

        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {

        }
        else
        {
            productid=extras.getInt("product_id");
            System.out.println("Product Id: "+productid);
            name = extras.getString("product_name");
            purprise = extras.getString("product_purchaseprice");
            recselprice = extras.getString("product_recselprice");
            stock = extras.getString("product_stock");
            sold = extras.getString("product_sold");
            date = extras.getString("product_date");
            setTitle(name);

            inflator = LayoutInflater.from(this);
            View v = inflator.inflate(R.layout.titleview, null);
            tf = Typeface.createFromAsset(this.getAssets(),"fonts/futura.ttf");
            ((TextView)v.findViewById(R.id.title)).setText(name);
            ((TextView)v.findViewById(R.id.title)).setTypeface(tf);
            Detail.this.getSupportActionBar().setCustomView(v);

            TextView datelbl = (TextView) findViewById(R.id.label_date);
            TextView purchaselbl = (TextView) findViewById(R.id.label_purchase);
            TextView selllbl = (TextView) findViewById(R.id.label_sell);
            TextView stocklbl = (TextView) findViewById(R.id.label_stock);
            TextView soldlbl = (TextView) findViewById(R.id.label_sold);

            Button sellbutt= (Button) findViewById(R.id.det_sellbutt);
            datelbl.setTypeface(tf);
            purchaselbl.setTypeface(tf);
            selllbl.setTypeface(tf);
            stocklbl.setTypeface(tf);
            soldlbl.setTypeface(tf);
            sellbutt.setTypeface(tf);


            t1.setText(name);
            String tempjual;
            String temp1;
            String temp2;
            String temp3;
            tempjual = purprise;
            if(tempjual.length()>3)
            {
                if(tempjual.length()<=6) {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(0, tempjual.length() - 3);
                    t2.setText("IDR " + temp2 + "." + temp1);
                }
                else if(tempjual.length()>6)
                {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                    temp3 = tempjual.substring(0, tempjual.length()-6);
                    t2.setText("IDR " + temp3+"."+temp2 + "." + temp1);
                }
            }
            else
            {
                t2.setText("IDR "+tempjual);
            }
            tempjual = recselprice;
            if(tempjual.length()>3)
            {
                if(tempjual.length()<=6) {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(0, tempjual.length() - 3);
                    t3.setText("IDR " + temp2 + "." + temp1);
                }
                else if(tempjual.length()>6)
                {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                    temp3 = tempjual.substring(0, tempjual.length()-6);
                    t3.setText("IDR " + temp3+"."+temp2 + "." + temp1);
                }
            }
            else
            {
                t3.setText("IDR "+tempjual);
            }
            t4.setText(""+stock);
            t5.setText(""+sold);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {

                datenotstring = formatter.parse(date);
                System.out.println("AAAAAAAAAAa"+datenotstring);
                System.out.println(formatter.format(datenotstring));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
            t6.setText(""+formatter.format(datenotstring));

            t1.setTypeface(tf);
            t2.setTypeface(tf);
            t3.setTypeface(tf);
            t4.setTypeface(tf);
            t5.setTypeface(tf);
            t6.setTypeface(tf);
        }
    }

    //ERASE DIALOG
    public void initDialog2(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);


        erase_txt_name = (TextView)dialog.findViewById(R.id.erase_lbl_name);
        erase_txt_sure = (TextView)dialog.findViewById(R.id.erase_txt_sure);
        erase_butt_erase = (Button) dialog.findViewById(R.id.erase_btn_erase);
        erase_butt_cancel = (Button) dialog.findViewById(R.id.erase_btn_cancel);
        erase_txt_name.setTypeface(tf);
        erase_txt_sure.setTypeface(tf);
        erase_butt_erase.setTypeface(tf);
        erase_butt_cancel.setTypeface(tf);
        erase_txt_name.setText("ERASE "+name.toUpperCase());

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==erase_butt_erase)
                {

                    Date date = new Date();
                    Toast.makeText(getApplicationContext(),name+" has been erased",Toast.LENGTH_SHORT).show();
                    db.open();
                    db.deleteProduct(productid);
                    db.close();
                    dialog.dismiss();
                    Intent intent = new Intent(Detail.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else if(v==erase_butt_cancel)
                {
                    dialog.dismiss();
                }
            }
        };
        erase_butt_erase.setOnClickListener(myListener);
        erase_butt_cancel.setOnClickListener(myListener);
        dialog.setCancelable(true);
    }

    //EDIT DIALOG
    public void initDialog3(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);




        edit_lbl_name = (TextView)dialog.findViewById(R.id.edit_lbl_name);
        edit_lbl_purchase = (TextView)dialog.findViewById(R.id.edit_lbl_purchase);
        edit_lbl_sell = (TextView)dialog.findViewById(R.id.edit_lbl_sell);
        edit_lbl_stock = (TextView)dialog.findViewById(R.id.edit_lbl_stock);
        edit_name = (EditText)dialog.findViewById(R.id.edit_name);
        edit_purchase = (EditText)dialog.findViewById(R.id.edit_purchase);
        edit_sell = (EditText)dialog.findViewById(R.id.edit_sell);
        edit_stock = (EditText)dialog.findViewById(R.id.edit_stock);
        edit_cancel = (Button)dialog.findViewById(R.id.edit_btn_cancel);
        edit_edit = (Button)dialog.findViewById(R.id.edit_btn_edit);
        edit_cancel.setTypeface(tf);
        edit_edit.setTypeface(tf);
        edit_lbl_name.setTypeface(tf);
        edit_lbl_purchase.setTypeface(tf);
        edit_lbl_sell.setTypeface(tf);
        edit_lbl_stock.setTypeface(tf);
        edit_name.setTypeface(tf);
        edit_purchase.setTypeface(tf);
        edit_sell.setTypeface(tf);
        edit_stock.setTypeface(tf);
        edit_name.setHint(name+"");
        edit_stock.setHint(stock+"");
        edit_name.setText(name+"");
        edit_stock.setText(stock+"");
        edit_name.setSelection(edit_name.getText().length());
        String tempjual;
        String temp1;
        String temp2;
        String temp3;
        tempjual = purprise.replaceAll("\\.","");
        if(tempjual.length()>3)
        {
            if(tempjual.length()<=6) {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(0, tempjual.length() - 3);
                edit_purchase.setHint(temp2 + "." + temp1);
                edit_purchase.setText(temp2 + "." + temp1);
            }
            else if(tempjual.length()>6)
            {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                temp3 = tempjual.substring(0, tempjual.length()-6);
                edit_purchase.setHint(temp3+"."+temp2 + "." + temp1);
                edit_purchase.setText(temp3+"."+temp2 + "." + temp1);
            }
        }
        else
        {
            edit_purchase.setHint(""+tempjual);
            edit_purchase.setText(""+tempjual);
        }


        tempjual = recselprice.replaceAll("\\.","");
        if(tempjual.length()>3)
        {
            if(tempjual.length()<=6) {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(0, tempjual.length() - 3);
                edit_sell.setHint(temp2 + "." + temp1);
                edit_sell.setText(temp2 + "." + temp1);
            }
            else if(tempjual.length()>6)
            {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                temp3 = tempjual.substring(0, tempjual.length()-6);
                edit_sell.setHint(temp3+"."+temp2 + "." + temp1);
                edit_sell.setText(temp3+"."+temp2 + "." + temp1);
            }
        }
        else
        {
            edit_sell.setHint(""+tempjual);
            edit_sell.setText(""+tempjual);
        }


        edit_purchase.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if (edit_purchase.getText().toString().matches("^0") )
                {
                    // Not allowed
                    edit_purchase.setText("");
                }

                if(!s.toString().equals(current)){
                    edit_purchase.removeTextChangedListener(this);
                    current=edit_purchase.getText().toString().replaceAll("\\.","");
                    String temp1, temp2, temp3;
                    current=edit_purchase.getText().toString().replaceAll("\\.","");
                    if(current.length()>3)
                    {
                        if(current.length()<=6) {
                            temp1 = current.substring(current.length() - 3, current.length());
                            temp2 = current.substring(0, current.length() - 3);
                            System.out.println(temp2 + "." + temp1+"length is"+current.length());
                            edit_purchase.setText(temp2 + "." + temp1);
                        }
                        else if(current.length()>6)
                        {
                            temp1 = current.substring(current.length() - 3, current.length());
                            temp2 = current.substring(current.length() - 6, current.length() - 3);
                            temp3 = current.substring(0, current.length()-6);
                            edit_purchase.setText(temp3+"."+temp2 + "." + temp1);
                            System.out.println("6 numbers");
                        }
                    }
                    else
                    {
                        edit_purchase.setText(s.toString().replaceAll("\\.",""));
                    }


                    edit_purchase.addTextChangedListener(this);
                    edit_purchase.setSelection(edit_purchase.getText().length());
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });

        edit_sell.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if (edit_sell.getText().toString().matches("^0") )
                {
                    // Not allowed
                    edit_sell.setText("");
                }

                if(!s.toString().equals(current2)){
                    edit_sell.removeTextChangedListener(this);
                    String temp1, temp2, temp3;
                    current2=edit_sell.getText().toString().replaceAll("\\.","");
                    if(current2.length()>3)
                    {
                        if(current2.length()<=6) {
                            temp1 = current2.substring(current2.length() - 3, current2.length());
                            temp2 = current2.substring(0, current2.length() - 3);
                            System.out.println(temp2 + "." + temp1+"length is"+current2.length());
                            edit_sell.setText(temp2 + "." + temp1);
                        }
                        else if(current2.length()>6)
                        {
                            temp1 = current2.substring(current2.length() - 3, current2.length());
                            temp2 = current2.substring(current2.length() - 6, current2.length() - 3);
                            temp3 = current2.substring(0, current2.length()-6);
                            edit_sell.setText(temp3+"."+temp2 + "." + temp1);
                            System.out.println("6 numbers");
                        }
                    }
                    else
                    {
                        edit_sell.setText(s.toString().replaceAll("\\.",""));
                    }


                    edit_sell.addTextChangedListener(this);
                    edit_sell.setSelection(edit_sell.getText().length());
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });


        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==edit_edit)
                {


                    if (edit_name.getText().toString().length() < 1) {
                        Toast.makeText(getApplicationContext(), "Product name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_purchase.getText().toString().replaceAll("\\.","").length() < 1) {
                        Toast.makeText(getApplicationContext(), "Purchase price cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_sell.getText().toString().replaceAll("\\.","").length() < 1) {
                        Toast.makeText(getApplicationContext(), "Sell price cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_stock.getText().toString().length() < 1) {
                        Toast.makeText(getApplicationContext(), "Stock cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edit_purchase.getText().toString().replaceAll("\\.","")) >= Integer.parseInt(edit_sell.getText().toString().replaceAll("\\.",""))) {
                        Toast.makeText(getApplicationContext(), "Sell price must be higher than purchase price ", Toast.LENGTH_SHORT).show();
                    } else {
                        Date date1 = new Date(); // Current time

                        String tempname = edit_name.getText().toString();
                        int tempmodal = Integer.parseInt(edit_purchase.getText().toString().replaceAll("\\.",""));
                        int tempsell = Integer.parseInt((edit_sell.getText().toString().replaceAll("\\.","")));
                        int tempstock = Integer.parseInt(edit_stock.getText().toString());
                        t1.setText(edit_name.getText());
                        t2.setText(edit_purchase.getText().toString().replaceAll("\\.",""));
                        t3.setText(edit_sell.getText().toString().replaceAll("\\.",""));
                        t4.setText(edit_stock.getText().toString());

                        name = tempname;
                        purprise = ""+tempmodal;
                        recselprice = ""+tempsell;
                        stock = ""+tempstock;

                        String temp1, temp2, temp3;
                        current2=purprise;
                        if(current2.length()>3)
                        {
                            if(current2.length()<=6) {
                                temp1 = current2.substring(current2.length() - 3, current2.length());
                                temp2 = current2.substring(0, current2.length() - 3);
                                System.out.println(temp2 + "." + temp1+"length is"+current2.length());
                                purprise=(temp2 + "." + temp1);
                            }
                            else if(current2.length()>6)
                            {
                                temp1 = current2.substring(current2.length() - 3, current2.length());
                                temp2 = current2.substring(current2.length() - 6, current2.length() - 3);
                                temp3 = current2.substring(0, current2.length()-6);
                                purprise=(temp3+"."+temp2 + "." + temp1);
                                System.out.println("6 numbers");
                            }
                        }
                        else
                        {
                            purprise=(purprise.replaceAll("\\.",""));
                        }
                        t2.setText("IDR "+purprise);


                        current2=recselprice;
                        if(current2.length()>3)
                        {
                            if(current2.length()<=6) {
                                temp1 = current2.substring(current2.length() - 3, current2.length());
                                temp2 = current2.substring(0, current2.length() - 3);
                                System.out.println(temp2 + "." + temp1+"length is"+current2.length());
                                recselprice=(temp2 + "." + temp1);
                            }
                            else if(current2.length()>6)
                            {
                                temp1 = current2.substring(current2.length() - 3, current2.length());
                                temp2 = current2.substring(current2.length() - 6, current2.length() - 3);
                                temp3 = current2.substring(0, current2.length()-6);
                                recselprice=(temp3+"."+temp2 + "." + temp1);
                                System.out.println("6 numbers");
                            }
                        }
                        else
                        {
                            recselprice=(recselprice.replaceAll("\\.",""));
                        }
                        t3.setText("IDR "+recselprice);


                        v = inflator.inflate(R.layout.titleview, null);
                        tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/futura.ttf");
                        ((TextView)v.findViewById(R.id.title)).setText(name);
                        ((TextView)v.findViewById(R.id.title)).setTypeface(tf);
                        Detail.this.getSupportActionBar().setCustomView(v);

                        DBAdapter db = new DBAdapter(getApplicationContext());
                        db.open();

                        db.updateProduct(productid,tempname,tempmodal,tempsell,tempstock);

                        Toast.makeText(getApplicationContext(),name+" has been edited",Toast.LENGTH_SHORT).show();
                        db.close();

                        dialog.dismiss();
                    }


                }

                else if(v==edit_cancel)
                {
                    dialog.dismiss();
                }
            }
        };
        edit_edit.setOnClickListener(myListener);
        edit_cancel.setOnClickListener(myListener);
        dialog.setCancelable(true);
    }

    public void initDialog()
    {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sell);

        tdialog1=(TextView)dialog.findViewById(R.id.dialog_lbl1);
        tdialog2=(TextView)dialog.findViewById(R.id.dialog_lbl2);
        tdialog3=(TextView)dialog.findViewById(R.id.dialog_lbl3);
        tDialogStock=(TextView)dialog.findViewById(R.id.dialog_lbl_currentStock);
        tDialogPrice = (TextView)dialog.findViewById(R.id.dialog_lbl_price);
        tDialogTotalPrice=(TextView)dialog.findViewById(R.id.dialog_lbl_totalprice);
        tDialogName = (TextView)dialog.findViewById(R.id.dialog_lbl_name);
        dialogInputQuantity=(EditText)dialog.findViewById(R.id.dialog_txtQuantity);
        dialogSell = (Button)dialog.findViewById(R.id.dialog_btn_sell);
        dialogCancel = (Button)dialog.findViewById(R.id.dialog_btn_cancel);

        tdialog1.setTypeface(tf); tDialogTotalPrice.setTypeface(tf);
        tdialog2.setTypeface(tf); tDialogPrice.setTypeface(tf);
        tdialog3.setTypeface(tf); dialogInputQuantity.setTypeface(tf);
        dialogSell.setTypeface(tf); dialogCancel.setTypeface(tf);
        tDialogStock.setTypeface(tf);
        tDialogName.setTypeface(tf);

        tDialogName.setText(""+name);
        tDialogStock.setText(""+stock);

        String tempjual=""+recselprice.replaceAll("\\.","");
        String temp1;
        String temp2;
        String temp3;


        if(tempjual.length()>3)
        {
            if(tempjual.length()<=6) {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(0, tempjual.length() - 3);
                tDialogPrice.setText("IDR " + temp2 + "." + temp1);
            }
            else if(tempjual.length()>6)
            {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                temp3 = tempjual.substring(0, tempjual.length()-6);
                tDialogPrice.setText("IDR " + temp3+"."+temp2 + "." + temp1);
            }
        }
        else
        {
            tDialogPrice.setText(recselprice);
        }

        tDialogTotalPrice.setText("IDR 0");

        dialogInputQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a=getPrice();
                int b;
                if(s.toString().equals(""))
                {
                    tDialogTotalPrice.setText("IDR 0");
                }
                else
                {
                    b=Integer.parseInt(s.toString());
                    String tempjual;
                    String temp1;
                    String temp2;
                    String temp3;
                    tempjual = (""+a*b);

                    if(tempjual.length()>3)
                    {
                        if(tempjual.length()<=6) {
                            temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                            temp2 = tempjual.substring(0, tempjual.length() - 3);
                            tDialogTotalPrice.setText("IDR " + temp2 + "." + temp1);
                        }
                        else if(tempjual.length()>6)
                        {
                            temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                            temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                            temp3 = tempjual.substring(0, tempjual.length()-6);
                            tDialogTotalPrice.setText("IDR " + temp3+"."+temp2 + "." + temp1);
                        }
                    }
                    else
                    {
                        tDialogTotalPrice.setText("IDR " +tempjual);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==dialogSell.getId())
                {
                    int qty;
                    int stck;
                    if(dialogInputQuantity.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please input quantity",Toast.LENGTH_SHORT).show();
                    }
                    else if(Integer.parseInt(dialogInputQuantity.getText().toString())==0)
                    {
                        Toast.makeText(getApplicationContext(),"Quantity cannot be zero",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        qty=Integer.parseInt(dialogInputQuantity.getText().toString());
                        stck = getStock();


                        if(qty>stck)
                        {
                            Toast.makeText(getApplicationContext(),"Not enough stock",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int st,so;
                            st=getStock()-qty;
                            so=getSold()+qty;

                            int profit = qty*(getPrice()-getModal());
                            Date date = new Date();
                            db.open();
                            db.insertTransaction(productid,date,qty,profit);
                            db.updateStockSold(productid,st,so);
                            db.close();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),qty+" of "+name+" has been sold",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Detail.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }
                    }
                }
                else if(v.getId()==dialogCancel.getId())
                {
                    dialog.dismiss();
                }
            }
        };
        dialogSell.setOnClickListener(myListener);
        dialogCancel.setOnClickListener(myListener);
        dialog.setCancelable(true);

    }

    public int getStock()
    {
        db.open();
        Cursor cursor = db.selectStock(productid);
        if(cursor.moveToFirst())
        {
            currentStock = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return currentStock;
    }

    public int getSold()
    {
        db.open();
        Cursor cursor = db.selectSold(productid);
        if(cursor.moveToFirst())
        {
            currentSold = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return currentSold;
    }
    public int getPrice()
    {
        db.open();
        Cursor cursor = db.selectSellPrice(productid);
        if(cursor.moveToFirst())
        {
            currentPrice = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return currentPrice;
    }

    public int getModal()
    {
        db.open();
        Cursor cursor = db.selectPurchasePrice(productid);
        if(cursor.moveToFirst())
        {
            currentModal = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return currentModal;
    }

    public void onClick(View view)
    {
        if(view.getId()==R.id.det_sellbutt)
        {
            initDialog();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.action_detail_delete)
        {
            initDialog2();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
        }
        else if(item.getItemId()==R.id.action_edit)
        {
            initDialog3();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
        }
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
