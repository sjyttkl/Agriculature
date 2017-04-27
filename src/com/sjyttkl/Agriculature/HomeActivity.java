package com.sjyttkl.Agriculature;

import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.Adapter.FragmentHomeAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity implements OnClickListener
{
	public static final int TAB_HOME = 0;
	public static final int TAB_LIZHI = 1;
	public static final int TAB_MANGGUO = 2;
	public static final int TAB_BUY = 3;
	public static final int TAB_MORE = 4;

	private ViewPager viewPager;
	private RadioButton main_tab_home, main_tab_lizhi, main_tab_mangguo, main_tab_buy, main_tab_more;
	final Activity activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentmain);

		initView();
		addListener();
	}
   
	
	@SuppressLint("NewApi")
	private void initView()
	{
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		main_tab_home = (RadioButton) findViewById(R.id.main_tab_home);
		main_tab_lizhi = (RadioButton) findViewById(R.id.main_tab_lizhi);
		main_tab_mangguo = (RadioButton) findViewById(R.id.main_tab_mangguo);
		main_tab_buy = (RadioButton) findViewById(R.id.main_tab_buy);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);
		main_tab_home.setOnClickListener(this);
		main_tab_lizhi.setOnClickListener(this);
		main_tab_mangguo.setOnClickListener(this);
		main_tab_buy.setOnClickListener(this);
		main_tab_more.setOnClickListener(this);

		FragmentHomeAdapter adapter = new FragmentHomeAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);

		
	}

	private void addListener()
	{
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int id)
			{
				switch (id)
				{
				case TAB_HOME:
					main_tab_home.setChecked(true);
					break;
				case TAB_LIZHI:
					main_tab_lizhi.setChecked(true);
					break;
				case TAB_MANGGUO:
					main_tab_mangguo.setChecked(true);
					break;
				case TAB_BUY:
					main_tab_buy.setChecked(true);
					break;
				case TAB_MORE:
					main_tab_more.setChecked(true);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (resultCode)
		{ // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case 1:
			viewPager.setCurrentItem(TAB_LIZHI);
			break;
		case 2:
			viewPager.setCurrentItem(TAB_MANGGUO);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.main_tab_home:
			viewPager.setCurrentItem(TAB_HOME);
			break;
		case R.id.main_tab_lizhi:
			viewPager.setCurrentItem(TAB_LIZHI);
			break;
		case R.id.main_tab_mangguo:
			viewPager.setCurrentItem(TAB_MANGGUO);
			break;
		case R.id.main_tab_buy:
			viewPager.setCurrentItem(TAB_BUY);
			break;
		case R.id.main_tab_more:
			viewPager.setCurrentItem(TAB_MORE);
			break;

		default:
			break;
		}
	}

	
}
