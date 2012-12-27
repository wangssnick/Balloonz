
package com.wzm.balloonz;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.util.DisplayMetrics;
import android.view.*;


/**
 * @author wangzhimin
 *
 */
public class BallWelcomeView extends View {
	private BalloonzActivity balloonzActivity;
	
	private Bitmap bitmapBackGround; //����ͼƬ
	
	private Bitmap bitmapStartGame;
	private Bitmap bitmapQuitGame;
	
	private Paint paintPicture = new Paint();
	private Paint paintText = new Paint();
	
	private int showWidth = 480;
	private int showHeight = 800;
	
	private float touchX = 0;
	private float touchY = 0;
	
	public BallWelcomeView(Context context)
	{
		super(context);
		
		balloonzActivity = (BalloonzActivity)context;
		
		//��ȡ��ǰview�ߴ�
		showWidth = balloonzActivity.getWidth();
		showHeight = balloonzActivity.getHeight();
				
		//����ͼƬ��Դ
		LoadResources();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		this.setBackgroundColor(Color.WHITE);

		drawBackGroundBitmap(canvas);
		drawWelcomeMenu(canvas);		
		
		paintText.setTextSize(22);
		paintText.setColor(Color.BLUE);
		paintText.setTextAlign(Align.LEFT);

		canvas.drawText("x=" + touchX, 100, 50, paintText);
		canvas.drawText("y=" + touchY, 100, 100, paintText);
		
		//�������ߣ�������Ļ�ߴ�ͱ߽�
		int hMinus = 5;
		canvas.drawLine(0, 1, showWidth, 1, paintText);
		canvas.drawLine(0, showHeight-hMinus, showWidth, showHeight-hMinus, paintText);
		
		canvas.drawLine(0, showHeight/2, showWidth, showHeight/2, paintText);
		canvas.drawLine(showWidth/2, 0, showWidth/2, showHeight, paintText);

		/*
		myPaint.setTypeface(Typeface.MONOSPACE);	

		String strExitGame = "�˳���Ϸ";
		textMenuPaint.setTextSize(30);
		textMenuPaint.setColor(Color.RED);
		textMenuPaint.setTextAlign(Align.CENTER);
		
		canvas.drawText(strExitGame, showWidth/2, showHeight-20, textMenuPaint);
		*/
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		touchX = event.getX();
		touchY = event.getY();
		
		return true;
	}

	private void LoadResources()
	{
		//ͼƬ������
		BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
		bfoOptions.inScaled = false;

		bitmapBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_back, bfoOptions);

		bitmapStartGame = BitmapFactory.decodeResource(getResources(), R.drawable.start_game, bfoOptions);
		bitmapQuitGame = BitmapFactory.decodeResource(getResources(), R.drawable.quit_game, bfoOptions);
	}
	//��ʾ����ͼƬ
	private void drawBackGroundBitmap(Canvas canvas)
	{		
		canvas.drawBitmap(bitmapBackGround, 0, 0, paintPicture);
	}

	private void drawWelcomeMenu(Canvas canvas)
	{
		canvas.drawBitmap(bitmapStartGame, (showWidth-bitmapStartGame.getWidth())/2, 200, paintPicture);
		canvas.drawBitmap(bitmapQuitGame, (showWidth-bitmapQuitGame.getWidth())/2, 500, paintPicture);
	}
}
