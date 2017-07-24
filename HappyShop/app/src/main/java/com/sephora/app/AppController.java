package com.sephora.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.sephora.Utils.LogUtils;
import com.sephora.Utils.LruBitmapCache;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class AppController extends Application {


    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;
    private com.nostra13.universalimageloader.core.ImageLoader universalImageLoader;
    private DisplayImageOptions profilePicDisplayOptions;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
        LogUtils.setEnabled(true);
    }

    public com.nostra13.universalimageloader.core.ImageLoader getUniversalImageLoader() {
        if (universalImageLoader == null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(R.drawable.thumbnail)
                    .showImageOnLoading(R.drawable.loading_image)
                    .showImageOnFail(R.drawable.thumbnail)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(options)
                    .threadPoolSize(3)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .memoryCacheSize(1500000) // 1.5 Mb
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .build();
            universalImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            universalImageLoader.init(config);
        }
        return universalImageLoader;
    }




    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public  void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

}
