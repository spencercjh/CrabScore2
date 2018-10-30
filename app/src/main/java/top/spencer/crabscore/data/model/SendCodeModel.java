package top.spencer.crabscore.data.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class SendCodeModel extends BaseModel {
    /**
     * 参数表String mobile
     * 参数校验放在Activity里写了
     * common接口不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "common/code?mobile=" + mvpParams[0];
        requestGetAPI(url, myCallBack, "");
    }
}
