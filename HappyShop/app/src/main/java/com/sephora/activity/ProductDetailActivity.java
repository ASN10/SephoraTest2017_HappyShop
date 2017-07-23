package com.sephora.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sephora.Utils.AppUtil;
import com.sephora.Utils.SimpleDialogFragment;
import com.sephora.Utils.StorageUtil;
import com.sephora.app.AppConstants;
import com.sephora.app.AppController;
import com.sephora.app.Extras;
import com.sephora.app.R;
import com.sephora.model.Product;
import com.sephora.model.ProductDetail;
import com.sephora.servicehelper.ProductsServiceHelper;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener, ProductsServiceHelper.ProductsServiceListener {

    private TextView productNameTextView, productPriceTextView, descriptionTextView, onSaleTextView;
    private ImageView productImageView;
    private Button addToCartButton;
    private static final int REQUEST_GET_PRODUCT = 2;
    private String productId = "1";
    private ArrayList<Product> cartList;
    private Product product;

    private int cartCount = 0;
    private static final String TAG_ALERT_FRAGMENT = "alert";
    public JSONObject  jsonresponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Product Detail");
        productId = getIntent().getStringExtra(Extras.EXTRAS_PRODUCT_ID);
        initViews();
        checkCart();
        if (AppUtil.checkIfInternetIsConnected(this)) {
            makeServiceCall(productId);
        } else {
            showSimpleAlert(getResources().getString(R.string.network_alert));
        }
    }

    /**
     * Bind views
     */
    private void initViews() {
        productNameTextView = (TextView) findViewById(R.id.product_name_textview);
        productPriceTextView = (TextView) findViewById(R.id.product_price_textview);
        descriptionTextView = (TextView) findViewById(R.id.product_description_textview);
        productImageView = (ImageView) findViewById(R.id.product_imageview);
        addToCartButton = (Button) findViewById(R.id.add_to_cart_button);
        onSaleTextView = (TextView) findViewById(R.id.under_sale_textview);
        addToCartButton.setOnClickListener(this);
    }

    /**
     * Make service call
     * @param productId
     */
    public void makeServiceCall(String productId) {
        String url = AppConstants.BASE_URL + AppConstants.GET_PRODUCT_URL;
        url = String.format(url, productId);
        Log.i("URL", url);
        ProductsServiceHelper productsServiceHelper = new ProductsServiceHelper(this);
        productsServiceHelper.setProductsServiceListener(this);
        productsServiceHelper.makeGetProductDetailServiceCall(url, REQUEST_GET_PRODUCT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        View count =  menu.findItem(R.id.action_cart).getActionView();
        MenuItem scan = menu.findItem(R.id.action_scan);
        scan.setVisible(false);
        TextView countTextView = (TextView) count.findViewById(R.id.badge_textview);
        if(cartCount > 0){
            countTextView.setText(String.valueOf(cartCount));
        }else{
            countTextView.setVisibility(View.GONE);
        }

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    /**
     * check cart size
     */
    public void checkCart(){
        ArrayList<Product> cartList = StorageUtil.getCartList(this);
        if(cartList == null || cartList.size() == 0){
            cartCount = 0;
        }else{
            cartCount = cartList.size();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCart();
        invalidateOptionsMenu();
    }

    @Override
    public void onClick(View v) {
        if (v == addToCartButton) {
            cartList = StorageUtil.getCartList(this);
            if (cartList == null || cartList.size() == 0) {
                cartList = new ArrayList<>();
            }
            cartList.add(product);
            StorageUtil.storeCartList(this, cartList);
            Toast.makeText(this, getResources().getString(R.string.item_added_to_cart), Toast.LENGTH_SHORT).show();
            checkCart();
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }else if(item.getItemId() == R.id.action_cart){

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Web service callback
     * @param requestCode
     * @param response
     */
    @Override
    public void onResponse(int requestCode, JSONObject response) {

        try {

            this.jsonresponse = response;
            if (requestCode == REQUEST_GET_PRODUCT) {

                    if (checkIfServiceResponseNotNull(response)) {
                        Gson gson = new Gson();
                        ProductDetail productDetail = gson.fromJson(response.toString(), ProductDetail.class);
                        product = productDetail.getProduct();
                        populateViews(product);
                    }

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * set data to the views
     * @param product
     */
    private void populateViews(Product product) {
        productNameTextView.setText(product.getName());
        String price = product.getPrice();
        int priceInNumber = 0;
        if(!price.equals("")){
            priceInNumber = Integer.parseInt(price)/100;
        }
        productPriceTextView.setText("Price : $ " + priceInNumber);
        //productPriceTextView.setText(product.getPrice());
        if(product.getDescription() != null && !product.getDescription().equals("")) {
            descriptionTextView.setText(product.getDescription());
        }else{
            descriptionTextView.setText(getString(R.string.product_description));
        }
        if(product.isUnderSale()) {
            onSaleTextView.setVisibility(View.VISIBLE);
        }else{
            onSaleTextView.setVisibility(View.GONE);
        }
        addToCartButton.setVisibility(View.VISIBLE);
        AppController.getInstance().getUniversalImageLoader().displayImage(product.getImageUrl(), productImageView);
    }

    /**
     * Error call back
     * @param requestCode
     * @param error
     */
    @Override
    public void onError(int requestCode, String error) {

        showSimpleAlert(error);
    }

    /**
     * Inflate alert dialog
     * @param message
     */
    private void showSimpleAlert(String message) {
        SimpleDialogFragment alert = new SimpleDialogFragment();
        Bundle args = new Bundle();
        args.putString(Extras.EXTRAS_ALERT_MESSAGE, message);
        alert.setArguments(args);
        alert.show(getSupportFragmentManager(), TAG_ALERT_FRAGMENT);

    }





    /**
     * check if web service response is null
     * @param response
     * @return
     */
    public static boolean checkIfServiceResponseNotNull(JSONObject response){
        if(response != null){
            return true;
        }else{
            return false;
        }

    }


}
