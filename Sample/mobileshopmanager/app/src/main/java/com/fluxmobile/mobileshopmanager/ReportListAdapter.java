package com.fluxmobile.mobileshopmanager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fluxmobile.mobilemarketmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportListAdapter extends BaseAdapter implements Filterable
{
    Context context;
    ArrayList<ProductReport> reportArrayList;
    ArrayList<ProductReport> UnfilteredList;
    ArrayList<ProductReport> FilteredList;
    LayoutInflater layoutInflater;
    Date datenotstring;

    public ReportListAdapter(Context context, ArrayList<ProductReport> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        this.layoutInflater = layoutInflater.from(context);
        UnfilteredList = reportArrayList;

    }

    @Override
    public int getCount() {
        return reportArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/futura.ttf");
        ViewHolder holder=new ViewHolder();

        if(convertView==null)
        {
            convertView = this.layoutInflater.inflate(R.layout.report_cell,parent,false);

            holder.product_name=(TextView)convertView.findViewById(R.id.report_cell_name);
            holder.product_date=(TextView)convertView.findViewById(R.id.report_cell_date);
            holder.product_quantity=(TextView)convertView.findViewById(R.id.report_cell_quantity);
            holder.product_profit=(TextView)convertView.findViewById(R.id.report_cell_profit);
            holder.lbl_profit=(TextView)convertView.findViewById(R.id.report_lbl_profit);

            holder.product_name.setTypeface(tf);
            holder.product_date.setTypeface(tf);
            holder.product_profit.setTypeface(tf);
            holder.product_quantity.setTypeface(tf);
            holder.lbl_profit.setTypeface(tf);

            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        ProductReport report = reportArrayList.get(position);
        holder.product_name.setText(report.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            datenotstring = formatter.parse(report.getDate());
            System.out.println("AAAAAAAAAAa"+datenotstring);
            System.out.println(formatter.format(datenotstring));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        holder.product_date.setText(""+formatter.format(datenotstring));
        holder.product_quantity.setText("Sold : "+report.getQuantity());



        String tempjual;
        String temp1;
        String temp2;
        String temp3;
        tempjual = ""+report.getProfit();
        if(tempjual.length()>3)
        {
            if(tempjual.length()<=6) {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(0, tempjual.length() - 3);
                holder.product_profit.setText("IDR " + temp2 + "." + temp1);
            }
            else if(tempjual.length()>6)
            {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                temp3 = tempjual.substring(0, tempjual.length()-6);
                holder.product_profit.setText("IDR " + temp3+"."+temp2 + "." + temp1);
            }
        }
        else
        {
            holder.product_profit.setText("IDR " + report.getProfit());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                reportArrayList = (ArrayList<ProductReport>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() < 1) {
                    // No filter implemented we return all the list
                    results.values = UnfilteredList;
                    results.count = UnfilteredList.size();

                } else {
                    FilteredList = new ArrayList<>();
                    for (int i = 0; i < UnfilteredList.size(); i++) {
                        String data =  UnfilteredList.get(i).getName();

                        if (data.toLowerCase().contains(
                                constraint.toString().toLowerCase())) {
                            FilteredList.add(UnfilteredList.get(i));
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }
        };

        return filter;
    }

    public static class ViewHolder
    {
        TextView product_name,product_quantity,product_profit,product_date,lbl_profit;

    }
}
