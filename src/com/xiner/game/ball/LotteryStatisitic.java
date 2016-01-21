package com.xiner.game.ball;

public class LotteryStatisitic
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
	private int mBlueCount;
	//luck count
	private int mLuckCount;
	//red statistic
	private float[] mRedStatisitic;
	//blue statistic
	private float mBlueStatisitic;
	
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
	
	public int getBlueCount()
	{
		return mBlueCount;
	}
	
	public void setBlueCount(int blueCount)
	{
		this.mBlueCount = blueCount;
	}
	
	public int getLuckCount()
	{
		return mLuckCount;
	}
	
	public void setLuckCount(int luckCount)
	{
		this.mLuckCount = luckCount;
	}
	
	public float[] getRedStatisitic()
	{
		return mRedStatisitic;
	}
	
	public void setRedStatisitic(float[] redStatisitic)
	{
		this.mRedStatisitic = redStatisitic;
	}
	
	public float getBlueStatisitic()
	{
		return mBlueStatisitic;
	}
	
	public void setBlueStatisitic(float blueStatisitic)
	{
		this.mBlueStatisitic = blueStatisitic;
	}
	
	
}
