package top.spencer.crabscore.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.VerifyCodePresenter;
import top.spencer.crabscore.util.PatternUtil;
import top.spencer.crabscore.view.view.VerifyCodeView;
import top.spencer.crabscore.view.helper.InitHelper;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 忘记密码活动
 *
 * @author spencercjh
 */
public class ForgetPasswordActivity extends BaseActivity implements VerifyCodeView {
    private VerifyCodePresenter verifyCodePresenter;
    @BindView(R.id.edit_phone)
    EditText phone;
    @BindView(R.id.seekbar_verify_phone)
    SeekBar verifyPhone;
    @BindView(R.id.button_verify_code)
    Button verifyCode;
    @BindView(R.id.edit_code_verify)
    EditText code;
    @BindView(R.id.edit_password_update)
    EditText password;
    @BindView(R.id.toggle_password_update)
    ToggleButton togglePassword;
    @BindView(R.id.button_update_password)
    Button updatePassword;

    private int seekBarProgress = 0;
    private boolean isVerified = false;
    private boolean isDelayed = false;
    private ThreadPoolExecutor executor;
    private long nextSendCodeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("验证手机修改密码");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        verifyCodePresenter = new VerifyCodePresenter();
        verifyCodePresenter.attachView(this);
        initThreadPool();
        initSeekBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyCodePresenter.detachView();
        executor.shutdown();
    }

    /**
     * 重写actionBar返回键
     *
     * @param item item
     * @return super.onOptionsItemSelected(item)
     */
    @Override
    @SuppressWarnings("Duplicates")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 初始化SeekBar
     */
    @Override
    @SuppressWarnings("Duplicates")
    public void initSeekBar() {
        verifyPhone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBarProgress == CommonConstant.SUCCESS_VERIFY && !isDelayed) {
                    String mobile = phone.getText().toString().trim();
                    if (PatternUtil.isMobile(mobile)) {
                        showToast("滑动条验证成功！将发送验证码短信");
                        verifyCodePresenter.sendCode(mobile);
                    } else {
                        showToast("手机号格式有误");
                    }
                } else if (isDelayed) {
                    showToast("请您再稍等" + new Date(nextSendCodeTime - System.currentTimeMillis()).getSeconds() + "秒的时间");
                }
            }
        });
    }

    /**
     * 校验验证码按钮的监听
     *
     * @param view view
     */
    @OnClick(R.id.button_verify_code)
    @SuppressWarnings({"Duplicates", "WeakerAccess"})
    public void verifyCode(View view) {
        String mobile = phone.getText().toString().trim();
        String codeString = code.getText().toString().trim();
        try {
            if (codeString.length() != CommonConstant.CODE_LENGTH) {
                throw new NumberFormatException("非法验证码");
            }
            Integer.parseInt(codeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("非法验证码");
            return;
        }
        if (PatternUtil.isMobile(mobile)) {
            verifyCodePresenter.verifyCode(mobile, codeString);
        } else {
            showToast("非法手机号");
        }
    }

    /**
     * 显示密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    @OnCheckedChanged(R.id.toggle_password_update)
    public void displayPassword(CompoundButton buttonView, boolean isChecked) {
        InitHelper.toggleButtonDisplayPassword(togglePassword, password, isChecked, getContext());
    }

    @SuppressWarnings("WeakerAccess")
    @OnClick(R.id.button_update_password)
    public void updatePassword(View view) {
        String mobile = phone.getText().toString().trim();
        String newPassword = password.getText().toString().trim();
        if (!PatternUtil.isMobile(mobile)) {
            showToast("非法搜狐及");
            return;
        } else if (!PatternUtil.isUsername(newPassword)) {
            showToast("非法密码");
            return;
        } else if (!isVerified) {
            showToast("请通过手机号校验");
            return;
        }
        verifyCodePresenter.forgetPassword(mobile, newPassword);
    }

    /**
     * 修改密码成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            showToast("密码修改成功");
        } else {
            showToast(successData.getString("message"));
        }
    }


    /**
     * 发送验证码成功
     *
     * @param successData 携带验证码的JSON串
     */
    @Override
    public void dealSendCode(JSONObject successData) {
        Integer code = successData.getInteger("code");
        String message = successData.getString("message");
        if (code.equals(CommonConstant.SUCCESS) && "验证码发送成功".equals(message)) {
            showToast("验证码发送成功！");
            delaySendCode();
        } else {
            showToast("验证码发送失败！");
        }
    }

    /**
     * 校验验证码成功
     *
     * @param successData 携带校验结果的JSON串
     */
    @Override
    public void dealVerifyCode(JSONObject successData) {
        Integer code = successData.getInteger("code");
        String message = successData.getString("message");
        if (code.equals(CommonConstant.SUCCESS) && "验证码校验成功".equals(message)) {
            isVerified = true;
            showToast("验证码校验成功！");
        } else {
            showToast("验证码校验失败！");
        }
    }

    @SuppressWarnings("Duplicates")
    private void initThreadPool() {
        executor = new ThreadPoolExecutor(
                5,
                5,
                80,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @SuppressWarnings("Duplicates")
    private void delaySendCode() {
        long sendCodeTime = System.currentTimeMillis();
        nextSendCodeTime = sendCodeTime + (60 * 1000);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                verifyPhone.setBackgroundColor(getColor(R.color.tab_checked));
                verifyPhone.setProgress(0);
            }
        });
        isDelayed = true;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                    isDelayed = false;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            verifyPhone.setBackgroundColor(getColor(R.color.white));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isDelayed = true;
                    Log.e(RegistActivity.class.getName(), "延迟60s出错");
                }
            }
        });
    }
}
