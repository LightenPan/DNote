package com.brcorner.dnote.android.restful;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

public class BaseNetTool {

    protected JSONObject getResultFromCache(RequestParams params){
        return null;
    }

    protected void saveResultToCache(RequestParams params, JSONObject msg){

    }

    public void get(final String url, final RequestParams params, final RequestCallback callback) {

        //如果有缓冲，先返回缓存数据，然后再去请求新数据
        JSONObject cacheResult = getResultFromCache(params);
        if (cacheResult != null && callback != null){
            callback.onSuccess(cacheResult);
        }

        DefaultRestfulClient.inst().get(url, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AsyncHttpClient.log.w("JsonHttpRH", "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                saveResultToCache(params, response);
                callback.onSuccess(response);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                AsyncHttpClient.log.w("JsonHttpRH", "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);

                if (throwable instanceof SocketTimeoutException) {
                    callback.onTimeOut();
                } else {
                    callback.onFailure(statusCode, throwable.getMessage());
                }
            }
        });
    }

    public void post(final String url, final RequestParams params, final RequestCallback callback) {

        //如果有缓冲，先返回缓存数据，然后再去请求新数据
        JSONObject cacheResult = getResultFromCache(params);
        if (cacheResult != null && callback != null){
            callback.onSuccess(cacheResult);
        }

        DefaultRestfulClient.inst().post(url, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AsyncHttpClient.log.w("JsonHttpRH", "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                saveResultToCache(params, response);
                callback.onSuccess(response);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                AsyncHttpClient.log.w("JsonHttpRH", "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);

                if (throwable instanceof SocketTimeoutException) {
                    callback.onTimeOut();
                } else {
                    callback.onFailure(statusCode, throwable.getMessage());
                }
            }
        });
    }
}