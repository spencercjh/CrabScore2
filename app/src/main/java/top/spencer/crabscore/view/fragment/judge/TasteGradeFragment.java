package top.spencer.crabscore.view.fragment.judge;

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
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.presenter.GroupPresenter;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.activity.judge.TasteGradeActivity;
import top.spencer.crabscore.view.adapter.GroupGradeListAdapter;
import top.spencer.crabscore.view.adapter.MyOnItemClickListener;
import top.spencer.crabscore.view.view.MyRecycleListView;
import top.spencer.crabscore.view.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 评委用户组的口感评分-所有小组页面
 *
 * @author spencercjh
 */
public class TasteGradeFragment extends BaseFragment implements MyRecycleListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView groupListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GroupGradeListAdapter groupGradeListAdapter;
    private GroupPresenter groupPresenter;
    private RankListPresenter rankListPresenter;
    private String jwt;
    private Competition presentCompetition;
    private List<GroupResult> groupList = new ArrayList<>(pageSize);
    private int pageNum = 1;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static TasteGradeFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        TasteGradeFragment fragment = new TasteGradeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        groupPresenter.detachView();
        rankListPresenter.detachView();
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
        groupPresenter = new GroupPresenter();
        groupPresenter.attachView(this);
        rankListPresenter = new RankListPresenter();
        rankListPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
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
        groupPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
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
                    groupPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
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
     * 初始化列表adapter，设置单击监听
     */
    private void initGroupListAdapter() {
        groupGradeListAdapter = new GroupGradeListAdapter(groupList);
        groupGradeListAdapter.setOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(View view) {
                final GroupResult groupResult = (GroupResult) view.getTag();
                Intent intent = new Intent(getContext(), TasteGradeActivity.class);
                intent.putExtra("group", groupResult);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {
                //nothing
            }
        });
    }

    /**
     * getAllGroup请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        boolean repeat = rankListPresenter.dealGroupListJSON(successData.getJSONArray("result"), groupList);
        if (repeat) {
//            showToast("没有更多了哦");
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                groupGradeListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        groupPresenter.getAllGroup(presentCompetition.getCompetitionId(), pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}