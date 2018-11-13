package top.spencer.crabscore.ui.view;

import top.spencer.crabscore.base.BaseView;

/**
 * 带TabLayout的一级页面View
 *
 * @author spencercjh
 */
public interface TabLayoutView extends BaseView {
    /**
     * 初始化视图
     */
    void initView();

    /**
     * 初始化TabLayout及其Fragment
     */
    void initTabLayout();
}
