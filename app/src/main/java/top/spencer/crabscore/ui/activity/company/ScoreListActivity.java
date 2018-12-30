package top.spencer.crabscore.ui.activity.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.model.entity.dto.GroupResult;
import top.spencer.crabscore.ui.adapter.TabLayoutPageAdapter;
import top.spencer.crabscore.ui.fragment.company.OneGroupAllCrabFragment;
import top.spencer.crabscore.ui.view.TabLayoutView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spencercjh
 */
public class ScoreListActivity extends BaseActivity implements TabLayoutView {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_title)
    TabLayout tabLayout;
    @BindView(R.id.tl_head)
    Toolbar toolbar;
    private GroupResult groupResult;

    /**
     * 取得从CompanyFragment传过来的groupResult
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list_actity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        groupResult = (GroupResult) intent.getSerializableExtra("group");
        initView();
    }

    /**
     * 初始化组件
     */
    @Override
    public void initView() {
        toolbar.setTitle("Company");
        toolbar.setEnabled(false);
        initTabLayout();
    }

    /**
     * 初始化二级页面TabLayout及其Fragment
     */
    @Override
    public void initTabLayout() {
        List<String> mTitleList = new ArrayList<>(1);
        mTitleList.add("该组全部螃蟹");
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(0)), true);
        List<Fragment> mFragmentList = new ArrayList<>(1);
        mFragmentList.add(OneGroupAllCrabFragment.newInstance(groupResult));
        TabLayoutPageAdapter adapter = new TabLayoutPageAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(mFragmentList.size());
        tabLayout.setupWithViewPager(vpContent);
    }

    /**
     * 没有请求
     *
     * @param successData 成功数据源
     */
    @Deprecated
    @Override
    public void showData(JSONObject successData) {
        //nothing
    }
}
