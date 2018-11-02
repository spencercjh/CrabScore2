package top.spencer.crabscore.fragment.rank;

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
import top.spencer.crabscore.adapter.RankPageAdapter;
import top.spencer.crabscore.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class RankFragment extends BaseFragment {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_title)
    TabLayout tabLayout;
    @BindView(R.id.tl_head)
    Toolbar toolbar;

    public static RankFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        RankFragment fragment = new RankFragment();
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
        toolbar.setTitle("Rank");
        toolbar.setEnabled(false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        initTabLayout();
    }

    private void initTabLayout() {
        List<String> mTitleList = new ArrayList<>(3);
        mTitleList.add("金蟹奖");
        mTitleList.add("最佳种质奖");
        mTitleList.add("口感奖");
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(2)));
        List<Fragment> mFragmentList = new ArrayList<>(3);
        mFragmentList.add(FatnessRankFragment.newInstance("FatnessRankFragment"));
        mFragmentList.add(QualityRankFragment.newInstance("QualityRankFragment"));
        mFragmentList.add(TasteRankFragment.newInstance("TasteRankFragment"));
        RankPageAdapter adapter = new RankPageAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(mFragmentList.size());
        tabLayout.setupWithViewPager(vpContent);
    }

    @Override
    public void showData(JSONObject successData) {

    }
}