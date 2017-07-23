package com.sephora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sephora.app.R;
import com.sephora.model.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 4/25/2016.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    private ArrayList<NavigationDrawerItem> itemList;
    private Context context;
    private LayoutInflater inflater;
    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerItem> itemList){
        this.context = context;
        this.itemList = itemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(itemList.get(position).isCategory()){
            return 0;
        }else{
            return 1;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if(view == null){
            if(getItemViewType(position) == 0) {
                view = inflater.inflate(R.layout.listitem_category_section, parent, false);
            }else{
                view = inflater.inflate(R.layout.listitem_drawer, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.categoryNameTextView = (TextView) view.findViewById(R.id.title);
            viewHolder.categoryIconImageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.parentLayout = (RelativeLayout) view.findViewById(R.id.listitem_drawer_layout);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.categoryIconImageView.setImageResource(itemList.get(position).getIcon());
        viewHolder.categoryNameTextView.setText(itemList.get(position).getCategoryName());
        if(itemList.get(position).isCategory()){
           viewHolder.parentLayout.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
           viewHolder.parentLayout.setEnabled(false);
        }
        return view;
    }


    public class ViewHolder {
        private TextView categoryNameTextView;
        private ImageView categoryIconImageView;
        private RelativeLayout parentLayout;
    }

}
