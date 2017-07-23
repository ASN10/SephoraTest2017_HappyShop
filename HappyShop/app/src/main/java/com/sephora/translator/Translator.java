package com.sephora.translator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public class Translator {
    public JSONObject translateStringToJSONObj(String response) throws Exception {
        JSONObject jsonObject = new JSONObject(response);

        return jsonObject;
    }

    public JSONArray translateStringToJSONArray(String response) throws Exception {
        JSONArray jsonArray = new JSONArray(response);

        return jsonArray;
    }


}
