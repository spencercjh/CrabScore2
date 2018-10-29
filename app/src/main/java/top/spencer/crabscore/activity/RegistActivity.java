package top.spencer.crabscore.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.presenter.RegistPresenter;
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
    @BindView(R.id.edit_code_regist)
    EditText code;
    @BindView(R.id.toggle_password_regist)
    ToggleButton togglePassword;
    @BindView(R.id.toggle_repeat_password_regist)
    ToggleButton toggleRepeatPassword;
    @BindView(R.id.toggle_code_regist)
    ToggleButton toggleCode;
    @BindView(R.id.button_regist)
    Button regist;
    @BindArray(R.array.roles)
    String[] roles;

    private int seekbarProgress = 0;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registPresenter.detachView();
    }

    //TODO 3个toggleButton的监听

    //TODO 参数校验 滑动检测

    @Override
    public void showData(JSONObject successData) {

    }

    @Override
    public void showFailure(JSONObject errorData) {

    }

    @OnClick(R.id.button_regist)
    public void regist(View view) {
        registPresenter.regist(phone.getText().toString().trim(),
                password.getText().toString().trim(),
                String.valueOf(roleChoice),
                phone.getText().toString().trim(),
                name.getText().toString().trim());
    }

    @Override
    public void initSeekBar() {
        verifyPhone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekbarProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showToast("请拖到底后点击校验按钮！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
}
