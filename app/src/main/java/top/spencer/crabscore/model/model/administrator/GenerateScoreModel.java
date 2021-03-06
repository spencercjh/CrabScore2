package top.spencer.crabscore.model.model.administrator;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class GenerateScoreModel extends BaseModel {
    /**
     * 参数表Integer competitionId String username String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "admin/result/calculate";
        Map<String, Object> postParams = new HashMap<>(4);
        postParams.put("competitionId", mvpParams[0]);
        postParams.put("username", mvpParams[1]);
        requestPostAPI(url, postParams, myCallBack, mvpParams[2]);
    }
}
