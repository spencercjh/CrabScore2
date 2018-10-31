package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.data.constant.Token;
import top.spencer.crabscore.data.model.ModelFactory;
import top.spencer.crabscore.view.RegistView;

/**
 * @author spencercjh
 */
@SuppressWarnings("Duplicates")
public class RegistPresenter extends BasePresenter<RegistView> {


    /**
     * 注册请求
     *
     * @param username    用户名（其实是手机号作为用户名）
     * @param password    密码
     * @param roleId      用户组（1,2,3,4）
     * @param email       其实是手机
     * @param displayName 显示名
     * @see top.spencer.crabscore.data.model.RegistModel
     */
    public void regist(String username, String password, String roleId, String email, String displayName) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_REGIST)
                .params(username, password, roleId, email, displayName)
                .execute(new Callback<JSONObject>() {
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
     * 发送验证码请求
     *
     * @param mobile 手机号
     * @see top.spencer.crabscore.data.model.SendCodeModel
     */
    public void sendCode(String mobile) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_SEND_CODE)
                .params(mobile)
                .execute(new Callback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().dealSendCode(data);
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
     * 校验验证码请求
     *
     * @param mobile 手机号
     * @param code   验证码
     * @see top.spencer.crabscore.data.model.VerifyCodeModel
     */
    public void verifyCode(String mobile, String code) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_VERIFY_CODE)
                .params(mobile, code)
                .execute(new Callback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().dealVerifyCode(data);
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