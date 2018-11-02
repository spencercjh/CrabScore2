package top.spencer.crabscore.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.alibaba.fastjson.JSONObject;

/**
 * @author spencercjh
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    private Unbinder unbinder;
    protected View mRootView;

    /**
     * 返回布局id
     *
     * @return content view(fragment layout) Id
     */
    public abstract int getContentViewId();

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        this.mContext = getActivity();
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        checkActivityAttached();
        ((BaseActivity) mContext).showToast(msg);
    }

    @Override
    public void showErr() {
        checkActivityAttached();
        ((BaseActivity) mContext).showErr();
    }

    @Override
    public void showFailure(JSONObject errorData) {
        showToast(errorData.getString("message"));
    }

    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

}