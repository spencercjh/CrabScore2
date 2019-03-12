package top.spencer.crabscore.model.model.administrator;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : spencercjh
 * @date : 2019/1/17 0:52
 */
public class OutputExcelModel extends BaseModel {
    /**
     * 参数表Integer competitionId String email String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.PYTHON_URL+mvpParams[0];
        Map<String, Object> postParams = new HashMap<>(4);
        postParams.put("email", mvpParams[1]);
        requestPostAPI(url, postParams, myCallBack, mvpParams[2]);
    }
}
