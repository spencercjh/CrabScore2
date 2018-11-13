package top.spencer.crabscore.base;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ToggleButton;
import top.spencer.crabscore.R;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author spencercjh
 */
public class BasePresenter<V extends BaseView> {

    /**
     * 绑定的view
     */
    private Reference<V> mvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V view) {
        this.mvpView = new WeakReference<>(view);
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        if (mvpView != null) {
            mvpView.clear();
            this.mvpView = null;
        }
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    protected boolean isViewAttached() {
        return mvpView == null || mvpView.get() == null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return mvpView.get();
    }

    /**
     * 对显示/隐藏密码按钮的初始化封装
     *
     * @param toggleButton ToggleButton
     * @param password     EditText
     * @param isChecked    isChecked
     * @param context      activity context
     */
    public void toggleButtonDisplayPassword(ToggleButton toggleButton, EditText password, boolean isChecked, Context context) {
        if (isChecked) {
            //如果选中，显示密码
            toggleButton.setBackground(context.getDrawable(R.drawable.icon_eye_open));
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            toggleButton.setBackground(context.getDrawable(R.drawable.icon_eye_close));
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}