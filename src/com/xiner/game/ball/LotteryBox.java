package com.xiner.game.ball;

import java.util.HashMap;
import java.util.Map;

public class LotteryBox
{
	/**
	 * key:	stage
	 * value: lottery of the stage
	 */
	private Map<String, LotteryStage> m_LotteryBox;
	
	private String m_year;
	
	public LotteryBox()
	{
		m_LotteryBox = new HashMap<String, LotteryStage>();
	}
	
	public void addLotteryStage(LotteryStage lotteryStage)
	{
		String stage = lotteryStage.getStage();
		
		if (!m_LotteryBox.containsKey(stage))
		{
			m_LotteryBox.put(stage, lotteryStage);
		}
	}

	public void addLotteryBox(LotteryBox lotteryBox)
	{
		Map<String, LotteryStage> mapBox = lotteryBox.getLotteryBox();
		
		for (LotteryStage entryStage : mapBox.values())
		{
			addLotteryStage(entryStage);
		}
	}
	
	public Map<String, LotteryStage> getLotteryBox()
	{
		return m_LotteryBox;
	}

	public String getYear()
	{
		return m_year;
	}

	public void setYear(String year)
	{
		this.m_year = year;
	}
}
