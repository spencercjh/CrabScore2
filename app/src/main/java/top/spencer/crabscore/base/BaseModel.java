package top.spencer.crabscore.base;

import android.support.annotation.NonNull;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.data.Callback;

import java.io.IOException;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * @author spencercjh
 */
@SuppressWarnings("Duplicates")
public abstract class BaseModel {
    /**
     * 数据请求参数
     */
    protected String[] mvpParams;

    /**
     * 设置数据请求参数
     *
     * @param args 参数数组
     */
    public BaseModel params(String... args) {
        mvpParams = args;
        return this;
    }

    /**
     * 具体的数据请求由子类实现
     *
     * @param myCallBack myCallBack
     */
    public abstract void execute(Callback<JSONObject> myCallBack);

    /**
     * OkHttp3 异步Get请求 
     *
     * @param url        URL (需要在外面处理好)
     * @param myCallBack myCallBack
     * @param jwt        Header里的JWT串
     */
    protected void requestGetAPI(String url, final Callback<JSONObject> myCallBack, String jwt) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("jwt", jwt)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                myCallBack.onError();
                myCallBack.onComplete();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String jwt = response.headers().get("jwt");
                JSONObject responseJsonResult;
                try {
                    assert response.body() != null;
                    Log.d(TAG, response.body().string());
                    responseJsonResult = JSON.parseObject(response.body().string());
                    responseJsonResult.put("jwt", jwt);
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                    myCallBack.onError();
                    myCallBack.onComplete();
                    return;
                }
                Integer code = responseJsonResult.getInteger("code");
                if (code.equals(CommonConstant.SUCCESS)) {
                    myCallBack.onSuccess(responseJsonResult);
                } else {
                    myCallBack.onFailure(responseJsonResult);
                }
                myCallBack.onComplete();
            }
        });
    }

    /**
     * OkHttp3 Post异步方式提交表单 
     *
     * @param url        URL
     * @param postParams body中的参数
     * @param myCallBack myCallBack
     * @param jwt        Header里的JWT串
     */
    protected void requestPostAPI(String url, Map<String, Object> postParams,
                                  final Callback<JSONObject> myCallBack, String jwt) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> param : postParams.entrySet()) {
            formBody.add(param.getKey(), param.getValue().toString());
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("jwt", jwt)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                myCallBack.onError();
                myCallBack.onComplete();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String jwt = response.headers().get("jwt");
                JSONObject responseJsonResult;
                try {
                    assert response.body() != null;
                    Log.d(TAG, response.body().string());
                    responseJsonResult = JSON.parseObject(response.body().string());
                    responseJsonResult.put("jwt", jwt);
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                    myCallBack.onError();
                    myCallBack.onComplete();
                    return;
                }
                Integer code = responseJsonResult.getInteger("code");
                if (code.equals(CommonConstant.SUCCESS)) {
                    myCallBack.onSuccess(responseJsonResult);
                } else {
                    myCallBack.onFailure(responseJsonResult);
                }
                myCallBack.onComplete();
            }
        });
    }

    /**
     * OkHttp3 PUT异步请求 
     *
     * @param url        URL
     * @param putParams  body中的参数
     * @param myCallBack myCallBack
     * @param jwt        Header里的JWT串
     */
    protected void requestPutAPI(String url, Map<String, Object> putParams,
                                 final Callback<JSONObject> myCallBack, String jwt) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> param : putParams.entrySet()) {
            formBody.add(param.getKey(), param.getValue().toString());
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .addHeader("jwt", jwt)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                myCallBack.onError();
                myCallBack.onComplete();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String jwt = response.headers().get("jwt");
                JSONObject responseJsonResult;
                try {
                    assert response.body() != null;
                    Log.d(TAG, response.body().string());
                    responseJsonResult = JSON.parseObject(response.body().string());
                    responseJsonResult.put("jwt", jwt);
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                    myCallBack.onError();
                    myCallBack.onComplete();
                    return;
                }
                Integer code = responseJsonResult.getInteger("code");
                if (code.equals(CommonConstant.SUCCESS)) {
                    myCallBack.onSuccess(responseJsonResult);
                } else {
                    myCallBack.onFailure(responseJsonResult);
                }
                myCallBack.onComplete();
            }
        });
    }

    /**
     * OkHttp3 Delete异步请求 
     *
     * @param url        URL (需要在外面处理好)
     * @param myCallBack myCallBack
     * @param jwt        Header里的JWT串
     */
    protected void requestDeleteAPI(String url, final Callback<JSONObject> myCallBack, String jwt) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("jwt", jwt)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                myCallBack.onError();
                myCallBack.onComplete();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                String jwt = response.headers().get("jwt");
                JSONObject responseJsonResult;
                try {
                    assert response.body() != null;
                    Log.d(TAG, response.body().string());
                    responseJsonResult = JSON.parseObject(response.body().string());
                    responseJsonResult.put("jwt", jwt);
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                    myCallBack.onError();
                    myCallBack.onComplete();
                    return;
                }
                Integer code = responseJsonResult.getInteger("code");
                if (code.equals(CommonConstant.SUCCESS)) {
                    myCallBack.onSuccess(responseJsonResult);
                } else {
                    myCallBack.onFailure(responseJsonResult);
                }
                myCallBack.onComplete();
            }
        });
    }
}
