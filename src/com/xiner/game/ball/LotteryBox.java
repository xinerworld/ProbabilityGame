package com.xiner.game.ball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	
	public List<Map.Entry<String, LotteryStage>> getLotteryBoxByOrder()
	{
		List<Map.Entry<String, LotteryStage>> boxList = null;
		// 通过ArrayList构造函数把map.entrySet()转换成list
		boxList = new ArrayList<Map.Entry<String, LotteryStage>>(m_LotteryBox.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(boxList, new Comparator<Map.Entry<String, LotteryStage>>()
		{
			public int compare(Map.Entry<String, LotteryStage> mapping1, Map.Entry<String, LotteryStage> mapping2)
			{
				return mapping1.getKey().compareTo(mapping2.getKey());
			}
		});
		
		return boxList;
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
