package top.spencer.crabscore.data.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class TasteRankModel extends BaseModel {
    /**
     * 参数表Integer competitionId
     * common接口不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "/common/score/tastes/" + mvpParams[0];
        requestGetAPI(url, myCallBack, "");
    }
}