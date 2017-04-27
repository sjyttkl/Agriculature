package com.sjyttkl.Agriculature.Fragment;

import java.util.Timer;
import java.util.TimerTask;

import com.sjyttkl.Agriculature.MangguoActivity;
import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.Adapter.FragmentAdapter;
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

public class MangguoFragment extends Fragment
{
	private ViewPager viewPager;

	private static final int UPTATE_VIEWPAGER = 2;
	// 装点点的数组
	private ImageView[] tips;
	// 装ImageView数组
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
	{ R.drawable.mangguo_01, R.drawable.mangguo_02, R.drawable.mangguo_03 };
	private static String[] lizhi_name =
	{ "金煌芒 ", "金皮芒", "贵妃芒" };
	private static String[] lizhi_description =
	{ "详细： 金煌芒果又称青皮芒果，是芒果中上品。在我国主产地为海南，四月上市，肉厚核小，甜度高", "详细: 台浓芒果，也是从台湾引进，在我国产地海南。三月多上市。台农芒果单果重50-300克，是一种小芒果",
			"详细: 贵妃芒果又称红金龙，系台湾引进品种，在海南大面积种植。三月份左右成熟，单果重300-500克" };
	private FragmentActivity activity;

	// 适配手机高度

	int height;

	private boolean flag = false;

	// 现在运行到图片的那个位置了，从0开始
	private Timer timer;
	private int autoCurrIndex = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		flag = true;
		System.out.println("MangguoFragment...onCreateView");
		return inflater.inflate(R.layout.main_mangguo, container, false);
	}

	@Override
	public void onStart()
	{

		super.onStart();
		activity = getActivity();
		timer = new Timer();
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
	public void onStop()
	{
		super.onStop();
		timer.cancel();
		flag = false;
	}

	private void init()
	{
		group = (ViewGroup) getActivity().findViewById(R.id.mangguo_viewGroup);
		viewPager = (ViewPager) getActivity().findViewById(R.id.mangguo_viewPager);
		height = DisPlayScreen.getHeight(activity);
		imgIdArray = new int[]
		{ R.drawable.mangguo_1, R.drawable.mangguo_2, R.drawable.mangguo_3, R.drawable.mangguo_4 };
		// 将点点加入到ViewGroup�?
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++)
		{
			ImageView imgeView = new ImageView(getActivity());
			imgeView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imgeView;
			if (i == 0)
			{
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else
			{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			group.addView(imgeView);

		}

		// 将图片装载到数组里去
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++)
		{
			ImageView imageView = new ImageView(getActivity());
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		viewPager.setAdapter(new MyAdapter());
		// 设置监听，主要是设置点点的背景
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height);
		viewPager.setLayoutParams(lp);
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
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}

			/**
			 * 设置选中的tip的背�?
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
		// viewPager.setCurrentItem((mImageViews.length) * 100);
		viewPager.setCurrentItem(0);
		timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				Message message = new Message();
				message.what = UPTATE_VIEWPAGER;
				System.out.println("autoCurrIndex..." + autoCurrIndex);
				if (autoCurrIndex == imgIdArray.length - 1)
				{
					autoCurrIndex = -1;
				}
				message.arg1 = ++autoCurrIndex;
				mHandler.sendMessage(message);

			}
		}, 5000, 5000);

	}

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
			((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}
	}

	private void listViewinit()
	{

		listview = (ListView) getActivity().findViewById(R.id.mangguo_list_pinzhong);

		listview.setAdapter(new MyListAdapter());
		listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				switch (position)
				{
				case 0:

					Intent intent = new Intent(getActivity(), MangguoActivity.class);
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

	public int dip2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
