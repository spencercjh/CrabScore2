package top.spencer.crabscore.model.model.company;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetOneCompanyAllGroupModel extends BaseModel {
    /**
     * 参数表Integer competitionId, Integer companyId, Integer pageNum, Integer pageSize, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "company/competition/" + mvpParams[0] + "/company/" + mvpParams[1] +
                "/groups?pageNum=" + mvpParams[2] + "&pageSize=" + mvpParams[3];
        requestGetAPI(url, myCallBack, mvpParams[4]);
    }
}
