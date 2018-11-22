package top.spencer.crabscore.model.model.judge;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetOneGroupAllTasteScoreModel extends BaseModel {
    /**
     * 参数表Integer competitionId, Integer groupId, Integer pageNum,
     * Integer pageSize, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "judge/tastes/" + mvpParams[0] + "/" + mvpParams[1] + "/" + mvpParams[2] +
                "/" + mvpParams[3];
        requestGetAPI(url, myCallBack, mvpParams[4]);
    }
}
