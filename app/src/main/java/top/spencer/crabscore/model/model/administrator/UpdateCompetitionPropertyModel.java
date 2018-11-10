package top.spencer.crabscore.model.model.administrator;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class UpdateCompetitionPropertyModel extends BaseModel {
    /**
     * 参数表Competition competition, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "admin/competition/property";
        requestPutJSONAPI(url, mvpParams[0], myCallBack, mvpParams[1]);
    }
}
