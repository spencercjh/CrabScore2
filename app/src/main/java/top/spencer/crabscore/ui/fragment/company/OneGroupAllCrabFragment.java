package top.spencer.crabscore.ui.fragment.company;

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
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.model.entity.vo.CrabScoreResult;
import top.spencer.crabscore.model.entity.vo.GroupResult;
import top.spencer.crabscore.presenter.CompanyListPresenter;
import top.spencer.crabscore.ui.adapter.CompanyCrabListAdapter;
import top.spencer.crabscore.ui.view.MyRecycleListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 参选单位用户组某一小组内的全部螃蟹和得分信息页面
 *
 * @author spencercjh
 */
public class OneGroupAllCrabFragment extends BaseFragment implements MyRecycleListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView crabScoreListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CompanyListPresenter companyListPresenter;
    private String jwt;
    private Competition presentCompetition;
    private GroupResult groupResult;
    private int pageNum = 1;
    private List<CrabScoreResult> crabScoreResultList = new ArrayList<>(pageSize);
    private CompanyCrabListAdapter companyCrabListAdapter;

    /**
     * 取得实例
     *
     * @param groupResult group
     * @return fragment
     */
    public static OneGroupAllCrabFragment newInstance(GroupResult groupResult) {
        Bundle args = new Bundle();
        args.putSerializable("group", groupResult);
        OneGroupAllCrabFragment fragment = new OneGroupAllCrabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        companyListPresenter.detachView();
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
        Bundle bundle = getArguments();
        companyListPresenter = new CompanyListPresenter();
        companyListPresenter.attachView(this);
        groupResult = (GroupResult) Objects.requireNonNull(bundle).getSerializable("group");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
        setRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        companyCrabListAdapter = new CompanyCrabListAdapter(crabScoreResultList, getContext());
        if (crabScoreResultList.size() == 0) {
            crabScoreListView.setEmptyView(emptyText);
        }
        crabScoreListView.setAdapter(companyCrabListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        crabScoreListView.setLayoutManager(layoutManager);
        crabScoreListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        crabScoreListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == companyCrabListAdapter.getItemCount()) {
                    companyListPresenter.getOneGroupAllCrabAndScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
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
     * 初始发起请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        companyListPresenter.getOneGroupAllCrabAndScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
    }

    /**
     * getOneGroupAllCrabAndScore请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        boolean repeat = companyListPresenter.dealCrabScoreResultJSON(successData.getJSONArray("result"), crabScoreResultList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                companyCrabListAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * swipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        companyListPresenter.getOneGroupAllCrabAndScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
