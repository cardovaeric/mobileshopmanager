package com.fluxmobile.mobileshopmanager;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fluxmobile.mobilemarketmanager.R;

import java.util.Date;

public class AddActivity extends ActionBarActivity
{

    EditText edit_name;
    EditText edit_modal;
    EditText edit_sell;
    EditText edit_stock;
    Typeface tf;
    Button butt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e8561f")));

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Typeface tf;
        tf = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");


        edit_name = (EditText)findViewById(R.id.txt_productName);
        edit_modal= (EditText)findViewById(R.id.txt_purchasePrice);
        edit_sell = (EditText) findViewById(R.id.txt_recSellPrice);
        edit_stock = (EditText) findViewById(R.id.txt_productStock);
        Button butt_submit = (Button) findViewById(R.id.btn_submit);

        edit_name.requestFocus();

        edit_name.setTypeface(tf);
        edit_modal.setTypeface(tf);
        edit_sell.setTypeface(tf);
        edit_stock.setTypeface(tf);
        butt_submit.setTypeface(tf);
        LayoutInflater inflator = LayoutInflater.from(this);
        View b = inflator.inflate(R.layout.titleview, null);
        tf = Typeface.createFromAsset(this.getAssets(),"fonts/futura.ttf");
        ((TextView)b.findViewById(R.id.title)).setText("ADD PRODUCT");
        ((TextView)b.findViewById(R.id.title)).setTypeface(tf);
        AddActivity.this.getSupportActionBar().setCustomView(b);


        edit_modal.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edit_modal.getText().toString().matches("^0") )
                {
                    // Not allowed
                    edit_modal.setText("");
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void onClick(View v)
    {

        if(v.getId()==R.id.btn_submit)
        {
            EditText edit_name = (EditText)findViewById(R.id.txt_productName);
            EditText edit_modal= (EditText)findViewById(R.id.txt_purchasePrice);
            EditText edit_sell = (EditText) findViewById(R.id.txt_recSellPrice);
            EditText edit_stock = (EditText) findViewById(R.id.txt_productStock);

           if(edit_name.getText().toString().length()<1)
            {
                Toast.makeText(this,"Product name cannot be empty",Toast.LENGTH_SHORT).show();
            }
            else if (edit_modal.getText().toString().length()<1)
            {
                Toast.makeText(this,"Purchase price cannot be empty",Toast.LENGTH_SHORT).show();
            }
            else if (edit_sell.getText().toString().length()<1)
            {
                Toast.makeText(this,"Recommended sell price cannot be empty",Toast.LENGTH_SHORT).show();
            }
            else if (edit_stock.getText().toString().length()<1)
            {
                Toast.makeText(this,"Stock cannot be empty",Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(edit_modal.getText().toString()) > Integer.parseInt(edit_sell.getText().toString()))
             {
                 Toast.makeText(this,"you cannot ",Toast.LENGTH_SHORT).show();
             }
            else
           {
               Date date1=new Date(); // Current time


               System.out.println("CURRENT DATE IS "+date1);
               DBAdapter db = new DBAdapter(this);
               db.open();

               db.insertProduct(edit_name.getText().toString(),
                       Integer.parseInt(edit_modal.getText().toString()),
                       Integer.parseInt(edit_sell.getText().toString()),
                       Integer.parseInt(edit_stock.getText().toString()),
                       0,date1);

               Toast.makeText(this,""+edit_name.getText().toString()+" ADDED TO LIST",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(this, MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
               finish();

           }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
