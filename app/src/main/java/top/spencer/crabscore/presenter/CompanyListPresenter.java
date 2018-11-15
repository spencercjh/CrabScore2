package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.vo.CrabScoreResult;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.ui.view.MyRecycleListView;

import java.util.List;

/**
 * @author spencercjh
 */
public class CompanyListPresenter extends BasePresenter<MyRecycleListView> {
    /**
     * 获取一个小组的所有螃蟹信息和相对应的种质成绩、口感成绩
     *
     * @param competitionId 大赛Id
     * @param groupId       小组Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     */
    public void getOneGroupAllCrabAndScore(Integer competitionId, Integer groupId, Integer pageNum, Integer pageSize,
                                           String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ONE_GROUP_ALL_CRAB_AND_SCORE)
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

    public boolean dealCrabScoreResultJSON(JSONArray results, List<CrabScoreResult> crabScoreResultList) {
        boolean repeat = true;
        for (Object object : results) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            CrabScoreResult crabScoreResult = JSONObject.parseObject(jsonString, CrabScoreResult.class);
            //是否需要调用add方法添加到crabScoreResultList中去
            boolean needAdd = true;
            //遍历已有的crabScoreResultList
            for (int i = 0; i < crabScoreResultList.size(); ++i) {
                //存在新crabScoreResult对象和已有的crabScoreResult对象的id相同
                if (crabScoreResultList.get(i).getCrabId().equals(crabScoreResult.getCrabId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!crabScoreResultList.get(i).equals(crabScoreResult)) {
                        //更新属性信息
                        crabScoreResultList.set(i, crabScoreResult);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                crabScoreResultList.add(crabScoreResult);
            }
        }
        return repeat;
    }
}
