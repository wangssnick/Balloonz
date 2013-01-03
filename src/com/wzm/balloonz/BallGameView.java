package com.wzm.balloonz;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class BallGameView extends View
{
	private BalloonzActivity balloonzActivity;
	private BallPool ballPool = null;

	public BallGameView(Context context)
	{
		super(context);		
		balloonzActivity = (BalloonzActivity) context;

		setBackgroundDrawable(getResources().getDrawable(R.drawable.game_back));
		
		ballPool = new BallPool(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		ballPool.onDraw(canvas);		
	}
	
	//����������
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			int touchX = (int) event.getX();
			int touchY = (int) event.getY();

            //ͨ��������꣬��λ���index,����
			ballPool.processTouchEvent(touchX, touchY);
		}

		return true;
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
	    if (keyCode == KeyEvent.KEYCODE_BACK &&
	    	event.getAction() == KeyEvent.ACTION_UP)
	    {
	    	balloonzActivity.processGameMsg(GameMsg.Msg_backtowelcome);   
	    }
	    return true; //���Ҫ�Լ�����,�ͷ���true
	}
	
}
