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
	private int gameLevel = 1; //��Ϸ�Ѷ�
	
	private int left = 0;
	private int top  = 0;
	
	private ColorBall[][] ballPool = new ColorBall[ROW_NUM][COLUMN_NUM];
	private Rect poolRect = null;
	private int num_of_same = 0;
	
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
	}
	private void InitBallPool()
	{
		int max_num = bitmapCollection.size();
		
		for (int row = 0; row < ROW_NUM; ++row)
		{
			for (int column = 0; column < COLUMN_NUM; ++column)
			{
				int ballIndex = rand.nextInt(max_num);
				
				ballPool[row][column] = new ColorBall(ballIndex, bitmapCollection.get(ballIndex));
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
			
			num_of_same = 1;
			killBall(rowIndex, columnIndex);
			gameView.invalidate();
			
			if (num_of_same > 1)
			{
				//Ӳ����
				long now = System.currentTimeMillis();
				for(;;)
				{
					long next = System.currentTimeMillis();
					if (next - now > 400)
					{
						break;
					}
				}
				up2down();
				right2left();
			}
		}
	}

	/* ����ӿں���. */
	public int getKillNum()
	{
		return num_of_same;
	}
	
	/* ˽�к��� */
	private boolean killBall(int rowIndex, int columnIndex)
	{
		ColorBall focus = ballPool[rowIndex][columnIndex];
		if (focus == null)
		{
			return true;
		}
		//�ȿ�һ������������û����ͬ����
		if (columnIndex > 0) //��
		{	
			if (focus.equals(ballPool[rowIndex][columnIndex-1]))
			{
				num_of_same++;
			}
		}
		if (columnIndex < COLUMN_NUM-1) //��
		{
			if (focus.equals(ballPool[rowIndex][columnIndex+1]))
			{
				num_of_same++;
			}
		}
		if (rowIndex > 0) //��
		{
			if (focus.equals(ballPool[rowIndex-1][columnIndex]))
			{
				num_of_same++;
			}
		}
		if (rowIndex < ROW_NUM-1) //��
		{
			if (focus.equals(ballPool[rowIndex+1][columnIndex]))
			{
				num_of_same++;
			}
		}
		
		if (num_of_same > 1) //������ͬ�������,�����Ƿ�Ҫ����
		{
			ballPool[rowIndex][columnIndex] = null; //�Ȱ��Լ�����,�����ھ��ڱȽϵ�ʱ��Ͳ���Ҫ����ı��
		
			if (columnIndex > 0) //��
			{	
				if (focus.equals(ballPool[rowIndex][columnIndex-1]))
				{
					killBall(rowIndex, columnIndex-1); //�ݹ����
				}
			}
			
			if (columnIndex < COLUMN_NUM - 1) // ��
			{
				if (focus.equals(ballPool[rowIndex][columnIndex + 1]))
				{
					killBall(rowIndex, columnIndex+1);
				}
			}
			if (rowIndex > 0) //��
			{
				if (focus.equals(ballPool[rowIndex-1][columnIndex]))
				{
					killBall(rowIndex-1, columnIndex);
				}
			}
			if (rowIndex < ROW_NUM-1) //��
			{
				if (focus.equals(ballPool[rowIndex+1][columnIndex]))
				{
					killBall(rowIndex+1, columnIndex);
				}
			}
		}		
		
		return true;
	}
	
	private void up2down()
	{
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
		
		gameView.invalidate(poolRect);
	}
	
	//ĳһ�е�������һ��û��,������һ�ж�û����,�ұߵ�����������ƽ��
	private void right2left()
	{
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
		
		gameView.invalidate(poolRect);
	}
}
