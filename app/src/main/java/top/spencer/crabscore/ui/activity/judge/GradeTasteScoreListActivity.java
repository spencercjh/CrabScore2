package top.spencer.crabscore.ui.activity.judge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import top.spencer.crabscore.model.entity.TasteScore;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.presenter.GradePresenter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.adapter.TasteScoreListAdapter;
import top.spencer.crabscore.ui.view.GradeListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 评委口感评分活动
 * //todo taste grade
 *
 * @author spencercjh
 */
public class GradeTasteScoreListActivity extends BaseActivity implements GradeListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView TasteScoreListView;
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
    private List<TasteScore> TasteScoreList = new ArrayList<>(pageSize);
    private TasteScoreListAdapter TasteScoreListAdapter;

    /**
     * 初始化参数和视图
     *
     * @param savedInstanceState savedInstanceState
     */
    @SuppressWarnings("Duplicates")
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
        actionBar.setTitle("对第" + groupResult.getGroupId() + "组口感评分");
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
        gradePresenter.getOneGroupAllTasteScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        initTasteScoreListAdapter();
        if (TasteScoreList.size() == 0) {
            TasteScoreListView.setEmptyView(emptyText);
        }
        TasteScoreListView.setAdapter(TasteScoreListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        TasteScoreListView.setLayoutManager(layoutManager);
        TasteScoreListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        TasteScoreListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == TasteScoreListAdapter.getItemCount()) {
                    gradePresenter.getOneGroupAllTasteScore(presentCompetition.getCompetitionId(),
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
     * 初始化TasteScoreListAdapter
     */
    private void initTasteScoreListAdapter() {
        TasteScoreListAdapter = new TasteScoreListAdapter(TasteScoreList);
        TasteScoreListAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                final TasteScore tasteScoreInList = (TasteScore) view.getTag();
                initEditTasteScoreDialog(tasteScoreInList);
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
     * @param tasteScoreInDialog tasteScoreInDialog
     */
    private void initEditTasteScoreDialog(TasteScore tasteScoreInDialog) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_grade_taste_score, null);
        final EditText scoreFin = dialogView.findViewById(R.id.edit_final_score);
        final EditText scoreYgys = dialogView.findViewById(R.id.edit_score_ygys);
        final EditText scoreSys = dialogView.findViewById(R.id.edit_score_sys);
        final EditText scoreGhys = dialogView.findViewById(R.id.edit_score_ghys);
        final EditText scoreXwxw = dialogView.findViewById(R.id.edit_score_xwxw);
        final EditText scoreGh = dialogView.findViewById(R.id.edit_score_gh);
        final EditText scoreFbjr = dialogView.findViewById(R.id.edit_score_fbjr);
        final EditText scoreBzjr = dialogView.findViewById(R.id.edit_score_bzjr);
        if (tasteScoreInDialog != null) {
            if (tasteScoreInDialog.getScoreFin() != null) {
                scoreFin.setText(String.valueOf(tasteScoreInDialog.getScoreFin()));
            }
            if (tasteScoreInDialog.getScoreYgys() != null) {
                scoreYgys.setText(String.valueOf(tasteScoreInDialog.getScoreYgys()));
            }
            if (tasteScoreInDialog.getScoreSys() != null) {
                scoreSys.setText(String.valueOf(tasteScoreInDialog.getScoreSys()));
            }
            if (tasteScoreInDialog.getScoreGhys() != null) {
                scoreGhys.setText(String.valueOf(tasteScoreInDialog.getScoreGhys()));
            }
            if (tasteScoreInDialog.getScoreXwxw() != null) {
                scoreXwxw.setText(String.valueOf(tasteScoreInDialog.getScoreXwxw()));
            }
            if (tasteScoreInDialog.getScoreGh() != null) {
                scoreGh.setText(String.valueOf(tasteScoreInDialog.getScoreGh()));
            }
            if (tasteScoreInDialog.getScoreFbjr() != null) {
                scoreFbjr.setText(String.valueOf(tasteScoreInDialog.getScoreFbjr()));
            }
            if (tasteScoreInDialog.getScoreBzjr() != null) {
                scoreBzjr.setText(String.valueOf(tasteScoreInDialog.getScoreBzjr()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("为第" + groupResult.getGroupId() + "组" + String.valueOf(
                Objects.requireNonNull(tasteScoreInDialog).getScoreId()) + "号螃蟹评分");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gradePresenter.updateTasteScore(scoreFin, scoreYgys, scoreSys, scoreGhys, scoreXwxw, scoreGh, scoreFbjr,
                        scoreBzjr, user, jwt);
                dialog.dismiss();
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
     * SwipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        gradePresenter.getOneGroupAllTasteScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * getOneGroupAllTasteScore请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        boolean repeat = gradePresenter.dealTasteScoreJSON(successData.getJSONArray("result"), TasteScoreList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                TasteScoreListAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * updateTasteScore请求成功
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
