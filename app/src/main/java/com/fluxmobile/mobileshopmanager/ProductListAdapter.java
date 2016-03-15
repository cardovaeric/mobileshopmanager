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

import java.util.ArrayList;


public class ProductListAdapter extends BaseAdapter implements Filterable
{
    Context context;
    ArrayList<Product> productlist;
    LayoutInflater inflater;
    ArrayList<Product> FilteredList;
    ArrayList<Product> UnfilteredList;
    Filter filter;
    public ProductListAdapter(Context context, ArrayList<Product> productlist1)
    {
        this.context=context;
        this.productlist=productlist1;
        this.inflater = LayoutInflater.from(context);
        UnfilteredList = productlist;
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    productlist = (ArrayList<Product>)results.values;
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
                        String data =  UnfilteredList.get(i).getNama();

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


    @Override
    public int getCount() {
        return productlist.size();
    }

    public ArrayList<Product> getlist(){
        return productlist;
    }

    @Override
    public Object getItem(int position) {
        return productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=new ViewHolder();


        if(convertView == null)
        {
            convertView = this.inflater.inflate(R.layout.row_cell, parent, false);
            holder.productname=(TextView)convertView.findViewById(R.id.cell_productName);
            holder.hargajual=(TextView)convertView.findViewById(R.id.cell_hargaModal);
            holder.stok=(TextView)convertView.findViewById(R.id.cell_stock);
            TextView labelstok = (TextView)convertView.findViewById(R.id.textView);
            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/futura.ttf");
            holder.productname.setTypeface(tf);
            holder.hargajual.setTypeface(tf);
            holder.stok.setTypeface(tf);
            labelstok.setTypeface(tf);


            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        Product product = productlist.get(position);

        holder.productname.setText(product.getNama());
        String tempjual;
        String temp1;
        String temp2;
        String temp3;
        tempjual = product.getHargajual()+"";
        if(tempjual.length()>3)
        {
            if(tempjual.length()<=6) {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(0, tempjual.length() - 3);
                holder.hargajual.setText("IDR " + temp2 + "." + temp1);
            }
            else if(tempjual.length()>6)
            {
                temp1 = tempjual.substring(tempjual.length() - 3, tempjual.length());
                temp2 = tempjual.substring(tempjual.length() - 6, tempjual.length() - 3);
                temp3 = tempjual.substring(0, tempjual.length()-6);
                holder.hargajual.setText("IDR " + temp3+"."+temp2 + "." + temp1);
            }
        }
        else
        {
            holder.hargajual.setText("IDR " + product.getHargajual());
        }
        //holder.hargajual.setText("IDR "+product.getHargajual());
        holder.stok.setText(product.getStock()+"");

        return convertView;
    }



    public static class ViewHolder
    {
        TextView productname;
        TextView hargajual;
        TextView stok;
    }
}