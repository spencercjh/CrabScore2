package top.spencer.crabscore.fragment.administrator;

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
import top.spencer.crabscore.adapter.AdministratorPageAdapter;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.fragment.company.CompanyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class AdministratorFragment extends BaseFragment {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_title)
    TabLayout tabLayout;
    @BindView(R.id.tl_head)
    Toolbar toolbar;

    public static AdministratorFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        AdministratorFragment fragment = new AdministratorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_has_top_navigation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        toolbar.setTitle("Admin");
        toolbar.setEnabled(false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        initTabLayout();
    }

    private void initTabLayout() {
        List<String> mTitleList = new ArrayList<>(4);
        mTitleList.add("用户列表");
        mTitleList.add("注册审核");
        mTitleList.add("参选单位");
        mTitleList.add("大赛管理");
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(3)));
        List<Fragment> mFragmentList = new ArrayList<>(4);
        mFragmentList.add(UserAdminFragment.newInstance("UserAdminFragment"));
        mFragmentList.add(RegistAssessmentFragment.newInstance("RegistAssessmentFragment"));
        mFragmentList.add(CompanyAdminFragment.newInstance("CompanyAdminFragment"));
        mFragmentList.add(CompetitionAdminFragment.newInstance("CompetitionAdminFragment"));
        AdministratorPageAdapter adapter = new AdministratorPageAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(mFragmentList.size());
        tabLayout.setupWithViewPager(vpContent);
    }

    @Override
    public void showData(JSONObject successData) {

    }
}