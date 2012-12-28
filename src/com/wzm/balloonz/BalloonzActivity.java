package com.wzm.balloonz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.*;
import android.util.DisplayMetrics;
import android.widget.*;
import android.graphics.Color;


public class BalloonzActivity extends Activity {

	private BallWelcomeView ballWelcomeView;
	
	private Handler handler;
	
	private int width = 480;
	private int height = 800;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*
		//ȫ������������ʾһ��״̬���ͱ�������Ȼ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);
        */
		//��ȡ������Ļ�ߴ�
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		
		ballWelcomeView = new BallWelcomeView(this);
		
		setContentView(ballWelcomeView);
	}
	
	public void transVew(View view)
	{
		setContentView(view);
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}
