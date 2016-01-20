package com.xiner.game.ball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryContain
{
	/**
	 * key: year
	 * value: lottery of the year
	 */
	private Map<Integer, LotteryBox> m_LotteryContain;
	
	public LotteryContain()
	{
		m_LotteryContain = new HashMap<Integer, LotteryBox>();
	}
	
	/**
	 * add lottery stage
	 * @param lotteryStage: a lottery stage
	 */
	public void addLotteryStage(LotteryStage lotteryStage)
	{
		String year = lotteryStage.getYear();
		
		LotteryBox lotteryBox = getLotteryBoxAdd(year);
		
		lotteryBox.addLotteryStage(lotteryStage);
	}
	
	public void addLotteryContain(LotteryContain lotteryContain)
	{
		Map<Integer, LotteryBox> mapContain = lotteryContain.getLotteryContain();
		
		for (LotteryBox entryBox : mapContain.values())
		{
			String year = entryBox.getYear();
			
			LotteryBox tLottery = getLotteryBoxAdd(year);
			
			tLottery.addLotteryBox(entryBox);
		}
	}
	
	/**
	 * get lotteryBox by year, if no lotteryBox, create and add
	 * @param year: lottery year
	 * @return: lotteryBox
	 */
	public LotteryBox getLotteryBoxAdd(String year)
	{
		LotteryBox lotteryBox = m_LotteryContain.get(Integer.valueOf(year));
		
		if (lotteryBox == null)
		{//not have
			lotteryBox = new LotteryBox();
			lotteryBox.setYear(year);
			m_LotteryContain.put(Integer.valueOf(year), lotteryBox);
		}
		
		return lotteryBox;
	}
	
	public Map<Integer, LotteryBox> getLotteryContain()
	{
		return m_LotteryContain;
	}
	
	public List<Map.Entry<Integer, LotteryBox>> getLotteryContainByOrder()
	{
		List<Map.Entry<Integer, LotteryBox>> containList = null;
		// 通过ArrayList构造函数把map.entrySet()转换成list
		containList = new ArrayList<Map.Entry<Integer, LotteryBox>>(m_LotteryContain.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(containList, new Comparator<Map.Entry<Integer, LotteryBox>>()
		{
			public int compare(Map.Entry<Integer, LotteryBox> mapping1, Map.Entry<Integer, LotteryBox> mapping2)
			{
				return mapping1.getKey().compareTo(mapping2.getKey());
			}
		});
		
		return containList;
	}
}
