package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Company;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.model.administrator.GetAllCompanyModel;
import top.spencer.crabscore.model.model.administrator.GetAllUserByStatusModel;
import top.spencer.crabscore.model.model.administrator.GetAllUserModel;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.ui.view.MyRecycleListView;

import java.util.List;


/**
 * @author spencercjh
 */
public class AdministratorListPresenter extends BasePresenter<MyRecycleListView> {
    /**
     * 查询所有用户，只返回状态为启用的用户
     *
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @param jwt      JWT
     * @see GetAllUserModel
     */
    public void getAllUser(Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_USER)
                .params(String.valueOf(pageNum), String.valueOf(pageSize), jwt)
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
     * 查询所有符合某一状态的用户
     *
     * @param status   状态（未启用：0）
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @param jwt      JWT
     * @see GetAllUserByStatusModel
     */
    public void getAllUserByStatus(Integer status, Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_USER_BY_STATUS)
                .params(String.valueOf(status), String.valueOf(pageNum), String.valueOf(pageSize), jwt)
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
     * 处理返回的用户结果
     *
     * @param users    jsonResult
     * @param userList list
     * @return 是否重复
     */
    public boolean dealUserListJSON(JSONArray users, List<User> userList) {
        boolean repeat = true;
        for (Object object : users) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            User user = JSONObject.parseObject(jsonString, User.class);
            //是否需要调用add方法添加到userList中去
            boolean needAdd = true;
            //遍历已有的userList
            for (int i = 0; i < userList.size(); ++i) {
                //存在新user对象和已有的user对象的id相同
                if (userList.get(i).getUserId().equals(user.getUserId())) {
                    needAdd = false;
                    //属性发生了变化
                    if (!userList.get(i).equals(user)) {
                        //更新属性信息
                        userList.set(i, user);
                        repeat = false;
                    }
                    break;
                }
            }
            if (needAdd) {
                repeat = false;
                userList.add(user);
            }
        }
        return repeat;
    }

    /**
     * 查询所有参选单位
     *
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @param jwt      JWT
     * @see GetAllCompanyModel
     */
    public void getAllCompany(Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ALL_COMPANY_ADMIN)
                .params(String.valueOf(pageNum), String.valueOf(pageSize), jwt)
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
     * 处理返回的参选单位结果
     *
     * @param companies   jsonResult
     * @param companyList list
     * @return 是否重复
     */
    public boolean dealCompanyListJSON(JSONArray companies, List<Company> companyList) {
        boolean repeat = true;
        for (Object object : companies) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Company company = JSONObject.parseObject(jsonString, Company.class);
            if (!companyList.contains(company)) {
                companyList.add(company);
                repeat = false;
            }
        }
        return repeat;
    }


    /**
     * 处理返回的参选单位结果
     *
     * @param companies   jsonResult
     * @param companyList list
     * @return 是否重复
     */
    public boolean dealCompanyListJSON(JSONArray companies, List<Company> companyList, List<String> companyNameList) {
        boolean repeat = true;
        for (Object object : companies) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Company company = JSONObject.parseObject(jsonString, Company.class);
            if (!companyList.contains(company)) {
                companyList.add(company);
                companyNameList.add(company.getCompanyName());
                repeat = false;
            }
        }
        return repeat;
    }
}
