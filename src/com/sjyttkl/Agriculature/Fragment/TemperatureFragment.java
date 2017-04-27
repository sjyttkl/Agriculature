package com.sjyttkl.Agriculature.Fragment;

import java.util.Random;

import com.sjyttkl.Agriculature.HomeActivity;
import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.Fragment.HumidtyFragment.myThread;
import com.sjyttkl.Agriculature.circle.ColorfulRingProgressView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class TemperatureFragment extends Fragment
{
	SeekBar sbPercent;
	SeekBar sbStartAngle;
	SeekBar sbStrokeWidth;
	TextView tvTip;
	ColorfulRingProgressView crpv;
	TextView tvPercent;
	private FragmentActivity activity;
	private Thread myThread;
	private boolean flag = false;
	private ImageView lv_temper_co2_back;
	private TextView tv_temper_lizhi_titleText;
	private ImageView lizhi_temper_titleBg;
	private int LIZHI = 1;
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
		System.out.println("lizhi----TemperatureFragment");
		return inflater.inflate(R.layout.lizhi_temper_flashcircle, container, false);

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
			case 2:
				// 完成主界面更新,拿到数据
				int data = (Integer) msg.obj;
				System.out.println("TemperatureFragment  " + data);

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
		
		tv_temper_lizhi_titleText = (TextView) activity.findViewById(R.id.tv_temper_lizhi_titleText);
		Bundle bundle = activity.getIntent().getExtras();
		tv_temper_lizhi_titleText.setText((String) bundle.get("cataglory"));
		lizhi_temper_titleBg = (ImageView) activity.findViewById(R.id.lizhi_temper_titleBg);
		crpv = (ColorfulRingProgressView) activity.findViewById(R.id.lizhi_temper_crpv);
		tvTip = (TextView) activity.findViewById(R.id.lizhi_temper_tvTip);
		sbPercent = (SeekBar) activity.findViewById(R.id.lizhi_temper_sbPercent);
		// sbPercent.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbStartAngle = (SeekBar) activity.findViewById(R.id.lizhi_temper_sbStartAngle);
		// sbStartAngle.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbStrokeWidth = (SeekBar) activity.findViewById(R.id.lizhi_temper_sbStrokeWidth);
		// sbStrokeWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
		tvPercent = (TextView) activity.findViewById(R.id.lizhi_temper_tvPercent);

		lizhi_temper_titleBg.setOnClickListener(new OnClickListener()
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
			Random random = new Random(3);
			while (true)
			{
				int progress = random.nextInt(100);
				// 耗时操作，完成之后发送消息给Handler，完成UI更新；

				// 需要数据传递，用下面方法；
				Message msg = new Message();
				msg.obj = progress;// 可以是基本类型，可以是对象，可以是List、map等；
				msg.what = 2;
				mHandler.sendMessage(msg);
				try
				{
					if(flag == true)
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
