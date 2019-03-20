package top.spencer.crabscore.ui.fragment.administrator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.*;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.presenter.CompetitionAdminPresenter;
import top.spencer.crabscore.presenter.NavigationPresenter;
import top.spencer.crabscore.ui.view.CompetitionAdminView;

import java.util.Objects;

/**
 * 管理员用户组的大赛配置信息管理页面
 *
 * @author spencercjh
 */
public class CompetitionAdminFragment extends BaseFragment implements CompetitionAdminView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.re_year_note)
    RelativeLayout yearAndNoteRelativeLayout;
    @BindView(R.id.textview_year_note)
    TextView yearAndNoteTextView;
    @BindView(R.id.re_var_fatness_m)
    RelativeLayout varFatnessMRelativeLayout;
    @BindView(R.id.textview_var_fatness_m)
    TextView varFatnessMTextView;
    @BindView(R.id.re_var_weight_m)
    RelativeLayout varWeightMRelativeLayout;
    @BindView(R.id.textview_var_weight_m)
    TextView varWeightMTextView;
    @BindView(R.id.re_var_mfatness_sd)
    RelativeLayout varMFatnessSdRelativeLayout;
    @BindView(R.id.textview_var_mfatness_sd)
    TextView varMFatnessSdTextView;
    @BindView(R.id.re_var_mweight_sd)
    RelativeLayout varMWeightSdRelativeLayout;
    @BindView(R.id.textview_var_mweight_sd)
    TextView varMWeightSdTextView;
    @BindView(R.id.re_var_fatness_f)
    RelativeLayout varFatnessFRelativeLayout;
    @BindView(R.id.textview_var_fatness_f)
    TextView varFatnessFTextView;
    @BindView(R.id.re_var_weight_f)
    RelativeLayout varWeightFRelativeLayout;
    @BindView(R.id.textview_var_weight_f)
    TextView varWeightFTextView;
    @BindView(R.id.re_var_ffatness_sd)
    RelativeLayout varFFatnessSdRelativeLayout;
    @BindView(R.id.textview_var_ffatness_sd)
    TextView varFFatnessSdTextView;
    @BindView(R.id.re_var_fweight_sd)
    RelativeLayout varFWeightSdRelativeLayout;
    @BindView(R.id.textview_var_fweight_sd)
    TextView varFWeightSdTextView;
    @BindView(R.id.re_more_setting)
    RelativeLayout moreSettingRelativeLayout;
    @BindView(R.id.textview_more_setting)
    TextView moreSettingTextView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Competition presentCompetition;
    private String jwt;
    private CompetitionAdminPresenter competitionAdminPresenter;
    private NavigationPresenter navigationPresenter;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static CompetitionAdminFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        CompetitionAdminFragment fragment = new CompetitionAdminFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        competitionAdminPresenter.detachView();
        navigationPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_competition_admin;
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
        competitionAdminPresenter = new CompetitionAdminPresenter();
        competitionAdminPresenter.attachView(this);
        navigationPresenter = new NavigationPresenter();
        navigationPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        swipeRefreshLayout.setOnRefreshListener(this);
        initView();
    }

    /**
     * 数据初始化UI
     */
    private void initView() {
        swipeRefreshLayout.setRefreshing(false);
        if (presentCompetition != null) {
            if (StrUtil.isNotBlank(presentCompetition.getCompetitionYear())) {
                yearAndNoteTextView.setText(presentCompetition.getCompetitionYear());
            }
            if (presentCompetition.getVarFatnessM() != null) {
                varFatnessMTextView.setText(String.valueOf(presentCompetition.getVarFatnessM()));
            }
            if (presentCompetition.getVarWeightM() != null) {
                varWeightMTextView.setText(String.valueOf(presentCompetition.getVarWeightM()));
            }
            if (presentCompetition.getVarMfatnessSd() != null) {
                varMFatnessSdTextView.setText(String.valueOf(presentCompetition.getVarMfatnessSd()));
            }
            if (presentCompetition.getVarMweightSd() != null) {
                varMWeightSdTextView.setText(String.valueOf(presentCompetition.getVarMweightSd()));
            }
            if (presentCompetition.getVarFatnessF() != null) {
                varFatnessFTextView.setText(String.valueOf(presentCompetition.getVarFatnessF()));
            }
            if (presentCompetition.getVarWeightF() != null) {
                varWeightFTextView.setText(String.valueOf(presentCompetition.getVarWeightF()));
            }
            if (presentCompetition.getVarFfatnessSd() != null) {
                varFFatnessSdTextView.setText(String.valueOf(presentCompetition.getVarFfatnessSd()));
            }
            if (presentCompetition.getVarFweightSd() != null) {
                varFWeightSdTextView.setText(String.valueOf(presentCompetition.getVarFweightSd()));
            }
            if (presentCompetition.getResultFatness() != null &&
                    presentCompetition.getResultQuality() != null &&
                    presentCompetition.getResultTaste() != null) {
                if (presentCompetition.getResultFatness() == 1 &&
                        presentCompetition.getResultQuality() == 1 &&
                        presentCompetition.getResultTaste() == 1) {
                    moreSettingTextView.setText("全部可见");
                } else if (presentCompetition.getResultFatness() == 1 ||
                        presentCompetition.getResultQuality() == 1 ||
                        presentCompetition.getResultTaste() == 1) {
                    moreSettingTextView.setText("部分可见");
                } else {
                    moreSettingTextView.setText("全部不可见");
                }
            }
        }
    }

    /**
     * 年份及备注的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_year_note)
    public void updateYearAndNote(View view) {
        initEditYearAndNote();
    }

    private void initEditYearAndNote() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_year_note, null);
        final EditText year = dialogView.findViewById(R.id.edit_year);
        final EditText note = dialogView.findViewById(R.id.edit_note);
        if (presentCompetition != null) {
            if (presentCompetition.getCompetitionYear() != null) {
                year.setText(presentCompetition.getCompetitionYear());
            }
            if (presentCompetition.getNote() != null) {
                year.setText(presentCompetition.getNote());
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改大赛年份和备注信息");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String yearString = year.getText().toString().trim();
            String noteString = note.getText().toString().trim();
            if (StrUtil.isEmpty(yearString)) {
                showToast("大赛年份不能为空");
                return;
            } else if (StrUtil.isEmpty(noteString)) {
                showToast("大赛备注不能为空");
                return;
            }
            if (yearString.equals(presentCompetition.getCompetitionYear()) && noteString.equals(presentCompetition.getNote())) {
                showToast("未作修改");
            } else {
                String display = yearString + noteString;
                yearAndNoteTextView.setText(display);
                presentCompetition.setCompetitionYear(yearString);
                presentCompetition.setNote(noteString);
                competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }


    /**
     * 雄蟹肥满度参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fatness_m)
    public void updateVarFatnessM(View view) {
        initEditVarFatnessM();
    }

    private void initEditVarFatnessM() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雄性肥满度参数");
        final EditText varFatnessM = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarFatnessM() != null) {
                varFatnessM.setText(String.valueOf(presentCompetition.getVarFatnessM()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雄性肥满度参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varFatnessMString = varFatnessM.getText().toString().trim();
            if (!NumberUtil.isNumber(varFatnessMString)) {
                showToast("请输入合法数字");
            } else {
                Float varFatnessMFloat = Float.parseFloat(varFatnessMString);
                if (presentCompetition.getVarFatnessM().equals(varFatnessMFloat)) {
                    showToast("未作修改");
                } else {
                    varFatnessMTextView.setText(varFatnessMString);
                    presentCompetition.setVarFatnessM(varFatnessMFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雄蟹体重参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_weight_m)
    public void updateVarWeightM(View view) {
        initEditVarWeightM();
    }

    private void initEditVarWeightM() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final EditText varWeightM = dialogView.findViewById(R.id.edit_single_line);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雄性体重参数");
        if (presentCompetition != null) {
            if (presentCompetition.getVarFatnessM() != null) {
                varWeightM.setText(String.valueOf(presentCompetition.getVarWeightM()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雄性体重参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varWeightMString = varWeightM.getText().toString().trim();
            if (!NumberUtil.isNumber(varWeightMString)) {
                showToast("请输入合法数字");
            } else {
                Float varWeightMFloat = Float.parseFloat(varWeightMString);
                if (presentCompetition.getVarWeightM().equals(varWeightMFloat)) {
                    showToast("未作修改");
                } else {
                    varWeightMTextView.setText(varWeightMString);
                    presentCompetition.setVarWeightM(varWeightMFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雄蟹肥满度标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_mfatness_sd)
    public void updateVarMFatnessSd(View view) {
        initEditVarMFatnessSd();
    }

    private void initEditVarMFatnessSd() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雄性肥满度标准差参数");
        final EditText varMFatnessSd = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarMfatnessSd() != null) {
                varMFatnessSd.setText(String.valueOf(presentCompetition.getVarMfatnessSd()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雄性肥满度标准差参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varMFatnessSdString = varMFatnessSd.getText().toString().trim();
            if (!NumberUtil.isNumber(varMFatnessSdString)) {
                showToast("请输入合法数字");
            } else {
                Float varMFatnessSdFloat = Float.parseFloat(varMFatnessSdString);
                if (presentCompetition.getVarMfatnessSd().equals(varMFatnessSdFloat)) {
                    showToast("未作修改");
                } else {
                    varMFatnessSdTextView.setText(varMFatnessSdString);
                    presentCompetition.setVarMfatnessSd(varMFatnessSdFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
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
     * 雄蟹体重标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_mweight_sd)
    public void updateVarMWeightSd(View view) {
        initEditVarMWeightSd();
    }

    private void initEditVarMWeightSd() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雄性体重标准差参数");
        final EditText varMWeightSd = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarMweightSd() != null) {
                varMWeightSd.setText(String.valueOf(presentCompetition.getVarMweightSd()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雄性体重标准差参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varMWeightSdString = varMWeightSd.getText().toString().trim();
            if (!NumberUtil.isNumber(varMWeightSdString)) {
                showToast("请输入合法数字");
            } else {
                Float varMWeightSdFloat = Float.parseFloat(varMWeightSdString);
                if (presentCompetition.getVarMweightSd().equals(varMWeightSdFloat)) {
                    showToast("未作修改");
                } else {
                    varMWeightSdTextView.setText(varMWeightSdString);
                    presentCompetition.setVarMweightSd(varMWeightSdFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雌蟹肥满度参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fatness_f)
    public void updateVarFatnessF(View view) {
        initEditVarFatnessF();
    }

    private void initEditVarFatnessF() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雌性肥满度参数");
        final EditText varFatnessF = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarFatnessF() != null) {
                varFatnessF.setText(String.valueOf(presentCompetition.getVarFatnessF()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雌性肥满度参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varFatnessFString = varFatnessF.getText().toString().trim();
            if (!NumberUtil.isNumber(varFatnessFString)) {
                showToast("请输入合法数字");
            } else {
                Float varFatnessFFloat = Float.parseFloat(varFatnessFString);
                if (presentCompetition.getVarFatnessF().equals(varFatnessFFloat)) {
                    showToast("未作修改");
                } else {
                    varFatnessFTextView.setText(varFatnessFString);
                    presentCompetition.setVarFatnessF(varFatnessFFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雌蟹体重参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_weight_f)
    public void updateVarWeightF(View view) {
        initEditVarWeightF();
    }

    private void initEditVarWeightF() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雌性体重参数");
        final EditText varWeightF = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarFatnessF() != null) {
                varWeightF.setText(String.valueOf(presentCompetition.getVarWeightF()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雌性体重参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varWeightFString = varWeightF.getText().toString().trim();
            if (!NumberUtil.isNumber(varWeightFString)) {
                showToast("请输入合法数字");
            } else {
                Float varWeightFFloat = Float.parseFloat(varWeightFString);
                if (presentCompetition.getVarWeightF().equals(varWeightFFloat)) {
                    showToast("未作修改");
                } else {
                    varWeightFTextView.setText(varWeightFString);
                    presentCompetition.setVarWeightF(varWeightFFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雌蟹肥满度标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_ffatness_sd)
    public void updateVarFFatnessSd(View view) {
        initEditVarFFatnessSd();
    }

    private void initEditVarFFatnessSd() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雌性肥满度标准差参数");
        final EditText varFFatnessSd = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarFfatnessSd() != null) {
                varFFatnessSd.setText(String.valueOf(presentCompetition.getVarFfatnessSd()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雌性肥满度标准差参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varFFatnessSdString = varFFatnessSd.getText().toString().trim();
            if (!NumberUtil.isNumber(varFFatnessSdString)) {
                showToast("请输入合法数字");
            } else {
                Float varFFatnessSdFloat = Float.parseFloat(varFFatnessSdString);
                if (presentCompetition.getVarFfatnessSd().equals(varFFatnessSdFloat)) {
                    showToast("未作修改");
                } else {
                    varFFatnessSdTextView.setText(varFFatnessSdString);
                    presentCompetition.setVarFfatnessSd(varFFatnessSdFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 雌蟹体重标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fweight_sd)
    public void updateVarFWeightSd(View view) {
        initEditVarFWeightSd();
    }

    private void initEditVarFWeightSd() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_single_line, null);
        final TextInputLayout textInputLayout = dialogView.findViewById(R.id.text_input_layout);
        textInputLayout.setHint("修改雌性体重标准差参数");
        final EditText varFWeightSd = dialogView.findViewById(R.id.edit_single_line);
        if (presentCompetition != null) {
            if (presentCompetition.getVarFweightSd() != null) {
                varFWeightSd.setText(String.valueOf(presentCompetition.getVarFweightSd()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改雌性体重标准差参数");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String varFWeightSdString = varFWeightSd.getText().toString().trim();
            if (!NumberUtil.isNumber(varFWeightSdString)) {
                showToast("请输入合法数字");
            } else {
                Float varFWeightSdFloat = Float.parseFloat(varFWeightSdString);
                if (presentCompetition.getVarFweightSd().equals(varFWeightSdFloat)) {
                    showToast("未作修改");
                } else {
                    varFWeightSdTextView.setText(varFWeightSdString);
                    presentCompetition.setVarFweightSd(varFWeightSdFloat);
                    competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 排名结果可见设置的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_more_setting)
    public void updateMoreSetting(View view) {
        initEditMoreSetting();
    }

    private void initEditMoreSetting() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_two_radio_group, null);
        final RadioButton fatnessRankEnable = dialogView.findViewById(R.id.radio_fatness_rank_enable);
        final RadioButton fatnessRankUnable = dialogView.findViewById(R.id.radio_fatness_rank_unable);
        final RadioButton qualityRankEnable = dialogView.findViewById(R.id.radio_quality_rank_enable);
        final RadioButton qualityRankUnable = dialogView.findViewById(R.id.radio_quality_rank_unable);
        final RadioButton tasteRankEnable = dialogView.findViewById(R.id.radio_taste_rank_enable);
        final RadioButton tasteRankUnable = dialogView.findViewById(R.id.radio_taste_rank_unable);
        if (presentCompetition != null) {
            if (presentCompetition.getResultFatness() != null) {
                if (presentCompetition.getResultFatness() == 1) {
                    fatnessRankEnable.setChecked(true);
                } else {
                    fatnessRankUnable.setChecked(true);
                }
            }
            if (presentCompetition.getResultQuality() != null) {
                if (presentCompetition.getResultQuality() == 1) {
                    qualityRankEnable.setChecked(true);
                } else {
                    qualityRankUnable.setChecked(true);
                }
            }
            if (presentCompetition.getResultTaste() != null) {
                if (presentCompetition.getResultTaste() == 1) {
                    tasteRankEnable.setChecked(true);
                } else {
                    tasteRankUnable.setChecked(true);
                }
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改大赛结果排名可见性");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            if (fatnessRankEnable.isChecked()) {
                presentCompetition.setResultFatness(1);
            } else if (fatnessRankUnable.isChecked()) {
                presentCompetition.setResultFatness(0);
            }
            if (qualityRankEnable.isChecked()) {
                presentCompetition.setResultQuality(1);
            } else if (qualityRankUnable.isChecked()) {
                presentCompetition.setResultQuality(0);
            }
            if (tasteRankEnable.isChecked()) {
                presentCompetition.setResultTaste(1);
            } else if (tasteRankUnable.isChecked()) {
                presentCompetition.setResultTaste(0);
            }
            competitionAdminPresenter.updateCompetitionProperty(presentCompetition, jwt);
        });
        if (presentCompetition.getResultFatness() == 1 &&
                presentCompetition.getResultQuality() == 1 &&
                presentCompetition.getResultFatness() == 1) {
            moreSettingTextView.setText("全部可见");
        } else if (presentCompetition.getResultFatness() == 0 &&
                presentCompetition.getResultQuality() == 0 &&
                presentCompetition.getResultTaste() == 0) {
            moreSettingTextView.setText("全部不可见");
        } else {
            moreSettingTextView.setText("部分可见");
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * getPresentCompetitionProperty请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        presentCompetition = JSONObject.parseObject(successData.getString("result"), Competition.class);
        SharedPreferencesUtil.putData("PRESENT_COMPETITION", presentCompetition);
        initView();
        showToast(successData.getString(CommonConstant.MESSAGE));
    }

    /**
     * updateCompetitionProperty请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUpdateCompetitionPropertyResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
        }
    }

    /**
     * swipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        navigationPresenter.getPresentCompetitionProperty();
        new Handler(Looper.getMainLooper()).post(() -> {
            initView();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}