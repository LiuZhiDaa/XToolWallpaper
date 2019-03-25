package app.xtoolwallpaper.com.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
  private Context context;
  private List<Fragment> fragmentList;
  public MainPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
    super(fm);
    this.context = context;
    this.fragmentList = fragmentList;
  }
  @Override
  public Fragment getItem(int i) {
    return fragmentList.get(i);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }
}
