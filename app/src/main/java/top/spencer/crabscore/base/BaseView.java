package top.spencer.crabscore.base;

import android.content.Context;

/**
 * @author spencercjh
 */
public interface BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据
     *
     * @param data 数据源
     */
    void showData(String data);

    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg Toast message
     */
    void showToast(String msg);

    /**
     * 显示失败
     */
    void showFailure();

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();
}