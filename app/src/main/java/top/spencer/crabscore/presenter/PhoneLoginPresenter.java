package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.data.constant.Token;
import top.spencer.crabscore.data.model.ModelFactory;
import top.spencer.crabscore.data.model.PhoneLoginModel;
import top.spencer.crabscore.view.PhoneLoginView;

/**
 * @author spencercjh
 */
@SuppressWarnings("Duplicates")
public class PhoneLoginPresenter extends BasePresenter<PhoneLoginView> {
    /**
     * 手机登陆
     * <p>
     * 这个接口会在body里返回JWT！
     *
     * @param mobile 手机号
     * @see PhoneLoginModel
     */
    public void loginOrRegist(String mobile) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_LOGIN_OR_REGIST)
                .params(mobile)
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
