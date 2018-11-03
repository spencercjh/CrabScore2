package top.spencer.crabscore.model.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class RegistModel extends BaseModel {
    /**
     * 参数表String username, String password, String roleId, String email, String displayName
     * 参数校验放在Activity里写了
     * common接口都不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(MyCallback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "common/creation";
        Map<String, Object> postParams = new HashMap<>(8);
        postParams.put("username", mvpParams[0]);
        postParams.put("password", new String(Hex.encodeHex(DigestUtils.md5(mvpParams[1]))).toUpperCase());
        postParams.put("roleId", mvpParams[2]);
        postParams.put("email", mvpParams[3]);
        postParams.put("displayName", mvpParams[4]);
        requestPostAPI(url, postParams, myCallBack, "");
    }
}
