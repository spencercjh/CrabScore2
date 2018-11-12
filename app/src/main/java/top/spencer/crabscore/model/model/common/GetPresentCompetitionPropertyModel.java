package top.spencer.crabscore.model.model.common;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetPresentCompetitionPropertyModel extends BaseModel {
    /**
     * 参数表 空
     * common接口都不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        requestGetAPI(CommonConstant.URL + "common/property/competition/present", myCallBack, "");
    }
}
