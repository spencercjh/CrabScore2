package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Group;
import top.spencer.crabscore.model.model.ModelFactory;
import top.spencer.crabscore.view.view.MyRecycleListView;

import java.util.List;

/**
 * @author spencercjh
 */
public class RankListPresenter extends BasePresenter<MyRecycleListView> {
    /**
     * 查询金蟹奖成绩
     *
     * @param competitionId 大赛Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @see top.spencer.crabscore.model.model.FatnessRankModel
     */
    public void getFatnessRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_FATNESS_RANK)
                .params(String.valueOf(competitionId), String.valueOf(pageNum), String.valueOf(pageSize))
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

    /**
     * 查询种质奖成绩
     *
     * @param competitionId 大赛Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @see top.spencer.crabscore.model.model.QualityRankModel
     */
    public void getQualityRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_QUALITY_RANK)
                .params(String.valueOf(competitionId), String.valueOf(pageNum), String.valueOf(pageSize))
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

    /**
     * 查询口感奖成绩
     *
     * @param competitionId 大赛Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @see top.spencer.crabscore.model.model.TasteRankModel
     */
    public void getTasteRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_TASTE_RANK)
                .params(String.valueOf(competitionId), String.valueOf(pageNum), String.valueOf(pageSize))
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

    /**
     * 对排行榜页面中处理返回的group对象的封装
     *
     * @param groups    jsonResult
     * @param groupList list
     * @return 是否有重复
     */
    public boolean dealGroupListJSON(JSONArray groups, List<Group> groupList) {
        boolean repeat = false;
        for (Object object : groups) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Group group = JSONObject.parseObject(jsonString, Group.class);
            if (!groupList.contains(group)) {
                groupList.add(group);
            } else {
                repeat = true;
            }
        }
        return repeat;
    }
}
