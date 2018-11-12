package top.spencer.crabscore.model.model.company;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class UserBindCompanyModel extends BaseModel {
    /**
     * 参数表Integer userId, Integer companyId, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "company/user";
        Map<String, Object> putParams = new HashMap<>(2);
        putParams.put("userId", mvpParams[0]);
        putParams.put("companyId", mvpParams[1]);
        requestPutAPI(url, putParams, myCallBack, mvpParams[2]);
    }
}
