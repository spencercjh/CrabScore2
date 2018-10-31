package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.Callback;
import top.spencer.crabscore.data.constant.Token;
import top.spencer.crabscore.data.model.ModelFactory;

/**
 * @author spencercjh
 */
public class LoginPresenter extends BasePresenter {


    /**
     * 登陆请求
     * <p>
     * 这个接口会在body里返回JWT！！！
     *
     * @param username 用户名
     * @param password 密码
     * @param roleId   用户组（1、2、3、4）
     * @see top.spencer.crabscore.data.model.LoginModel
     */
    public void login(String username, String password, String roleId) {
        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        getView().showLoading();
        ModelFactory
                // 设置请求标识token
                .request(Token.API_LOGIN)
                // 添加请求参数，没有则不添加
                .params(username, password, roleId)
                // 注册监听回调
                .execute(new Callback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        //调用view接口显示数据，在具体的Activity中被重载
                        getView().showData(data);
                    }

                    @Override
                    public void onFailure(JSONObject data) {
                        //调用view接口提示失败信息，在具体的Activity中被重载
                        getView().showFailure(data);
                    }

                    @Override
                    public void onError() {
                        //调用view接口提示请求异常，在BaseActivity中已经实现
                        getView().showErr();
                    }

                    @Override
                    public void onComplete() {
                        // 隐藏正在加载进度条，在BaseActivity中已经实现
                        getView().hideLoading();
                    }
                });
    }
}