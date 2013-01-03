package com.wzm.balloonz;


import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.media.MediaPlayer;


public class BalloonzActivity extends Activity
{
	private BallWelcomeView ballWelcomeView;   //��ӭ����
	private BallGameView ballGameView = null; //��Ϸ����
	
	private boolean soundSwitch = false;
	private MediaPlayer audioPlayer = null;
	
	private int width = 480;
	private int height = 800;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		/*
		//ȫ������������ʾһ��״̬���ͱ�������Ȼ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);
        */

		initGame();
	}

//	public void onBackPressed()
//	{
//	
//	}
	public void processTouchMsg(TouchMsg msg)
	{
		switch(msg)
		{
		case Msg_startgame:
			if (ballGameView == null)
			{
				ballGameView = new BallGameView(this);
			}
			setContentView(ballGameView);
			break;
			
		case Msg_sound:
			soundSwitch = !soundSwitch;
			break;
			
		case Msg_quitgame:
			quitGame();
			break;
			
		case Msg_backtowelcome:
			setContentView(ballWelcomeView);
			break;
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public boolean getSound()
	{
		return soundSwitch;
	}
	public void playBackMusic()
	{
		if (soundSwitch)
		{
			try
			{
				audioPlayer.start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			audioPlayer.pause();
		}
	}

	//=====================================================
	private void initGame()
	{
		// ��ȡ������Ļ�ߴ�
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		
		ballWelcomeView = new BallWelcomeView(this);
		setContentView(ballWelcomeView);
		
		audioPlayer = MediaPlayer.create(this, R.raw.back_ground);
	}
	
	private void quitGame()
	{
		if (audioPlayer != null)
		{
			audioPlayer.stop();
			audioPlayer.release();
		}
		System.exit(0);
	}
}

