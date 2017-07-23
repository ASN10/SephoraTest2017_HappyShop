package com.sephora.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.sephora.Utils.LogUtils;
import com.sephora.Utils.StorageUtil;
import com.sephora.activity.CartActivity;
import com.sephora.activity.ProductDetailActivity;
import com.sephora.activity.ProductsListActivity;
import com.sephora.adapter.NavigationDrawerAdapter;
import com.sephora.fragments.HomeFragment;
import com.sephora.model.NavigationDrawerItem;
import com.sephora.model.Product;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private GestureDetector mGestureDetector;
    private FrameLayout containerLayout;
    private int cartCount=0;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Happy Shop");

        initViews();
        View header = getLayoutInflater().inflate(R.layout.drawer_header, drawerListView, false);
        drawerListView.addHeaderView(header);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, //nav menu toggle icon
                R.string.openDrawer, // nav drawer open - description for accessibility
                R.string.closeDrawer // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mGestureDetector = new GestureDetector(LandingActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        setDataSource();
        setAdapter();
        inflateFragment();
        checkCart();


        qrScan = new IntentIntegrator(this);

        qrScan.setCameraId(0);  // Use a specific camera of the device

        qrScan.setBeepEnabled(false);

        qrScan.setPrompt("Scan a QR code");

        qrScan.setBarcodeImageEnabled(true);



    }

    /**
     * Check cart size
     */
    public void checkCart(){
        ArrayList<Product> cartList = StorageUtil.getCartList(this);
        if(cartList == null || cartList.size() == 0){
            cartCount = 0;
        }else{
            cartCount = cartList.size();
        }

    }

    /**
     * Bind views
     */
    private void initViews(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.list_slidermenu);
        containerLayout = (FrameLayout) findViewById(R.id.frame_container);

    }

    private void inflateFragment(){
        HomeFragment fragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame_container, fragment,  "home_fragment").commit();
    }


    private void setDataSource(){
        String[] categoryKeys = getResources().getStringArray(R.array.category_keys);
        navigationDrawerItemArrayList = new ArrayList<>();
        NavigationDrawerItem itemTitle = new NavigationDrawerItem();
        itemTitle.setCategoryName(getString(R.string.category_title));
        itemTitle.setIcon(R.mipmap.ic_launcher);
        itemTitle.setIsCategory(true);
        itemTitle.setCategoryKey("");
        navigationDrawerItemArrayList.add(itemTitle);

        NavigationDrawerItem itemMakeUp = new NavigationDrawerItem();
        itemMakeUp.setCategoryName(getString(R.string.make_up_title));
        itemMakeUp.setIcon(R.mipmap.ic_launcher);
        itemMakeUp.setIsCategory(false);
        itemMakeUp.setCategoryKey(categoryKeys[0]);
        navigationDrawerItemArrayList.add(itemMakeUp);

        NavigationDrawerItem itemSkincare = new NavigationDrawerItem();
        itemSkincare.setCategoryName(getString(R.string.skin_care_title));
        itemSkincare.setIcon(R.mipmap.ic_launcher);
        itemSkincare.setIsCategory(false);
        itemSkincare.setCategoryKey(categoryKeys[1]);
        navigationDrawerItemArrayList.add(itemSkincare);

        NavigationDrawerItem itemHair = new NavigationDrawerItem();
        itemHair.setCategoryName(getString(R.string.hair_title));
        itemHair.setIcon(R.mipmap.ic_launcher);
        itemHair.setIsCategory(false);
        itemHair.setCategoryKey(categoryKeys[2]);
        navigationDrawerItemArrayList.add(itemHair);

        NavigationDrawerItem itemTools = new NavigationDrawerItem();
        itemTools.setCategoryName(getString(R.string.tools_title));
        itemTools.setIcon(R.mipmap.ic_launcher);
        itemTools.setIsCategory(false);
        itemTools.setCategoryKey(categoryKeys[3]);
        navigationDrawerItemArrayList.add(itemTools);


        NavigationDrawerItem itemNails = new NavigationDrawerItem();
        itemNails.setCategoryName(getString(R.string.nails_title));
        itemNails.setIcon(R.mipmap.ic_launcher);
        itemNails.setIsCategory(false);
        itemNails.setCategoryKey(categoryKeys[4]);
        navigationDrawerItemArrayList.add(itemNails);

        NavigationDrawerItem itemBath = new NavigationDrawerItem();
        itemBath.setCategoryName(getString(R.string.bath_body));
        itemBath.setIcon(R.mipmap.ic_launcher);
        itemBath.setIsCategory(false);
        itemBath.setCategoryKey(categoryKeys[5]);
        navigationDrawerItemArrayList.add(itemBath);


    }

    private void setAdapter(){
        navigationDrawerAdapter = new NavigationDrawerAdapter(this, navigationDrawerItemArrayList);
        drawerListView.setAdapter(navigationDrawerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        View count =  menu.findItem(R.id.action_cart).getActionView();

        TextView countTextView = (TextView) count.findViewById(R.id.badge_textview);
        if(cartCount > 0){
            countTextView.setText(String.valueOf(cartCount));
        }else{
            countTextView.setVisibility(View.GONE);
        }
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, CartActivity.class);
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
        if (id == R.id.action_scan) {
            qrScan.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Navigation drawer item click listener
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
          if(position !=0) {
                NavigationDrawerItem item = (NavigationDrawerItem) parent.getAdapter().getItem(position);
                selectItem(item);
            }
        }
    }


    /**
     * Method to handle navigation drawer item selection
     * @param item
     */
    private void selectItem(NavigationDrawerItem item){
        if(checkIfCategoryKeyIsPresent(item.getCategoryKey())) {
            Intent intent = new Intent(this, ProductsListActivity.class);
            Log.i("key", item.getCategoryKey());
            intent.putExtra(Extras.EXTRAS_CATEGORY, item.getCategoryKey());
            intent.putExtra(Extras.EXTRAS_CATEGORY_NAME, item.getCategoryName());
            startActivity(intent);
        }
    }

    /**
     * check if the item selected from the navigation drawer is a category header or the section item
     * @param category
     * @return
     */
    public boolean checkIfCategoryKeyIsPresent(String category){
        if(category.equals("")) {
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {

            } else {
                //if qr contains data
                String url = result.getContents();
                LogUtils.i("result ", url) ;
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra(Extras.EXTRAS_PRODUCT_ID, url);
                startActivity(intent);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
