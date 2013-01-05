package com.wzm.balloonz;


import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.media.MediaPlayer;


public class BalloonzActivity extends Activity
{
	private BallWelcomeView ballWelcomeView;   //��ӭ����
	private BallGameView ballGameView = null; //��Ϸ����
	
	private boolean soundSwitch = false;
	private int gameDifficultyLevel = 1;
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
		if (audioPlayer == null)
		{
			WzmLog.log("initGame audioPlayer == null.");
		}
	}
	
	/* �¼���Ӧ���� */
	public void processGameMsg(GameMsg msg)
	{
		switch(msg)
		{
		case Msg_startgame:
			ballGameView = new BallGameView(this);
			setContentView(ballGameView);
			break;
			
		case Msg_sound:
			soundSwitch = !soundSwitch;
			break;
			
		case Msg_level:
			gameDifficultyLevel++;
			if (gameDifficultyLevel > 3)
			{
				gameDifficultyLevel = 1;
			}
			break;
			
		case Msg_quitgame:
			finish();
			break;
			
		case Msg_backtowelcome:
			setContentView(ballWelcomeView);
			break;
		}
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
	    if (keyCode == KeyEvent.KEYCODE_BACK &&
	    	event.getAction() == KeyEvent.ACTION_UP)
	    {
	    	finish();   
	    }
	    return true;
	}
	
	public void onDestroy()
	{
		if (audioPlayer != null)
		{
			audioPlayer.stop();
			audioPlayer.release();
		}
		
		super.onDestroy();
	}
	/* ����ӿں���. */
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
				if (audioPlayer == null)
				{
					WzmLog.log("audioPlayer == null.");
					return;
				}
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
	public int getLevel()
	{
		return gameDifficultyLevel;
	}
	/* ˽�к��� */
	
}

