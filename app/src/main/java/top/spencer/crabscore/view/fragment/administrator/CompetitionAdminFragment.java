package top.spencer.crabscore.view.fragment.administrator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.util.SharedPreferencesUtil;

/**
 * 管理员用户组的大赛配置信息管理页面
 *
 * @author spencercjh
 */
public class CompetitionAdminFragment extends BaseFragment {
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
    private Competition presentCompetition;

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
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        initView();
    }

    /**
     * 数据初始化UI
     */
    private void initView() {
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
                if (presentCompetition.getResultFatness() == 1 ||
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

    }

    /**
     * 雄蟹肥满度参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fatness_m)
    public void updateVarFatnessM(View view) {

    }

    /**
     * 雄蟹体重参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_weight_m)
    public void updateVarWeightM(View view) {

    }

    /**
     * 雄蟹肥满度标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_mfatness_sd)
    public void updateVarMFatnessSd(View view) {

    }

    /**
     * 雄蟹体重标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_mweight_sd)
    public void updateVarMWeightSd(View view) {

    }

    /**
     * 雌蟹肥满度参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fatness_f)
    public void updateVarFatnessF(View view) {

    }

    /**
     * 雌蟹体重参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_weight_f)
    public void updateVarWeightF(View view) {

    }

    /**
     * 雌蟹肥满度标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_ffatness_sd)
    public void updateVarFFatnessSd(View view) {

    }

    /**
     * 雌蟹体重标准差参数的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_var_fweight_sd)
    public void updateVarFWeightSd(View view) {

    }

    /**
     * 排名结果可见设置的RelativeLayout的单击监听
     *
     * @param view view
     */
    @OnClick(R.id.re_more_setting)
    public void updateMoreSetting(View view) {

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
     * 请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {

    }
}