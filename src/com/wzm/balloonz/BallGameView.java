/**
 * 
 */
package com.wzm.balloonz;

import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.*;

/**
 * @author wangzhimin
 *
 */
public class BallGameView extends View {
	private Bitmap backBitmap;
	private Paint myPaint = new Paint();
	
	private int showWidth = 480;
	private int showHeight = 800;
	
	public BallGameView(Context context)
	{
		super(context);

		//ͼƬ������
		BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
		bfoOptions.inScaled = false;
		
		backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.king, bfoOptions);
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		//��ȡ��ǰview�ߴ�, �۳��˲˵�������ʾ����, ����������Ļ�ĳߴ�
		showWidth  = this.getWidth();
		showHeight = this.getHeight();
		
		//���ĵ㻭��ʮ��
		myPaint.setColor(Color.BLUE);
		
		canvas.drawLine(0, showHeight/2, showWidth, showHeight/2, myPaint);
		canvas.drawLine(showWidth/2, 0, showWidth/2, showHeight, myPaint);

		//������ʾ����ͼƬ
		int showPicX = (showWidth - backBitmap.getWidth()) / 2;
		int showPicY = (showHeight - backBitmap.getHeight()) / 2;

		canvas.drawBitmap(backBitmap, showPicX, showPicY, myPaint);
		
		//��ӡͼƬ�ߴ�
		myPaint.setTextSize(22);
		myPaint.setTypeface(Typeface.MONOSPACE);
		
		canvas.drawText("picx = " + showPicX, 1, 600, myPaint);
		canvas.drawText("picy = " + showPicY, 1, 650, myPaint);		

		String strPicWidth  = "pic width  = " + backBitmap.getWidth();
		String strPicHeight = "pic Height = " + backBitmap.getHeight();

		float pixelLength = myPaint.measureText(strPicWidth);
		float showX = (showWidth - pixelLength) / 2;
		canvas.drawText(strPicWidth, showX, 50, myPaint);
		canvas.drawText(strPicHeight, showX, 100, myPaint);
	}

}
