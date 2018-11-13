package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.ui.view.UserAdminListView;

/**
 * @author spencercjh
 */
public class UserAdminPresenter extends BasePresenter<UserAdminListView> {
    /**
     * 管理员修改用户资料
     *
     * @param user 用户对象
     * @param jwt  JWT
     * @see top.spencer.crabscore.model.model.administrator.UpdateUserPropertyModel
     */
    public void updateUserProperty(User user, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_USER_PROPERTY)
                .params(JSON.toJSON(user).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdateUserPropertyResponse(data);
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
     * 管理员删除用户
     *
     * @param userId 用户id
     * @param jwt    JWT
     */
    public void deleteUser(Integer userId, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_DELETE_USER)
                .params(String.valueOf(userId), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showDeleteUserResponse(data);
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
