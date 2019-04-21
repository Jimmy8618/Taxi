package com.jimmy.mytaxi;

import android.util.Log;

import org.json.JSONArray;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOKHttp3 {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * 测试http get方法
     */
    @Test
    public void testGet() {
//        创建okhttpclient对象
        OkHttpClient client = new OkHttpClient();

//        创建Request对象
        try {
            Request request = new Request.Builder()
                    .url("https://httpbin.org/get?id=id")
                    .build();
            //        OkHttpClient执行request
            Response response = client.newCall(request).execute();
//            Log.d("TestOKHttp3","Response==="+response.body().string());
            System.out.println("Response==="+response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试http post方法
     */
    @Test
    public void testPost() {
//        创建okhttpclient对象
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON,"{\"name\":\"jimmy\"}");
//        创建Request对象
        try {
            Request request = new Request.Builder()
                    .url("https://httpbin.org/post")
                    .post(body)
                    .build();
            //        OkHttpClient执行request
            Response response = client.newCall(request).execute();
//            Log.d("TestOKHttp3","Response==="+response.body().string());
            System.out.println("Response==="+response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 拦截器
     */
    @Test
    public void testInterceptor(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long start = System.currentTimeMillis();
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                long end = System.currentTimeMillis();
//                System.out.println("request:"+request.method());
//                System.out.println("proceed:"+proceed.body().string());
                System.out.println("time："+(end-start));
                return proceed;
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor) //添加拦截器
                .build();

//        创建Request对象
        try {
            Request request = new Request.Builder()
                    .url("https://httpbin.org/get?id=id")
                    .build();
            //        OkHttpClient执行request
            Response response = client.newCall(request).execute();
//            Log.d("TestOKHttp3","Response==="+response.body().string());
            System.out.println("Response==="+response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试缓存
     */
    @Test
    public void testCache(){

        Cache cache = new Cache(new File("cache.cache"),1204*1204);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache) //添加缓存
                .build();

//        创建Request对象
        try {
            Request request = new Request.Builder()
                    .url("https://httpbin.org/get?id=id")
                    .cacheControl(CacheControl.FORCE_CACHE) //强制从缓存取FORCE_CACHE，从网络取FORCE_NETWORK
                    .build();
            //        OkHttpClient执行request
            Response response = client.newCall(request).execute();
            Response responseCache = response.cacheResponse(); //在缓存取
            Response responseNet = response.networkResponse(); //在网络取
            if(responseCache != null){
                System.out.println("Response from cache");
            }
            if(responseNet != null){
                System.out.println("Response from network");
            }

//            Log.d("TestOKHttp3","Response==="+response.body().string());
            System.out.println("Response==="+response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
