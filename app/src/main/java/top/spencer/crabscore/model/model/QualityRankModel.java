package top.spencer.crabscore.model.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class QualityRankModel extends BaseModel {
    /**
     * 参数表Integer competitionId
     * common接口不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "/common/score/qualities/" + mvpParams[0] + "/" + mvpParams[1] + "/" + mvpParams[2];
        requestGetAPI(url, myCallBack, "");
    }
}
