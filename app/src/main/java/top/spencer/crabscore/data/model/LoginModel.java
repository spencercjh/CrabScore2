package top.spencer.crabscore.data.model;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.data.Callback;
import top.spencer.crabscore.data.constant.CommonConstant;

import java.io.IOException;

/**
 * @author spencercjh
 */
public class LoginModel extends BaseModel<String> {
    @Override
    public void execute(final Callback<String> myCallBack) {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", mParams[0])
                .add("password", DigestUtils.md5Hex(mParams[1]))
                .build();
        Request request = new Request.Builder()
                .url(CommonConstant.url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                myCallBack.onError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = null;
                if (response.body() != null) {
                    result = response.body().string();
                } else {
                    myCallBack.onError();
                }
                JSONObject resultJson = JSON.parseObject(result);
                Integer code = resultJson.getInteger("code");
                if (!code.equals(CommonConstant.SUCCESS)) {
                    myCallBack.onFailure(result);
                } else {
                    myCallBack.onSuccess(result);
                }
            }
        });
        myCallBack.onComplete();
    }
}
