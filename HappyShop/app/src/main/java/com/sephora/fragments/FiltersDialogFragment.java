package com.sephora.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sephora.adapter.FilterAdapter;
import com.sephora.app.R;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 8/2/17.
 */

public class FiltersDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private ListView filtersListView;
    private ArrayList<String> filtersArrayList;
    private FilterAdapter adapter;



    private ItemClickListener itemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_filters, container, false);
        filtersListView = (ListView) view.findViewById(R.id.filter_listview);
        filtersListView.setOnItemClickListener(this);
        filtersArrayList = new ArrayList<>();
        setDataSource();
        setAdapter();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(itemClickListener != null){
            itemClickListener.onitemClicked(filtersArrayList.get(position));
        }
        dismiss();
    }

    private void setDataSource(){
        filtersArrayList.add("Makeup");
        filtersArrayList.add("Skincare");
        filtersArrayList.add("Hair");
        filtersArrayList.add("Nails");

    }


    private void setAdapter(){
        adapter = new FilterAdapter(getActivity(), R.layout.listitem_filter, filtersArrayList);
        filtersListView.setAdapter(adapter);
    }


    public interface ItemClickListener{
        void onitemClicked(String filter);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
