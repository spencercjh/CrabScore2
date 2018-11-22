package top.spencer.crabscore.model.model.judge;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class UpdateQualityScoreModel extends BaseModel {
    /**
     * 参数表QualityScore qualityScore, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "judge/quality";
        requestPutJSONAPI(url, mvpParams[0], myCallBack, mvpParams[1]);
    }
}
