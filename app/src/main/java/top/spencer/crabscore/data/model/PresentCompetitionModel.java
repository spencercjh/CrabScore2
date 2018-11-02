package top.spencer.crabscore.data.model;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;

/**
 * @author spencercjh
 */
public class PresentCompetitionModel extends BaseModel {
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        //TODO 后台这个接口只有admin用户组里有，应该有一个公共用户组的查询当前大赛信息和其他信息的接口
    }
}
