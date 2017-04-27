package com.sjyttkl.Agriculature;

import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.Adapter.FragmentAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class LizhiActivity extends FragmentActivity implements OnClickListener
{

	public static final int co2 = 0;
	public static final int temperature = 1;
	public static final int humidity = 2;
    private static final int COUNT = 3;
	private ImageView lizhi_back;
	private TextView tv_manlizhi_titleText;
	private int LIZHI = 1;
	private ViewPager viewPager;
	private RadioButton Rb_lizhi_co2, Rb_lizhi_temper, Rb_lizhi_humid;
   
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lizhi);
		initView();
		addListener();
		
		
	}
 
	
	private void initView()
	{
		tv_manlizhi_titleText = (TextView) findViewById(R.id.tv_lizhi_titleText);
		lizhi_back = (ImageView) findViewById(R.id.lizhi_back);
		Bundle bundle = getIntent().getExtras();
		tv_manlizhi_titleText.setText((String) bundle.get("cataglory"));
		lizhi_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(LizhiActivity.this, HomeActivity.class);
				// startActivity(intent);
				setResult(LIZHI, intent);
				finish();

			}
		});

		viewPager = (ViewPager) findViewById(R.id.lizhi_acitity_viewpager);
		Rb_lizhi_co2 = (RadioButton) findViewById(R.id.Rb_lizhi_co2);
		Rb_lizhi_temper = (RadioButton) findViewById(R.id.Rb_lizhi_temper);
		Rb_lizhi_humid = (RadioButton) findViewById(R.id.Rb_lizhi_humid);
		Rb_lizhi_co2.setOnClickListener(this);
		Rb_lizhi_temper.setOnClickListener(this);
		Rb_lizhi_humid.setOnClickListener(this);
		
		
		FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),COUNT);
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
				case co2:
					Rb_lizhi_co2.setChecked(true);
					break;
				case temperature:
					Rb_lizhi_temper.setChecked(true);
					break;
				case humidity:
					Rb_lizhi_humid.setChecked(true);
					break;
				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.Rb_lizhi_co2:
			viewPager.setCurrentItem(co2);
			break;
		case R.id.Rb_lizhi_temper:
			viewPager.setCurrentItem(temperature);
			break;
		case R.id.Rb_lizhi_humid:
			viewPager.setCurrentItem(humidity);
			break;
		default:
			break;

		}

	}
}
