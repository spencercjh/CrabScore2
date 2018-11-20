package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Crab;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.ui.view.StaffGroupListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author spencercjh
 */
public class StaffPresenter extends BasePresenter<StaffGroupListView> {
    /**
     * 查找所有比赛小组
     *
     * @param competitionId 大赛Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see top.spencer.crabscore.model.model.staff.GetAllGroupModel
     */
    public void getAllGroup(Integer competitionId, Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_GROUP_STAFF)
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
     * 添加螃蟹
     *
     * @param crab 螃蟹对象
     * @param jwt  JWT
     * @see top.spencer.crabscore.model.model.staff.AddCrabListModel
     */
    public void addCrab(Crab crab, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_ADD_CRAB)
                .params(JSON.toJSON(crab).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showAddCrabResponse(data);
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
     * 批量添加螃蟹
     *
     * @param crabList crabList
     * @param jwt      JWT
     * @see top.spencer.crabscore.model.model.staff.AddCrabListModel
     */
    private void addCrabList(List<Crab> crabList, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_ADD_CRAB_LIST)
                .params(JSON.toJSON(crabList).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showAddCrabResponse(data);
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
     * 生成螃蟹对象放入List并发起批量添加螃蟹请求
     *
     * @param groupInDialog groupResult
     * @param addAmount     addAmount
     * @param isAddMale     isAddMale
     * @param isAddFemale   isAddFemale
     * @param username      staff user name
     * @param jwt           JWT
     * @see StaffPresenter#addCrabList(List, String)
     */
    public void sendCrabList(GroupResult groupInDialog, int addAmount, boolean isAddMale, boolean isAddFemale,
                             String username, String jwt) {
        List<Crab> crabList = new ArrayList<>(addAmount * 2);
        if (isAddFemale) {
            for (int i = 0; i < addAmount; ++i) {
                Crab crab = new Crab();
                crab.setGroupId(groupInDialog.getGroupId());
                crab.setCrabSex(CommonConstant.CRAB_FEMALE);
                crab.setCrabLabel(String.valueOf(groupInDialog.getCompetitionId()) +
                        String.valueOf(groupInDialog.getCompanyId()) +
                        String.valueOf(groupInDialog.getGroupId()) +
                        String.valueOf(CommonConstant.CRAB_FEMALE) +
                        String.valueOf(i));
                crab.setCompetitionId(groupInDialog.getCompetitionId());
                crab.setCreateDate(new Date(System.currentTimeMillis()));
                crab.setUpdateDate(new Date(System.currentTimeMillis()));
                crab.setCreateUser(username);
                crab.setUpdateUser(username);
                crabList.add(crab);
            }
        }
        if (isAddMale) {
            for (int i = 0; i < addAmount; ++i) {
                Crab crab = new Crab();
                crab.setGroupId(groupInDialog.getGroupId());
                crab.setCrabSex(CommonConstant.CRAB_MALE);
                crab.setCrabLabel(String.valueOf(groupInDialog.getCompetitionId()) +
                        String.valueOf(groupInDialog.getCompanyId()) +
                        String.valueOf(groupInDialog.getGroupId()) +
                        String.valueOf(CommonConstant.CRAB_MALE) +
                        String.valueOf(i));
                crab.setCompetitionId(groupInDialog.getCompetitionId());
                crab.setCreateDate(new Date(System.currentTimeMillis()));
                crab.setUpdateDate(new Date(System.currentTimeMillis()));
                crab.setCreateUser(username);
                crab.setUpdateUser(username);
                crabList.add(crab);
            }
        }
        this.addCrabList(crabList, jwt);
    }

    /**
     * 根据标签查找螃蟹
     *
     * @param label label
     * @param jwt   JWT
     * @see top.spencer.crabscore.model.model.staff.FindCrabByLabelModel
     */
    public void findCrabByLabel(String label, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_FIND_CRAB_BY_LABEL)
                .params(label, jwt)
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
     * 查找某一年某一组某一性别的所有螃蟹
     *
     * @param competitionId 大赛Id
     * @param groupId       小组Id
     * @param sex           性别
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see top.spencer.crabscore.model.model.staff.GetOneGroupOneSexCrabModel
     */
    public void getOneGroupOneSexCrab(Integer competitionId, Integer groupId, Integer sex, Integer pageNum, Integer pageSize,
                                      String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ONE_GROUP_ONE_SEX_CRAB)
                .params(String.valueOf(competitionId), String.valueOf(groupId), String.valueOf(sex),
                        String.valueOf(pageNum), String.valueOf(pageSize), jwt)
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

    public boolean dealCrabJSON(JSONArray results, List<Crab> crabList) {
        boolean repeat = true;
        for (Object object : results) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Crab crab = JSONObject.parseObject(jsonString, Crab.class);
            //是否需要调用add方法添加到crabList中去
            boolean needAdd = true;
            //遍历已有的crabList
            for (int i = 0; i < crabList.size(); ++i) {
                //存在新crab对象和已有的crab对象的id相同
                if (crabList.get(i).getCrabId().equals(crab.getCrabId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!crabList.get(i).equals(crab)) {
                        //更新属性信息
                        crabList.set(i, crab);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                crabList.add(crab);
            }
        }
        return repeat;
    }

    /**
     * 更新螃蟹信息
     *
     * @param crab 螃蟹对象
     * @param jwt  JWT
     * @see top.spencer.crabscore.model.model.staff.UpdateCrabInfoModel
     */
    public void updateCrabInfo(Crab crab, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_CRAB_INFO)
                .params(JSON.toJSON(crab).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdateCrabInfoResponse(data);
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
