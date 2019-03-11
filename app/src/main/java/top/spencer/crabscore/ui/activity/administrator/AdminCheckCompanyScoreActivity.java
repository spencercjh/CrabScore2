package top.spencer.crabscore.ui.activity.administrator;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.ui.view.MyRecycleListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

/**
 * 管理员查看参选单位小组分数活动
 * //todo admin check company score
 *
 * @author spencercjh
 */
public class AdminCheckCompanyScoreActivity extends BaseActivity implements MyRecycleListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView groupListView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_score);
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setRecycleView() {

    }

    @Override
    public void showData(JSONObject successData) {

    }
}
