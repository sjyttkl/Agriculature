package com.sjyttkl.Agriculature.Fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sjyttkl.Agriculature.R;
import com.sjyttkl.Agriculature.util.DisPlayScreen;

public class HomeFragment extends Fragment implements OnPageChangeListener
{
	protected static final int UPTATE_VIEWPAGER = 0;

	// final Activity activity = getActivity();

	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;

	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;

	/**
	 * 图片资源id
	 */
	private int[] imgIdArray;
	ViewGroup group;

	private FragmentActivity activity;
	private WebView webView;

	// 适配手机高度

	int height;
	private boolean flag = false;
	// 现在运行到图片的那个位置了，从0开始
	private Timer timer;
	private int autoCurrIndex = 0;

	@Override
	public void onAttach(Activity activity)
	{
		System.out.println("onAttach...HomeFragment");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		System.out.println("onCreate...HomeFragment");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		System.out.println("onCreateView...HomeFragment");
		return inflater.inflate(R.layout.main_home, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		System.out.println("onActivityCreated...HomeFragment");
		flag = true;
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart()
	{
		activity = this.getActivity();
		timer = new Timer();
		System.out.println("onStart...HomeFragment");
		super.onStart();
		if (flag == true && group == null)
		{
			init();

		} else if (flag == true)
		{
			init();
		}
		flag = false;
		initwebView();

	}

	@Override
	public void onPause()
	{
		System.out.println("onPause...HomeFragment");
		super.onPause();
	}

	@SuppressLint("NewApi")
	private void initwebView()
	{
		webView = (WebView) activity.findViewById(R.id.webview);
		// 设置WebView属性，能够执行Javascript脚本
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSupportZoom(true);
		webSettings.setDisplayZoomControls(true);
		webSettings.setUseWideViewPort(true);
		webView.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{

				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				activity.setProgress(progress * 1000);
			}
		});
		webView.setWebViewClient(new HelloWebViewClient());
		// 加载需要显示的网页
		webView.loadUrl("http://nxy.gxu.edu.cn/");

	}

	@Override
	public void onResume()
	{

		activity = this.getActivity();
		System.out.println("onResume...HomeFragment");
		super.onResume();
	}

	@Override
	public void onStop()
	{
		System.out.println("onStop...HomeFragment");
		flag = false;
		timer.cancel();
		super.onStop();

	}

	// 获取activity只能放在onstart()方法中
	public void init()
	{
		group = (ViewGroup) activity.findViewById(R.id.main_viewGroup);
		viewPager = (ViewPager) activity.findViewById(R.id.main_viewPager);
		height = DisPlayScreen.getHeight(activity);
		// 载入图片资源ID
		imgIdArray = new int[]
		{ R.drawable.item11, R.drawable.item12, R.drawable.item13, R.drawable.item14, R.drawable.item15, R.drawable.item16, R.drawable.item17,
				R.drawable.item18 };

		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		ImageView imageViewtip = null;
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
		ImageView imageView = null;

		for (int i = 0; i < mImageViews.length; i++)
		{

			imageView = new ImageView(activity);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);

		}

		// 设置Adapter
		viewPager.setAdapter(new MyAdapter());

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height);
		viewPager.setLayoutParams(lp);
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
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
				if (autoCurrIndex == mImageViews.length - 1)
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

	// 设置自动轮播图片，5s后执行，周期是5s

	/**
	 * 
	 * @author xiaodong
	 *
	 */
	class MyAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			return mImageViews.length;// Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object)
		{
			((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position)
		{
			// position是从1开始的
			// autoCurrIndex = position;
			Log.i("position", position + "");
			((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0)
	{

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2)
	{

	}

	@Override
	public void onPageSelected(int arg0)
	{
		setImageBackground(arg0 % mImageViews.length);
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

	private class HelloWebViewClient extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			view.loadUrl(url);
			return true;

		}

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
		{
			Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		}
	}
}