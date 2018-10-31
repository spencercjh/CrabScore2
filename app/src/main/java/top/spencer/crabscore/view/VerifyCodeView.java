package top.spencer.crabscore.view;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseView;

/**
 * @author spencercjh
 */
public interface VerifyCodeView extends BaseView {
    /**
     * 初始化校验SeekBar
     */
    void initSeekBar();

    /**
     * 处理发送验证码后收到的信息
     *
     * @param successData 携带验证码的JSON串
     */
    void dealSendCode(JSONObject successData);

    /**
     * 处理校验验证码后收到的信息
     *
     * @param successData 携带校验结果的JSON串
     */
    void dealVerifyCode(JSONObject successData);
}
