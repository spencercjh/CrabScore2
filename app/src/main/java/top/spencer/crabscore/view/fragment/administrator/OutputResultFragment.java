package top.spencer.crabscore.view.fragment.administrator;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.CompetitionConfig;
import top.spencer.crabscore.presenter.OutputResultPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.view.OutputResultView;

import java.util.*;

/**
 * 管理员用户组-导出大赛结果页面
 *
 * @author spencercjh
 */
public class OutputResultFragment extends BaseFragment implements OutputResultView {
    @BindView(R.id.re_generate_score)
    RelativeLayout generateScore;
    @BindView(R.id.re_output_excel)
    RelativeLayout outputExcel;
    @BindView(R.id.re_update_present_year)
    RelativeLayout updatePresentYear;
    private String jwt;
    private Competition presentCompetition;
    private OutputResultPresenter outputResultPresenter;
    private List<Map<String, Object>> allCompetition = new ArrayList<>(8);

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static OutputResultFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        OutputResultFragment fragment = new OutputResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        outputResultPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_output_result;
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
        outputResultPresenter = new OutputResultPresenter();
        outputResultPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
    }

    /**
     * 初始化请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        outputResultPresenter.allCompetition(jwt);
    }

    @OnClick(R.id.re_generate_score)
    public void generateScore(View view) {
        outputResultPresenter.generateScore(presentCompetition, jwt);
    }

    @OnClick(R.id.re_output_excel)
    public void outputExcel(View view) {
        outputResultPresenter.outputExcel();
    }

    @OnClick(R.id.re_update_present_year)
    public void updatePresentYear(View view) {
        outputResultPresenter.allCompetition(jwt);
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_single_list, null);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改当前大赛");
        dialog.setView(dialogView);
        ListView competitionListView = dialogView.findViewById(R.id.list_all_competition);
        competitionListView.setAdapter(new SimpleAdapter(getContext(),
                allCompetition, R.layout.item_competition_admin, new String[]{
                "competitionId", "competitionYear", "note"}, new int[]{
                R.id.textview_competition_id, R.id.textview_competition_year, R.id.textview_note}));
        competitionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> competitionMap = allCompetition.get(position);
                Competition competition = new Competition();
                competition.setCompetitionId((Integer) competitionMap.get("competitionId"));
                competition.setCompetitionYear((String) competitionMap.get("competitionYear"));
                competition.setVarFatnessM((Float) competitionMap.get("varFatnessM"));
                competition.setVarFatnessF((Float) competitionMap.get("varFatnessF"));
                competition.setVarWeightM((Float) competitionMap.get("varWeightM"));
                competition.setVarWeightF((Float) competitionMap.get("varWeightF"));
                competition.setVarMfatnessSd((Float) competitionMap.get("varMfatnessSd"));
                competition.setVarFfatnessSd((Float) competitionMap.get("varFfatnessSd"));
                competition.setVarMweightSd((Float) competitionMap.get("varMweightSd"));
                competition.setVarFweightSd((Float) competitionMap.get("varFweightSd"));
                competition.setResultFatness((Integer) competitionMap.get("resultFatness"));
                competition.setResultQuality((Integer) competitionMap.get("resultQuality"));
                competition.setResultTaste((Integer) competitionMap.get("resultTaste"));
                competition.setNote((String) competitionMap.get("note"));
                competition.setStatus((Integer) competitionMap.get("status"));
                competition.setCreateDate((Date) competitionMap.get("createDate"));
                competition.setCreateUser((String) competitionMap.get("createUser"));
                competition.setUpdateDate((Date) competitionMap.get("updateDate"));
                competition.setUpdateUser((String) competitionMap.get("updateUser"));
                SharedPreferencesUtil.putData("PRESENT_COMPETITION", competition);
                CompetitionConfig competitionConfig = new CompetitionConfig();
                competitionConfig.setId(1);
                competitionConfig.setCompetitionId(competition.getCompetitionId());
                competitionConfig.setUpdateDate(new Date(System.currentTimeMillis()));
                competitionConfig.setUpdateUser((String) (SharedPreferencesUtil.getData("USERNAME", "")));
                outputResultPresenter.updatePresentCompetitionConfig(competitionConfig, jwt);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * allCompetition请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        outputResultPresenter.dealCompetitionListJSON(successData.getJSONArray("result"), allCompetition);
    }

    /**
     * updatePresentCompetitionConfig请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUpdatePresentCompetitionConfigResponse(JSONObject successData) {
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString("message"));
        }
    }

    /**
     * generateScore请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showGenerateScoreResponse(JSONObject successData) {
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString("message"));
        }
    }
}