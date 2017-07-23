package com.sephora.servicehelper;

import android.content.Context;

import com.mikepenz.iconics.utils.Utils;
import com.sephora.Utils.LogUtils;
import com.sephora.app.R;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nivedhitha.a on 7/22/17.
 */

public class ClientHelper {
    private OkHttpClient clientBuilder = getUnsafeOkHttpClient();
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /*public void setupCache(Context context) {
        try {
            File httpCacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
            HttpResponseCache cache = new HttpResponseCache(httpCacheDirectory, 20 * 1024);
            client.setResponseCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.sslSocketFactory(sslSocketFactory);
//            builder.hostnameVerifier(hostnameVerifier);
            builder.protocols(Arrays.asList(Protocol.HTTP_1_1));
            builder.connectTimeout(10, TimeUnit.SECONDS) .writeTimeout(30, TimeUnit.SECONDS) .readTimeout(30, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String url) throws IOException {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            LogUtils.i("Service Url : " + url);
            Response response = clientBuilder.newCall(request).execute();
            int responseCode = response.code();
            LogUtils.i("response code " + responseCode);
            return response.body().string();

    }
}
