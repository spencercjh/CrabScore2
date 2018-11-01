package top.spencer.crabscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import top.spencer.crabscore.fragment.administrator.CompanyAdminFragment;
import top.spencer.crabscore.fragment.administrator.CompetitionAdminFragment;
import top.spencer.crabscore.fragment.administrator.RegistAssessmentFragment;
import top.spencer.crabscore.fragment.administrator.UserAdminFragment;
import top.spencer.crabscore.fragment.company.OverallScoreFragment;
import top.spencer.crabscore.fragment.company.QualityScoreFragment;
import top.spencer.crabscore.fragment.company.TasteScoreFragment;

import java.util.ArrayList;

/**
 * @author spencercjh
 */
public class CompanyPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public CompanyPageAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return OverallScoreFragment.newInstance("OverallScoreFragment");
        } else if (position == 1) {
            return QualityScoreFragment.newInstance("QualityScoreFragment");
        } else if (position == 2) {
            return TasteScoreFragment.newInstance("TasteScoreFragment");
        }
        return OverallScoreFragment.newInstance("OverallScoreFragment");
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
