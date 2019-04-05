package top.spencer.crabscore.ui.view;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseView;

/**
 * 登陆活动View
 *
 * @author spencercjh
 */
public interface LoginView extends BaseView {
    /**
     * 初始化用户组Spinner
     */
    void initSpinner();

    /**
     * 读取SharedPreferences，执行相关业务逻辑
     */
    void readSharedPreferences();

    /**
     * 登录成功回调
     */
    void showLoginSuccessData(JSONObject successData);
}
