package top.spencer.crabscore.data.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class PhoneLoginModel extends BaseModel {
    /**
     * 参数表String mobile
     * 参数校验放在Activity里写了
     * common接口都不传JWT
     * <p>
     * 这个接口会在body里返回JWT！
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "common/direct";
        Map<String, Object> postParams = new HashMap<>(2);
        postParams.put("mobile", mvpParams[0]);
        requestPostAPI(url, postParams, myCallBack, "");
    }
}
