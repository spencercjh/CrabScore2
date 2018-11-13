package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.CompetitionConfig;
import top.spencer.crabscore.model.model.administrator.GetAllCompetitionModel;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.ui.view.OutputResultView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author spencercjh
 */
public class OutputResultPresenter extends BasePresenter<OutputResultView> {
    /**
     * 查询所有大赛
     *
     * @param jwt JWT
     * @see GetAllCompetitionModel
     */
    public void allCompetition(String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_COMPETITION)
                .params(jwt)
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
     * 修改当前大赛
     *
     * @param competitionConfig 大赛配置对象
     * @param jwt               JWT
     * @see top.spencer.crabscore.model.model.administrator.UpdatePresentCompetitionConfigModel
     */
    public void updatePresentCompetitionConfig(CompetitionConfig competitionConfig, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_PRESENT_COMPETITION_CONFIG)
                .params(JSON.toJSON(competitionConfig).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdatePresentCompetitionConfigResponse(data);
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
     * 生成大赛成绩
     *
     * @param competition 大赛对象
     * @param jwt         JWT
     * @see top.spencer.crabscore.model.model.administrator.GenerateScoreModel
     */
    public void generateScore(Competition competition, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GENERATE_SCORE)
                .params(JSON.toJSON(competition).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showGenerateScoreResponse(data);
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
     * 导出Excel文件
     */
    public void outputExcel() {
        //TODO output excel work
    }

    /**
     * 处理返回的大赛对象结果
     *
     * @param competitions    jsonResult
     * @param competitionList list
     */
    public void dealCompetitionListJSON(JSONArray competitions, List<Map<String, Object>> competitionList) {
        for (Object object : competitions) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Competition competition = JSONObject.parseObject(jsonString, Competition.class);
            Map<String, Object> competitionMap = new HashMap<>(32);
            competitionMap.put("competitionId", competition.getCompetitionId());
            competitionMap.put("competitionYear", competition.getCompetitionYear());
            competitionMap.put("varFatnessM", competition.getVarFatnessM());
            competitionMap.put("varFatnessF", competition.getVarFatnessF());
            competitionMap.put("varWeightM", competition.getVarWeightM());
            competitionMap.put("varWeightF", competition.getVarWeightF());
            competitionMap.put("varMfatnessSd", competition.getVarMfatnessSd());
            competitionMap.put("varMweightSd", competition.getVarMweightSd());
            competitionMap.put("varFfatnessSd", competition.getVarFfatnessSd());
            competitionMap.put("varFweightSd", competition.getVarFweightSd());
            competitionMap.put("resultFatness", competition.getResultFatness());
            competitionMap.put("resultQuality", competition.getResultQuality());
            competitionMap.put("resultTaste", competition.getResultTaste());
            competitionMap.put("note", competition.getNote());
            competitionMap.put("status", competition.getStatus());
            competitionMap.put("createDate", competition.getCreateDate());
            competitionMap.put("createUser", competition.getCreateUser());
            competitionMap.put("updateDate", competition.getUpdateDate());
            competitionMap.put("updateUser", competition.getUpdateUser());
            if (!competitionList.contains(competitionMap)) {
                competitionList.add(competitionMap);
            }
        }
    }
}
