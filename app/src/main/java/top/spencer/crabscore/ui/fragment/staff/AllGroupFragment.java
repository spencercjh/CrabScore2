package top.spencer.crabscore.ui.fragment.staff;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.BindView;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.dto.GroupResult;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.presenter.StaffPresenter;
import top.spencer.crabscore.ui.activity.staff.CrabListActivity;
import top.spencer.crabscore.ui.adapter.GroupGradeListAdapter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.view.StaffGroupListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 工作人员用户组数据录入页面
 *
 * @author spencercjh
 */
public class AllGroupFragment extends BaseFragment implements StaffGroupListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView groupListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GroupGradeListAdapter groupGradeListAdapter;
    private StaffPresenter staffPresenter;
    private RankListPresenter rankListPresenter;
    private String jwt;
    private User user;
    private Competition presentCompetition;
    private List<GroupResult> groupList = new ArrayList<>(pageSize);
    private int pageNum = 1;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static AllGroupFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        AllGroupFragment fragment = new AllGroupFragment();
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
        rankListPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_has_empty_textview;
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
        rankListPresenter = new RankListPresenter();
        rankListPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
        setRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void setRecycleView() {
        initGroupListAdapter();
        if (groupList.size() == 0) {
            groupListView.setEmptyView(emptyText);
        }
        groupListView.setAdapter(groupGradeListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        groupListView.setLayoutManager(layoutManager);
        groupListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        groupListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == groupGradeListAdapter.getItemCount()) {
                    staffPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
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
     * 初始化groupListAdapter
     */
    private void initGroupListAdapter() {
        groupGradeListAdapter = new GroupGradeListAdapter(groupList);
        groupGradeListAdapter.setOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(View view) {
                final GroupResult groupInList = (GroupResult) view.getTag();
                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                final MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.pop_menu_staff_group, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_add_crab: {
                            addCrabAlertDialog(groupInList);
                            break;
                        }
                        case R.id.menu_female_crab_info: {
                            Intent intent = new Intent(getContext(), CrabListActivity.class);
                            intent.putExtra("GROUP", groupInList);
                            intent.putExtra("USER", user);
                            intent.putExtra("SEX", CommonConstant.CRAB_FEMALE);
                            startActivity(intent);
                            break;
                        }
                        case R.id.menu_male_crab_info: {
                            Intent intent = new Intent(getContext(), CrabListActivity.class);
                            intent.putExtra("GROUP", groupInList);
                            intent.putExtra("USER", user);
                            intent.putExtra("SEX", CommonConstant.CRAB_MALE);
                            startActivity(intent);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    return false;
                });
                popupMenu.show();
            }

            @Deprecated
            @Override
            public void onItemLongClick(View view) {
                //nothing
            }
        });
    }

    /**
     * 添加螃蟹AlertDialog
     *
     * @param groupInDialog 小组对象
     */
    private void addCrabAlertDialog(final GroupResult groupInDialog) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_staff_add_crab, null);
        final CheckBox addMaleCrab = dialogView.findViewById(R.id.checkbox_add_male_crab);
        final CheckBox addFemaleCrab = dialogView.findViewById(R.id.checkbox_add_female_crab);
        final EditText addAmount = dialogView.findViewById(R.id.edit_crab_amount);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("为第" + groupInDialog.getGroupId() + "组添加螃蟹");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "添加", (dialog1, which) -> {
            String addCrabAmountString = addAmount.getText().toString().trim();
            if (NumberUtil.isNumber(addCrabAmountString)) {
                int addCrabAmount = Integer.parseInt(addCrabAmountString);
                boolean isAddMale = false;
                boolean isAddFemale = false;
                if (addFemaleCrab.isChecked()) {
                    isAddFemale = true;
                }
                if (addMaleCrab.isChecked()) {
                    isAddMale = true;
                }
                staffPresenter.sendCrabList(groupInDialog, addCrabAmount, isAddMale, isAddFemale,
                        user.getUserName(), jwt);
            } else {
                showToast("添加数量非法");
            }
            dialog1.dismiss();
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 发起初始请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        staffPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
    }

    /**
     * SwipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        staffPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    /**
     * AddCrab请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showAddCrabResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
        }
    }

    @Deprecated
    @Override
    public void showUpdateCrabInfoResponse(JSONObject successData) {
        //nothing
    }

    /**
     * getAllGroup请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        rankListPresenter.dealGroupListJSON(successData.getJSONArray("result"), groupList);
        new Handler(Looper.getMainLooper()).post(() -> {
            swipeRefreshLayout.setRefreshing(false);
            groupGradeListAdapter.notifyDataSetChanged();
        });
    }
}