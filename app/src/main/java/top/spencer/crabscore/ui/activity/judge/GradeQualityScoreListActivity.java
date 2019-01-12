package top.spencer.crabscore.ui.activity.judge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.QualityScore;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.dto.GroupResult;
import top.spencer.crabscore.presenter.GradePresenter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.adapter.QualityScoreListAdapter;
import top.spencer.crabscore.ui.view.GradeListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 评委种质评分活动
 *
 * @author spencercjh
 */
public class GradeQualityScoreListActivity extends BaseActivity implements GradeListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView qualityScoreListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GradePresenter gradePresenter;
    private String jwt;
    private Competition presentCompetition;
    private GroupResult groupResult;
    private User user;
    private int pageNum = 1;
    private List<QualityScore> qualityScoreList = new ArrayList<>(pageSize);
    private QualityScoreListAdapter qualityScoreListAdapter;

    /**
     * 初始化参数和视图
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_has_empty_textview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        groupResult = (GroupResult) intent.getSerializableExtra("group");
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
        gradePresenter = new GradePresenter();
        gradePresenter.attachView(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("对第" + groupResult.getGroupId() + "组种质评分");
        setRecycleView();
    }

    /**
     * 重写onDestroy 断开view
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradePresenter.detachView();
    }

    /**
     * 初始请求
     */
    @Override
    protected void onStart() {
        super.onStart();
        gradePresenter.getOneGroupAllQualityScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        initQualityScoreListAdapter();
        if (qualityScoreList.size() == 0) {
            qualityScoreListView.setEmptyView(emptyText);
        }
        qualityScoreListView.setAdapter(qualityScoreListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        qualityScoreListView.setLayoutManager(layoutManager);
        qualityScoreListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        qualityScoreListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == qualityScoreListAdapter.getItemCount()) {
                    gradePresenter.getOneGroupAllQualityScore(presentCompetition.getCompetitionId(),
                            groupResult.getGroupId(), pageNum, pageSize, jwt);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition[0] = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 初始化QualityScoreListAdapter
     */
    private void initQualityScoreListAdapter() {
        qualityScoreListAdapter = new QualityScoreListAdapter(qualityScoreList);
        qualityScoreListAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                final QualityScore qualityScoreInList = (QualityScore) view.getTag();
                initEditQualityScoreDialog(qualityScoreInList);
            }

            @Override
            public void onItemLongClick(View view) {
                //nothing
            }
        });
    }

    /**
     * 评分dialog
     *
     * @param qualityScoreInDialog qualityScoreInDialog
     */
    private void initEditQualityScoreDialog(final QualityScore qualityScoreInDialog) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_grade_quality_score, null);
        final EditText scoreFin = dialogView.findViewById(R.id.edit_final_score);
        final EditText scoreBts = dialogView.findViewById(R.id.edit_score_bts);
        final EditText scoreFts = dialogView.findViewById(R.id.edit_score_fts);
        final EditText scoreEc = dialogView.findViewById(R.id.edit_score_ec);
        final EditText scoreDscc = dialogView.findViewById(R.id.edit_score_dscc);
        final EditText scoreBbyzt = dialogView.findViewById(R.id.edit_score_bbyzt);
        if (null != qualityScoreInDialog) {
            if (null != qualityScoreInDialog.getScoreFin()) {
                scoreFin.setText(String.valueOf(qualityScoreInDialog.getScoreFin()));
            }
            if (null != qualityScoreInDialog.getScoreBts()) {
                scoreBts.setText(String.valueOf(qualityScoreInDialog.getScoreBts()));
            }
            if (null != qualityScoreInDialog.getScoreFts()) {
                scoreFts.setText(String.valueOf(qualityScoreInDialog.getScoreFts()));
            }
            if (null != qualityScoreInDialog.getScoreEc()) {
                scoreEc.setText(String.valueOf(qualityScoreInDialog.getScoreEc()));
            }
            if (null != qualityScoreInDialog.getScoreDscc()) {
                scoreDscc.setText(String.valueOf(qualityScoreInDialog.getScoreDscc()));
            }
            if (null != qualityScoreInDialog.getScoreBbyzt()) {
                scoreBbyzt.setText(String.valueOf(qualityScoreInDialog.getScoreBbyzt()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("为第" + groupResult.getGroupId() + "组" + Objects.requireNonNull(qualityScoreInDialog).getScoreId() + "号螃蟹评分");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            gradePresenter.updateQualityScore(scoreFin, scoreBts, scoreFts, scoreEc, scoreDscc, scoreBbyzt, qualityScoreInDialog, user, jwt);
            dialog1.dismiss();
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog2, which) -> dialog2.dismiss());
        dialog.show();
    }

    /**
     * SwipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        gradePresenter.getOneGroupAllQualityScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    /**
     * getOneGroupAllQualityScore请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        gradePresenter.dealQualityScoreJSON(successData.getJSONArray("result"), qualityScoreList);
        new Handler(Looper.getMainLooper()).post(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                    qualityScoreListAdapter.notifyDataSetChanged();
                }
        );
    }

    /**
     * updateQualityScore请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUpdateScoreResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
        }
    }
}
