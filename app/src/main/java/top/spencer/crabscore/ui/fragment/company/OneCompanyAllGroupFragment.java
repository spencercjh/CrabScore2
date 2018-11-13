package top.spencer.crabscore.ui.fragment.company;

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
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Company;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.presenter.AdministratorListPresenter;
import top.spencer.crabscore.presenter.CompanyPresenter;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.ui.activity.company.ScoreListActivity;
import top.spencer.crabscore.ui.adapter.CompanyCheckGroupListAdapter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.view.CompanyView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 评委用户组的种质评分-所有小组页面
 *
 * @author spencercjh
 */
public class OneCompanyAllGroupFragment extends BaseFragment implements CompanyView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView groupListView;
    @BindView(R.id.button_empty)
    Button bindCompany;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CompanyCheckGroupListAdapter companyCheckGroupListAdapter;
    private RankListPresenter rankListPresenter;
    private CompanyPresenter companyPresenter;
    private AdministratorListPresenter administratorListPresenter;
    private String jwt;
    private User user;
    private Competition presentCompetition;
    private List<GroupResult> groupList = new ArrayList<>(pageSize);
    private int pageNum = 1;
    private List<Company> allCompanyList = new ArrayList<>(pageSize);
    private List<String> allCompanyNameList = new ArrayList<>(pageSize);

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static OneCompanyAllGroupFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        OneCompanyAllGroupFragment fragment = new OneCompanyAllGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        companyPresenter.detachView();
        rankListPresenter.detachView();
        administratorListPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_has_empty_button;
    }

    /**
     * 初始化视图
     *
     * @param view               view
     * @param savedInstanceState saveInstanceState
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companyPresenter = new CompanyPresenter();
        companyPresenter.attachView(this);
        rankListPresenter = new RankListPresenter();
        rankListPresenter.attachView(this);
        administratorListPresenter = new AdministratorListPresenter();
        administratorListPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        user = (User) (SharedPreferencesUtil.getData("USER", new User()));
        setRecycleView();
    }

    /**
     * 初始发起请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        companyPresenter.getOneCompanyAllGroup(presentCompetition.getCompetitionId(), user.getCompanyId(),
                pageNum, pageSize, jwt);
        companyPresenter.getAllCompany(jwt);
    }

    /**
     * 初始化RecycleView
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void setRecycleView() {
        initCompanyCheckGroupListAdapter();
        if (groupList.size() == 0) {
            groupListView.setEmptyView(bindCompany);
        }
        groupListView.setAdapter(companyCheckGroupListAdapter);
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
                        && lastVisibleItemPosition[0] + 1 == companyCheckGroupListAdapter.getItemCount()) {
                    companyPresenter.getOneCompanyAllGroup(presentCompetition.getCompetitionId(), user.getCompanyId(),
                            pageNum, pageSize, jwt);
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
     * 初始化companyCheckGroupListAdapter
     */
    private void initCompanyCheckGroupListAdapter() {
        companyCheckGroupListAdapter = new CompanyCheckGroupListAdapter(groupList);
        companyCheckGroupListAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                GroupResult groupResultInList = (GroupResult) view.getTag();
                Intent intent = new Intent(getContext(), ScoreListActivity.class);
                intent.putExtra("group", groupResultInList);
                startActivity(intent);
            }

            @Deprecated
            @Override
            public void onItemLongClick(View view) {
                //nothing
            }
        });
    }

    /**
     * swipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        companyPresenter.getOneCompanyAllGroup(presentCompetition.getCompetitionId(), user.getCompanyId(),
                pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 设置列表为空时的按键监听
     *
     * @param view view
     */
    @OnClick(R.id.button_empty)
    public void userBindCompany(View view) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_user_bind_company, null);
        final TextView username = dialogView.findViewById(R.id.textview_user_name);
        final TextView displayName = dialogView.findViewById(R.id.textview_display_name);
        if (user != null) {
            if (user.getUserName() != null) {
                username.setText(user.getUserName());
            }
            if (user.getDisplayName() != null) {
                displayName.setText(user.getDisplayName());
            }
        }
        final Spinner allCompanySpinner = dialogView.findViewById(R.id.spinner_company);
        initSpinner(allCompanySpinner);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("选择一个参选单位进行绑定");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "绑定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                companyPresenter.userBindCompany(user.getUserId(), user.getCompanyId(), jwt);
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
     * 初始化参选单位Spinner
     *
     * @param companySpinner companySpinner
     */
    private void initSpinner(Spinner companySpinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.item_spinner_company, allCompanyNameList);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown_company);
        companySpinner.setAdapter(adapter);
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Company company = allCompanyList.get(pos);
                user.setCompanyId(company.getCompanyId());
            }

            @Deprecated
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });
        companySpinner.setSelection(0);
    }

    /**
     * GetOneCompanyAllGroup请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        boolean repeat = rankListPresenter.dealGroupListJSON(successData.getJSONArray("result"), groupList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                companyCheckGroupListAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * GetAllCompany请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showGetAllCompanyResponse(JSONObject successData) {
        administratorListPresenter.dealCompanyListJSON(successData.getJSONArray("result"),
                allCompanyList, allCompanyNameList);
    }

    /**
     * UserBindCompany请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUserBindCompanyResponse(JSONObject successData) {
        if (successData.getInteger("code").equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString("message"));
            companyPresenter.getOneCompanyAllGroup(presentCompetition.getCompetitionId(), user.getCompanyId(),
                    pageNum, pageSize, jwt);
        }
    }
}