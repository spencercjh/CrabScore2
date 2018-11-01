package top.spencer.crabscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import top.spencer.crabscore.fragment.administrator.CompanyAdminFragment;
import top.spencer.crabscore.fragment.administrator.CompetitionAdminFragment;
import top.spencer.crabscore.fragment.administrator.RegistAssessmentFragment;
import top.spencer.crabscore.fragment.administrator.UserAdminFragment;

import java.util.ArrayList;

/**
 * @author spencercjh
 */
public class AdministratorPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public AdministratorPageAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UserAdminFragment.newInstance("USER ADMIN");
        } else if (position == 1) {
            return RegistAssessmentFragment.newInstance("REGIST ASSESSMENT");
        } else if (position == 2) {
            return CompanyAdminFragment.newInstance("COMPANY ADMIN");
        } else if (position == 3) {
            return CompetitionAdminFragment.newInstance("COMPETITION ADMIN");
        }
        return UserAdminFragment.newInstance("USER ADMIN");
    }

    @Override
    public int getCount() {
        return mTitleArray.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray.get(position);
    }
}
