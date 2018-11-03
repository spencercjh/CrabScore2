package top.spencer.crabscore.model.model.administrator;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class AllUserModel extends BaseModel {
    /**
     * 参数表Integer pageNum, Integer pageSize ,String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "admin/user/users/" + mvpParams[0] + "/" + mvpParams[1];
        requestGetAPI(url, myCallBack, mvpParams[2]);
    }
}
