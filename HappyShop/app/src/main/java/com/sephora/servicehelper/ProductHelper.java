package com.sephora.servicehelper;

import android.app.ProgressDialog;
import android.content.Context;

import com.sephora.app.R;
import com.sephora.definitions.IProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public enum ProductHelper  implements IProduct {
    INSTANCE;

    private ProductNetworkHelper networkingHelper;
    private static ProgressDialog progressDialog;
    private Context context;
    public void setContext(Context context) {
        this.context = context;
        networkingHelper = new ProductNetworkHelper(context);
    }


    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(context, "", context.getString(R.string.loading), true);
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public Task<JSONObject> getProductList(String url) {
        return networkingHelper.getProductList(url).continueWith(new Continuation<JSONObject, JSONObject>() {
            @Override
            public JSONObject then(Task<JSONObject> task) throws Exception {
                final JSONObject jsonObject = task.getResult();
                return jsonObject;
            }
        });
    }

    @Override
    public Task<JSONObject> getProductDetail(String url) {
        showProgressDialog();
        return networkingHelper.getProductDetail(url).continueWith(new Continuation<JSONObject, JSONObject>() {
            @Override
            public JSONObject then(Task<JSONObject> task) throws Exception {
                final JSONObject jsonObject = task.getResult();
                dismissDialog();
                return jsonObject;
            }
        });
    }
}
