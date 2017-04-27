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
	// װ��������
	private ImageView[] tips;

	// װImageView����
	private ImageView[] mImageViews;
	/**
	 * ͼƬ��Դid
	 */
	private int[] imgIdArray;
	/**
	 * ���
	 */
	private ViewGroup group;

	private ListView listview;

	private static int[] imageViews =
	{ R.drawable.pingzhong_lizhi_01, R.drawable.pingzhong_lizhi_02, R.drawable.pingzhong_lizhi_03, R.drawable.pingzhong_lizhi_02 };
	private static String[] lizhi_name =
	{ "�޺���֦ ", "������", "��ζ��֦" };
	private static String[] lizhi_description =
	{ "��ϸ����Ŵ���١���֦�ǹ㶫����������֦Ʒ��֮һ��Ŵ������֦���󣬵�����20��27.6�ˣ������Σ���Ƥ�ʺ�ɫ���ϱ�������Ƭ����͹�𣬳������Σ��������У���Ƭ��ƽ��������һ������ͻ�𣬹�����Բ�� ",
			"��ϸ�� ������Ц�� �����󣬵�����30�����ϣ���Բ�Σ�Ƥɫ���죬ɫ�����ޣ����Ƥ����С������ϸ����֬��ˬ������ζ�������֭��������Ц��ֲ��������ʢ���Ȳ��߲�����С�겻���ԣ���������Ʒ�֡�",
			"��ϸ������ζ��Ϊ�㶫��ͳ������֦Ʒ�֣��Թ����С����ζ���㣬�ڸм��Ѷ�����������ζ����ɫ�ʺ����ۣ��������ɫ����͸���У�����ˬ�࣬ζ���𣬾߹��㣬�����Թ�����Լ19�����ϣ������ʸ�,ƤӲ�������ˡ�" };

	// �����ֻ��߶�

	private int height;
	private FragmentActivity activity;
	private boolean flag = false;
	// �������е�ͼƬ���Ǹ�λ���ˣ���0��ʼ
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
		// ����ͼƬ��ԴID//ż����ͼƬ��������������
		imgIdArray = new int[]
		{ R.drawable.lizhi_1, R.drawable.lizhi_2, R.drawable.lizhi_3, R.drawable.lizhi_4 };
		// �������뵽ViewGroup��
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

		// ��ͼƬװ�ص�������
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
		// ���ü�������Ҫ�����õ��ı���
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
			 * ����ѡ�е�tip�ı���
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
		// ����ViewPager��Ĭ����, ����Ϊ���ȵ�100���������ӿ�ʼ�������󻬶�
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

	// ��ʱ�ֲ�ͼƬ����Ҫ�����߳������޸� UI
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
					// false ����ĩҳ������ҳ�ǣ�����ʾ��ҳ����Ч����
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
		 * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ�
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
					Toast.makeText(getActivity(), "���������..", 0).show();
					break;
				case 2:
					Toast.makeText(getActivity(), "���������..", 0).show();
					break;
				case 3:
					Toast.makeText(getActivity(), "���������..", 0).show();
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
