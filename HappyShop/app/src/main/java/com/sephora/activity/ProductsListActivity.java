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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.paging.gridview.PagingGridView;
import com.sephora.Utils.AppUtil;
import com.sephora.Utils.SimpleDialogFragment;
import com.sephora.Utils.StorageUtil;
import com.sephora.adapter.ProductsListGridAdapter;
import com.sephora.app.AppConstants;
import com.sephora.app.Extras;
import com.sephora.app.R;
import com.sephora.fragments.FiltersDialogFragment;
import com.sephora.model.Product;
import com.sephora.model.ProductsList;
import com.sephora.servicehelper.ProductsServiceHelper;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 *
 */


/**
 * Adding menu on the actionbar
 * Display List of Categories
 * On User selection, make the API call and refresh the data source
 */
public class ProductsListActivity extends AppCompatActivity implements ProductsServiceHelper.ProductsServiceListener, AdapterView.OnItemClickListener, View.OnClickListener, FiltersDialogFragment.ItemClickListener {

    private PagingGridView pagingGridView;
    private int pageNumber = 1;
    private String category = "Makeup";
    private static final int REQUEST_GET_PRODUCTS = 1;
    private Toolbar toolbar;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductsListGridAdapter adapter;
    private int cartCount = 0;
    private TextView emptyTextView;
    public static final String TAG_ALERT_FRAGMENT = "alert";
    public JSONObject jsonresponse;
    private String categoryName ="";
    private ProgressDialog dialog;
    private Button filterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        initActionbar();
        initViews();
    }

    private void initActionbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categoryName = getIntent().getStringExtra(Extras.EXTRAS_CATEGORY_NAME);
        setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initViews() {
        pagingGridView = (PagingGridView) findViewById(R.id.paging_grid_view);
        pagingGridView.setOnItemClickListener(this);
        pagingGridView.setHasMoreItems(true);
        emptyTextView = (TextView) findViewById(R.id.empty_textview);
        category = getIntent().getStringExtra(Extras.EXTRAS_CATEGORY);
        checkCart();
        if (AppUtil.checkIfInternetIsConnected(this)) {
            showProgressDialog();
            makeServiceCall(category, 1);
        } else {
            showSimpleAlert(getResources().getString(R.string.network_alert));
        }
        filterButton = (Button) findViewById(R.id.filter_button);
        filterButton.setOnClickListener(this);
        initPagination();

    }

    private void initPagination() {
        pagingGridView.setPagingableListener(new PagingGridView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AppUtil.checkIfInternetIsConnected(ProductsListActivity.this)) {
                    makeServiceCall(category, pageNumber);
                } else {
                    pagingGridView.onFinishLoading(false, null);
                    showSimpleAlert(getResources().getString(R.string.network_alert));
                }
            }
        });
    }

    public void checkCart() {
        ArrayList<Product> cartList = StorageUtil.getCartList(this);
        if (cartList == null || cartList.size() == 0) {
            cartCount = 0;
        } else {
            cartCount = cartList.size();
        }

    }

    public void makeServiceCall(String category, int pageNumber) {

        if (category.contains(" ")) {
            category = category.replace(" ", "%20");
        }
        String url = AppConstants.BASE_URL + AppConstants.GET_PRODUCTS_URL + "category=" + category + "&page=" + pageNumber;
        ProductsServiceHelper productsServiceHelper = new ProductsServiceHelper(this);
        productsServiceHelper.setProductsServiceListener(this);
        productsServiceHelper.makeGetProductsListServiceCall(url, REQUEST_GET_PRODUCTS);
    }

    @Override
    public void onResponse(int requestCode, JSONObject response) {
        dismissProgressDialog();
        this.jsonresponse = response;
        if (requestCode == REQUEST_GET_PRODUCTS) {
          //  Log.i("response", response.toString());
            try {
                if (checkIfServiceResponseNotNull(response)) {
                    Gson gson = new Gson();
                    ProductsList productsList = gson.fromJson(response.toString(), ProductsList.class);
                    if (productsList.getProductArrayList().size() > 0) {
                        pagingGridView.setVisibility(View.VISIBLE);
                        emptyTextView.setVisibility(View.GONE);
                        pageNumber++;
                        products.addAll(productsList.getProductArrayList());
                        if (adapter == null) {
                            adapter = new ProductsListGridAdapter(this, R.layout.products_grid_item, products);
                            pagingGridView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        pagingGridView.onFinishLoading(true, productsList.getProductArrayList());
                    } else {
                        pagingGridView.onFinishLoading(false, null);
                        if (products.size() == 0) {
                            pagingGridView.setVisibility(View.GONE);
                            emptyTextView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        View count = menu.findItem(R.id.action_cart).getActionView();
        MenuItem scan = menu.findItem(R.id.action_scan);
        scan.setVisible(false);

        TextView countTextView = (TextView) count.findViewById(R.id.badge_textview);
        if (cartCount > 0) {
            countTextView.setText(String.valueOf(cartCount));
        } else {
            countTextView.setVisibility(View.GONE);
        }
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsListActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCart();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            return true;

        } else if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onError(int requestCode, String error) {
        dismissProgressDialog();
        pagingGridView.onFinishLoading(false, null);
        if(pageNumber == 1) {
            showSimpleAlert(getResources().getString(R.string.service_error));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Extras.EXTRAS_PRODUCT_ID, products.get(position).getId());
        startActivity(intent);
    }

    private void showSimpleAlert(String message) {
        SimpleDialogFragment alert = new SimpleDialogFragment();
        Bundle args = new Bundle();
        args.putString(Extras.EXTRAS_ALERT_MESSAGE, message);
        alert.setArguments(args);
        alert.show(getSupportFragmentManager(), TAG_ALERT_FRAGMENT);

    }


    public static boolean checkIfServiceResponseNotNull(JSONObject response) {
        if (response != null) {
            return true;
        } else {
            return false;
        }
    }

    public static int checkIfProductsSizeNotEmpty(JSONObject response) {
        if (response != null) {
            Gson gson = new Gson();
            ProductsList productsList = gson.fromJson(response.toString(), ProductsList.class);
            return productsList.getProductArrayList().size();
        }

        return 0;
    }


    private void showProgressDialog() {
        dialog = ProgressDialog.show(this, "", "Loading...", true);
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }


    @Override
    public void onClick(View v) {
        if(v == filterButton){
            FiltersDialogFragment fragment = new FiltersDialogFragment();
            fragment.setItemClickListener(this);
            fragment.show(getSupportFragmentManager(), "filter_dialog");
        }
    }

    @Override
    public void onitemClicked(String filter) {
        products.clear();
        makeServiceCall(filter, 1);
    }
}
