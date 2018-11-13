package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.model.common.*;
import top.spencer.crabscore.ui.view.VerifyCodeView;

/**
 * @author spencercjh
 */
public class VerifyCodePresenter extends BasePresenter<VerifyCodeView> {
    /**
     * 注册请求
     *
     * @param username    用户名（其实是手机号作为用户名）
     * @param password    密码
     * @param roleId      用户组（1,2,3,4）
     * @param email       其实是手机
     * @param displayName 显示名
     * @see RegistModel
     */
    public void regist(String username, String password, String roleId, String email, String displayName) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_REGIST)
                .params(username, password, roleId, email, displayName)
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
     * 忘记密码请求
     *
     * @param username    用户名（手机号）
     * @param newPassword 新密码
     * @see ForgetPasswordModel
     */
    public void forgetPassword(String username, String newPassword) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_FORGET_PASSWORD)
                .params(username, newPassword)
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
     * 手机登陆
     * <p>
     * 这个接口会在body里返回JWT！
     *
     * @param mobile 手机号
     * @see PhoneLoginModel
     */
    public void phoneLogin(String mobile) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_LOGIN_OR_REGIST)
                .params(mobile)
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
     * 发送验证码请求
     *
     * @param mobile 手机号
     * @see SendCodeModel
     */
    public void sendCode(String mobile) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_SEND_CODE)
                .params(mobile)
                .execute(new MyCallback<JSONObject>() {
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
     * @see VerifyCodeModel
     */
    public void verifyCode(String mobile, String code) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_VERIFY_CODE)
                .params(mobile, code)
                .execute(new MyCallback<JSONObject>() {
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
