package top.spencer.crabscore.view;

import top.spencer.crabscore.base.BaseView;

/**
 * @author spencercjh
 */
public interface TestView extends BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据
     *
     * @param data 数据源
     */
    void showData(String data);
}