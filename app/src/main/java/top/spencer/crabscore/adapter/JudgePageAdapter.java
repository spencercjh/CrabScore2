package top.spencer.crabscore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import top.spencer.crabscore.fragment.administrator.CompanyAdminFragment;
import top.spencer.crabscore.fragment.administrator.CompetitionAdminFragment;
import top.spencer.crabscore.fragment.administrator.RegistAssessmentFragment;
import top.spencer.crabscore.fragment.administrator.UserAdminFragment;
import top.spencer.crabscore.fragment.judge.AllGroupFragment;
import top.spencer.crabscore.fragment.judge.QualityGradeFragment;
import top.spencer.crabscore.fragment.judge.TasteGradeFragment;

import java.util.ArrayList;

/**
 * @author spencercjh
 */
public class JudgePageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitleArray;

    public JudgePageAdapter(FragmentManager fm, ArrayList<String> titleArray) {
        super(fm);
        mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return AllGroupFragment.newInstance("AllGroupFragment");
        } else if (position == 1) {
            return QualityGradeFragment.newInstance("QualityGradeFragment");
        } else if (position == 2) {
            return TasteGradeFragment.newInstance("TasteGradeFragment");
        }
        return AllGroupFragment.newInstance("AllGroupFragment");
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
