package top.spencer.crabscore.model.model.staff;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public class GetOneGroupOneSexCrabModel extends BaseModel {
    /**
     * 参数表Integer competitionId, Integer groupId,Integer sex, Integer pageNum, Integer pageSize,String jwt
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "staff/competition/" + mvpParams[0] + "/group/" +
                mvpParams[1] + "/crabSex/" + mvpParams[2] + "/crabs?pageNum=" + mvpParams[3] +
                "&pageSize=" + mvpParams[4];
        requestGetAPI(url, myCallBack, mvpParams[5]);
    }
}
