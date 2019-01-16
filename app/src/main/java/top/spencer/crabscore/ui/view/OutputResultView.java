package top.spencer.crabscore.ui.view;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BaseView;

public interface OutputResultView extends BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据。这个请求一般是该页面里最重要的一个请求，别的请求会在具体的View里写
     *
     * @param successData 成功数据源
     */
    void showUpdatePresentCompetitionConfigResponse(JSONObject successData);

    /**
     * 当数据请求成功后，调用此接口显示数据。这个请求一般是该页面里最重要的一个请求，别的请求会在具体的View里写
     *
     * @param successData 成功数据源
     */
    void showGenerateScoreResponse(JSONObject successData);

    /**
     * 当数据请求成功后，调用此接口显示数据。这个请求一般是该页面里最重要的一个请求，别的请求会在具体的View里写
     *
     * @param successData 成功数据源
     */
    void shouOutputExcelResponse(JSONObject successData);
}
