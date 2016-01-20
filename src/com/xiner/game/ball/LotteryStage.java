package com.xiner.game.ball;

/**
 * 
 * @author xiner
 *
 */
public class LotteryStage
{
	private DoubleBall mDoubleBall;
	private String mStage;
	private String mYear;
	private String mMoon;
	private String mDate;
	
	public DoubleBall getDoubleBall()
	{
		return mDoubleBall;
	}
	
	public void setDoubleBall(DoubleBall doubleBall)
	{
		mDoubleBall = doubleBall;
	}
		
	public String getStage()
	{
		return mStage;
	}

	public void setStage(String mStage)
	{
		this.mStage = mStage;
	}

	public String getYear()
	{
		return mYear;
	}

	public void setYear(String mYear)
	{
		this.mYear = mYear;
	}

	public String getMoon()
	{
		return mMoon;
	}

	public void setMoon(String mMoon)
	{
		this.mMoon = mMoon;
	}

	public String getDate()
	{
		return mDate;
	}

	public void setDate(String mDate)
	{
		this.mDate = mDate;
	}
	
	public void setCalendar(String calendar)
	{
		String[] calSplit = calendar.split("-");
		
		for (int i = 0; i < calSplit.length; i++)
		{
			switch (i)
			{
			case 0:
				setYear(calSplit[i]);
				break;
			case 1:
				setMoon(calSplit[i]);
				break;
			case 2:
				setDate(calSplit[i]);
				break;
			}
		}
	}
}