package top.spencer.crabscore.model.model.judge;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetOneGroupAllQualityScoreModel extends BaseModel {
    /**
     * 参数表Integer competitionId, Integer groupId, Integer pageNum,
     * Integer pageSize, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "judge/qualities/competition/" + mvpParams[0] + "/group/" + mvpParams[1] + "?pageNum=" + mvpParams[2] + "&pageSize=" + mvpParams[3];
        requestGetAPI(url, myCallBack, mvpParams[4]);
    }
}
