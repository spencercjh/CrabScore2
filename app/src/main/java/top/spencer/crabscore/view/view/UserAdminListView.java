package top.spencer.crabscore.view.view;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseView;

/**
 * 列表页面View
 *
 * @author spencercjh
 */
public interface UserAdminListView extends MyRecycleListView {
    /**
     * 当数据请求成功后，调用此接口显示数据。这个请求一般是该页面里最重要的一个请求，别的请求会在具体的View里写
     *
     * @param successData 成功数据源
     */
    void showUpdateUserResponse(JSONObject successData);
}
