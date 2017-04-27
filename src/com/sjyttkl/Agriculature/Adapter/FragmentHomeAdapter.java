package com.sjyttkl.Agriculature.Adapter;

import com.sjyttkl.Agriculature.HomeActivity;
import com.sjyttkl.Agriculature.Fragment.BuyFragment;
import com.sjyttkl.Agriculature.Fragment.HomeFragment;
import com.sjyttkl.Agriculature.Fragment.LizhiFragment;
import com.sjyttkl.Agriculature.Fragment.MangguoFragment;
import com.sjyttkl.Agriculature.Fragment.MoreFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentHomeAdapter extends FragmentPagerAdapter
{
	public final static int TAB_COUNT = 5;

	public FragmentHomeAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int id)
	{
		switch (id)
		{
		case HomeActivity.TAB_HOME:
			HomeFragment homeFragment = new HomeFragment();
			return homeFragment;
		case HomeActivity.TAB_LIZHI:
			LizhiFragment categoryFragment = new LizhiFragment();
			return categoryFragment;
		case HomeActivity.TAB_MANGGUO:
			MangguoFragment carFragment = new MangguoFragment();
			return carFragment;
		case HomeActivity.TAB_BUY:
			BuyFragment buyFragment = new BuyFragment();
			return buyFragment;
		case HomeActivity.TAB_MORE:
			MoreFragment moreFragment = new MoreFragment();
			return moreFragment;
		}
		return null;
	}

	@Override
	public int getCount()
	{
		return TAB_COUNT;
	}
}
