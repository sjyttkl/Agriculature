package com.sjyttkl.Agriculature.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sjyttkl.Agriculature.LizhiActivity;
import com.sjyttkl.Agriculature.Fragment.SunFragment;
import com.sjyttkl.Agriculature.Fragment.HumidtyFragment;
import com.sjyttkl.Agriculature.Fragment.TemperatureFragment;

public class FragmentAdapter extends FragmentPagerAdapter
{
	private int count;

	public FragmentAdapter(FragmentManager fm, int count)
	{
		super(fm);
		this.count = count;
	}

	@Override
	public Fragment getItem(int id)
	{
		switch (id)
		{
		case LizhiActivity.co2:
			SunFragment co2Fragment = new SunFragment();
			return co2Fragment;
		case LizhiActivity.temperature:
			TemperatureFragment temperatureFragment = new TemperatureFragment();
			return temperatureFragment;
		case LizhiActivity.humidity:
			HumidtyFragment humidityFragment = new HumidtyFragment();
			return humidityFragment;

		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount()
	{

		return count;
	}

}
