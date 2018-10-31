package top.spencer.crabscore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.VerifyCodePresenter;
import top.spencer.crabscore.util.PatternUtil;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.VerifyCodeView;

import java.util.Map;

/**
 * 手机登陆
 *
 * @author spencercjh
 */
public class PhoneLoginActivity extends BaseActivity implements VerifyCodeView {
    private VerifyCodePresenter verifyCodePresenter;
    @BindView(R.id.edit_phone_login)
    EditText phone;
    @BindView(R.id.edit_code_verify)
    EditText code;
    @BindView(R.id.button_verify_code)
    Button verifyCode;
    @BindView(R.id.button_phone_login)
    Button phoneLogin;
    @BindView(R.id.seekbar_verify_phone)
    SeekBar verifyPhone;

    private int seekBarProgress = 0;
    private boolean isVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("手机号一键登录");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        verifyCodePresenter = new VerifyCodePresenter();
        verifyCodePresenter.attachView(this);
        initSeekBar();
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
    @SuppressWarnings("Duplicates")
    @Override
    public void initSeekBar() {
        verifyPhone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBarProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showToast("拖到底后会收到验证码短信！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBarProgress == CommonConstant.SUCCESS_VERIFY) {
                    showToast("滑动条验证成功！将发送验证码短信");
                    String mobile = phone.getText().toString().trim();
                    verifyCodePresenter.sendCode(mobile);
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
    @SuppressWarnings("Duplicates")
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
     * 手机登录按钮的监听
     *
     * @param view view
     */
    @OnClick(R.id.button_phone_login)
    public void loginOrRegist(View view) {
        String mobile = phone.getText().toString().trim();
        if (PatternUtil.isMobile(mobile)) {
            if (isVerified) {
                verifyCodePresenter.loginOrRegist(mobile);
            } else {
                showToast("请通过手机号校验");
            }
        } else {
            showToast("非法手机号");
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
            showToast(message);
        }
    }

    /**
     * 一键登录/注册成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            SharedPreferencesUtil.putData("USERNAME", phone.getText().toString().trim());
            showToast(successData.getString("message"));
            Map result = (Map) successData.get("result");
            SharedPreferencesUtil.putData("JWT", result.get("jwt"));
            SharedPreferencesUtil.putData("ROLE_CHOICE", result.get("roleId"));
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
