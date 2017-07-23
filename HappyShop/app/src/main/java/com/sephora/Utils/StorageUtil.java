package com.sephora.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sephora.model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 27-Apr-16.
 */
public class StorageUtil {

    private static final String PREF_NAME = "happy_shop_pref";
    private static final String CART_LIST = "cart_list";

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void storeCartList(Context context, ArrayList<Product> list) {
        SharedPreferences preferences = getSharedPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String cartItems = gson.toJson(list);
        editor.putString(CART_LIST, cartItems);
        editor.commit();
    }


    public static  ArrayList<Product> getCartList(Context context) {
        SharedPreferences preferences = getSharedPreference(context);
        String jsonString = preferences.getString(CART_LIST, "");
        if (jsonString != null && jsonString.length() > 1) {
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<Product> cartList = gson.fromJson(jsonString, type);
            return cartList;
        }

        return null;
    }


}
