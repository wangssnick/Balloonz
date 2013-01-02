package com.wzm.balloonz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author wangzhimin
 * 
 */
public class BallWelcomeView extends View
{
	private BalloonzActivity balloonzActivity;

	private BitmapMenu menuStartGame;
	private BitmapMenu menuSoundOpen;
	private BitmapMenu menuSoundClose;
	private BitmapMenu menuQuitGame;
	
	private Paint paintPicture = new Paint();

	private int showWidth = 480;
	private int showHeight = 800;

	private Handler handlerWelcome = new Handler(new BallHandlerCallback());

	private MediaPlayer audioPlayer = null;
	
	public BallWelcomeView(Context context)
	{
		super(context);

		balloonzActivity = (BalloonzActivity) context;

		// ��ȡ��ǰview�ߴ�
		showWidth = balloonzActivity.getWidth();
		showHeight = balloonzActivity.getHeight();

		setBackgroundDrawable(getResources().getDrawable(R.drawable.welcome_back));
		
		// ������Ϸ��Դ
		InitGameResources();
		
		setFocusableInTouchMode(true);
	}

	private void InitGameResources()
	{
		// ͼƬ������
		BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
		bfoOptions.inScaled = false;

		int centerX = showWidth / 2;

		Bitmap bitmapStartGame = BitmapFactory.decodeResource(getResources(), R.drawable.start_game, bfoOptions);
		Rect start_rect = new Rect(centerX - bitmapStartGame.getWidth() / 2, 200, centerX + bitmapStartGame.getWidth() / 2 - 1, 200 + bitmapStartGame.getHeight() - 1);
		menuStartGame = new BitmapMenu(bitmapStartGame, start_rect);

		Bitmap bitmapSoundOpen = BitmapFactory.decodeResource(getResources(), R.drawable.sound_open, bfoOptions);
		Rect open_rect = new Rect(centerX - bitmapSoundOpen.getWidth() / 2, 300, centerX + bitmapSoundOpen.getWidth() / 2 - 1, 300 + bitmapSoundOpen.getHeight() - 1);
		menuSoundOpen = new BitmapMenu(bitmapSoundOpen, open_rect);
		
		Bitmap bitmapSoundClose = BitmapFactory.decodeResource(getResources(), R.drawable.sound_close, bfoOptions);
		Rect close_rect = new Rect(centerX - bitmapSoundClose.getWidth() / 2, 300, centerX + bitmapSoundClose.getWidth() / 2 - 1, 300 + bitmapSoundClose.getHeight() -1);
		menuSoundClose = new BitmapMenu(bitmapSoundClose, close_rect);
		
		Bitmap bitmapQuitGame = BitmapFactory.decodeResource(getResources(), R.drawable.quit_game, bfoOptions);
		Rect quit_rect = new Rect(centerX - bitmapQuitGame.getWidth() / 2, 500, centerX + bitmapQuitGame.getWidth() / 2 - 1, 500 + bitmapQuitGame.getHeight() - 1);
		menuQuitGame = new BitmapMenu(bitmapQuitGame, quit_rect);
		
		audioPlayer = MediaPlayer.create(balloonzActivity, R.raw.back_ground);	
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		drawWelcomeMenu(canvas);
	}

	// ��������, ���ݵ������,ȷ��ѡ��Ĳ˵�
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			int tmpX = (int) event.getX();
			int tmpY = (int) event.getY();

			if (menuStartGame.contains(tmpX, tmpY))
			{
				handlerWelcome.sendEmptyMessage(1);
			}
			else if (menuSoundOpen.contains(tmpX, tmpY))
			{
				handlerWelcome.sendEmptyMessage(2);
			}
			else if (menuQuitGame.contains(tmpX, tmpY))
			{
				handlerWelcome.sendEmptyMessage(5);
			}
		}

		return true;
	}

	private class BallHandlerCallback implements Callback
	{
		//����ǰ����Ĵ����¼�
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1: //��ʼ��Ϸ
				balloonzActivity.processTouchMsg(TouchMsg.Msg_startgame);
				break;

			case 2: //��������
				balloonzActivity.processTouchMsg(TouchMsg.Msg_sound);
				invalidate();
				if (balloonzActivity.getSound())
				{
				    try
					{
						audioPlayer.prepare();
						audioPlayer.start();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					audioPlayer.stop();
				}
				break;
				
			case 5: //�˳���Ϸ
				balloonzActivity.processTouchMsg(TouchMsg.Msg_quitgame);
				break;
			}

			return true;
		}
	}

	// ��ʾ��ʼ����˵�
	private void drawWelcomeMenu(Canvas canvas)
	{
		menuStartGame.onDraw(canvas, paintPicture);
		
		
		if (balloonzActivity.getSound())
		{
			menuSoundOpen.onDraw(canvas, paintPicture);
		}
		else
		{
			menuSoundClose.onDraw(canvas, paintPicture);
		}
		
		menuQuitGame.onDraw(canvas, paintPicture);
	}
}
