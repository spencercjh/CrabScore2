package top.spencer.crabscore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.*;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.LoginPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.LoginView;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * 登录活动
 *
 * @author spencercjh
 */
@SuppressWarnings("Duplicates")
public class LoginActivity extends BaseActivity implements LoginView {

    private LoginPresenter loginPresenter;

    @BindView(R.id.edit_username)
    EditText username;
    @BindView(R.id.edit_password)
    EditText password;
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.button_goto_regist)
    Button register;
    @BindView(R.id.button_forget_password)
    Button forgetPassword;
    @BindView(R.id.button_phone_login)
    Button phoneLogin;
    @BindView(R.id.spinner_role)
    Spinner roleSpinner;
    @BindArray(R.array.roles)
    String[] roles;
    @BindView(R.id.checkbox_remember_password)
    CheckBox rememberPassword;
    @BindView(R.id.checkbox_auto_login)
    CheckBox autoLogin;
    @BindView(R.id.toggle_password)
    ToggleButton togglePassword;

    private int roleChoice = 0;
    private long lastPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("请先登录");
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        initSpinner();
        readSharedPreferences();
        returnFromRegist();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    /**
     * 从注册界面返回，自动填上刚注册的用户信息
     */
    private void returnFromRegist() {
        try {
            Intent intent = getIntent();
            String usernameString = intent.getStringExtra("USERNAME");
            String passwordString = intent.getStringExtra("PASSWORD");
            roleChoice = intent.getIntExtra("ROLE_CHOICE", 0);
            if (StrUtil.isBlank(usernameString)) {
                throw new Exception("并不是从注册界面返回");
            } else {
                Log.d(TAG, "从注册界面返回");
                username.setText(usernameString);
                password.setText(passwordString);
                roleSpinner.setSelection(roleChoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "并不是从注册界面返回");
        }
    }

    /**
     * 初始化Spinner
     */
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
     * 读取SharedPreferences修改UI
     */
    @Override
    public void readSharedPreferences() {
        //读取SharedPreferences中的用户组信息
        if (SharedPreferencesUtil.getData(CommonConstant.ADMINISTRATOR, Boolean.FALSE).equals(true)) {
            roleSpinner.setSelection(1);
            roleChoice = 1;
        } else if (SharedPreferencesUtil.getData(CommonConstant.JUDGE, Boolean.FALSE).equals(true)) {
            roleSpinner.setSelection(2);
            roleChoice = 2;
        } else if (SharedPreferencesUtil.getData(CommonConstant.STAFF, Boolean.FALSE).equals(true)) {
            roleSpinner.setSelection(3);
            roleChoice = 3;
        } else if (SharedPreferencesUtil.getData(CommonConstant.COMPANY, Boolean.FALSE).equals(true)) {
            roleSpinner.setSelection(4);
            roleChoice = 4;
        }
        //读取上一次用户选择的用户组
        try {
            roleChoice = (Integer) SharedPreferencesUtil.getData("ROLE_CHOICE", roleChoice);
            roleSpinner.setSelection(roleChoice);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        //记住密码
        if (SharedPreferencesUtil.getData(CommonConstant.REMEMBER_PASSWORD, false).equals(true)) {
            rememberPassword.setChecked(true);
            username.setText((String) SharedPreferencesUtil.getData("USERNAME", ""));
            password.setText((String) SharedPreferencesUtil.getData("PASSWORD", ""));
        }
        //自动登录
        if (SharedPreferencesUtil.getData(CommonConstant.AUTO_LOGIN, false).equals(true)) {
            autoLogin.setChecked(true);
            loginPresenter.login(username.getText().toString().trim(), password.getText().toString().trim(), String.valueOf(roleChoice));
        }
    }

    /**
     * 显示密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @OnCheckedChanged(R.id.toggle_password)
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

    /**
     * 记住密码的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @OnCheckedChanged(R.id.checkbox_remember_password)
    public void rememberPassword(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "记住密码已选中");
            showToast("记住密码已选中");
            SharedPreferencesUtil.putData("REMEMBER_PASSWORD", true);
        } else {
            Log.d(TAG, "记住密码没有选中");
            showToast("记住密码没有选中");
            SharedPreferencesUtil.putData("REMEMBER_PASSWORD", false);
        }
    }

    /**
     * 自动登录的监听
     *
     * @param buttonView buttonView
     * @param isChecked  isChecked
     */
    @OnCheckedChanged(R.id.checkbox_auto_login)
    public void autoLogin(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "自动登录已选中");
            showToast("自动登录已选中");
            SharedPreferencesUtil.putData("AUTO_LOGIN", true);
        } else {
            Log.d(TAG, "自动登录未选中");
            showToast("自动登录未选中");
            SharedPreferencesUtil.putData("AUTO_LOGIN", false);
        }
    }

    /**
     * 登陆按钮的监听
     *
     * @param view view
     */
    @OnClick(R.id.button_login)
    public void login(View view) {
        loginPresenter.login(username.getText().toString().trim(), password.getText().toString().trim(), String.valueOf(roleChoice));
    }

    /**
     * 注册按钮监听
     *
     * @param view view
     */
    @OnClick(R.id.button_goto_regist)
    public void register(View view) {
        Intent intent = new Intent(getContext(), RegistActivity.class);
        startActivity(intent);
    }

    /**
     * 手机登录按钮的监听
     *
     * @param view view
     */
    @OnClick(R.id.button_phone_login)
    public void phoneLoginRegist(View view) {
        Intent intent = new Intent(getContext(), PhoneLoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 忘记密码按钮的监听
     *
     * @param view view
     */
    @OnClick(R.id.button_forget_password)
    public void forgetPassword(View view) {
        Intent intent = new Intent(getContext(), ForgetPasswordActvity.class);
        startActivity(intent);
    }

    /**
     * 登陆成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        SharedPreferencesUtil.putData("USERNAME", username.getText().toString().trim());
        SharedPreferencesUtil.putData("PASSWORD", password.getText().toString().trim());
        SharedPreferencesUtil.putData("ROLE_CHOICE", roleChoice);
        showToast(successData.getString("message"));
        Map result = (Map) successData.get("result");
        SharedPreferencesUtil.putData("JWT", result.get("jwt"));
        //TODO BUG
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 重写返回键
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressTime < CommonConstant.EXIT_GAP_TIME) {
            finish();
            Runtime.getRuntime().exit(0);
        } else {
            lastPressTime = System.currentTimeMillis();
            showToast("再按一次返回键退出程序");
        }
    }
}