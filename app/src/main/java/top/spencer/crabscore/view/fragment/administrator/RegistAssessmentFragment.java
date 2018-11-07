package top.spencer.crabscore.view.fragment.administrator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.User;
import top.spencer.crabscore.presenter.AdministratorListPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.adapter.UserAdminListAdapter;
import top.spencer.crabscore.view.view.MyRecycleListView;
import top.spencer.crabscore.view.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 管理员用户组的用户注册审核管理页面
 *
 * @author spencercjh
 */
public class RegistAssessmentFragment extends BaseFragment implements MyRecycleListView {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView userListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private AdministratorListPresenter administratorListPresenter;
    private String jwt;
    private UserAdminListAdapter userAdminListAdapter;
    private List<User> userList = new ArrayList<>(10);
    private int pageNum = 1;
    private boolean repeat = false;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static RegistAssessmentFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        RegistAssessmentFragment fragment = new RegistAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        administratorListPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_list;
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
        administratorListPresenter = new AdministratorListPresenter();
        administratorListPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        setRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        userAdminListAdapter = new UserAdminListAdapter(userList);
        if (userList.size() == 0) {
            userListView.setEmptyView(emptyText);
        }
        userListView.setAdapter(userAdminListAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                administratorListPresenter.getAllUserByStatus(CommonConstant.USER_STATUS_LOCK, pageNum, pageSize, jwt);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        userListView.setLayoutManager(layoutManager);
        userListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        userListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == userAdminListAdapter.getItemCount()) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (!repeat) {
                        administratorListPresenter.getAllUserByStatus(CommonConstant.USER_STATUS_LOCK, pageNum, pageSize, jwt);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition[0] = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void showResponse1(JSONObject successData) {

    }

    /**
     * 初始发起请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        administratorListPresenter.getAllUserByStatus(CommonConstant.USER_STATUS_LOCK, pageNum, pageSize, jwt);
    }

    /**
     * getAllUserByStatus请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        repeat = administratorListPresenter.dealUserListJSON(successData.getJSONArray("result"), userList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                userAdminListAdapter.notifyDataSetChanged();
            }
        });
    }
}