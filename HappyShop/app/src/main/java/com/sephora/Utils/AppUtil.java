package com.sephora.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sephora.app.R;
import com.sephora.model.NavigationDrawerItem;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 27-Apr-16.
 */
public class AppUtil {

    public static boolean checkIfInternetIsConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    private ArrayList<NavigationDrawerItem> setcategoriesDataSource(Context context){
        String[] categoryKeys = context.getResources().getStringArray(R.array.category_keys);
        ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList = new ArrayList<>();
        NavigationDrawerItem itemTitle = new NavigationDrawerItem();
        itemTitle.setCategoryName(context.getString(R.string.category_title));
        itemTitle.setIcon(R.mipmap.ic_launcher);
        itemTitle.setIsCategory(true);
        itemTitle.setCategoryKey("");
        navigationDrawerItemArrayList.add(itemTitle);

        NavigationDrawerItem itemMakeUp = new NavigationDrawerItem();
        itemMakeUp.setCategoryName(context.getString(R.string.make_up_title));
        itemMakeUp.setIcon(R.mipmap.ic_launcher);
        itemMakeUp.setIsCategory(false);
        itemMakeUp.setCategoryKey(categoryKeys[0]);
        navigationDrawerItemArrayList.add(itemMakeUp);

        NavigationDrawerItem itemSkincare = new NavigationDrawerItem();
        itemSkincare.setCategoryName(context.getString(R.string.skin_care_title));
        itemSkincare.setIcon(R.mipmap.ic_launcher);
        itemSkincare.setIsCategory(false);
        itemSkincare.setCategoryKey(categoryKeys[1]);
        navigationDrawerItemArrayList.add(itemSkincare);

        NavigationDrawerItem itemHair = new NavigationDrawerItem();
        itemHair.setCategoryName(context.getString(R.string.hair_title));
        itemHair.setIcon(R.mipmap.ic_launcher);
        itemHair.setIsCategory(false);
        itemHair.setCategoryKey(categoryKeys[2]);
        navigationDrawerItemArrayList.add(itemHair);

        NavigationDrawerItem itemTools = new NavigationDrawerItem();
        itemTools.setCategoryName(context.getString(R.string.tools_title));
        itemTools.setIcon(R.mipmap.ic_launcher);
        itemTools.setIsCategory(false);
        itemTools.setCategoryKey(categoryKeys[3]);
        navigationDrawerItemArrayList.add(itemTools);

        NavigationDrawerItem itemNails = new NavigationDrawerItem();
        itemTools.setCategoryName(context.getString(R.string.nails_title));
        itemTools.setIcon(R.mipmap.ic_launcher);
        itemTools.setIsCategory(false);
        itemTools.setCategoryKey(categoryKeys[4]);
        navigationDrawerItemArrayList.add(itemNails);

        NavigationDrawerItem itemBath = new NavigationDrawerItem();
        itemTools.setCategoryName(context.getString(R.string.bath_body));
        itemTools.setIcon(R.mipmap.ic_launcher);
        itemTools.setIsCategory(false);
        itemTools.setCategoryKey(categoryKeys[5]);
        navigationDrawerItemArrayList.add(itemTools);

        return navigationDrawerItemArrayList;
    }
}
