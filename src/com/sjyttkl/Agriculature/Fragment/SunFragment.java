package com.sjyttkl.Agriculature.Fragment;

import java.util.Random;

import com.sjyttkl.Agriculature.HomeActivity;
import com.sjyttkl.Agriculature.LizhiActivity;
import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.circle.ColorfulRingProgressView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SunFragment extends Fragment
{
	SeekBar sbPercent;
	SeekBar sbStartAngle;
	SeekBar sbStrokeWidth;
	TextView tvTip;
	ColorfulRingProgressView crpv;
	TextView tvPercent;
	private FragmentActivity activity;
	private Thread myThread;
	private ImageView lv_lizhi_co2_back;
	private int LIZHI = 1;
	private boolean flag = false;
	private TextView tv_sunshine_lizhi_titleText;
	private ImageView lizhi_sunshine_titleBg;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myThread = new myThread();
		myThread.start();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		System.out.println("lizhi----Co2Fragment");
		return inflater.inflate(R.layout.lizhi_sunshine_flashcircle, container, false);

	}

	@Override
	public void onStart()
	{
		activity = getActivity();
		
		super.onStart();
		initView();

	}

	public void onDestroy()
	{
		super.onDestroy();
		System.out.println("destory");
		flag = true;
	};

	Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case 0:
				// ������������,�õ�����
				int data = (Integer) msg.obj;
				System.out.println("Co2Fragment  " + data);

				crpv.setPercent(Float.valueOf(data));
				tvPercent.setText(data + "");

				break;
			default:
				break;
			}
		};
	};

	private void initView()
	{
		tv_sunshine_lizhi_titleText = (TextView) activity.findViewById(R.id.tv_sunshine_lizhi_titleText);
		Bundle bundle = activity.getIntent().getExtras();
		tv_sunshine_lizhi_titleText.setText((String) bundle.get("cataglory"));
		
		lizhi_sunshine_titleBg = (ImageView) activity.findViewById(R.id.lizhi_sunshine_titleBg);
		crpv = (ColorfulRingProgressView) activity.findViewById(R.id.lizhi_co2_crpv);
		tvTip = (TextView) activity.findViewById(R.id.lizhi_co2_tvTip);
		sbPercent = (SeekBar) activity.findViewById(R.id.lizhi_co2_sbPercent);
		// sbPercent.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbStartAngle = (SeekBar) activity.findViewById(R.id.lizhi_co2_sbStartAngle);
		// sbStartAngle.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbStrokeWidth = (SeekBar) activity.findViewById(R.id.lizhi_co2_sbStrokeWidth);
		// sbStrokeWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
		tvPercent = (TextView) activity.findViewById(R.id.lizhi_co2_tvPercent);
		// lv_lizhi_co2_back = (ImageView)
		// activity.findViewById(R.id.lv_lizhi_co2_back);

		lizhi_sunshine_titleBg.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(activity, HomeActivity.class);
				// startActivity(intent);
				activity.setResult(LIZHI, intent);
				activity.finish();

			}
		});
		crpv.setOnClickListener(new View.OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				ObjectAnimator anim = ObjectAnimator.ofFloat(v, "percent", 0, ((ColorfulRingProgressView) v).getPercent());
				anim.setInterpolator(new LinearInterpolator());
				anim.setDuration(1000);
				anim.start();
			}
		});

	}

	class myThread extends Thread
	{
		@Override
		public void run()
		{
			Random random = new Random(1);
			while (true)
			{

				int progress = random.nextInt(100);
				// ��ʱ���������֮������Ϣ��Handler�����UI���£�

				// ��Ҫ���ݴ��ݣ������淽����
				Message msg = new Message();
				msg.obj = progress;// �����ǻ������ͣ������Ƕ��󣬿�����List��map�ȣ�
				msg.what = 0;
				mHandler.sendMessage(msg);
				try
				{
					if (flag == true)
					{
						break;
					}
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{

				}

			}
		}
	}

	private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			if (seekBar.getId() == R.id.sbPercent)
			{
				crpv.setPercent(progress);
				tvPercent.setText(progress + "");
			} else if (seekBar.getId() == R.id.sbStartAngle)
			{
				crpv.setStartAngle(progress);
			} else if (seekBar.getId() == R.id.sbStrokeWidth)
			{
				crpv.setStrokeWidthDp(progress);
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{

		}

	};

}
