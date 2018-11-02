package top.spencer.crabscore.view.fragment.rank;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.data.entity.Group;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.adapter.TasteRankListAdapter;
import top.spencer.crabscore.view.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class TasteRankFragment extends BaseFragment {
    @BindView(R.id.recycler_view_rank)
    EmptyRecyclerView rankListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private TasteRankListAdapter tasteRankListAdapter;
    private RankListPresenter rankListPresenter;
    private List<Group> groupList = new ArrayList<>(4);

    public static TasteRankFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        TasteRankFragment fragment = new TasteRankFragment();
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rankListPresenter.getTasteRank((Integer) SharedPreferencesUtil.getData("PRESENT_COMPETITION_ID", 1));
            }
        });
        rankListView.setLayoutManager(new LinearLayoutManager(getContext()));
        rankListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
    }

    private void setRecycleView() {
        tasteRankListAdapter = new TasteRankListAdapter(groupList);
        if (groupList.size() == 0) {
            rankListView.setEmptyView(emptyText);
        }
        rankListView.setAdapter(tasteRankListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankListPresenter.getTasteRank((Integer) SharedPreferencesUtil.getData("PRESENT_COMPETITION_ID", 1));
    }

    @Override
    public void showData(JSONObject successData) {
        groupList.clear();
        JSONArray groups = successData.getJSONArray("result");
        //noinspection Duplicates
        for (Object object : groups) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Group group = JSONObject.parseObject(jsonString, Group.class);
            groupList.add(group);
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                tasteRankListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}