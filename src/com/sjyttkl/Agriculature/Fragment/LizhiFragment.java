package com.sjyttkl.Agriculature.Fragment;

import java.util.Timer;
import java.util.TimerTask;

import com.sjyttkl.Agriculature.LizhiActivity;
import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.util.DisPlayScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LizhiFragment extends Fragment
{

	private ViewPager viewPager;
	private static final int UPTATE_VIEWPAGER = 1;
	// 装点点的数组
	private ImageView[] tips;

	// 装ImageView数组
	private ImageView[] mImageViews;
	/**
	 * 图片资源id
	 */
	private int[] imgIdArray;
	/**
	 * 点点
	 */
	private ViewGroup group;

	private ListView listview;

	private static int[] imageViews =
	{ R.drawable.pingzhong_lizhi_01, R.drawable.pingzhong_lizhi_02, R.drawable.pingzhong_lizhi_03, R.drawable.pingzhong_lizhi_02 };
	private static String[] lizhi_name =
	{ "无核荔枝 ", "鸡嘴荔", "桂味荔枝" };
	private static String[] lizhi_description =
	{ "详细：“糯米糍”荔枝是广东主栽优质荔枝品种之一。糯米糍荔枝果大，单果重20～27.6克，扁心形；果皮鲜红色，较薄，龟裂片明显凸起，呈狭长形，纵向排列，裂片峰平滑，果肩一边显著突起，果顶浑圆； ",
			"详细： “妃子笑” 果粒大，单果重30克以上，卵圆形，皮色淡红，色泽鲜艳，肉厚皮薄核小，肉质细嫩凝脂，爽脆有香味，清甜多汁。“妃子笑”植株生势旺盛，稳产高产，大小年不明显，属早中熟品种。",
			"详细：“桂味”为广东传统名优荔枝品种，以果大核小，风味清香，口感极佳而闻名。“桂味”果色鲜红美观，果肉白腊色，有透明感，肉质爽脆，味清甜，具桂花香，可溶性固形物约19％以上，焦核率高,皮硬较耐贮运。" };

	// 适配手机高度

	private int height;
	private FragmentActivity activity;
	private boolean flag = false;
	// 现在运行到图片的那个位置了，从0开始
	private Timer timer;
	private int autoCurrIndex = 0;

	@Override
	public void onAttach(Activity activity)
	{
		System.out.println("onAttach...LizhiFragment");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		System.out.println("onCreate...LizhiFragment");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		System.out.println("onCreateView...LizhiFragment");
		return inflater.inflate(R.layout.main_lizhi, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		flag = true;
		System.out.println("onActivityCreated...LizhiFragment");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume()
	{

		activity = this.getActivity();
		System.out.println("onResume...LizhiFragment");
		super.onResume();

	}

	@Override
	public void onStop()
	{
		System.out.println("onStop...LizhiFragment");
		timer.cancel();
		flag = false;
		super.onStop();

	}

	@Override
	public void onStart()
	{

		activity = this.getActivity();
		System.out.println("onStart...LizhiFragment");
		timer = new Timer();
		// TODO Auto-generated method stub
		super.onStart();
		if (flag == true && group == null)
		{
			init();
			listViewinit();
		} else if (flag == true)
		{
			init();
			listViewinit();
		}
		flag = false;
	}

	@Override
	public void onDestroy()
	{
		System.out.println("onDestroy...LizhiFragment");
		super.onDestroy();
	}

	public void init()
	{
		group = (ViewGroup) activity.findViewById(R.id.lizhi_viewGroup);
		viewPager = (ViewPager) activity.findViewById(R.id.lizhi_viewPager);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		height = DisPlayScreen.getHeight(activity);
		// 载入图片资源ID//偶数个图片，否则会出现问题
		imgIdArray = new int[]
		{ R.drawable.lizhi_1, R.drawable.lizhi_2, R.drawable.lizhi_3, R.drawable.lizhi_4 };
		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		ImageView imageViewtip;
		for (int i = 0; i < tips.length; i++)
		{
			imageViewtip = new ImageView(activity);
			imageViewtip.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageViewtip;
			if (i == 0)
			{
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else
			{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			group.addView(imageViewtip);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		ImageView imageView;
		for (int i = 0; i < mImageViews.length; i++)
		{
			imageView = new ImageView(activity);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		viewPager.setAdapter(new MyAdapter());
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height);
		viewPager.setLayoutParams(lp);
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int arg0)
			{
				this.setImageBackground(arg0 % mImageViews.length);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}

			/**
			 * 设置选中的tip的背景
			 * 
			 * @param selectItems
			 */
			private void setImageBackground(int selectItems)
			{
				for (int i = 0; i < tips.length; i++)
				{
					if (i == selectItems)
					{
						tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
					} else
					{
						tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
					}
				}
			}
		});
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem(0);

		timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				Message message = new Message();
				message.what = UPTATE_VIEWPAGER;
				System.out.println("lizhi_____autoCurrIndex..." + autoCurrIndex);
				if (autoCurrIndex == imgIdArray.length - 1)
				{
					autoCurrIndex = -1;
				}
				message.arg1 = ++autoCurrIndex;
				mHandler.sendMessage(message);

			}
		}, 5000, 5000);

	}

	// 定时轮播图片，需要在主线程里面修改 UI
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case UPTATE_VIEWPAGER:
				if (msg.arg1 != 0)
				{
					viewPager.setCurrentItem(msg.arg1);
				} else
				{
					// false 当从末页调到首页是，不显示翻页动画效果，
					viewPager.setCurrentItem(msg.arg1);
				}
				break;
			}
		};
	};

	class MyAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return imgIdArray.length;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{

			((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			System.out.println("lizhi__postion.." + position);
			((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}
	}

	private void listViewinit()
	{

		listview = (ListView) getActivity().findViewById(R.id.lizhi_list_pinzhong);

		listview.setAdapter(new MyListAdapter());
		listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				switch (position)
				{
				case 0:

					Intent intent = new Intent(getActivity(), LizhiActivity.class);
					intent.putExtra("cataglory", lizhi_name[position]);
					getActivity().startActivityForResult(intent, 0);
					break;
				case 1:
					Toast.makeText(getActivity(), "软件升级中..", 0).show();
					break;
				case 2:
					Toast.makeText(getActivity(), "软件升级中..", 0).show();
					break;
				case 3:
					Toast.makeText(getActivity(), "软件升级中..", 0).show();
					break;
				}

			}
		});
	}

	class MyListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return lizhi_name.length;
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view;
			ViewHolder holder;

			if (convertView != null && convertView instanceof RelativeLayout)
			{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else
			{
				view = View.inflate(getActivity(), R.layout.litem_pinzhong, null);
				// view.setLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				// dip2px(getActivity(),height/7));
				holder = new ViewHolder();

				holder.iv_item = (ImageView) view.findViewById(R.id.iv_item);
				holder.tv_item = (TextView) view.findViewById(R.id.tv_item);
				holder.tv_description = (TextView) view.findViewById(R.id.tv_description);
				view.setTag(holder);
			}

			holder.iv_item.setBackgroundResource(imageViews[position]);
			holder.tv_item.setText(lizhi_name[position]);
			holder.tv_description.setText(lizhi_description[position]);
			return view;
		}

	}

	static class ViewHolder
	{
		ImageView iv_item;
		TextView tv_item;
		TextView tv_description;
		// ImageView iv_status;
	}

}
