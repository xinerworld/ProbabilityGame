package com.xiner.game.ball;

import java.util.Random;

/**
 * ball
 * @author Moon
 */
public class Ball
{
	/**
	 * ball color enum
	 * @author Moon
	 */
	public static enum EBallColor
	{
		s_eColorRed(0xFFFF0000),
		s_eColorBlue(0xFF0000FF);
		
		private int value;

		private EBallColor(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
	}
	
	/**
	 * pu tu ball type = 0x1
	 */
	public static final int s_nBallType_COMMON = 0x1;
	/**
	 * luck ball type = 0x2
	 */
	public static final int s_nBallType_LUCK = 0x2;
	/**
	 *  red ball range 1~33
	 */
	public static final int s_nRedBall_Range = 33;
	/**
	 * blue ball range 1~16
	 */
	public static final int s_nBlueBall_Range = 16;
	/**
	 * ball color. default color is red
	 */
	public EBallColor m_eBallColor = EBallColor.s_eColorRed;
	/**
	 * ball number. default number is 0
	 */
	public int m_nBallNum = 0;
	/**
	 * ball type. default type is pu tong
	 */
	private int m_nBallType = s_nBallType_COMMON;
	
	public Ball()
	{
	}

	public Ball(EBallColor eBallColor)
	{
		createBall(eBallColor);
	}
	
	public Ball(EBallColor eBallColor, int nBallNum)
	{
		m_eBallColor = eBallColor;
		m_nBallNum = nBallNum;
	}
	
	/**
	 * create ball by color
	 * @param eBallColor: ball color
	 */
	public void createBall(EBallColor eBallColor)
	{
		m_eBallColor = eBallColor;
		
		updateBallNum();
	}
	
	/**
	 * update ball number
	 */
	public void updateBallNum()
	{
		Random random = new Random();
		if (m_eBallColor == EBallColor.s_eColorRed)
		{//red
			m_nBallNum = random.nextInt(s_nRedBall_Range) + 1;
		}
		else
		{//blue
			m_nBallNum = random.nextInt(s_nBlueBall_Range) + 1;
		}
		random = null;
	}
	
	/**
	 * change ball type
	 * @param nBallType
	 */
	public void changeBallType(int nBallType)
	{
		m_nBallType = nBallType;
	}
	
	/**
	 * get ball type
	 * @return: ball type
	 */
	public int getBallType()
	{
		return m_nBallType;
	}
}
