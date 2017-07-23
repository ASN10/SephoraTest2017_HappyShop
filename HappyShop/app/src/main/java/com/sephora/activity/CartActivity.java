package com.sephora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sephora.Utils.SimpleDialogFragment;
import com.sephora.Utils.StorageUtil;
import com.sephora.app.Extras;
import com.sephora.app.R;
import com.sephora.model.Product;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 4/29/2016.
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG_ALERT_FRAGMENT = "alert" ;
    private Button clearCartButton;
    private int cartCount = 0;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
    }

    /**
     * Bind views
     */
    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clearCartButton = (Button) findViewById(R.id.clear_cart_button);
        clearCartButton.setOnClickListener(this);
        emptyTextView = (TextView) findViewById(R.id.cart_empty_textview);
        ArrayList<Product> productArrayList = StorageUtil.getCartList(this);
        if(productArrayList != null && productArrayList.size() >0) {
            clearCartButton.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }else{
            clearCartButton.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        checkCart();
    }


    /**
     * Button callback
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == clearCartButton) {
            ArrayList<Product> productArrayList = StorageUtil.getCartList(this);
            if (productArrayList != null && productArrayList.size() > 0) {
                productArrayList.clear();
                StorageUtil.storeCartList(this, productArrayList);
            } else {
                showSimpleAlert(getResources().getString(R.string.cart_list_empty_message));
            }

            checkCart();
            invalidateOptionsMenu();

            if (productArrayList != null && productArrayList.size() > 0) {
                clearCartButton.setVisibility(View.VISIBLE);
                emptyTextView.setVisibility(View.GONE);
            } else {
                clearCartButton.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.VISIBLE);

            }
        }
    }

    /**
     * Inflate alert dialog
     * @param message
     */
    private void showSimpleAlert(String message){
        SimpleDialogFragment alert = new SimpleDialogFragment();
        Bundle args = new Bundle();
        args.putString(Extras.EXTRAS_ALERT_MESSAGE, message);
        alert.setArguments(args);
        alert.show(getSupportFragmentManager(), TAG_ALERT_FRAGMENT);

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

    /**
     * Handle badge view
     * @param menu
     * @return
     */
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

        return true;
    }


    /**
     * Back arrow action is handled here
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
