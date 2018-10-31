package top.spencer.crabscore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.*;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.VerifyCodePresenter;
import top.spencer.crabscore.util.PatternUtil;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.InitHelper;
import top.spencer.crabscore.view.VerifyCodeView;

/**
 * 注册活动
 *
 * @author spencercjh
 */
public class RegistActivity extends BaseActivity implements VerifyCodeView {
    private VerifyCodePresenter verifyCodePresenter;
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

    private int seekBarProgress = 0;
    private boolean isVerified = false;
    private int roleChoice = 0;

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
    }

    /**
     * 显示密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @OnCheckedChanged(R.id.toggle_password_regist)
    public void displayPassword(CompoundButton buttonView, boolean isChecked) {
        InitHelper.toggleButtonDisplayPassword(togglePassword, password, isChecked, getContext());
    }

    /**
     * 显示确认密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @OnCheckedChanged(R.id.toggle_repeat_password_regist)
    public void displayRepeatPassword(CompoundButton buttonView, boolean isChecked) {
        InitHelper.toggleButtonDisplayPassword(toggleRepeatPassword, repeatPassword, isChecked, getContext());
    }

    /**
     * 注册按钮的监听
     *
     * @param view view
     */
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
     * 初始化SeekBar
     */
    @Override
    @SuppressWarnings("Duplicates")
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
     * 初始化Spinner
     */
    @SuppressWarnings("Duplicates")
    public void initSpinner() {
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
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString("message"));
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("USERNAME", phone.getText().toString().trim());
            intent.putExtra("PASSWORD", password.getText().toString().trim());
            intent.putExtra("ROLE_CHOICE", roleChoice);
            startActivity(intent);
            finish();
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
}
