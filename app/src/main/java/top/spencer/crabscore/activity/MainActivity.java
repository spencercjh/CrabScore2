package top.spencer.crabscore.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.util.SharedPreferencesUtil;
import top.spencer.crabscore.view.*;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");

        initNavigationBar();

    }

    private void initNavigationBar() {
        int roleChoice = (int) SharedPreferencesUtil.getData("ROLE_CHOICE", CommonConstant.USER_TYPE_COMPANY);
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
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
        viewPagerAdapter.setList(list);
    }

    @Override
    public void showData(JSONObject successData) {

    }
}
