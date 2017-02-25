package com.chailijun.mtime.api;

import android.os.Build;
import android.webkit.WebSettings;

import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManage {

    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(MtimeApp.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .removeHeader("User-Agent")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    public static ApiManage apiManage;
    private static File httpCacheDirectory = new File(MtimeApp.getContext().getCacheDir(), "mtime_Cache");
    private static Cache cache = new Cache(httpCacheDirectory, 1024 * 1024 * 100);//100M
    private Object monitor = new Object();
    private static final long DEFAULT_TIMEOUT = 5000;
    private static OkHttpClient mOkHttpClient;
    public CommApi commApi;
    private HomeApi homeApi;
    private HomeStrApi homeStrApi;
    private TicketApi ticketApi;
    private MallApi mallApi;

    private MyHostApi myHostApi;

    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (ApiManage.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .cache(cache)
                            .build();
                }
            }
        }

    }

    public static ApiManage getInstence() {
        if (apiManage == null) {
            synchronized (ApiManage.class) {
                if (apiManage == null) {
                    apiManage = new ApiManage();
                }
            }
        }
        return apiManage;
    }

    public static  OkHttpClient getOkHttpClient(){
        initOkHttpClient();
        return mOkHttpClient;
    }

    public CommApi getCommApi() {

        if (commApi == null) {
            synchronized (monitor) {
                if (commApi == null) {
                    initOkHttpClient();
                    commApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.COMM_API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(CommApi.class);
                }
            }
        }
        return commApi;
    }

    public HomeApi getHomeApi() {

        if (homeApi == null) {
            synchronized (monitor) {
                if (homeApi == null) {
                    initOkHttpClient();
                    homeApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(HomeApi.class);
                }
            }
        }
        return homeApi;
    }

    public HomeStrApi getHomeStrApi() {

        if (homeStrApi == null) {
            synchronized (monitor) {
                if (homeStrApi == null) {
                    initOkHttpClient();
                    homeStrApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(ScalarsConverterFactory.create())//改变转换器
                            .build().create(HomeStrApi.class);
                }
            }
        }
        return homeStrApi;
    }

    /**
     * Retrofit请求返回string
     * @return
     */
    /*public HomeApi getHomeApiStr() {

        if (homeApi == null) {
            synchronized (monitor) {
                if (homeApi == null) {
                    initOkHttpClient();
                    homeApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(ScalarsConverterFactory.create())//改变转换器
                            .build().create(HomeApi.class);
                }
            }
        }
        return homeApi;
    }*/

    public MyHostApi getMyHostApi() {
        if (myHostApi == null) {
            synchronized (monitor) {
                if (myHostApi == null) {
                    initOkHttpClient();
                    myHostApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.MYHOST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MyHostApi.class);
                }
            }
        }
        return myHostApi;
    }


    public TicketApi getTicketApi() {
        if (ticketApi == null) {
            synchronized (monitor) {
                if (ticketApi == null) {
                    initOkHttpClient();
                    ticketApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.TICKET_API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(TicketApi.class);
                }
            }
        }
        return ticketApi;
    }

    public MallApi getMallApi() {
        if (mallApi == null) {
            synchronized (monitor) {
                if (mallApi == null) {
                    initOkHttpClient();
                    mallApi = new Retrofit.Builder()
                            .baseUrl(ApiConstants.Mall_API)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MallApi.class);
                }
            }
        }
        return mallApi;
    }


    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(MtimeApp.getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
