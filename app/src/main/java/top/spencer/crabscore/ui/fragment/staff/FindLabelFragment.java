package top.spencer.crabscore.ui.fragment.staff;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.dto.CrabResult;
import top.spencer.crabscore.presenter.StaffPresenter;
import top.spencer.crabscore.ui.view.StaffGroupListView;

/**
 * 工作人员用户组查找标签一级页面
 *
 * @author spencercjh
 */
public class FindLabelFragment extends BaseFragment implements StaffGroupListView {
    @BindView(R.id.edit_crab_label)
    EditText crabLabel;
    @BindView(R.id.button_find)
    Button find;
    @BindView(R.id.textview_crab_id)
    TextView crabId;
    @BindView(R.id.textview_group_id)
    TextView groupId;
    @BindView(R.id.textview_company_id)
    TextView companyId;
    @BindView(R.id.textview_company_name)
    TextView companyName;
    @BindView(R.id.textview_crab_sex)
    TextView crabSex;
    @BindView(R.id.textview_crab_weight)
    TextView crabWeight;
    @BindView(R.id.textview_crab_length)
    TextView crabLength;
    @BindView(R.id.textview_crab_fatness)
    TextView crabFatness;
    @BindView(R.id.textview_crab_competition_id)
    TextView crabCompetitionId;
    private StaffPresenter staffPresenter;
    private Competition presentCompetition;
    private String jwt;
    private User user;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static FindLabelFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        FindLabelFragment fragment = new FindLabelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        staffPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_find_label;
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
        staffPresenter = new StaffPresenter();
        staffPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
    }

    @OnClick(R.id.button_find)
    public void findCrabByLabel(View view) {
        String label = crabLabel.getText().toString().trim();
        if (StrUtil.isNotBlank(label)) {
            staffPresenter.findCrabByLabel(label, jwt);
        } else {
            showToast("标识非法");
        }
    }

    /**
     * findCrabByLabel请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            final CrabResult crab = JSONObject.parseObject(successData.getString("result"), CrabResult.class);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    String crabIdString = crabId.getText().toString() + crab.getCrabId();
                    crabId.setText(crabIdString);
                    crabId.setVisibility(View.VISIBLE);
                    String crabGroupIdString = groupId.getText().toString() + crab.getGroupId();
                    groupId.setText(crabGroupIdString);
                    groupId.setVisibility(View.VISIBLE);
                    String crabCompanyIdString = companyId.getText().toString() + crab.getCompanyId();
                    companyId.setText(crabCompanyIdString);
                    companyId.setVisibility(View.VISIBLE);
                    String crabCompanyNameString = companyName.getText().toString() + crab.getCompanyName();
                    companyName.setText(crabCompanyNameString);
                    companyName.setVisibility(View.VISIBLE);
                    String crabSexString = crabSex.getText().toString() + (crab.getCrabSex().equals(
                            CommonConstant.CRAB_FEMALE) ? "雌性" : "雄性");
                    crabSex.setText(crabSexString);
                    crabSex.setVisibility(View.VISIBLE);
                    String crabWeightString = crabWeight.getText().toString() + crab.getCrabWeight();
                    crabWeight.setText(crabWeightString);
                    crabWeight.setVisibility(View.VISIBLE);
                    String crabLengthString = crabLength.getText().toString() + crab.getCrabLength();
                    crabLength.setText(crabLengthString);
                    crabLength.setVisibility(View.VISIBLE);
                    String crabFatnessString = crabFatness.getText().toString() + crab.getCrabFatness();
                    crabFatness.setText(crabFatnessString);
                    crabFatness.setVisibility(View.VISIBLE);
                    String crabCompetitionIdString = crabCompetitionId.getText().toString() +
                            crab.getCompetitionId();
                    crabCompetitionId.setText(crabCompetitionIdString);
                    crabCompetitionId.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Deprecated
    @Override
    public void showAddCrabResponse(JSONObject successData) {
        //nothing
    }

    @Override
    public void showUpdateCrabInfoResponse(JSONObject successData) {
        //nothing
    }

    @Deprecated
    @Override
    public void setRecycleView() {
        //nothing
    }
}