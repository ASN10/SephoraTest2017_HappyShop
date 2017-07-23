package com.sephora.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sephora.app.R;
import com.sephora.model.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public class CatergoryRecyclerViewAdapter extends RecyclerView.Adapter<CatergoryRecyclerViewAdapter.CategoryViewHolder> {


    private static ViewClickListener viewClickListener;
    private ArrayList<NavigationDrawerItem> categoryList;


    public CatergoryRecyclerViewAdapter(Context context, ArrayList<NavigationDrawerItem> categoryList){
        this.categoryList = categoryList;
    }

    @Override
    public CatergoryRecyclerViewAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_category, parent, false);

        CategoryViewHolder dataObjectHolder = new CategoryViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(CatergoryRecyclerViewAdapter.CategoryViewHolder holder, int position) {
        holder.categoryImageView.setImageResource(categoryList.get(position).getIcon());
        holder.categoryTextView.setText(categoryList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView categoryImageView;
        private TextView categoryTextView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
           categoryImageView = (ImageView) itemView.findViewById(R.id.category_imageview);
           categoryTextView = (TextView) itemView.findViewById(R.id.category_title_textview);
           itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            viewClickListener.onViewClicked(getAdapterPosition(), itemView);
        }





    }


    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }


    public interface ViewClickListener{
        void onViewClicked(int position, View v);
    }
}
