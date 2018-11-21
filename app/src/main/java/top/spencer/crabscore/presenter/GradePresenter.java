package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.QualityScore;
import top.spencer.crabscore.model.entity.TasteScore;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.judge.GetAllGroupModel;
import top.spencer.crabscore.model.model.judge.GetOneGroupAllQualityScoreModel;
import top.spencer.crabscore.model.model.judge.GetOneGroupAllTasteScoreModel;
import top.spencer.crabscore.ui.view.GradeListView;

import java.util.List;

/**
 * @author spencercjh
 */
public class GradePresenter extends BasePresenter<GradeListView> {
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

    /**
     * 获取一个小组的所有口感得分对象
     *
     * @param competitionId 大赛Id
     * @param groupId       小组Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see GetOneGroupAllTasteScoreModel
     */
    public void getOneGroupAllTasteScore(Integer competitionId, Integer groupId, Integer pageNum,
                                         Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ONE_GROUP_ALL_TASTE_SCORE)
                .params(String.valueOf(competitionId), String.valueOf(groupId), String.valueOf(pageNum),
                        String.valueOf(pageSize), jwt)
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
     * 获取一个小组的所有种质得分对象
     *
     * @param competitionId 大赛Id
     * @param groupId       小组Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see GetOneGroupAllQualityScoreModel
     */
    public void getOneGroupAllQualityScore(Integer competitionId, Integer groupId, Integer pageNum,
                                           Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ONE_GROUP_ALL_QUALITY_SCORE)
                .params(String.valueOf(competitionId), String.valueOf(groupId), String.valueOf(pageNum),
                        String.valueOf(pageSize), jwt)
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
     * 评委更新某个种质得分对象
     *
     * @param qualityScore 种质得分对象
     * @param jwt          JWT
     * @see top.spencer.crabscore.model.model.judge.UpdateQualityScoreModel
     */
    public void updateQualityScore(QualityScore qualityScore, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_QUALITY_SCORE)
                .params(JSON.toJSON(qualityScore).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdateScoreResponse(data);
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
     * 评委更新某个口感得分对象信息
     *
     * @param tasteScore 口感得分对象
     * @param jwt        JWT
     * @see top.spencer.crabscore.model.model.judge.UpdateTasteScoreModel
     */
    public void updateTasteScore(TasteScore tasteScore, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_TASTE_SCORE)
                .params(JSON.toJSON(tasteScore).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdateScoreResponse(data);
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

    public boolean dealQualityScoreJSON(JSONArray results, List<QualityScore> qualityScoreList) {
        boolean repeat = true;
        for (Object object : results) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            QualityScore qualityScore = JSONObject.parseObject(jsonString, QualityScore.class);
            //是否需要调用add方法添加到qualityScoreList中去
            boolean needAdd = true;
            //遍历已有的qualityScoreList
            for (int i = 0; i < qualityScoreList.size(); ++i) {
                //存在新qualityScore对象和已有的qualityScore对象的id相同
                if (qualityScoreList.get(i).getScoreId().equals(qualityScore.getScoreId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!qualityScoreList.get(i).equals(qualityScore)) {
                        //更新属性信息
                        qualityScoreList.set(i, qualityScore);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                qualityScoreList.add(qualityScore);
            }
        }
        return repeat;
    }

    public boolean dealTasteScoreJSON(JSONArray results, List<TasteScore> tasteScoreList) {
        boolean repeat = true;
        for (Object object : results) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            TasteScore tasteScore = JSONObject.parseObject(jsonString, TasteScore.class);
            //是否需要调用add方法添加到tasteScoreList中去
            boolean needAdd = true;
            //遍历已有的tasteScoreList
            for (int i = 0; i < tasteScoreList.size(); ++i) {
                //存在新tasteScore对象和已有的tasteScore对象的id相同
                if (tasteScoreList.get(i).getScoreId().equals(tasteScore.getScoreId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!tasteScoreList.get(i).equals(tasteScore)) {
                        //更新属性信息
                        tasteScoreList.set(i, tasteScore);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                tasteScoreList.add(tasteScore);
            }
        }
        return repeat;
    }
}
