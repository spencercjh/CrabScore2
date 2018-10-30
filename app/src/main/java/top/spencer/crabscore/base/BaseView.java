package top.spencer.crabscore.base;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;

/**
 * @author spencercjh
 */
public interface BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据。这个请求一般是该页面里最重要的一个请求，别的请求会在具体的View里写
     *
     * @param successData 成功数据源
     */
    void showData(JSONObject successData);

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
     *
     * @param errorData 错误数据源
     */
    void showFailure(JSONObject errorData);

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