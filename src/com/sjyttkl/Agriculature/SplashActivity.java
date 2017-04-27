package com.sjyttkl.Agriculature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sjyttkl.Agriculature.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity
{
	protected static final String TAG = "MainActivity";
	protected static final int SHOW_UPDATE_DIALOG = 0;
	protected static final int ENTENT = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	private TextView tv_update_info;
	/**
	 * 新版本下载链接
	 */
	private String apkurl;
	private SharedPreferences sp;
	private String description;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = sp.getBoolean("update", false);

		/*
		 * 创建快捷图标
		 */
		installShortCut();

		// 拷贝数据库的功能
		//copyDB("power.db");
		if (update)
		{

			// 检查升级
			//checkUpdate();
		} else
		{
			// 自动升级已经关闭。。
			handler.postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					// 进入主页面
					enterHome();
				}

			}, 2000);
		}

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);// 0.2的透明度到 1.0的透明度
		aa.setDuration(500);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
	}

	/**
	 * path 把address.db这个数据库拷贝到data/data/<包名>/files/address.db
	 */
	private void copyDB(String filename)
	{
		// "data/data/com.sjyttkl.mobliesafe/files/address.db";
		// 只要你拷贝了一次，我就不要你再拷贝了
		try
		{
			InputStream is = getAssets().open(filename);
			File file = new File(getFilesDir(), filename);
//			if (file.exists() && file.length() > 0)
//			{
//				// 已经存在了，不需要再拷贝了
//				Log.i(TAG, "正常了，就不需要拷贝了");
//			} else
//			{

				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1)
				{
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
//			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void installShortCut()
	{
		boolean shortcut = sp.getBoolean("shortcut", false);
		if (shortcut)
		{
			return;
		}
		Editor editor = sp.edit();

		// 发送广播的意图，大吼一声告诉桌面，要创建的快捷图标
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "充电云");
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		// 桌面点击图标第对应的意图
		Intent shortcutIntent = new Intent();
		shortcutIntent.setAction("android.intent.action.MAIN");
		shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		shortcutIntent.setClassName(getPackageName(), "com.gxdx.power.MainActivity");
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		sendBroadcast(intent);
		editor.putBoolean("shortcut", true);
		editor.commit();

	}

	private void enterHome()
	{
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// 关闭当前页面
		finish();
	}

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{

			super.handleMessage(msg);
			switch (msg.what)
			{
			case SHOW_UPDATE_DIALOG:// 显示升级的对话框
				Log.i(TAG, "显示升级的对话框");
				ShowUpdateDialog();
				break;
			case ENTENT:// 进入主页面
				enterHome();
				break;
			case URL_ERROR:// URL错误
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();
				break;
			case NETWORK_ERROR:// 网络异常

				enterHome();
				Toast.makeText(getApplicationContext(), "网络异常", 0).show();
				break;
			case JSON_ERROR:// json解析出错

				enterHome();
				Toast.makeText(getApplicationContext(), " json解析出错", 0).show();
				break;

			default:
				break;
			}
		}

	};

//	private void checkUpdate()
//	{
//		new Thread()
//		{
//			public void run()
//			{
//				long startTime = System.currentTimeMillis();
//				Message msg = Message.obtain();
//				try
//				{
//					URL url = new URL(getString(R.string.serverurl1));
//					// 联网
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setRequestMethod("GET");
//					conn.setConnectTimeout(4000);
//					int code = conn.getResponseCode();
//					if (code == 200)
//					{ // 联网成功
//						InputStream is = conn.getInputStream();
//						// 把流转成String
//						String result = StreamTools.readFromStream(is);
//						Log.i(TAG, "联网成功了" + result);
//						// 得到服务器的版本信息
//						JSONObject obj = new JSONObject(result);
//						String version = (String) obj.get("version");
//
//						description = (String) obj.get("description");
//						apkurl = (String) obj.get("apkurl");
//						// 校验
//						if (getVersionName().equals(version))
//						{
//							// 版本一致，没有新版本，进入主页面
//							msg.what = ENTENT;
//						} else
//						{
//							// 有新版本弹出一个升级对话框
//							msg.what = SHOW_UPDATE_DIALOG;
//
//						}
//
//					}
//
//				} catch (MalformedURLException e)
//				{
//					msg.what = URL_ERROR;
//					e.printStackTrace();
//				} catch (IOException e)
//				{
//					msg.what = NETWORK_ERROR;
//					e.printStackTrace();
//
//				} catch (JSONException e)
//				{
//					e.printStackTrace();
//					msg.what = JSON_ERROR;
//				} finally
//				{
//					long endTime = System.currentTimeMillis();
//					long dTime = endTime - startTime;
//					if (dTime < 2000)
//					{
//						try
//						{
//							Thread.sleep(2000 - dTime);
//						} catch (InterruptedException e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//					handler.sendMessage(msg);
//
//				}
//			}
//		}.start();
//	}

	// 弹出升级对话框
	private void ShowUpdateDialog()
	{
		// this == Activity.this
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示升级");
		builder.setMessage(description);
		// builder.setCancelable(false);//点击返回键是不起作用的,强制升级
		builder.setOnCancelListener(new OnCancelListener()
		{

			@Override
			public void onCancel(DialogInterface dialog)
			{
				enterHome();
				dialog.dismiss();
			}
		});
//		builder.setPositiveButton("立刻升级", new OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				// 下载APK，并且替换安装
//				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//				{
//					System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
//					// sdcard存在
//					FinalHttp finals = new FinalHttp();
//					finals.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/mobilesafe2.0.apk",
//							new AjaxCallBack<File>()
//							{
//
//								@Override
//								public void onFailure(Throwable t, int errorNo, String strMsg)
//								{
//									t.printStackTrace();
//									Toast.makeText(getApplicationContext(), "下载失败", 1).show();
//									super.onFailure(t, errorNo, strMsg);
//								}
//
//								@Override
//								public void onLoading(long count, long current)
//								{
//									super.onLoading(count, current);
//									tv_update_info.setVisibility(View.VISIBLE);
//									// 当前下载进度
//									int progress = (int) (current * 100 / count);
//									tv_update_info.setText("加载进度：" + progress + "%");
//								}
//
//								@Override
//								public void onSuccess(File t)
//								{
//									// TODO Auto-generated method stub
//									super.onSuccess(t);
//									installAPK(t);
//								}
//
//								private void installAPK(File t)
//								{
//									Intent intent = new Intent();
//									intent.setAction("android.intent.action.VIEW");
//									intent.addCategory("android.intent.category.DEFAULT");
//									intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
//									startActivity(intent);
//								}
//							});
//				} else
//				{
//					Toast.makeText(getApplicationContext(), "没有sdcard,请安装后在试一次", 0).show();
//
//				}
//
//			}
//		});
		builder.setNegativeButton("下次再说", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				enterHome();

			}
		});
		builder.show();
	}

	private String getVersionName()
	{
		PackageManager pm = getPackageManager();
		try
		{
			// 得到指定apk的功能清单文件
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		// 用来管理手机的apks
		return null;

	}


}
