package top.spencer.crabscore.model.model.administrator;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class UpdateUserPropertyModel extends BaseModel {
    /**
     * 参数表User user的JSON, String jwt
     *
     * @param myCallBack myCallBack
     *                   //FIXME CODE:415 BUG
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "admin/user/property";
        Map<String, Object> putParam = new HashMap<>(1);
        Log.d("UpdateUserPropertyModel", mvpParams[1]);
        Log.d("UpdateUserPropertyModel", mvpParams[0]);
        putParam.put("user", mvpParams[0]);
        requestPutAPI(url, putParam, myCallBack, mvpParams[1]);
    }
}
