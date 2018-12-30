package top.spencer.crabscore.ui.activity.staff;

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
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.Crab;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.dto.GroupResult;
import top.spencer.crabscore.presenter.StaffPresenter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.adapter.StaffCrabListAdapter;
import top.spencer.crabscore.ui.view.StaffGroupListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 工作人员用户组-小组某性别螃蟹列表页面
 * //todo 给螃蟹拍照并上传
 *
 * @author spencercjh
 */
public class CrabListActivity extends BaseActivity implements StaffGroupListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView crabListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private String jwt;
    private Competition presentCompetition;
    private GroupResult groupResult;
    private int pageNum = 1;
    private List<Crab> crabList = new ArrayList<>(pageSize);
    private StaffPresenter staffPresenter;
    private StaffCrabListAdapter staffCrabListAdapter;
    private User user;
    private Integer sex;

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
        try {
            groupResult = (GroupResult) intent.getSerializableExtra("GROUP");
            user = (User) intent.getSerializableExtra("USER");
            sex = (Integer) intent.getSerializableExtra("SEX");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("参数有误");
            finish();
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("第 " + groupResult.getGroupId() + " 组" +
                (sex.equals(CommonConstant.CRAB_MALE) ? "雄性" : "雌性") + "全部螃蟹");
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
        staffPresenter = new StaffPresenter();
        staffPresenter.attachView(this);
        setRecycleView();
    }

    /**
     * 重写onDestroy 断开view
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        staffPresenter.detachView();
    }

    /**
     * 初始请求
     */
    @Override
    protected void onStart() {
        super.onStart();
        staffPresenter.getOneGroupOneSexCrab(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                sex, pageNum, pageSize, jwt);
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        initCrabListAdapter();
        if (crabList.size() == 0) {
            crabListView.setEmptyView(emptyText);
        }
        crabListView.setAdapter(staffCrabListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        crabListView.setLayoutManager(layoutManager);
        crabListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        crabListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == staffCrabListAdapter.getItemCount()) {
                    staffPresenter.getOneGroupOneSexCrab(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                            sex, pageNum, pageSize, jwt);
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
     * 初始化crabListAdapter
     */
    private void initCrabListAdapter() {
        staffCrabListAdapter = new StaffCrabListAdapter(crabList, getContext());
        staffCrabListAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                final Crab crabInList = (Crab) view.getTag();
                initEditCrabDialog(crabInList);
            }

            @Override
            public void onItemLongClick(View view) {
                //nothing
            }
        });
    }

    /**
     * 编辑螃蟹信息AlertDialog
     *
     * @param crabInDialog 螃蟹对象
     */
    private void initEditCrabDialog(final Crab crabInDialog) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_staff_edit_crab_info, null);
        final EditText crabLabel = dialogView.findViewById(R.id.edit_crab_label);
        final EditText crabWeight = dialogView.findViewById(R.id.edit_crab_weight);
        final EditText crabLength = dialogView.findViewById(R.id.edit_crab_length);
        if (crabInDialog != null) {
            if (crabInDialog.getCrabLabel() != null) {
                crabLabel.setText(crabInDialog.getCrabLabel());
            }
            if (crabInDialog.getCrabWeight() != null) {
                crabWeight.setText(String.valueOf(crabInDialog.getCrabWeight()));
            }
            if (crabInDialog.getCrabLength() != null) {
                crabLength.setText(String.valueOf(crabInDialog.getCrabLength()));
            }
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改用户信息");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = crabLabel.getText().toString().trim();
                String weightString = crabWeight.getText().toString().trim();
                String lengthString = crabLength.getText().toString().trim();
                if (!StrUtil.isNotBlank(label)) {
                    showToast("标签不能为空");
                    return;
                }
                if (!NumberUtil.isNumber(weightString)) {
                    showToast("重量数字非法");
                    return;
                }
                if (!NumberUtil.isNumber(lengthString)) {
                    showToast("长度数字非法");
                    return;
                }
                Objects.requireNonNull(crabInDialog).setCrabLabel(label);
                crabInDialog.setCrabWeight(Float.parseFloat(weightString));
                crabInDialog.setCrabLength(Float.parseFloat(lengthString));
                crabInDialog.setCrabFatness(crabInDialog.getCrabWeight() /
                        (crabInDialog.getCrabLength() * crabInDialog.getCrabLength() * crabInDialog.getCrabLength()));
                crabInDialog.setUpdateUser(user.getUserName());
                crabInDialog.setUpdateDate(new Date(System.currentTimeMillis()));
                staffPresenter.updateCrabInfo(crabInDialog, jwt);
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
        staffPresenter.getOneGroupOneSexCrab(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                sex, pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * getOneGroupOneSexCrab请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        boolean repeat = staffPresenter.dealCrabJSON(successData.getJSONArray("result"), crabList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                staffCrabListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Deprecated
    @Override
    public void showAddCrabResponse(JSONObject successData) {
        //nothing
    }

    /**
     * updateCrabInfo请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUpdateCrabInfoResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
        }
    }
}
