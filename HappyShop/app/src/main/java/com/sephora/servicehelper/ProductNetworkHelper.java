package com.sephora.servicehelper;

import android.content.Context;

import com.sephora.Utils.LogUtils;
import com.sephora.translator.Translator;
import com.sephora.definitions.IProduct;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public class ProductNetworkHelper extends ClientHelper implements IProduct {

    Translator translator = new Translator();

    private Context context;

    public ProductNetworkHelper(Context context) {
        this.context = context;
        setContext(this.context);
    }

    @Override
    public Task<JSONObject> getProductList(final String url) {
        return Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String responseString = get(url);
                LogUtils.i("Response : " + responseString);
                return translator.translateStringToJSONObj(responseString);
            }
        });
    }

    @Override
    public Task<JSONObject> getProductDetail(final String url) {
        return Task.callInBackground(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String responseString = get(url);
                LogUtils.i("Response : " + responseString);
                return translator.translateStringToJSONObj(responseString);
            }
        });
    }
}
