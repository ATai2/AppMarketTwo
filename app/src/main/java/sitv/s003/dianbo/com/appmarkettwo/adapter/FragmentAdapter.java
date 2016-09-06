package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;

/**
 * Created by wx on 8/10 0010.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public FragmentAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    public Fragment getItem(int fragment) {
        return fragments.get(fragment);
    }

    public int getCount() {
        return fragments.size();
    }
}
