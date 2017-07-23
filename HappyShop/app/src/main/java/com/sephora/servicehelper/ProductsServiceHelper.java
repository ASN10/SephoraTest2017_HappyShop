package com.sephora.servicehelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.sephora.Utils.LogUtils;
import com.sephora.app.AppController;
import com.sephora.app.R;
import com.sephora.model.Product;
import com.sephora.model.ProductsList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class ProductsServiceHelper  {

    private static final String GET_PRODUCTS_LIST_TAG = "get_products_list_tag";
    private static final String GET_PRODUCTS_DETAIL_TAG = "get_product_detail_tag";
    private final Context context;


    private ProductsServiceListener productsServiceListener;
    private ProgressDialog dialog;



    public ProductsServiceHelper(Context context) {
        this.context = context;
    }

    public void makeGetProductsListServiceCall(String url, final int requestCode) {

        ProductHelper.INSTANCE.setContext(context);
        ProductHelper.INSTANCE.getProductList(url).continueWith(new Continuation<JSONObject, Object>() {
            @Override
            public Object then(Task<JSONObject> task) throws Exception {

                try {
                    JSONObject result = task.getResult();
                    if(result != null) {
                        LogUtils.i("response in helper", result.toString());
                        productsServiceListener.onResponse(requestCode, result);
                    }else if(result != null && result.has("error")){
                        productsServiceListener.onError(requestCode, context.getString(R.string.service_error));

                    }else{
                        productsServiceListener.onError(requestCode, context.getString(R.string.empty_text));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    productsServiceListener.onError(requestCode, context.getString(R.string.service_error));
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public void makeGetProductDetailServiceCall(String url, final int requestCode) {

        ProductHelper.INSTANCE.setContext(context);
        ProductHelper.INSTANCE.getProductDetail(url).continueWith(new Continuation<JSONObject, Object>() {
            @Override
            public Object then(Task<JSONObject> task) throws Exception {

                try {
                    JSONObject result = task.getResult();
                    if(result != null && result.has("product") && result.getJSONObject("product").length()>0) {
                        LogUtils.i("response in helper", result.toString());
                        productsServiceListener.onResponse(requestCode, result);
                    }else if(result != null && result.has("status") && result.getString("status").equals("404")){
                        productsServiceListener.onError(requestCode, context.getString(R.string.product_not_found));
                    }else{
                        productsServiceListener.onError(requestCode, context.getString(R.string.empty_text));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    productsServiceListener.onError(requestCode, context.getString(R.string.service_error));
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    public interface ProductsServiceListener {
        void onResponse(int requestCode, JSONObject response);
        void onError(int requestCode, String error);
    }

    public void setProductsServiceListener(ProductsServiceListener productsServiceListener) {
        this.productsServiceListener = productsServiceListener;
    }

    private void showProgressDialog() {
        dialog = ProgressDialog.show(context, "", "Loading...", true);
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }



}
