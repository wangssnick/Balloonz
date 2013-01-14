package com.wzm.balloonz;

import android.content.res.Resources;
import android.graphics.*;


import java.util.*;

public class BallPool
{
	private BallGameView gameView = null;
	
	private final int ROW_NUM    = 12;
	private final int COLUMN_NUM = 8;
	
	private final int ballWidth  = 50; //С��ĳߴ�
	private final int ballHeight = 50;

	ArrayList<Bitmap> bitmapCollection = new ArrayList<Bitmap>(); //���8�����bitmap���±��Ӧ������
	
	private Bitmap fireballBitmap;
	private ColorBall ballFireBall;
	
	private int gameLevel = 1; //��Ϸ�Ѷ�
	
	private int left = 0;
	private int top  = 0;
	
	private ColorBall[][] ballPool = new ColorBall[ROW_NUM][COLUMN_NUM];
	private Rect poolRect = null;
	private int num_of_killed = 0;//������������
	private ColorBall ballFocus = null;
	
	private Paint ballPaint = new Paint();
	private Random rand = new Random();
	
	public BallPool(BallGameView view, int level)
	{
		gameView = view;
		gameLevel = level;
		
		left = 40;
		top  = 100;
		
		LoadResources();
		InitBallPool();
		
		poolRect = new Rect(left, top, left + ballWidth * COLUMN_NUM, top + ballHeight * ROW_NUM);
	}
	private void LoadResources()
	{
		// ͼƬ������
		BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
		bfoOptions.inScaled = false;
		
		Resources res = gameView.getResources();
		
		bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.foot_ball, bfoOptions));	  //����	
		bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.basket_ball, bfoOptions));  //����
		bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.volley_ball, bfoOptions));  //����
		bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.bowling_ball, bfoOptions)); //������
		
		if (gameLevel >= 2)
		{
			bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.tennis_ball, bfoOptions));  //����
			bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.rugby_ball, bfoOptions));   //�����
		}
		
		if (gameLevel == 3)
		{
			bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.billiards_ball, bfoOptions)); //̨��,�ڰ�
		}
		fireballBitmap = BitmapFactory.decodeResource(res, R.drawable.fire_ball, bfoOptions);
		ballFireBall = new ColorBall(0xff, fireballBitmap);
	}
	private void InitBallPool()
	{
		for (int row = 0; row < ROW_NUM; ++row)
		{
			for (int column = 0; column < COLUMN_NUM; ++column)
			{				
				ballPool[row][column] = createColorBall();
			}
		}
	}
	
	public void onDraw(Canvas canvas)
	{
		for (int row = 0; row < ROW_NUM; ++row)
		{
			for (int column = 0; column < COLUMN_NUM; ++column)
			{
				int showX = left + column * ballWidth;
				int showY = top + row * ballHeight;
				
				if (ballPool[row][column] != null)
				{
					ballPool[row][column].onDraw(canvas, showX, showY, ballPaint);
				}
			}
		}
	}
	
	public void processTouchEvent(int x, int y)
	{
		if (poolRect.contains(x, y))
		{
			int rowIndex    = (y - top) / ballHeight; //���±�,����������
			int columnIndex = (x - left) / ballWidth;
			
			int numToKill = getNumToKill(rowIndex, columnIndex); //��֮ǰԤ��������,ֻҪ��һ����Ԥ��������1����
			
			if (numToKill > 1) //������ͬ�������,�����Ƿ�Ҫ����,ֻ�е�һ�������Ҫ�ж�
			{
				num_of_killed = 1;
				ballFocus = ballPool[rowIndex][columnIndex];
				killBall(rowIndex, columnIndex);
				gameView.postInvalidate();
				
				if (num_of_killed > 1) //������,����Ҫ���µ���λ��
				{				
					up2down();	
					right2left();
					fillRight();
				}
			}
		}
	}

	/* ����ӿں���. */
	public int getKillNum()
	{
		int num = num_of_killed;
		num_of_killed = 0;
		
		return num;
	}
	
	/* ˽�к��� */
	private ColorBall createColorBall()
	{
		int max_num = bitmapCollection.size();
		int ballIndex = rand.nextInt(max_num);
		
		return new ColorBall(ballIndex, bitmapCollection.get(ballIndex));
	}
	private void killBall(int rowIndex, int columnIndex)
	{
		ColorBall me = ballPool[rowIndex][columnIndex];
		if (me == null ||
			!me.equals(ballFocus))
		{
			return;
		}
		
		ballPool[rowIndex][columnIndex] = null; // �Ȱ��Լ�����,�����ھ��ڱȽϵ�ʱ��Ͳ���Ҫ����ı��
		num_of_killed++;

		if (columnIndex > 0) // ��
		{
			if (me.equals(ballPool[rowIndex][columnIndex - 1]))
			{
				killBall(rowIndex, columnIndex - 1); // �ݹ����
			}
		}

		if (columnIndex < COLUMN_NUM - 1) // ��
		{
			if (me.equals(ballPool[rowIndex][columnIndex + 1]))
			{
				killBall(rowIndex, columnIndex + 1);
			}
		}
		if (rowIndex > 0) // ��
		{
			if (me.equals(ballPool[rowIndex - 1][columnIndex]))
			{
				killBall(rowIndex - 1, columnIndex);
			}
		}
		if (rowIndex < ROW_NUM - 1) // ��
		{
			if (me.equals(ballPool[rowIndex + 1][columnIndex]))
			{
				killBall(rowIndex + 1, columnIndex);
			}
		}
	}
	
	private int getNumToKill(int rowIndex, int columnIndex)
	{
		int numToKill = 1;
		ColorBall focus = ballPool[rowIndex][columnIndex];
		
		if (focus != null)
		{
			// �ȿ�һ������������û����ͬ����
			if (columnIndex > 0) // ��
			{
				if (focus.equals(ballPool[rowIndex][columnIndex - 1]))
				{
					numToKill++;
				}
			}
			if (columnIndex < COLUMN_NUM - 1) // ��
			{
				if (focus.equals(ballPool[rowIndex][columnIndex + 1]))
				{
					numToKill++;
				}
			}
			if (rowIndex > 0) // ��
			{
				if (focus.equals(ballPool[rowIndex - 1][columnIndex]))
				{
					numToKill++;
				}
			}
			if (rowIndex < ROW_NUM - 1) // ��
			{
				if (focus.equals(ballPool[rowIndex + 1][columnIndex]))
				{
					numToKill++;
				}
			}
		}
		
		return numToKill;
	}
	
	private void up2down()
	{
		delay(200);
		for (int columnIndex = 0; columnIndex < COLUMN_NUM; ++columnIndex) //һ��һ�еĴ���
		{
			//������ð������,�ѿյĸ�Ų������ȥ
			for (int num = 0; num < ROW_NUM; ++num) //����
			{
				for (int rowIndex = ROW_NUM - 1; rowIndex > num; --rowIndex) //������һ�����账��
				{
					if (ballPool[rowIndex][columnIndex] == null)
					{
						ballPool[rowIndex][columnIndex] = ballPool[rowIndex - 1][columnIndex]; // ����Ųһ��
						ballPool[rowIndex - 1][columnIndex] = null;
					}
				}
			}
		}
		refresh();
	}
	
	//ĳһ�е�������һ��û��,������һ�ж�û����,�ұߵ�����������ƽ��
	private void right2left()
	{
		delay(200);
		for (int columnIndex = COLUMN_NUM-2; columnIndex >= 0; --columnIndex) //���ұ�һ�����账��
		{
			if (ballPool[ROW_NUM-1][columnIndex] == null)
			{
				for (int moveIndex = columnIndex; moveIndex < COLUMN_NUM-1; ++moveIndex) 
				{
					for(int row = 0; row < ROW_NUM; ++row)
					{
						ballPool[row][moveIndex] = ballPool[row][moveIndex+1];
						ballPool[row][moveIndex+1] = null;
					}
				}
			}
		}
		refresh();
	}
	private void fillRight()
	{
		delay(200);
		for (int columnIndex = 0; columnIndex < COLUMN_NUM; ++columnIndex)
		{
			if (ballPool[ROW_NUM-1][columnIndex] == null)
			{
				for (int row = 0; row < ROW_NUM; ++row)
				{
					ballPool[row][columnIndex] = createColorBall();
				}
				refresh();
				delay(100);
			}
		}
	}
	
	private void delay(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	private void refresh()
	{
		gameView.postInvalidate();
	}
}
