package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.common.GetPresentCompetitionPropertyModel;

/**
 * @author spencercjh
 */
public class NavigationPresenter extends BasePresenter<BaseView> {
    /**
     * 获取当前大赛信息
     *
     * @see GetPresentCompetitionPropertyModel
     */
    public void getPresentCompetitionProperty() {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_PRESENT_COMPETITION_PROPERTY)
                .params()
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showData(data);
                    }

                    @Override
                    public void onFailure(JSONObject data) {
                        getView().showFailure(data);
                    }

                    @Override
                    public void onError() {
                        getView().showErr();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }
}
