package com.sephora.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sephora.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivedhitha.a on 8/2/17.
 */

public class FilterAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> filtersList;
    private LayoutInflater inflater;

    public FilterAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context =  context;
        this.filtersList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.listitem_filter, parent, false);
            holder = new ViewHolder();
            holder.filterTextView = (TextView) view.findViewById(R.id.filter_textview);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.filterTextView.setText(getItem(position));
        return view;
    }



    class ViewHolder{
        TextView filterTextView;
    }



}
