package top.spencer.crabscore.model.model.administrator;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class UpdateUserPropertyModel extends BaseModel {
    /**
     * 参数表User user的JSON, String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "admin/user/property";
        Log.d("UpdateUserPropertyModel", mvpParams[1]);
        Log.d("UpdateUserPropertyModel", mvpParams[0]);
        requestPutJSONAPI(url, mvpParams[0], myCallBack, mvpParams[1]);
    }
}
