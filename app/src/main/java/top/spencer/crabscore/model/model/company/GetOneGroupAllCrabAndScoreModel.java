package top.spencer.crabscore.model.model.company;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetOneGroupAllCrabAndScoreModel extends BaseModel {
    /**
     * 参数表Integer competitionId, Integer groupId,Integer pageNum, Integer pageSize,String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "company/group/" + mvpParams[1] + "/competition/" + mvpParams[0] + "/crabs?pageNum=" + mvpParams[2] + "&pageSize=" + mvpParams[3];
        requestGetAPI(url, myCallBack, mvpParams[4]);
    }
}
