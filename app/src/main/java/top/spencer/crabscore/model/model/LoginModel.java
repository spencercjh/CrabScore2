package top.spencer.crabscore.model.model;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import top.spencer.crabscore.base.BaseModel;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.util.PatternUtil;


/**
 * 调用Login接口的Model层
 *
 * @author spencercjh
 */
public class LoginModel extends BaseModel {
    /**
     * 参数表 String username, String password, String roleId
     * login接口JWT为空，返回会拿到一个JWT
     * <p>
     * 这个接口会在body里返回JWT！！！
     *
     * @param myCallBack myCallBack
     */
    @Override
    public void execute(final MyCallback<JSONObject> myCallBack) {
        if (StrUtil.isEmpty(mvpParams[0]) || !PatternUtil.isUsername(mvpParams[0])) {
            String result = "{\"code\":501,\"message\":\"用户名不能为空或有非法输入\",\"result\":{},\"success\":false,\"timestamp\":0}";
            JSONObject resultJson = JSON.parseObject(result);
            myCallBack.onFailure(resultJson);
            myCallBack.onComplete();
            return;
        } else if (StrUtil.isEmpty(mvpParams[1])) {
            String result = "{\"code\":501,\"message\":\"密码不能为空\",\"result\":{},\"success\":false,\"timestamp\":0}";
            JSONObject resultJson = JSON.parseObject(result);
            myCallBack.onFailure(resultJson);
            myCallBack.onComplete();
            return;
        } else if (Integer.parseInt(mvpParams[2]) == 0) {
            String result = "{\"code\":501,\"message\":\"用户组不能不选择\",\"result\":{},\"success\":false,\"timestamp\":0}";
            JSONObject resultJson = JSON.parseObject(result);
            myCallBack.onFailure(resultJson);
            myCallBack.onComplete();
            return;
        }
        String url = CommonConstant.URL + "common/login" + "?username=" + mvpParams[0] +
                "&password=" + new String(Hex.encodeHex(DigestUtils.md5(mvpParams[1]))).toUpperCase() +
                "&roleId=" + mvpParams[2];
        requestGetAPI(url, myCallBack, "");
    }
}
