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
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    private Unbinder unbinder;
    protected static int pageSize = 15;

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
        View mRootView = inflater.inflate(getContentViewId(), container, false);
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
        showToast(errorData.getString(CommonConstant.MESSAGE));
    }

    /**
     * 检查activity连接情况
     */
    private void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

}