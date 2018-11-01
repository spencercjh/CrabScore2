package top.spencer.crabscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import top.spencer.crabscore.fragment.administrator.CompanyAdminFragment;
import top.spencer.crabscore.fragment.administrator.CompetitionAdminFragment;
import top.spencer.crabscore.fragment.administrator.RegistAssessmentFragment;
import top.spencer.crabscore.fragment.administrator.UserAdminFragment;
import top.spencer.crabscore.fragment.rank.FatnessRankFragment;
import top.spencer.crabscore.fragment.rank.QualityRankFragment;
import top.spencer.crabscore.fragment.rank.TasteRankFragment;

import java.util.ArrayList;

/**
 * @author spencercjh
 */
public class RankPageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public RankPageAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FatnessRankFragment.newInstance("FatnessRankFragment");
        } else if (position == 1) {
            return QualityRankFragment.newInstance("QualityRankFragment");
        } else if (position == 2) {
            return TasteRankFragment.newInstance("TasteRankFragment");
        }
        return FatnessRankFragment.newInstance("FatnessRankFragment");
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
