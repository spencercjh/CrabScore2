package top.spencer.crabscore.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author spencercjh
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}