package top.spencer.crabscore.view.fragment.company;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.view.adapter.TabLayoutPageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class CompanyFragment extends BaseFragment {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_title)
    TabLayout tabLayout;
    @BindView(R.id.tl_head)
    Toolbar toolbar;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static CompanyFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        CompanyFragment fragment = new CompanyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_has_top_navigation;
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
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        toolbar.setTitle("Company");
        toolbar.setEnabled(false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        initTabLayout();
    }

    /**
     * 初始化二级页面TabLayout及其Fragment
     */
    private void initTabLayout() {
        List<String> mTitleList = new ArrayList<>(3);
        mTitleList.add("整体情况");
        mTitleList.add("种质得分");
        mTitleList.add("口感得分");
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(2)));
        List<Fragment> mFragmentList = new ArrayList<>(3);
        mFragmentList.add(OverallScoreFragment.newInstance("OverallScoreFragment"));
        mFragmentList.add(QualityScoreFragment.newInstance("QualityScoreFragment"));
        mFragmentList.add(TasteScoreFragment.newInstance("TasteScoreFragment"));
        TabLayoutPageAdapter adapter = new TabLayoutPageAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(mFragmentList.size());
        tabLayout.setupWithViewPager(vpContent);
    }

    @Override
    public void showData(JSONObject successData) {

    }

    //TODO UserAdminFragment
}