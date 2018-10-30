package top.spencer.crabscore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.*;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.RegistPresenter;
import top.spencer.crabscore.util.PatternUtil;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.RegistView;

import static android.content.ContentValues.TAG;

/**
 * @author spencercjh
 */
@SuppressWarnings("Duplicates")
public class RegistActivity extends BaseActivity implements RegistView {
    private RegistPresenter registPresenter;

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
    Button sendCode;
    @BindView(R.id.button_regist)
    Button regist;
    @BindArray(R.array.roles)
    String[] roles;

    private final static Integer SUCCESS_VERIFY = 100;
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
        ButterKnife.bind(this);
        registPresenter = new RegistPresenter();
        registPresenter.attachView(this);
        initSeekBar();
        initSpinner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registPresenter.detachView();
    }

    @OnCheckedChanged(R.id.toggle_password_regist)
    public void displayPassword(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //如果选中，显示密码
            togglePassword.setBackground(getDrawable(R.drawable.eye_open));
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            togglePassword.setBackground(getDrawable(R.drawable.eye_close));
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnCheckedChanged(R.id.toggle_repeat_password_regist)
    public void displayRepeatPassword(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //如果选中，显示密码
            toggleRepeatPassword.setBackground(getDrawable(R.drawable.eye_open));
            repeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            toggleRepeatPassword.setBackground(getDrawable(R.drawable.eye_close));
            repeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

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
        registPresenter.regist(mobile, passwordString, String.valueOf(roleChoice), mobile, nameString);
    }

    @OnClick(R.id.button_verify_code)
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
            registPresenter.verifyCode(mobile, codeString);
        } else {
            showToast("非法手机号");
        }
    }

    @Override
    public void initSeekBar() {
        verifyPhone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBarProgress = progress;
                }
                /*if(seekBarProgress==100){
                    showToast("滑动条验证成功！将发送验证码短信");
                }*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showToast("拖到底后会收到验证码短信！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBarProgress == SUCCESS_VERIFY) {
                    showToast("滑动条验证成功！将发送验证码短信");
                    String mobile = phone.getText().toString().trim();
                    registPresenter.sendCode(mobile);
                }
            }
        });
    }

    @Override
    public void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Log.d(TAG, "用户组改变");
                showToast("用户组改变");
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
                Log.d(TAG, "用户组未改变");
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
        String message = successData.getString("message");
        showToast(message);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("USERNAME", phone.getText().toString().trim());
        intent.putExtra("PASSWORD", password.getText().toString().trim());
        intent.putExtra("ROLE_CHOICE", roleChoice);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailure(JSONObject errorData) {
        String message = errorData.getString("message");
        showToast(message);
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
