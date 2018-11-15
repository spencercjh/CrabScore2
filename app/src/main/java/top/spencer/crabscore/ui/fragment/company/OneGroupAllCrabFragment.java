package top.spencer.crabscore.ui.fragment.company;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import top.spencer.crabscore.ui.view.MyRecycleListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * //todo adapter
 *
 * @author spencercjh
 */
public class OneGroupAllCrabFragment extends BaseFragment implements MyRecycleListView {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView groupListView;
    @BindView(R.id.textview_empty)
    TextView empty;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CompanyListPresenter companyListPresenter;
    private String jwt;
    private Competition presentCompetition;
    private GroupResult groupResult;
    private int pageNum = 1;
    private boolean repeat = false;
    private List<CrabScoreResult> crabScoreResultList = new ArrayList<>(pageSize);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        companyListPresenter.detachView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_has_empty_textview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        companyListPresenter = new CompanyListPresenter();
        companyListPresenter.attachView(this);
        groupResult = (GroupResult) Objects.requireNonNull(bundle).getSerializable("group");
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        presentCompetition = (Competition) (SharedPreferencesUtil.getData("PRESENT_COMPETITION", new Competition()));
    }

    @Override
    public void setRecycleView() {
        //todo recycle view
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        companyListPresenter.getOneGroupAllCrabAndScore(presentCompetition.getCompetitionId(), groupResult.getGroupId(),
                pageNum, pageSize, jwt);
    }

    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        repeat = companyListPresenter.dealCrabScoreResultJSON(successData.getJSONArray("result"), crabScoreResultList);
        if (repeat) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
//                .notifyDataSetChanged();
            }
        });
    }
}
