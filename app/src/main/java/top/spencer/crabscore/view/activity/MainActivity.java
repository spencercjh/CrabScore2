package top.spencer.crabscore.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.Competition;
import top.spencer.crabscore.presenter.NavigationPresenter;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.adapter.MainPagerAdapter;
import top.spencer.crabscore.view.fragment.administrator.AdministratorFragment;
import top.spencer.crabscore.view.fragment.company.CompanyFragment;
import top.spencer.crabscore.view.fragment.judge.JudgeFragment;
import top.spencer.crabscore.view.fragment.person.PersonCenterFragment;
import top.spencer.crabscore.view.fragment.rank.RankFragment;
import top.spencer.crabscore.view.fragment.staff.StaffFragment;
import top.spencer.crabscore.view.helper.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 主导航活动
 *
 * @author spencercjh
 */
public class MainActivity extends BaseActivity implements BaseView {
    @BindView(R.id.navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.view_page)
    ViewPager viewPager;
    private MenuItem menuItem;
    private long lastPressTime = 0;
    private NavigationPresenter navigationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        initNavigationBar();
        navigationPresenter = new NavigationPresenter();
        navigationPresenter.attachView(this);
        navigationPresenter.getPresentCompetitionProperty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationPresenter.detachView();
    }

    /**
     * 初始化底部导航栏及其3个Fragment
     */
    private void initNavigationBar() {
        int roleChoice = (int) SharedPreferencesUtil.getData("ROLE_CHOICE", CommonConstant.USER_TYPE_COMPANY);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(RankFragment.newInstance("大赛排名"));
        if (roleChoice == CommonConstant.USER_TYPE_ADMIN) {
            list.add(AdministratorFragment.newInstance("管理员"));
        } else if (roleChoice == CommonConstant.USER_TYPE_JUDGE) {
            list.add(JudgeFragment.newInstance("评委"));
        } else if (roleChoice == CommonConstant.USER_TYPE_STAFF) {
            list.add(StaffFragment.newInstance("工作人员"));
        } else {
            list.add(CompanyFragment.newInstance("参选单位"));
        }
        list.add(PersonCenterFragment.newInstance("个人中心"));
        mainPagerAdapter.setList(list);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItem = item;
                switch (item.getItemId()) {
                    case R.id.navigation_rank:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_function:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_person_center:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setOffscreenPageLimit(3);
    }

    /**
     * getPresentCompetitionProperty请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        SharedPreferencesUtil.putData("PRESENT_COMPETITION",
                JSONObject.parseObject(successData.getString("result"), Competition.class));
    }

    /**
     * 重写返回键
     */
    @Override
    @SuppressWarnings("Duplicates")
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressTime < CommonConstant.EXIT_GAP_TIME) {
            finish();
            Runtime.getRuntime().exit(0);
        } else {
            lastPressTime = System.currentTimeMillis();
            showToast("再按一次返回键退出程序");
        }
    }
}
