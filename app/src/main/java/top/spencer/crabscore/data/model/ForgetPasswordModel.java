package top.spencer.crabscore.data.model;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.common.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spencercjh
 */
public class ForgetPasswordModel extends BaseModel {
    /**
     * 参数表 String username, String newPassword
     * 参数校验放在Activity里写了
     * common接口都不传JWT
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(Callback<JSONObject> myCallBack) {
        String url = CommonConstant.URL + "common/password";
        Map<String, Object> putParams = new HashMap<>(2);
        putParams.put("mobile", mvpParams[0]);
        putParams.put("password", new String(Hex.encodeHex(DigestUtils.md5(mvpParams[1]))).toUpperCase());
        requestPutAPI(url, putParams, myCallBack, "");
    }
}
