package top.spencer.crabscore.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.common.CommonConstant;

/**
 * @author spencercjh
 */
@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("请稍后");
        mProgressDialog.setCancelable(true);
    }

    @Override
    public void showLoading() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_sever_error_msg));
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void showFailure(JSONObject errorData) {
        showToast(errorData.getString("message"));
    }
}