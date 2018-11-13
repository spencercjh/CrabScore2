package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.judge.GetAllGroupModel;
import top.spencer.crabscore.ui.view.MyRecycleListView;

/**
 * @author spencercjh
 */
public class GradePresenter extends BasePresenter<MyRecycleListView> {
    /**
     * 查看所有比赛组
     *
     * @param competitionId 大赛id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see GetAllGroupModel
     */
    public void getAllGroup(Integer competitionId, Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_GROUP_JUDGE)
                .params(String.valueOf(competitionId), String.valueOf(pageNum), String.valueOf(pageSize), jwt)
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
