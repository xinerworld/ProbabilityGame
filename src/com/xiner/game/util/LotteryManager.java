package com.xiner.game.util;

import java.util.HashMap;
import java.util.Map;

import com.xiner.game.ball.LotteryStage;
import com.xiner.game.ball.LotteryStatistic;
import com.xiner.game.ball.LotteryContain;

/**
 * https://dl-ssl.google.com/android/eclipse/
 * @author xiner
 *
 */
public class LotteryManager
{
	static LotteryManager m_LotteryManager = null;
	
	/**
	 * key: lottery flag name
	 * value: lottery content
	 */
	private Map<String, LotteryContain> m_Lottery = null;
	
	private Map<String, LotteryStatistic> m_Statistic = null;
	
	public LotteryManager()
	{
		m_LotteryManager = this;
		
		m_Lottery = new HashMap<String, LotteryContain>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static LotteryManager getInstance()
	{
		if (m_LotteryManager == null)
		{
			m_LotteryManager = new LotteryManager();
		}
		
		return m_LotteryManager;
	}
	
	/**
	 * add lottery stage
	 * @param lotteryKey: lottery flag key
	 * @param lotteryStage: lottery stage
	 */
	public void addLotteryStage(String lotteryKey, LotteryStage lotteryStage)
	{
		LotteryContain lotteryContain = getLotteryContain(lotteryKey);
		
		lotteryContain.addLotteryStage(lotteryStage);
	}
	
	/**
	 * get lotteryContain by lottery flag key
	 * @param lotteryKey: lottery flag key
	 * @return
	 */
	public LotteryContain getLotteryContain(String lotteryKey)
	{
		LotteryContain lotteryContain = null;
		
		lotteryContain = m_Lottery.get(lotteryKey);
		
		if (lotteryContain == null)
		{
			lotteryContain = new LotteryContain();
			
			m_Lottery.put(lotteryKey, lotteryContain);
		}
		
		return lotteryContain;
	}
	
	/**
	 * source lottery add to traget lottery
	 * @param skey: source flag key
	 * @param tKey: traget flag key
	 */
	public void addSource2Target(String skey, String tKey)
	{
		LotteryContain sContain = getLotteryContain(skey);
		
		LotteryContain tContain = getLotteryContain(tKey);
				
		tContain.addLotteryContain(sContain);
	}

	public void addLotteryStatistic(String key, LotteryStatistic lotteryStatistic)
	{
		
	}
	
	public LotteryStatistic getLotteryStatistic(String key)
	{
		LotteryStatistic lotteryStatistic = null;
		
		lotteryStatistic = m_Statistic.get(key);
		
		if (lotteryStatistic == null)
		{
			lotteryStatistic = new LotteryStatistic();
			
			m_Statistic.put(key, lotteryStatistic);
		}
		
		return lotteryStatistic;
	}
}
