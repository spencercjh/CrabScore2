package top.spencer.crabscore.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import top.spencer.crabscore.R;

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
        mProgressDialog.setCancelable(true);
    }

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            if (!"main".equals(Thread.currentThread().getName())) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                mProgressDialog.show();
                Looper.loop();
            }
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            if (!"main".equals(Thread.currentThread().getName())) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                mProgressDialog.dismiss();
                Looper.loop();
            }
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        if (!"main".equals(Thread.currentThread().getName())) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_sever_error_msg));
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}