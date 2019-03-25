package app.xtoolwallpaper.com.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
  private Context context;
  private List<Fragment> fragmentList;
  private List<String> list_Title;
  public MyPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList,List<String> list_Title) {
    super(fm);
    this.context = context;
    this.fragmentList = fragmentList;
    this.list_Title = list_Title;
  }
  @Override
  public Fragment getItem(int i) {
    return fragmentList.get(i);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }
  @Override
  public CharSequence getPageTitle(int position) {
    return list_Title.get(position);
  }
}
