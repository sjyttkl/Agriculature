package com.sjyttkl.Agriculature.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

public class DisPlayScreen
{

	public static int getHeight(Activity activity)
	{
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		// 得的尺寸单位为px，即像素，而不是屏幕的绝对尺寸。
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		// 图片适配手机的高度
		return dip2px(activity, h_screen / 3);

	}

	public static int getHeight(FragmentActivity activity)
	{
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		// 得的尺寸单位为px，即像素，而不是屏幕的绝对尺寸。
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		// 图片适配手机的高度
		return (dip2px(activity, h_screen / 3));

	}

	public static int dip2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		System.out.println("scale...." + scale);
		return (int) (dipValue + 0.5f);
	}
}
