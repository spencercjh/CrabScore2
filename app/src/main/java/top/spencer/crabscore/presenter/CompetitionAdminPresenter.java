package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.model.common.ModelFactory;

/**
 * @author spencercjh
 */
public class CompetitionAdminPresenter extends BasePresenter<BaseView> {
    /**
     * 修改大赛属性
     *
     * @param competition 大赛对象
     * @param jwt         JWT
     * @see top.spencer.crabscore.model.model.administrator.UpdateCompetitionPropertyModel
     */
    public void updateCompetitionProperty(Competition competition, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_COMPETITION_PROPERTY)
                .params(JSON.toJSON(competition).toString(), jwt)
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
