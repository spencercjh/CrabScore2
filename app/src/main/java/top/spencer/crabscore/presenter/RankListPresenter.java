package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.common.rank.GetFatnessRankModel;
import top.spencer.crabscore.model.model.common.rank.GetQualityRankModel;
import top.spencer.crabscore.model.model.common.rank.GetTasteRankModel;
import top.spencer.crabscore.ui.view.MyRecycleListView;

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
     * @see GetFatnessRankModel
     */
    public void getFatnessRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_FATNESS_RANK)
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
     * @see GetQualityRankModel
     */
    public void getQualityRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_QUALITY_RANK)
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
     * @see GetTasteRankModel
     */
    public void getTasteRank(Integer competitionId, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_TASTE_RANK)
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
    public boolean dealGroupListJSON(JSONArray groups, List<GroupResult> groupList) {
        boolean repeat = true;
        for (Object object : groups) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            GroupResult group = JSONObject.parseObject(jsonString, GroupResult.class);
            if (!groupList.contains(group)) {
                groupList.add(group);
                repeat = false;
            }
        }
        return repeat;
    }
}
