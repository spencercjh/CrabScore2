package top.spencer.crabscore.model.model.company;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

public class GetCompanyModel extends BaseModel {
    /**
     * 参数表Integer companyId, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "company/" + mvpParams[0];
        requestGetAPI(url, myCallBack, mvpParams[1]);
    }
}
