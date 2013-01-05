package com.wzm.balloonz;

import android.content.Context;
import android.graphics.*;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class BallGameView extends View
{
	private BalloonzActivity balloonzActivity;
	private BallPool ballPool = null;
	
	private Paint textPaint = new Paint();
	private int score = 0;
	
	public BallGameView(Context context)
	{
		super(context);		
		balloonzActivity = (BalloonzActivity) context;
		
		setBackgroundDrawable(getResources().getDrawable(R.drawable.game_back));
		textPaint.setColor(Color.WHITE);
		
		ballPool = new BallPool(this, balloonzActivity.getLevel());
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		ballPool.onDraw(canvas);
		canvas.drawText("����:" + score, 10, 750, textPaint);
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
			
			int killNum = ballPool.getKillNum();
			
			if (killNum >= 2)
			{
				score = fibonacci(killNum);				
			}
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
	
	
	/* ˽�к���. */
	private int fibonacci(int n)
	{		
		double Root_of_Five = Math.sqrt(5);
		
		double result = (Math.pow((1 + Root_of_Five)/2, n) - Math.pow((1-Root_of_Five)/2, n)) / Root_of_Five; 
		
		return (int)result;
	}	
}
