package com.sephora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.gridview.PagingBaseAdapter;
import com.sephora.app.AppController;
import com.sephora.app.R;
import com.sephora.model.NavigationDrawerItem;
import com.sephora.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class ProductsListGridAdapter extends PagingBaseAdapter {

    private Context context;
    private ArrayList<Product> categoryList;
    private LayoutInflater inflater;
    public ProductsListGridAdapter(Context context, int resource, ArrayList<Product> objects) {

        this.context = context;
        this.categoryList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.products_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.categoryImageView = (ImageView) view.findViewById(R.id.category_imageview);
            viewHolder.categoryTextView = (TextView) view.findViewById(R.id.category_title_textview);
            viewHolder.priceTextView = (TextView) view.findViewById(R.id.product_price_textview);
            viewHolder.underSaleTextView = (TextView) view.findViewById(R.id.under_sale_textview);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

           viewHolder.categoryTextView.setText(categoryList.get(position).getName());
           String price = categoryList.get(position).getPrice();
           int priceInNumber = 0;
           if(!price.equals("")){
                priceInNumber = Integer.parseInt(price)/100;
           }
           viewHolder.priceTextView.setText("$ " + priceInNumber);
           if(categoryList.get(position).isUnderSale()) {
               viewHolder.underSaleTextView.setVisibility(View.VISIBLE);
           }else{
               viewHolder.underSaleTextView.setVisibility(View.INVISIBLE);
           }
           AppController.getInstance().getUniversalImageLoader().
                   displayImage(categoryList.get(position).getImageUrl(), viewHolder.categoryImageView);
        return view;
    }


    private class ViewHolder {
        private ImageView categoryImageView;
        private TextView categoryTextView, priceTextView, underSaleTextView;
    }
}
