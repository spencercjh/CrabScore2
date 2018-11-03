package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.model.ModelFactory;
import top.spencer.crabscore.view.view.MyRecycleListView;

import java.util.List;


/**
 * @author spencercjh
 */
public class AdministratorListPresenter extends BasePresenter<MyRecycleListView> {
    /**
     * 查询所有用户
     *
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @see top.spencer.crabscore.model.model.AllUserModel
     */
    public void getAllUser(Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_ALL_USER)
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
     * 处理返回结果
     *
     * @param users    jsonResult
     * @param userList list
     * @return 是否重复
     * @see top.spencer.crabscore.view.fragment.administrator.UserAdminFragment#showData(JSONObject)
     */
    public boolean dealUserListJSON(JSONArray users, List<User> userList) {
        boolean repeat = false;
        for (Object object : users) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            User user = JSONObject.parseObject(jsonString, User.class);
            if (!userList.contains(user)) {
                userList.add(user);
            } else {
                repeat = true;
            }
        }
        return repeat;
    }
}
