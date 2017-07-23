package com.sephora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.sephora.app.R;
import com.sephora.model.NavigationDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class CategoryAdapter extends ArrayAdapter<NavigationDrawerItem> {

    private Context context;
    private ArrayList<NavigationDrawerItem> categoryList;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, int resource, ArrayList<NavigationDrawerItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.categoryList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.listitem_category, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.categoryImageView = (ImageView) view.findViewById(R.id.category_imageview);
            viewHolder.categoryTextView = (TextView) view.findViewById(R.id.category_title_textview);
            view.setTag(viewHolder);

        } else {
             viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.categoryImageView.setImageResource(getItem(position).getIcon());
        viewHolder.categoryTextView.setText(getItem(position).getCategoryName());
        return view;
    }


    private class ViewHolder {
        private ImageView categoryImageView;
        private TextView categoryTextView;
    }
}
