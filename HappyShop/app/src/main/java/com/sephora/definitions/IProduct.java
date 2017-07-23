package com.sephora.definitions;

import org.json.JSONObject;

import bolts.Task;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public interface IProduct {

    public Task<JSONObject> getProductList(String url);

    public Task<JSONObject> getProductDetail(String url);
}
