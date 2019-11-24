package com.jdxiang.airTicket.httpService;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 主要的http请求服务
 */
public class BaseHttpService {

    public static final String BASE_HOST = "http://192.168.2.234:8080/";

    public static String token = "";

    public static void setToken(java.lang.String token) {
        BaseHttpService.token = token;
    }

    /**
     * 用于发起get请求
     *
     * @param url      请求的url
     * @param callBack 回调函数，将在子线程中发起请求，在主线程中执行回调函数
     * @param params   请求参数
     */
    public <T> void get(String url, CallBack callBack, Class<T> type, String... params) {
        Request request = new Request.Builder().url(BASE_HOST + url)
                .addHeader("Authorization", token).build();
        new HttpTask<T>(callBack, type).execute(request);
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param data
     * @param callback
     * @param type
     * @param <T>
     */
    public <T> void post(String url, Object data, BaseHttpService.CallBack callback, Class<T> type) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_HOST + url)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        new HttpTask<T>(callback, type).execute(request);
    }

    /**
     * 自定义异步任务 用于发送http请求
     * 在子线程中执行请求操作
     * 在主线程中调用回调函数 以便修改ui
     *
     * @param <E>
     */
    public class HttpTask<E> extends AsyncTask<Request, Void, HttpTask.CustomerResponse> {

        CallBack callBack;

        Class type; // 类型

        Gson gson = new Gson();

        public HttpTask(CallBack callBack, Class type) {
            this.callBack = callBack;
            this.type = type;
        }

        @Override
        protected HttpTask.CustomerResponse doInBackground(Request... requests) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
                Response response = okHttpClient.newCall(requests[0]).execute();
                // 在这里将返回流转化为需要的范型数据并返回
                HttpTask.CustomerResponse customerResponse = new HttpTask.CustomerResponse();
                customerResponse.response = response;
                if (response.code() >= 200 && response.code() < 300) {
                    customerResponse.data = gson.fromJson(response.body().string(), type);
                }
                return customerResponse;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HttpTask.CustomerResponse response) {
            this.callBack.onSuccess(response);
        }


        public class CustomerResponse {
            Response response;
            E data;

            public Response getResponse() {
                return response;
            }

            public E getData() {
                return data;
            }
        }
    }

    /**
     * 请求回调接口
     */
    public interface CallBack {
        void onSuccess(HttpTask.CustomerResponse result);
    }
}
