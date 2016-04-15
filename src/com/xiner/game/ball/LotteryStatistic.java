package com.xiner.game.ball;

public class LotteryStatistic
{
	//start stage
	private String mStartStage;
	//end stage
	private String mEndStage;
	//all total
	private int mTotal;
	//red count
	private int[] mRedCount;
	//blue count
	private int[] mBlueCount;
	//red statistic
	private float[] mRedStatistic;
	//blue statistic
	private float[] mBlueStatistic;
	
	public LotteryStatistic()
	{
		init();
	}
	
	private void init()
	{
		mStartStage = null;
		mEndStage = null;
		mTotal = 0;
		mRedCount = new int[Ball.s_nRedBall_Range];
		mBlueCount = new int[Ball.s_nBlueBall_Range];
		mRedStatistic = new float[Ball.s_nRedBall_Range];
		mBlueStatistic = new float[Ball.s_nBlueBall_Range];
	}
	
	public void clear()
	{
		init();
	}
	
	public String getStartStage()
	{
		return mStartStage;
	}
	
	public void setStartStage(String startStage)
	{
		this.mStartStage = startStage;
	}
	
	public String getEndStage()
	{
		return mEndStage;
	}
	
	public void setEndStage(String endStage)
	{
		this.mEndStage = endStage;
	}
	
	public int getTotal()
	{
		return mTotal;
	}
	
	public void setTotal(int total)
	{
		this.mTotal = total;
	}
	
	public int[] getRedCount()
	{
		return mRedCount;
	}
	
	public void setRedCount(int[] redCount)
	{
		this.mRedCount = redCount;
	}
	
	public int getRedCount(int index)
	{
		return mRedCount[index];
	}
	
	public void setRedCount(int index, int count)
	{
		mRedCount[index] = count;
	}
	
	public int[] getBlueCount()
	{
		return mBlueCount;
	}
	
	public void setBlueCount(int[] blueCount)
	{
		this.mBlueCount = blueCount;
	}
	
	public int getBlueCount(int index)
	{
		return mBlueCount[index];
	}
	
	public void setBlueCount(int index, int blueCount)
	{
		this.mBlueCount[index] = blueCount;
	}
	
	public float[] getRedStatistic()
	{
		return mRedStatistic;
	}
	
	public void setRedStatistic(float[] redStatistic)
	{
		this.mRedStatistic = redStatistic;
	}
	
	public float getRedStatistic(int index)
	{
		return mRedStatistic[index];
	}
	
	public void setRedStatistic(int index, float redStatistic)
	{
		mRedStatistic[index] = redStatistic;
	}

	public float[] getBlueStatistic()
	{
		return mBlueStatistic;
	}
	
	public void setBlueStatistic(float[] blueStatisitic)
	{
		this.mBlueStatistic = blueStatisitic;
	}
	
	public float getBlueStatistic(int index)
	{
		return mBlueStatistic[index];
	}
	
	public void setBlueStatistic(int index, float blueStatisitic)
	{
		this.mBlueStatistic[index] = blueStatisitic;
	}
	
	/**
	 * update lottery stage
	 * @param lotteryStage: LotteryStage
	 */
	public void updateByLotteryStage(LotteryStage lotteryStage)
	{
        int stage = Integer.parseInt(lotteryStage.getStage());
        int curStage = Integer.parseInt(mEndStage);
        if (stage <= curStage)
            return;

		DoubleBall dblBall = lotteryStage.getDoubleBall();
		
		Ball[] redBall = dblBall.getRedBall();
		Ball blueBall = dblBall.getBlueBall();
		
		//update red count
		for (int i = 0; i < redBall.length; i++)
		{
			int redNum = redBall[i].m_nBallNum;
			mRedCount[redNum-1]++;
		}
		
		//update blue count
		int blueNum = blueBall.m_nBallNum;
		mBlueCount[blueNum-1]++;
		
		//update total
		mTotal++;
	}
	
	/**
	 * calcul
	 */
	public void updateCalcul()
	{
		for (int i = 0; i < Ball.s_nRedBall_Range; i++)
		{
			mRedStatistic[i] = (float)(mRedCount[i] * 100) / (float)mTotal;
		}
		
		for (int i = 0; i < Ball.s_nBlueBall_Range; i++)
		{
			mBlueStatistic[i] = (float)(mBlueCount[i] * 100) / (float)mTotal;
		}
	}
}
