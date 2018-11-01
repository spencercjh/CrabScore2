package top.spencer.crabscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import top.spencer.crabscore.fragment.administrator.CompanyAdminFragment;
import top.spencer.crabscore.fragment.administrator.CompetitionAdminFragment;
import top.spencer.crabscore.fragment.administrator.RegistAssessmentFragment;
import top.spencer.crabscore.fragment.administrator.UserAdminFragment;
import top.spencer.crabscore.fragment.staff.DataEntryFragment;
import top.spencer.crabscore.fragment.staff.FindLabelFragment;

import java.util.ArrayList;

/**
 * @author spencercjh
 */
public class StaffPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public StaffPageAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return DataEntryFragment.newInstance("DataEntryFragment");
        } else if (position == 1) {
            return FindLabelFragment.newInstance("FindLabelFragment");
        }
        return DataEntryFragment.newInstance("DataEntryFragment");
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
