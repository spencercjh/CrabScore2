package top.spencer.crabscore.model.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class PresentCompetitionPropertyModel extends BaseModel {
    /**
     * 参数表 空
     * common接口都不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        requestGetAPI(CommonConstant.URL + "common/property/competition/present", myCallBack, "");
    }
}
