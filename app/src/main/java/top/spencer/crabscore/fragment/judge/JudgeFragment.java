package top.spencer.crabscore.fragment.judge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.adapter.AdministratorPageAdapter;
import top.spencer.crabscore.adapter.JudgePageAdapter;
import top.spencer.crabscore.base.BaseFragment;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author spencercjh
 */
public class JudgeFragment extends BaseFragment {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.tl_head)
    Toolbar toolbar;

    private ArrayList<String> mTitleArray = new ArrayList<>();


    public static JudgeFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        JudgeFragment fragment = new JudgeFragment();
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
        toolbar.setTitle("Judge");
        toolbar.setEnabled(false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        initTabLayout();
        initTabViewPager();
    }

    private void initTabLayout() {
        tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTitleArray.add("全部小组");
        mTitleArray.add("种质评分");
        mTitleArray.add("口感评分");
        tabTitle.addTab(tabTitle.newTab().setText(mTitleArray.get(0)),true);
        tabTitle.addTab(tabTitle.newTab().setText(mTitleArray.get(1)));
        tabTitle.addTab(tabTitle.newTab().setText(mTitleArray.get(2)));
    }

    private void initTabViewPager() {
        JudgePageAdapter adapter = new JudgePageAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), mTitleArray);
        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Objects.requireNonNull(tabTitle.getTabAt(position)).select();
            }
        });
    }

    @Override
    public void showData(JSONObject successData) {

    }
}