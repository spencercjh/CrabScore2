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
        String url = CommonConstant.URL + "company/" + mvpParams[1] + "/crabs/" + mvpParams[0] +
                "/" + mvpParams[2] + "/" + mvpParams[3];
        requestGetAPI(url, myCallBack, mvpParams[4]);
    }
}
