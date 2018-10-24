package top.spencer.crabscore.presenter;

import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.data.Callback;
import top.spencer.crabscore.data.constant.Token;
import top.spencer.crabscore.data.model.DataModel;

/**
 * @author spencercjh
 */
@SuppressWarnings("unchecked")
public class LoginPresenter extends BasePresenter<BaseView> {


    /**
     * 登陆请求
     *
     * @param username 用户名
     * @param password 密码
     */
    public void login(String username, String password) {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }

        //显示正在加载进度条
        getView().showLoading();

        DataModel
                // 设置请求标识token
                .request(Token.API_LOGIN)
                // 添加请求参数，没有则不添加`
                .params(username, password)
                // 注册监听回调
                .execute(new Callback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        //调用view接口显示数据
                        getView().showData(data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        //调用view接口提示失败信息
                        getView().showData(msg);
                    }

                    @Override
                    public void onError() {
                        //调用view接口提示请求异常
                        getView().showErr();
                    }

                    @Override
                    public void onComplete() {
                        // 隐藏正在加载进度条
                        getView().hideLoading();
                    }
                });
    }

}