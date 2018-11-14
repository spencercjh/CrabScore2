package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.model.model.common.person.UpdateUserPropertyModel;
import top.spencer.crabscore.ui.view.PersonCenterView;

/**
 * @author spencercjh
 */
public class PersonCenterPresenter extends BasePresenter<PersonCenterView> {
    /**
     * 更新用户属性
     *
     * @param user 用户对象
     * @param jwt  JWT
     * @see UpdateUserPropertyModel
     */
    public void updateUserProperty(User user, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_USER_PROPERTY_USER)
                .params(JSON.toJSON(user).toString(), jwt)
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
     * 获取七牛云的AppKey，Secret,BucketName，并上传七牛云
     *
     * @param jwt JWT
     * @see top.spencer.crabscore.model.model.common.person.GetQiNiuPropertyModel
     */
    public void getQiNiuPropertyAndUpload(String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_QINIU_PROPERTY)
                .params(jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showGetQiNiuPropertyAndUploadResponse(data);
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
     * 上传七牛云
     */
    public void upload() {
        getView().upload();
    }
}
