package top.spencer.crabscore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.*;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.VerifyCodePresenter;
import top.spencer.crabscore.common.util.PatternUtil;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.ui.view.VerifyCodeView;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 注册活动
 *
 * @author spencercjh
 */
public class RegistActivity extends BaseActivity implements VerifyCodeView {
    @BindView(R.id.edit_phone_regist)
    EditText phone;
    @BindView(R.id.edit_password_regist)
    EditText password;
    @BindView(R.id.edit_repeat_password_regist)
    EditText repeatPassword;
    @BindView(R.id.edit_name_regist)
    EditText name;
    @BindView(R.id.spinner_role_regist)
    Spinner roleSpinner;
    @BindView(R.id.seekbar_verify_phone)
    SeekBar verifyPhone;
    @BindView(R.id.edit_code_verify)
    EditText code;
    @BindView(R.id.toggle_password_regist)
    ToggleButton togglePassword;
    @BindView(R.id.toggle_repeat_password_regist)
    ToggleButton toggleRepeatPassword;
    @BindView(R.id.button_verify_code)
    Button verifyCode;
    @BindView(R.id.button_regist)
    Button regist;
    @BindArray(R.array.roles)
    String[] roles;
    private VerifyCodePresenter verifyCodePresenter;
    private int seekBarProgress = 0;
    private boolean isVerified = false;
    private int roleChoice = 0;
    private boolean isDelayed = false;
    private ThreadPoolExecutor executor;
    private long nextSendCodeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        verifyCodePresenter = new VerifyCodePresenter();
        verifyCodePresenter.attachView(this);
        initThreadPool();
        initSeekBar();
        initSpinner();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyCodePresenter.detachView();
        executor.shutdown();
    }

    /**
     * 显示密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @SuppressWarnings("unused")
    @OnCheckedChanged(R.id.toggle_password_regist)
    void displayPassword(CompoundButton buttonView, boolean isChecked) {
        verifyCodePresenter.toggleButtonDisplayPassword(togglePassword, password, isChecked, getContext());
    }

    /**
     * 显示确认密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @SuppressWarnings("unused")
    @OnCheckedChanged(R.id.toggle_repeat_password_regist)
    void displayRepeatPassword(CompoundButton buttonView, boolean isChecked) {
        verifyCodePresenter.toggleButtonDisplayPassword(toggleRepeatPassword, repeatPassword, isChecked, getContext());
    }

    /**
     * 注册按钮的监听
     *
     * @param view view
     */
    @SuppressWarnings("WeakerAccess")
    @OnClick(R.id.button_regist)
    public void regist(View view) {
        String mobile = phone.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String repeatPasswordString = repeatPassword.getText().toString().trim();
        String nameString = name.getText().toString().trim();
        if (!PatternUtil.isMobile(mobile)) {
            showToast("非法手机号");
            return;
        } else if (!PatternUtil.isUsername(passwordString) || !PatternUtil.isUsername(repeatPasswordString)) {
            showToast("非法密码");
            return;
        } else if (!PatternUtil.isName(nameString)) {
            showToast("非法姓名");
            return;
        } else if (!isVerified) {
            showToast("请通过手机号校验");
            return;
        } else if (!passwordString.equals(repeatPasswordString)) {
            showToast("两次密码不一致");
            return;
        }
        verifyCodePresenter.regist(mobile, passwordString, String.valueOf(roleChoice), mobile, nameString);
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
     * 初始化Spinner
     */
    @SuppressWarnings("Duplicates")
    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                roleChoice = pos;
                if (pos == CommonConstant.USER_TYPE_ADMIN) {
                    SharedPreferencesUtil.putData(CommonConstant.ADMINISTRATOR, true);
                } else if (pos == CommonConstant.USER_TYPE_JUDGE) {
                    SharedPreferencesUtil.putData(CommonConstant.JUDGE, true);
                } else if (pos == CommonConstant.USER_TYPE_STAFF) {
                    SharedPreferencesUtil.putData(CommonConstant.STAFF, true);
                } else if (pos == CommonConstant.USER_TYPE_COMPANY) {
                    SharedPreferencesUtil.putData(CommonConstant.COMPANY, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SharedPreferencesUtil.putData(CommonConstant.ADMINISTRATOR, false);
                SharedPreferencesUtil.putData(CommonConstant.JUDGE, false);
                SharedPreferencesUtil.putData(CommonConstant.STAFF, false);
                SharedPreferencesUtil.putData(CommonConstant.COMPANY, false);
            }
        });
    }

    /**
     * 注册成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("USERNAME", phone.getText().toString().trim());
            intent.putExtra("PASSWORD", password.getText().toString().trim());
            intent.putExtra("ROLE_CHOICE", roleChoice);
            startActivity(intent);
            finish();
        } else {
            showToast(successData.getString(CommonConstant.MESSAGE));
        }

    }

    /**
     * 发送验证码成功
     *
     * @param successData 携带验证码的JSON串
     */
    @Override
    public void dealSendCode(JSONObject successData) {
        Integer code = successData.getInteger(CommonConstant.CODE);
        String message = successData.getString(CommonConstant.MESSAGE);
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
        Integer code = successData.getInteger(CommonConstant.CODE);
        String message = successData.getString(CommonConstant.MESSAGE);
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
