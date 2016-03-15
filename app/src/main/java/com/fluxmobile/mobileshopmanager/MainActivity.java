package com.fluxmobile.mobileshopmanager;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fluxmobile.mobilemarketmanager.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView productlist;
    Date date = new Date();
    ProductListAdapter adapter;
    EditText edit_name;
    EditText edit_modal;
    EditText edit_sell;
    EditText edit_stock;
    Dialog dialog;
    Typeface tf;
    String current="";
    String current2="";
    Button butt_submit,butt_cancel;
    //ProductListAdapter adapter;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffda531f")));

        loadList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        Date da = new Date();

        //Toast.makeText(this,""+dateFormat.format(da),Toast.LENGTH_LONG).show();
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        tf = Typeface.createFromAsset(this.getAssets(),"fonts/futura.ttf");
        ((TextView)v.findViewById(R.id.title)).setText(this.getTitle().toString());
        ((TextView)v.findViewById(R.id.title)).setTypeface(tf);
        MainActivity.this.getSupportActionBar().setCustomView(v);

    }

    public void loadList()
    {
        productlist=(ListView)findViewById(R.id.listView);
        ArrayList<Product> temp = new ArrayList<Product>();
        adapter= new ProductListAdapter(this,temp);
        productlist.setAdapter(adapter);
        productlist.setOnItemClickListener(this);

        //ngambil data dari database masukin ke list
        DBAdapter db = new DBAdapter(this);
        db.open();

        Cursor cursor = db.getAllProducts();
        if(cursor.moveToFirst())
        {
            do
            {
                //name , modal , jual, stock, sold, date
                Product prodtemp = new Product(cursor.getInt(0),cursor.getString(1).toString(),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6));
                temp.add(prodtemp);
            }while(cursor.moveToNext());

            cursor.close();
            db.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        android.support.v7.widget.SearchView searches = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();

            searches.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searches.setIconifiedByDefault(true);

        android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextChange(String newText)
            {

                // this is your adapter that will be filtered
                adapter.getFilter().filter(newText);
                System.out.println("on text change text: "+newText);
                return true;

            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);

                return true;

            }

        };

        searches.setOnQueryTextListener(textChangeListener);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_add)
        {
            /*
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
            return true;*/


            initDialog();



            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
        }
        else if(id==R.id.action_finance)
        {

           Intent intent = new Intent(MainActivity.this,ReportActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void initDialog()
    {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_add);



        edit_name = (EditText)dialog.findViewById(R.id.txt_productName);
        edit_modal= (EditText)dialog.findViewById(R.id.txt_purchasePrice);
        edit_sell = (EditText)dialog.findViewById(R.id.txt_recSellPrice);
        edit_stock = (EditText)dialog.findViewById(R.id.txt_productStock);
        butt_submit = (Button) dialog.findViewById(R.id.btn_submit);
        butt_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        edit_name.setTypeface(tf);
        edit_modal.setTypeface(tf);
        edit_sell.setTypeface(tf);
        edit_stock.setTypeface(tf);
        butt_submit.setTypeface(tf);
        butt_cancel.setTypeface(tf);
        edit_modal.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edit_modal.getText().toString().matches("^0") )
                {
                    // Not allowed
                    edit_modal.setText("");
                }

                if(!s.toString().equals(current)){
                    edit_modal.removeTextChangedListener(this);

                    String temp1, temp2, temp3;
                    current=edit_modal.getText().toString().replaceAll("\\.","");
                    if(current.length()>3)
                    {
                        if(current.length()<=6) {
                            temp1 = current.substring(current.length() - 3, current.length());
                            temp2 = current.substring(0, current.length() - 3);
                            System.out.println(temp2 + "." + temp1+"length is"+current.length());
                            edit_modal.setText(temp2 + "." + temp1);
                        }
                        else if(current.length()>6)
                        {
                            temp1 = current.substring(current.length() - 3, current.length());
                            temp2 = current.substring(current.length() - 6, current.length() - 3);
                            temp3 = current.substring(0, current.length()-6);
                            edit_modal.setText(temp3+"."+temp2 + "." + temp1);
                            System.out.println("6 numbers");
                        }
                    }
                    else
                    {
                        edit_modal.setText(s.toString().replaceAll("\\.",""));
                    }


                    edit_modal.addTextChangedListener(this);
                    edit_modal.setSelection(edit_modal.getText().length());
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
                if(!s.toString().equals(current)){
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
                /*
                String temp1, temp2, temp3;
                if(edit_sell.getText().toString().replaceAll("\\.","").length()>3)
                {
                    if(edit_sell.getText().toString().length()<=6) {
                        temp1 = edit_sell.getText().toString().replaceAll("\\.","").substring(edit_sell.getText().toString().replaceAll("\\.","").length() - 3, edit_sell.getText().toString().replaceAll("\\.","").length());
                        temp2 = edit_sell.getText().toString().replaceAll("\\.","").substring(0, edit_sell.getText().toString().replaceAll("\\.","").length() - 3);
                        System.out.println(temp2 + "." + temp1);
                        edit_sell.setText(temp2 + "." + temp1);
                    }
                    else if(edit_sell.getText().toString().replaceAll("\\.","").length()>6)
                    {
                        temp1 = edit_sell.getText().toString().replaceAll("\\.","").substring(edit_sell.getText().toString().replaceAll("\\.","").length() - 3, edit_sell.getText().toString().replaceAll("\\.","").length());
                        temp2 = edit_sell.getText().toString().replaceAll("\\.","").substring(edit_sell.getText().toString().replaceAll("\\.","").length() - 6, edit_sell.getText().toString().replaceAll("\\.","").length() - 3);
                        temp3 = edit_sell.getText().toString().replaceAll("\\.","").substring(0, edit_sell.getText().toString().replaceAll("\\.","").length()-6);
                        edit_sell.setText(temp3+"."+temp2 + "." + temp1);
                    }
                }*/


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });

        edit_stock.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edit_stock.getText().toString().matches("^0") )
                {
                    // Not allowed
                    edit_stock.setText("");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_submit) {
                    EditText edit_name = (EditText) dialog.findViewById(R.id.txt_productName);
                    EditText edit_modal = (EditText)dialog.findViewById(R.id.txt_purchasePrice);
                    EditText edit_sell = (EditText)dialog.findViewById(R.id.txt_recSellPrice);
                    EditText edit_stock = (EditText) dialog.findViewById(R.id.txt_productStock);

                    if (edit_name.getText().toString().length() < 1) {
                        Toast.makeText(getApplicationContext(), "Product name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_modal.getText().toString().replaceAll("\\.","").length() < 1) {
                        Toast.makeText(getApplicationContext(), "Purchase price cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_sell.getText().toString().replaceAll("\\.","").length() < 1) {
                        Toast.makeText(getApplicationContext(), "Sell price cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (edit_stock.getText().toString().length() < 1) {
                        Toast.makeText(getApplicationContext(), "Stock cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edit_modal.getText().toString().replaceAll("\\.","")) >= Integer.parseInt(edit_sell.getText().toString().replaceAll("\\.",""))) {
                        Toast.makeText(getApplicationContext(), "Sell price must be higher than purchase price ", Toast.LENGTH_SHORT).show();
                    } else {
                        Date date1 = new Date(); // Current time


                        System.out.println("CURRENT DATE IS " + date1);
                        DBAdapter db = new DBAdapter(getApplicationContext());
                        db.open();

                        db.insertProduct(edit_name.getText().toString(),
                                Integer.parseInt(edit_modal.getText().toString().replaceAll("\\.","")),
                                Integer.parseInt(edit_sell.getText().toString().replaceAll("\\.","")),
                                Integer.parseInt(edit_stock.getText().toString()),
                                0, date1);

                        Toast.makeText(getApplicationContext(), "" + edit_name.getText().toString() + " ADDED TO LIST", Toast.LENGTH_SHORT).show();
                        db.close();
                        dialog.dismiss();
                        loadList();

                    }


                }
                if(v==butt_cancel)
                {
                    dialog.dismiss();
                }
            }
        };
        butt_submit.setOnClickListener(myListener);
        butt_cancel.setOnClickListener(myListener);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Product item = (Product)parent.getAdapter().getItem(position);

        Intent intent = new Intent(MainActivity.this,Detail.class);
        intent.putExtra("product_id", item.getId());
        intent.putExtra("product_name",item.getNama());
        intent.putExtra("product_purchaseprice",""+item.getHargamodal());
        intent.putExtra("product_recselprice",""+item.getHargajual());
        intent.putExtra("product_stock",""+item.getStock());
        intent.putExtra("product_sold",""+item.getSold());
        intent.putExtra("product_date", "" + item.getDate());
        startActivity(intent);

    }


}
