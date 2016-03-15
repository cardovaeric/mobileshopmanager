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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fluxmobile.mobilemarketmanager.R;

import java.util.ArrayList;
import java.util.Date;

public class ReportActivity extends ActionBarActivity {


    TextView totalProfit;
    DBAdapter db;
    ArrayList<ProductReport> arrayList;
    ListView listView;
    ReportListAdapter listAdapter;
    int total=0;
    Dialog dialog;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e8561f")));
        setTitle("Sales Report");

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        tf = Typeface.createFromAsset(this.getAssets(), "fonts/futura.ttf");
        ((TextView)v.findViewById(R.id.title)).setText(this.getTitle().toString());
        ((TextView)v.findViewById(R.id.title)).setTypeface(tf);
        ReportActivity.this.getSupportActionBar().setCustomView(v);
        ((TextView)findViewById(R.id.report_lbl_profit)).setTypeface(tf);
        ((TextView)findViewById(R.id.textView2)).setTypeface(tf);
        loadList();


    }

    public void loadList()
    {
        db = new DBAdapter(this);
        db.open();
        listView = (ListView)findViewById(R.id.report_listView);
        arrayList = new ArrayList<ProductReport>();
        listAdapter = new ReportListAdapter(this,arrayList);
        listView.setAdapter(listAdapter);
        totalProfit = (TextView)findViewById(R.id.report_lbl_profit);
        Cursor cursor = db.selectReport();
        if(cursor.moveToFirst())
        {
            do{
                String tempdate = cursor.getString(0);
                String tempname = cursor.getString(1);
                int tempqty = cursor.getInt(2);
                int tempprofit = cursor.getInt(3);


                total+=tempprofit;
                ProductReport reporttemp = new ProductReport(tempname,tempdate,tempqty,tempprofit);
                arrayList.add(reporttemp);
            }while (cursor.moveToNext());
            ;


            String temp1;
            String temp2;
            String temp3;
            String tempjual=(""+total);
            if(tempjual.length()>3)
            {
                if(tempjual.length()<=6) {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(0, tempjual.length() - 3);
                    totalProfit.setText("IDR " + temp2 + "." + temp1);
                }
                else if(tempjual.length()>6)
                {
                    temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                    temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                    temp3 = tempjual.substring(0, tempjual.length()-6);
                    totalProfit.setText("IDR " + temp3+"."+temp2 + "." + temp1);
                }
            }
            else
            {
                totalProfit.setText("IDR " + total);
            }

            cursor.close();
            db.close();

        }

    }

    public void initDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report_erase);

        TextView report_lbl_erase,report_lbl_blank,report_lbl_sure;
        final Button report_btn_erase,report_btn_cancel;
        report_lbl_erase = (TextView)dialog.findViewById(R.id.report_lbl_erase);
        report_lbl_blank = (TextView)dialog.findViewById(R.id.report_lbl_blank);
        report_lbl_sure = (TextView)dialog.findViewById(R.id.report_lbl_sure);
        report_btn_erase = (Button) dialog.findViewById(R.id.report_erase);
        report_btn_cancel = (Button) dialog.findViewById(R.id.report_cancel);
        report_lbl_erase.setTypeface(tf);
        report_lbl_blank.setTypeface(tf);
        report_lbl_sure.setTypeface(tf);
        report_btn_cancel.setTypeface(tf);
        report_btn_erase.setTypeface(tf);


        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==report_btn_erase)
                {

                    Toast.makeText(getApplicationContext(), "sales report has been erased", Toast.LENGTH_SHORT).show();
                    db.open();
                    db.deleteAllReport();
                    total=0;
                    totalProfit.setText("IDR 0");
                    db.close();
                    dialog.dismiss();
                    loadList();

                }
                else if(v==report_btn_cancel)
                {
                    dialog.dismiss();
                }
            }
        };
        report_btn_cancel.setOnClickListener(myListener);
        report_btn_erase.setOnClickListener(myListener);
        dialog.setCancelable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        android.support.v7.widget.SearchView searches = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search_report).getActionView();

        searches.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searches.setIconifiedByDefault(true);

        android.support.v7.widget.SearchView.OnQueryTextListener textChangeListener = new android.support.v7.widget.SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextChange(String newText)
            {

                // this is your adapter that will be filtered
                listAdapter.getFilter().filter(newText);
                System.out.println("on text change text: "+newText);
                return true;

            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                listAdapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);

                return true;

            }

        };

        searches.setOnQueryTextListener(textChangeListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(item.getItemId()==R.id.action_delete_report)
        {
            initDialog();
            dialog.show();
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
