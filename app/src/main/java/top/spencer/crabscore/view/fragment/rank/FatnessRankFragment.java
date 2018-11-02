package top.spencer.crabscore.view.fragment.rank;

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
import top.spencer.crabscore.data.entity.Group;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.RankListView;
import top.spencer.crabscore.view.adapter.FatnessRankListAdapter;
import top.spencer.crabscore.view.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static top.spencer.crabscore.view.InitHelper.dealGroupListJSON;

/**
 * @author spencercjh
 */
public class FatnessRankFragment extends BaseFragment implements RankListView {
    @BindView(R.id.recycler_view_rank)
    EmptyRecyclerView rankListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private FatnessRankListAdapter fatnessRankListAdapter;
    private RankListPresenter rankListPresenter;
    private List<Group> groupList = new ArrayList<>(10);
    private int pageNum = 1;
    private boolean repeat = false;

    public static FatnessRankFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        FatnessRankFragment fragment = new FatnessRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rankListPresenter.detachView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_rank_list;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rankListPresenter = new RankListPresenter();
        rankListPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        setRecycleView();
    }

    @Override
    public void setRecycleView() {
        fatnessRankListAdapter = new FatnessRankListAdapter(groupList);
        if (groupList.size() == 0) {
            rankListView.setEmptyView(emptyText);
        }
        rankListView.setAdapter(fatnessRankListAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rankListPresenter.getFatnessRank((Integer) SharedPreferencesUtil.getData(
                        "PRESENT_COMPETITION_ID", 1), pageNum, pageSize);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rankListView.setLayoutManager(layoutManager);
        rankListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        rankListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == fatnessRankListAdapter.getItemCount()) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (!repeat) {
                        rankListPresenter.getFatnessRank((Integer) SharedPreferencesUtil.getData(
                                "PRESENT_COMPETITION_ID", 1), pageNum, pageSize);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankListPresenter.getFatnessRank((Integer) SharedPreferencesUtil.getData("PRESENT_COMPETITION_ID", 1),
                pageNum, pageSize);
    }

    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        repeat = dealGroupListJSON(successData.getJSONArray("result"), groupList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                fatnessRankListAdapter.notifyDataSetChanged();
            }
        });
    }

}