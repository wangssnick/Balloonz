package com.wzm.balloonz;

import android.content.Context;
import android.graphics.*;
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
	
	BitmapMenuGroup menuGroup = null;
	
	private Paint paintPicture = new Paint();

	private int showWidth = 480;
	private int showHeight = 800;

	private Handler handlerWelcome = new Handler(new BallHandlerCallback());
	
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
		menuGroup = new BitmapMenuGroup(this, showWidth);
			
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		menuGroup.onDraw(canvas, paintPicture);
	}

	// ��������, ���ݵ������,ȷ��ѡ��Ĳ˵�
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			int tmpX = (int) event.getX();
			int tmpY = (int) event.getY();

			menuGroup.processTouchEvent(handlerWelcome, tmpX, tmpY);
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
				balloonzActivity.playBackMusic();
				break;
				
			case 5: //�˳���Ϸ
				balloonzActivity.processTouchMsg(TouchMsg.Msg_quitgame);
				break;
			}

			return true;
		}
	}

	public boolean getSoundSwitch()
	{
		return balloonzActivity.getSound();
	}
}
