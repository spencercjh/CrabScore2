package top.spencer.crabscore.model.model.staff;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class AddTasteScoreListModel extends BaseModel {
    /**
     * 参数表List<TasteScore> tasteScoreList,String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "staff/tastes";
        requestPostJSONAPI(url, mvpParams[0], myCallBack, mvpParams[1]);
    }
}
