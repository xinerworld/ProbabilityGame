package com.xiner.game.ball;

import com.xiner.game.ball.Ball.EBallColor;
import com.xiner.game.data.ReturnCode;

/***
 * double ball
 * @author Moon
 */
public class DoubleBall
{
	/**
	 * all red ball
	 */
	private Ball[] m_RedBall;
	/**
	 * blue ball
	 */
	private Ball m_BlueBall;
	/**
	 * luck ball
	 */
	private Ball m_LuckBall;
	/**
	 * red ball size
	 */
	public static final int m_nRedSize = 6;
	/**
	 * red ball count
	 */
	private int m_nRedCount = 0;
	
	public DoubleBall()
	{
		m_RedBall = new Ball[m_nRedSize];
		m_BlueBall = null;
		m_LuckBall = null;
		m_nRedCount = 0;
	}
	
	/**
	 * create double ball
	 */
	public void createDoubleBall()
	{
		//create red ball
		for (int i = 0; i < m_nRedSize; i++)
		{
			Ball ball = new Ball(EBallColor.s_eColorRed);
	        
	        int count = 0;
	        
	        while (insertRedBall(ball) == ReturnCode.RED_BALL_CREAT_ERROR_EQUAL)
			{//if ball num is have, update ball num
	        	ball.updateBallNum();
				count++;
				System.out.println(i+"   count: "+count);
			}
		}
		
		//create blue ball
		Ball ball = new Ball(EBallColor.s_eColorBlue);
		setBlueBall(ball);
	}
	
	/**
	 * insert red ball by order
	 * @param ball: ball
	 * @return: insert succed or error
	 */
	public int insertRedBall(Ball ball)
	{
		int retCode = ReturnCode.RED_BALL_CREAT_OK;
		int i = 0;
		
		for (i = 0; i < m_nRedSize; i++)
		{
			if (m_RedBall[i] == null)
			{
				m_RedBall[i] = ball;
				
				m_nRedCount++;
				retCode = ReturnCode.RED_BALL_CREAT_OK;
				break;
			}
			
			if (m_RedBall[i].m_nBallNum > ball.m_nBallNum)
			{//if old > new, old front insert new
				for (int j = m_nRedSize-2; j >= i; j--)
				{//form last 2 start
					if (m_RedBall[j] != null)
					{
						m_RedBall[j+1] = m_RedBall[j];
					}
				}
				
				m_RedBall[i] = ball;
				
				m_nRedCount++;
				retCode = ReturnCode.RED_BALL_CREAT_OK;
				break;
			}
			else if (m_RedBall[i].m_nBallNum == ball.m_nBallNum)
			{//if new == old, return wrong
				retCode = ReturnCode.RED_BALL_CREAT_ERROR_EQUAL;
				break;
			}
		}
		
		if (m_nRedCount == m_nRedSize)
		{//if red full
			retCode = ReturnCode.RED_BALL_CREAT_FULL;
		}
		
		return retCode;
	}

	/**
	 * red ball is full
	 * @return: the flag of full
	 */
	public int isRedBallFull()
	{
		return m_nRedCount == m_nRedSize ? ReturnCode.RED_BALL_CREAT_FULL : ReturnCode.RED_BALL_CREAT_NOT_FULL;
	}
	
	/**
	 * set double ball by int array
	 * @param anDoubleBall
	 */
	public void setDoubleBall(int[] anDoubleBall)
	{
		m_nRedCount = 0;
		int size = anDoubleBall.length;
		
		for (int i = 0; i < size; i++)
		{
			if (i < 6)
			{//0,1,2,3,4,5red ball
				m_RedBall[i] = new Ball(EBallColor.s_eColorRed, anDoubleBall[i]);
				
				m_nRedCount++;
			}
			else
			{
				if (i == 6)
				{//6 blue ball
					m_BlueBall = new Ball(EBallColor.s_eColorBlue, anDoubleBall[i]);
				}
				else if (i == 7)
				{//7 luck ball
					m_LuckBall = new Ball(EBallColor.s_eColorBlue, anDoubleBall[i]);
					m_LuckBall.changeBallType(Ball.s_nBallType_LUCK);
				}
			}
		}
	}	
	
	/**
	 * get red ball by position
	 * @param position
	 * @return: red ball
	 */
	public Ball getRedBall(int position)
	{
		return m_RedBall[position];
	}
	
	/**
	 * set red ball at position
	 * @param ball
	 * @param position
	 */
	public void setRedBall(Ball ball, int position)
	{
		if (position >= 0 && position < m_nRedSize)
		{
			m_RedBall[position] = ball;
		}
	}
	
	/**
	 * get blue ball
	 * @return: blue ball
	 */
	public Ball getBlueBall()
	{
		return m_BlueBall;
	}
	
	/**
	 * set blue ball
	 * @param ball
	 */
	public void setBlueBall(Ball ball)
	{
		m_BlueBall = ball;
	}

	/**
	 * get Luck ball
	 * @return: blue ball
	 */
	public Ball getLuckBall()
	{
		return m_LuckBall;
	}
	
	/**
	 * set Luck ball
	 * @param ball
	 */
	public void setLuckBall(Ball ball)
	{
		m_LuckBall = ball;
	}
	
	/**
	 * get double ball
	 * @return: ball array of double ball
	 */
	public Ball[] getDoubleBall()
	{
		Ball[] ball = new Ball[m_nRedSize+1];
		
		for (int i = 0; i < m_nRedSize; i++)
		{
			ball[i] = m_RedBall[i];
		}
		
		ball[m_nRedSize] = m_BlueBall;
		
		return ball;
	}
	
	/**
	 * get all red ball
	 * @return: all red ball
	 */
	public Ball[] getRedBall()
	{
		Ball[] ball = new Ball[m_nRedSize+1];
		
		for (int i = 0; i < m_nRedSize; i++)
		{
			ball[i] = m_RedBall[i];
		}
		
		return ball;
	}
	
	/**
	 * get red count
	 * @return: red count
	 */
	public int getRedCount()
	{
		return m_nRedCount;
	}
	
	/**
	 * paint double ball num
	 */
	public void paintBall()
	{
		StringBuffer ballStr = new StringBuffer("red: ");
		
		for (int i = 0; i < m_RedBall.length; i++)
		{
			if (m_RedBall[i] == null)
			{
				ballStr.append("NA");
			}
			else
			{
				if (m_RedBall[i].m_nBallNum < 10)
				{
					ballStr.append(String.format("%02d", m_RedBall[i].m_nBallNum));     
				}
				else
				{
					ballStr.append(m_RedBall[i].m_nBallNum);
				}
			}
			
			ballStr.append(" ");
		}
		
		ballStr.append("blue: ");
		
		if (m_BlueBall == null)
		{
			ballStr.append("NA");
		}
		else
		{
			if (m_BlueBall.m_nBallNum < 10)
			{
				ballStr.append(String.format("%02d", m_BlueBall.m_nBallNum));     
			}
			else
			{
				ballStr.append(m_BlueBall.m_nBallNum);
			}
		}
				
		System.out.println(ballStr.toString());
	}
}
