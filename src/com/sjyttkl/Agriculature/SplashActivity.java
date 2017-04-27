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
	 * �°汾��������
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
		 * �������ͼ��
		 */
		installShortCut();

		// �������ݿ�Ĺ���
		//copyDB("power.db");
		if (update)
		{

			// �������
			//checkUpdate();
		} else
		{
			// �Զ������Ѿ��رա���
			handler.postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					// ������ҳ��
					enterHome();
				}

			}, 2000);
		}

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);// 0.2��͸���ȵ� 1.0��͸����
		aa.setDuration(500);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
	}

	/**
	 * path ��address.db������ݿ⿽����data/data/<����>/files/address.db
	 */
	private void copyDB(String filename)
	{
		// "data/data/com.sjyttkl.mobliesafe/files/address.db";
		// ֻҪ�㿽����һ�Σ��ҾͲ�Ҫ���ٿ�����
		try
		{
			InputStream is = getAssets().open(filename);
			File file = new File(getFilesDir(), filename);
//			if (file.exists() && file.length() > 0)
//			{
//				// �Ѿ������ˣ�����Ҫ�ٿ�����
//				Log.i(TAG, "�����ˣ��Ͳ���Ҫ������");
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

		// ���͹㲥����ͼ�����һ���������棬Ҫ�����Ŀ��ͼ��
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�����");
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		// ������ͼ��ڶ�Ӧ����ͼ
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
		// �رյ�ǰҳ��
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
			case SHOW_UPDATE_DIALOG:// ��ʾ�����ĶԻ���
				Log.i(TAG, "��ʾ�����ĶԻ���");
				ShowUpdateDialog();
				break;
			case ENTENT:// ������ҳ��
				enterHome();
				break;
			case URL_ERROR:// URL����
				enterHome();
				Toast.makeText(getApplicationContext(), "URL����", 0).show();
				break;
			case NETWORK_ERROR:// �����쳣

				enterHome();
				Toast.makeText(getApplicationContext(), "�����쳣", 0).show();
				break;
			case JSON_ERROR:// json��������

				enterHome();
				Toast.makeText(getApplicationContext(), " json��������", 0).show();
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
//					// ����
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setRequestMethod("GET");
//					conn.setConnectTimeout(4000);
//					int code = conn.getResponseCode();
//					if (code == 200)
//					{ // �����ɹ�
//						InputStream is = conn.getInputStream();
//						// ����ת��String
//						String result = StreamTools.readFromStream(is);
//						Log.i(TAG, "�����ɹ���" + result);
//						// �õ��������İ汾��Ϣ
//						JSONObject obj = new JSONObject(result);
//						String version = (String) obj.get("version");
//
//						description = (String) obj.get("description");
//						apkurl = (String) obj.get("apkurl");
//						// У��
//						if (getVersionName().equals(version))
//						{
//							// �汾һ�£�û���°汾��������ҳ��
//							msg.what = ENTENT;
//						} else
//						{
//							// ���°汾����һ�������Ի���
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

	// ���������Ի���
	private void ShowUpdateDialog()
	{
		// this == Activity.this
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ����");
		builder.setMessage(description);
		// builder.setCancelable(false);//������ؼ��ǲ������õ�,ǿ������
		builder.setOnCancelListener(new OnCancelListener()
		{

			@Override
			public void onCancel(DialogInterface dialog)
			{
				enterHome();
				dialog.dismiss();
			}
		});
//		builder.setPositiveButton("��������", new OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				// ����APK�������滻��װ
//				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//				{
//					System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
//					// sdcard����
//					FinalHttp finals = new FinalHttp();
//					finals.download(apkurl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/mobilesafe2.0.apk",
//							new AjaxCallBack<File>()
//							{
//
//								@Override
//								public void onFailure(Throwable t, int errorNo, String strMsg)
//								{
//									t.printStackTrace();
//									Toast.makeText(getApplicationContext(), "����ʧ��", 1).show();
//									super.onFailure(t, errorNo, strMsg);
//								}
//
//								@Override
//								public void onLoading(long count, long current)
//								{
//									super.onLoading(count, current);
//									tv_update_info.setVisibility(View.VISIBLE);
//									// ��ǰ���ؽ���
//									int progress = (int) (current * 100 / count);
//									tv_update_info.setText("���ؽ��ȣ�" + progress + "%");
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
//					Toast.makeText(getApplicationContext(), "û��sdcard,�밲װ������һ��", 0).show();
//
//				}
//
//			}
//		});
		builder.setNegativeButton("�´���˵", new OnClickListener()
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
			// �õ�ָ��apk�Ĺ����嵥�ļ�
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		// ���������ֻ���apks
		return null;

	}


}
