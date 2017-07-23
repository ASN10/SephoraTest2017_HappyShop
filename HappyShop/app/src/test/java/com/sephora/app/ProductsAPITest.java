package com.sephora.app;

import android.content.Context;

import com.sephora.translator.Translator;
import com.sephora.servicehelper.ClientHelper;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by nivedhitha.a on 7/21/17.
 */


public class ProductsAPITest  {

    @Mock
    private Context context;

    @Mock
    ClientHelper clientHelper;
    @Mock
    Translator translator;

    private static final int REQUEST_GET_PRODUCTS = 1;

    String url = AppConstants.BASE_URL +  AppConstants.GET_PRODUCTS_URL + "category=Makeup&page=1";
    String productDetailUrl = AppConstants.BASE_URL + "products/26.json";




    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientHelper = new ClientHelper();
        clientHelper.setContext(Mockito.mock(Context.class));


    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testProductsListNotNull() {
        try {

            System.out.print("url " + url);
            String response = clientHelper.get(url);
            System.out.print(response);
            Assert.assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testProductListSizeGreaterThanZero() {
        try {

            System.out.print("url " + url);
            String response = clientHelper.get(url);
            System.out.print(response);
            JSONObject responseobj = new JSONObject(response);
            JSONArray productsArray = responseobj.getJSONArray("products");
            Assert.assertNotEquals(productsArray.length(), 0);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testProductListSizeZero() {
        try {
            String emptyUrl = AppConstants.BASE_URL +  AppConstants.GET_PRODUCTS_URL + "category=Hairp&page=1";
            System.out.print("url " + url);
            String response = clientHelper.get(url);
            System.out.print(response);
            JSONObject responseobj = new JSONObject(response);
            JSONArray productsArray = responseobj.getJSONArray("products");
            Assert.assertEquals(productsArray.length(), 0);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testProductdetailNotNull() {
        try {

            System.out.print("url " + productDetailUrl);
            String response = clientHelper.get(url);
            System.out.print(response);
            JSONObject responseObj = new JSONObject(response);
            Assert.assertNotNull(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testProductdetailNull() {
        try {
            String noProductUrl = AppConstants.BASE_URL + "products/266.json";
            System.out.print("url " + productDetailUrl);
            String response = clientHelper.get(noProductUrl);
            System.out.print(response);
            JSONObject responseObj = new JSONObject(response);
            Assert.assertNull(responseObj);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
