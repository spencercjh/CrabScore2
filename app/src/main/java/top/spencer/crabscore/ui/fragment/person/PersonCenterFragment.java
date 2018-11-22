package top.spencer.crabscore.ui.fragment.person;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.PatternUtil;
import top.spencer.crabscore.common.util.QiNiuUploadUtil;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.presenter.PersonCenterPresenter;
import top.spencer.crabscore.ui.view.PersonCenterView;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * 个人中心页面
 *
 * @author spencercjh
 */
public class PersonCenterFragment extends BaseFragment implements PersonCenterView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.re_avatar)
    RelativeLayout avatarRelativeLayout;
    @BindView(R.id.re_update_user_name)
    RelativeLayout usernameRelativeLayout;
    @BindView(R.id.re_update_password)
    RelativeLayout passwordRelativeLayout;
    @BindView(R.id.re_update_display_name)
    RelativeLayout displayNameRelativeLayout;
    @BindView(R.id.re_update_phone)
    RelativeLayout phoneRelativeLayout;
    @BindView(R.id.imageview_avatar)
    ImageView avatarImageView;
    @BindView(R.id.textview_user_name)
    TextView usernameTextView;
    @BindView(R.id.textview_display_name)
    TextView displayNameTextView;
    @BindView(R.id.textview_phone)
    TextView phoneTextView;
    private PersonCenterPresenter personCenterPresenter;
    private String jwt;
    private User user;
    private String accesskey;
    private String secret;
    private String bucket;
    private final Integer UPLOAD_CODE = 0x1;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static PersonCenterFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        PersonCenterFragment fragment = new PersonCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        personCenterPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_person_center;
    }

    /**
     * 初始化视图
     *
     * @param view               view
     * @param savedInstanceState saveInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personCenterPresenter = new PersonCenterPresenter();
        personCenterPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
        initView();
        Glide.with(Objects.requireNonNull(getContext()))
                .load(user.getAvatarUrl())
                .into(avatarImageView);
    }

    /**
     * 数据初始化UI
     */
    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        if (user != null) {
            if (user.getUserName() != null) {
                usernameTextView.setText(user.getUserName());
            }
            if (user.getDisplayName() != null) {
                displayNameTextView.setText(user.getDisplayName());
            }
            if (user.getEmail() != null) {
                phoneTextView.setText(user.getEmail());
            }
        }
    }

    /**
     * 初始发起请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 监听更换头像RelativeLayout
     *
     * @param view view
     */
    @OnClick(R.id.re_avatar)
    public void updateAvatar(View view) {
        accesskey = (String) SharedPreferencesUtil.getData("accessKey", CommonConstant.NULL);
        secret = (String) SharedPreferencesUtil.getData("secretKey", CommonConstant.NULL);
        bucket = (String) SharedPreferencesUtil.getData("bucket", CommonConstant.NULL);
        if (CommonConstant.NULL.equals(accesskey) &&
                CommonConstant.NULL.equals(secret) &&
                CommonConstant.NULL.equals(bucket)) {
            personCenterPresenter.getQiNiuPropertyAndUpload(jwt);
        } else {
            personCenterPresenter.upload();
        }
    }

    /**
     * 监听修改用户名RelativeLayout
     *
     * @param view view
     */
    @OnClick(R.id.re_update_user_name)
    public void updateUsername(View view) {
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改用户名");
        final EditText usernameEditText = dialogView.findViewById(R.id.edit_single_line);
        if (user.getUserName() != null) {
            usernameEditText.setText(user.getUserName());
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改用户名");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = usernameEditText.getText().toString().trim();
                if (PatternUtil.isUsername(username)) {
                    user.setUserName(username);
                    user.setUpdateUser(user.getUserName());
                    user.setUpdateDate(new Date(System.currentTimeMillis()));
                    personCenterPresenter.updateUserProperty(user, jwt);
                } else {
                    showToast("非法用户名");
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 监听修改密码RelativeLayout
     *
     * @param view view
     */
    @OnClick(R.id.re_update_password)
    public void updatePassword(View view) {
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_password, null);
        final EditText passwordEditText = dialogView.findViewById(R.id.edit_password);
        final EditText newPasswordEditText = dialogView.findViewById(R.id.edit_new_password);
        final ToggleButton togglePassword = dialogView.findViewById(R.id.toggle_password);
        final ToggleButton toggleNewPassword = dialogView.findViewById(R.id.toggle_new_password);
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                personCenterPresenter.toggleButtonDisplayPassword(togglePassword, passwordEditText,
                        isChecked, getContext());
            }
        });
        toggleNewPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                personCenterPresenter.toggleButtonDisplayPassword(toggleNewPassword, newPasswordEditText,
                        isChecked, getContext());
            }
        });
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改密码");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = passwordEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                if (password.equals(user.getPassword())) {
                    if (PatternUtil.isUsername(password)) {
                        user.setPassword(newPassword);
                        user.setUpdateUser(user.getUserName());
                        user.setUpdateDate(new Date(System.currentTimeMillis()));
                        personCenterPresenter.updateUserProperty(user, jwt);
                    } else {
                        showToast("非法密码");
                    }
                } else {
                    showToast("旧密码输入错误");
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 监听修改显示名RelativeLayout
     *
     * @param view view
     */
    @OnClick(R.id.re_update_display_name)
    public void updateDisplayName(View view) {
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改显示名");
        final EditText displayNameEditText = dialogView.findViewById(R.id.edit_single_line);
        if (user.getDisplayName() != null) {
            displayNameEditText.setText(user.getDisplayName());
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改显示名");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String displayName = displayNameEditText.getText().toString().trim();
                if (PatternUtil.isName(displayName)) {
                    user.setDisplayName(displayName);
                    user.setUpdateUser(user.getUserName());
                    user.setUpdateDate(new Date(System.currentTimeMillis()));
                    personCenterPresenter.updateUserProperty(user, jwt);
                } else {
                    showToast("非法显示名");
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 监听修改手机RelativeLayout
     *
     * @param view view
     */
    @OnClick(R.id.re_update_phone)
    public void updatePhone(View view) {
        @SuppressLint("InflateParams") View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改手机号");
        final EditText phoneEditText = dialogView.findViewById(R.id.edit_single_line);
        if (user.getEmail() != null) {
            phoneEditText.setText(user.getEmail());
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改手机号");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone = phoneEditText.getText().toString().trim();
                if (PatternUtil.isMobile(phone)) {
                    user.setEmail(phone);
                    user.setUpdateUser(user.getUserName());
                    user.setUpdateDate(new Date(System.currentTimeMillis()));
                    personCenterPresenter.updateUserProperty(user, jwt);
                } else {
                    showToast("非法手机号");
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * updateUserProperty请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    initView();
                }
            });
        }
    }

    /**
     * SwipeRefreshLayout监听刷新
     */
    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * GetQiNiuPropertyAndUpload请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showGetQiNiuPropertyAndUploadResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            String resultJSON = successData.getString("result");
            JSONObject resultJSONObject = JSON.parseObject(resultJSON);
            accesskey = resultJSONObject.getString("accessKey");
            secret = resultJSONObject.getString("secretKey");
            bucket = resultJSONObject.getString("bucket");
            SharedPreferencesUtil.putData("accessKey", accesskey);
            SharedPreferencesUtil.putData("secretKey", secret);
            SharedPreferencesUtil.putData("bucket", bucket);
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, UPLOAD_CODE);
        }
    }

    /**
     * 利用本地存储的秘钥上传七牛云
     */
    @Override
    public void upload() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, UPLOAD_CODE);
    }

    /**
     * 图片选择后进行上传
     *
     * @param requestCode 0x1
     * @param resultCode  RESULT_OK
     * @param data        picture
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_CODE && resultCode == RESULT_OK) {
            QiNiuUploadUtil.init(accesskey, secret, bucket);
            if (data != null) {
                Cursor cursor = null;
                ContentResolver resolver = Objects.requireNonNull(getActivity()).getContentResolver();
                try {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    cursor = resolver.query(Objects.requireNonNull(uri), projection, null, null, null);
                    int columnIndex = Objects.requireNonNull(cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String photoPath = cursor.getString(columnIndex);
                    QiNiuUploadUtil.uploadPicture(photoPath, UUID.randomUUID().toString(), new QiNiuUploadUtil.UploadCallBack() {
                        @Override
                        public void success(String url) {
                            Glide.with(Objects.requireNonNull(getContext()))
                                    .load(CommonConstant.QINIU_URL + url)
                                    .into(avatarImageView);
                            user.setAvatarUrl(CommonConstant.QINIU_URL + url);
                            personCenterPresenter.updateUserProperty(user, jwt);
                        }

                        @Override
                        public void fail(String key, ResponseInfo info) {
                            showToast(key + info.error);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Objects.requireNonNull(cursor).close();
                }
            }
        }
    }
}