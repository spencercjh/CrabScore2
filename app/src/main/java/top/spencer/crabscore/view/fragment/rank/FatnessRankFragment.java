package top.spencer.crabscore.view.fragment.rank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.presenter.RankListPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.adapter.FatnessRankListAdapter;
import top.spencer.crabscore.view.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class FatnessRankFragment extends BaseFragment {
    @BindView(R.id.recycler_view_rank)
    EmptyRecyclerView rankListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;

    private RankListPresenter rankListPresenter;
    private List groupList = new ArrayList<>(4);

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

    private void setRecycleView() {
        rankListView.setLayoutManager(new LinearLayoutManager(getContext()));
        rankListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        FatnessRankListAdapter fatnessRankListAdapter = new FatnessRankListAdapter(groupList);
        if (groupList.size() == 0) {
            rankListView.setEmptyView(emptyText);
        }
        rankListView.setAdapter(fatnessRankListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankListPresenter.getFatnessRank((Integer) SharedPreferencesUtil.getData("PRESENT_COMPETITION_ID", 1));
    }

    @Override
    public void showData(JSONObject successData) {
        groupList = (List) successData.get("result");
        setRecycleView();
    }
}