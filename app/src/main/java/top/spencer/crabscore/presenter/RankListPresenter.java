package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.dto.GroupResult;
import top.spencer.crabscore.model.entity.dto.RankResult;
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
     * @param presentCompetition 当前大赛
     * @param pageNum            页数
     * @param pageSize           页面大小
     * @see GetFatnessRankModel
     */
    public void getFatnessRank(Competition presentCompetition, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        if (presentCompetition.getResultFatness() == 0) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_FATNESS_RANK)
                .params(String.valueOf(presentCompetition.getCompetitionId()), String.valueOf(pageNum), String.valueOf(pageSize))
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
     * @param presentCompetition 当前大赛
     * @param pageNum            页数
     * @param pageSize           页面大小
     * @see GetQualityRankModel
     */
    public void getQualityRank(Competition presentCompetition, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        if (presentCompetition.getResultQuality() == 0) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_QUALITY_RANK)
                .params(String.valueOf(presentCompetition.getCompetitionId()), String.valueOf(pageSize), String.valueOf(pageNum))
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
     * @param presentCompetition 当前大赛
     * @param pageNum            页数
     * @param pageSize           页面大小
     * @see GetTasteRankModel
     */
    public void getTasteRank(Competition presentCompetition, Integer pageNum, Integer pageSize) {
        if (isViewAttached()) {
            return;
        }
        if (presentCompetition.getResultTaste() == 0) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_TASTE_RANK)
                .params(String.valueOf(presentCompetition.getCompetitionId()), String.valueOf(pageNum), String.valueOf(pageSize))
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
            //是否需要调用add方法添加到groupList中去
            boolean needAdd = true;
            //遍历已有的groupList
            for (int i = 0; i < groupList.size(); ++i) {
                //存在新groupResult对象和已有的groupResult对象的id相同
                if (groupList.get(i).getGroupId().equals(group.getGroupId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!groupList.get(i).equals(group)) {
                        //更新属性信息
                        groupList.set(i, group);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                groupList.add(group);
            }
        }
        return repeat;
    }

    /**
     * 对排行榜页面中处理返回的group对象的封装
     *
     * @param groups         jsonResult
     * @param rankResultList list
     * @return 是否有重复
     */
    public boolean dealRankListJSON(JSONArray groups, List<RankResult> rankResultList) {
        boolean repeat = true;
        for (Object object : groups) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            RankResult rankResult = JSONObject.parseObject(jsonString, RankResult.class);
            //是否需要调用add方法添加到rankResultList中去
            boolean needAdd = true;
            //遍历已有的rankResultList
            for (int i = 0; i < rankResultList.size(); ++i) {
                //存在新rankResult对象和已有的rankResult对象的id相同
                if (rankResultList.get(i).getGroupId().equals(rankResult.getGroupId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!rankResultList.get(i).equals(rankResult)) {
                        //更新属性信息
                        rankResultList.set(i, rankResult);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                rankResultList.add(rankResult);
            }
        }
        return repeat;
    }
}
