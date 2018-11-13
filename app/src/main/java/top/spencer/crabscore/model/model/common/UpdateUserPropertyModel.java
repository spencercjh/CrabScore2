package top.spencer.crabscore.model.model.common;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class UpdateUserPropertyModel extends BaseModel {
    /**
     * 参数表User user,String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "user/property";
        requestPutJSONAPI(url, mvpParams[0], myCallBack, mvpParams[1]);
    }
}
