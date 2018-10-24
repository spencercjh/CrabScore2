package top.spencer.crabscore.view;

import top.spencer.crabscore.base.BaseView;

/**
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
}
