package top.spencer.crabscore.presenter;

import android.widget.EditText;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.QualityScore;
import top.spencer.crabscore.model.entity.TasteScore;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.judge.GetAllGroupModel;
import top.spencer.crabscore.model.model.judge.GetOneGroupAllQualityScoreModel;
import top.spencer.crabscore.model.model.judge.GetOneGroupAllTasteScoreModel;
import top.spencer.crabscore.ui.view.GradeListView;

import java.util.Date;
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
     * 评委更新某个种质得分信息
     *
     * @param scoreFin   EditText
     * @param scoreBts   EditText
     * @param scoreFts   EditText
     * @param scoreEc    EditText
     * @param scoreDscc  EditText
     * @param scoreBbyzt EditText
     * @param user       USER
     * @param jwt        JWT
     * @see top.spencer.crabscore.model.model.judge.UpdateQualityScoreModel
     */
    public void updateQualityScore(final EditText scoreFin, final EditText scoreBts, final EditText scoreFts,
                                   final EditText scoreEc, final EditText scoreDscc, final EditText scoreBbyzt,
                                   User user, String jwt) {
        QualityScore qualityScore = new QualityScore();
        String scoreFinString = scoreFin.getText().toString().trim();
        float finalScore = 0;
        String scoreBtsString = scoreBts.getText().toString().trim();
        float btsScore;
        String scoreFtsString = scoreFts.getText().toString().trim();
        float ftsScore;
        String scoreEcString = scoreEc.getText().toString().trim();
        float ecScore;
        String scoreDsccString = scoreDscc.getText().toString().trim();
        float dsccScore;
        String scoreBbyztString = scoreBbyzt.getText().toString().trim();
        float bbyztScore;
        if (!StrUtil.isBlank(scoreFinString)) {
            if (!NumberUtil.isNumber(scoreFinString)) {
                getView().showToast("种质总分:非法数字");
                return;
            }
        } else {
            finalScore = Float.parseFloat(scoreFinString);
            qualityScore.setScoreFin(finalScore);
        }
        if (!NumberUtil.isNumber(scoreBtsString)) {
            getView().showToast("背部体色得分:非法数字");
            return;
        } else {
            btsScore = Float.parseFloat(scoreBtsString);
            qualityScore.setScoreBts(btsScore);
        }
        if (!NumberUtil.isNumber(scoreFtsString)) {
            getView().showToast("腹部体色得分:非法数字");
            return;
        } else {
            ftsScore = Float.parseFloat(scoreFtsString);
            qualityScore.setScoreFts(ftsScore);
        }
        if (!NumberUtil.isNumber(scoreEcString)) {
            getView().showToast("额齿得分:非法数字");
            return;
        } else {
            ecScore = Float.parseFloat(scoreEcString);
            qualityScore.setScoreEc(ecScore);
        }
        if (!NumberUtil.isNumber(scoreDsccString)) {
            getView().showToast("第四侧齿得分:非法数字");
            return;
        } else {
            dsccScore = Float.parseFloat(scoreDsccString);
            qualityScore.setScoreDscc(dsccScore);
        }
        if (!NumberUtil.isNumber(scoreBbyztString)) {
            getView().showToast("背部疣状突得分:非法数字");
            return;
        } else {
            bbyztScore = Float.parseFloat(scoreBbyztString);
            qualityScore.setScoreBbyzt(bbyztScore);
        }
        if (finalScore != bbyztScore + dsccScore + ecScore + ftsScore + btsScore) {
            finalScore = bbyztScore + dsccScore + ecScore + ftsScore + btsScore;
            qualityScore.setScoreFin(finalScore);
            getView().showToast("已为您自动计算并设置总分");
        }
        qualityScore.setUserId(user.getUserId());
        qualityScore.setUpdateDate(new Date(System.currentTimeMillis()));
        qualityScore.setUpdateUser(user.getUserName());
        sendUpdateQualityScoreRequest(qualityScore, jwt);
    }

    private void sendUpdateQualityScoreRequest(QualityScore qualityScore, String jwt) {
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
     * 评委更新某个口感得分信息
     *
     * @param scoreFin  EditText
     * @param scoreYgys EditText
     * @param scoreSys  EditText
     * @param scoreGhys EditText
     * @param scoreXwxw EditText
     * @param scoreGh   EditText
     * @param scoreFbjr EditText
     * @param scoreBzjr EditText
     * @param user      User
     * @param jwt       JWT
     * @see top.spencer.crabscore.model.model.judge.UpdateTasteScoreModel
     */
    public void updateTasteScore(final EditText scoreFin, final EditText scoreYgys, final EditText scoreSys,
                                 final EditText scoreGhys, final EditText scoreXwxw, final EditText scoreGh,
                                 final EditText scoreFbjr, final EditText scoreBzjr, User user, String jwt) {
        TasteScore tasteScore = new TasteScore();
        String scoreFinString = scoreFin.getText().toString().trim();
        float finalScore = 0;
        String scoreYgysString = scoreYgys.getText().toString().trim();
        float ygysScore;
        String scoreSysString = scoreSys.getText().toString().trim();
        float sysScore;
        String scoreGhysString = scoreGhys.getText().toString().trim();
        float ghysScore;
        String scoreXwxwString = scoreXwxw.getText().toString().trim();
        float xwxwScore;
        String scoreGhString = scoreGh.getText().toString().trim();
        float ghScore;
        String scoreFbjrString = scoreFbjr.getText().toString().trim();
        float fbjrScore;
        String scoreBzjrString = scoreBzjr.getText().toString().trim();
        float bzjrScore;
        if (!StrUtil.isBlank(scoreFinString)) {
            if (!NumberUtil.isNumber(scoreFinString)) {
                getView().showToast("口感总分:非法数字");
                return;
            }
        } else {
            finalScore = Float.parseFloat(scoreFinString);
            tasteScore.setScoreFin(finalScore);
        }
        if (!NumberUtil.isNumber(scoreYgysString)) {
            getView().showToast("蟹盖颜色得分:非法数字");
            return;
        } else {
            ygysScore = Float.parseFloat(scoreYgysString);
            tasteScore.setScoreYgys(ygysScore);
        }
        if (!NumberUtil.isNumber(scoreSysString)) {
            getView().showToast("腮颜色得分:非法数字");
            return;
        } else {
            sysScore = Float.parseFloat(scoreSysString);
            tasteScore.setScoreSys(sysScore);
        }
        if (!NumberUtil.isNumber(scoreGhysString)) {
            getView().showToast("膏黄颜色得分:非法数字");
            return;
        } else {
            ghysScore = Float.parseFloat(scoreGhysString);
            tasteScore.setScoreGhys(ghysScore);
        }
        if (!NumberUtil.isNumber(scoreXwxwString)) {
            getView().showToast("腥香味得分:非法数字");
            return;
        } else {
            xwxwScore = Float.parseFloat(scoreXwxwString);
            tasteScore.setScoreXwxw(xwxwScore);
        }
        if (!NumberUtil.isNumber(scoreGhString)) {
            getView().showToast("膏黄得分:非法数字");
            return;
        } else {
            ghScore = Float.parseFloat(scoreGhString);
            tasteScore.setScoreGh(ghScore);
        }
        if (!NumberUtil.isNumber(scoreFbjrString)) {
            getView().showToast("腹部肌肉得分:非法数字");
            return;
        } else {
            fbjrScore = Float.parseFloat(scoreFbjrString);
            tasteScore.setScoreFbjr(fbjrScore);
        }
        if (!NumberUtil.isNumber(scoreBzjrString)) {
            getView().showToast("第二三步足肌肉得分:非法数字");
            return;
        } else {
            bzjrScore = Float.parseFloat(scoreBzjrString);
            tasteScore.setScoreBzjr(bzjrScore);
        }
        if (finalScore != ygysScore + sysScore + ghysScore + xwxwScore + ghScore + fbjrScore + bzjrScore) {
            finalScore = ygysScore + sysScore + ghysScore + xwxwScore + ghScore + fbjrScore + bzjrScore;
            tasteScore.setScoreFin(finalScore);
            getView().showToast("已为您自动计算并设置总分");
        }
        tasteScore.setUserId(user.getUserId());
        tasteScore.setUpdateDate(new Date(System.currentTimeMillis()));
        tasteScore.setUpdateUser(user.getUserName());
        sendUpdateTasteScoreRequest(tasteScore, jwt);
    }

    private void sendUpdateTasteScoreRequest(TasteScore tasteScore, String jwt) {
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
