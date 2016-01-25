package com.xiner.game.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xiner.game.ball.LotteryBox;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.ball.LotteryStatistic;
import com.xiner.game.ball.LotteryContain;
import com.xiner.game.data.CommonData;

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
	
	/**
	 * key: statistic flag name
	 * value: lottery statistic
	 */
	private Map<String, LotteryStatistic> m_Statistic = null;
	
	private String m_KEY_Lottery;
	
	private String m_KEY_Statistic;
	
	public LotteryManager()
	{
		m_LotteryManager = this;
		
		m_Lottery = new HashMap<String, LotteryContain>();
		m_Statistic = new HashMap<String, LotteryStatistic>();
	}
	
	public static void create()
	{
		if (m_LotteryManager == null)
		{
			m_LotteryManager = new LotteryManager();
		}
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
	
	public String getKEY_Lottery()
	{
		if (m_KEY_Lottery == null || "".equals(m_KEY_Lottery))
		{
			m_KEY_Lottery = CommonData.KEY_Lottery_AnalysisHTML;
		}
		return m_KEY_Lottery;
	}

	public void setKEY_Lottery(String KEY_Lottery)
	{
		this.m_KEY_Lottery = KEY_Lottery;
	}

	public String getKEY_Statistic()
	{
		if (m_KEY_Statistic == null || "".equals(m_KEY_Statistic))
		{
			m_KEY_Statistic = CommonData.KEY_Statistic_TEMP;
		}
		return m_KEY_Statistic;
	}

	public void setKEY_Statistic(String KEY_Statistic)
	{
		this.m_KEY_Statistic = KEY_Statistic;
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
	
	/**
	 * clear lotterContain by key
	 * @param key: lotteryContain flag name
	 */
	public void clearContain(String key)
	{
		LotteryContain lotteryContain = getLotteryContain(key);
		
		lotteryContain.getLotteryContain().clear();
	}

	/**
	 * add lottery statistic
	 * @param key: statistic flag name
	 * @param lotteryStatistic: lotteryStatistic
	 */
	public void putLotteryStatistic(String key, LotteryStatistic lotteryStatistic)
	{
		LotteryStatistic lStatistic = getLotteryStatistic(key, false);
		
		if (lStatistic != null)
		{
			m_Statistic.remove(key);
		}
		m_Statistic.put(key, lotteryStatistic);
	}
	
	/**
	 * get lotteryStatistic
	 * @param key: statistic flag name
	 * @param isAdd: ture -if null and add one
	 * @return: lotteryStatistic
	 */
	public LotteryStatistic getLotteryStatistic(String key, boolean isAdd)
	{
		LotteryStatistic lotteryStatistic = null;
		
		lotteryStatistic = m_Statistic.get(key);
		
		if (isAdd && lotteryStatistic == null)
		{
			lotteryStatistic = new LotteryStatistic();
			m_Statistic.put(key, lotteryStatistic);
		}
		
		return lotteryStatistic;
	}
	
	/**
	 * clear lotteryStatistic by key
	 * @param key: lotteryStatistic flag name
	 */
	public void clearStatistic(String key)
	{
		LotteryStatistic lotteryStatistic = getLotteryStatistic(key, false);
		
		if (lotteryStatistic != null)
		{
			lotteryStatistic.clear();
		}
	}
	
	/**
	 * lotteryCotain change to lotteryStatistic
	 * @param lotteryContain: lotteryContain
	 * @param lotteryStatistic: lotteryStatistic
	 */
	public void lotteryContain2Statistic(LotteryContain lotteryContain, LotteryStatistic lotteryStatistic)
	{
		List<Entry<Integer, LotteryBox>> boxList = lotteryContain.getLotteryContainByOrder();
		
		for (Entry<Integer, LotteryBox> entryBox : boxList)
		{
			LotteryBox lotteryBox = entryBox.getValue();
			
			lotteryBox2Statistic(lotteryBox, lotteryStatistic);
		}
	}
	
	/**
	 * lotteryBox change to lotteryStatistic
	 * @param lotteryBox: lotteryBox
	 * @param lotteryStatistic: lotteryStatistic
	 */
	public void lotteryBox2Statistic(LotteryBox lotteryBox, LotteryStatistic lotteryStatistic)
	{
		List<Entry<String, LotteryStage>> stageList = lotteryBox.getLotteryBoxByOrder();
		
		for (Entry<String, LotteryStage> entryStage : stageList)
		{
			LotteryStage lotteryStage = entryStage.getValue();
			
			lotteryStage2Statistic(lotteryStage, lotteryStatistic);
		}
	}
	
	/**
	 * lotteryStage change to lotteryStatistic
	 * @param lotteryStage: lotteryStage
	 * @param lotteryStatistic: lotteryStatistic
	 */
	public void lotteryStage2Statistic(LotteryStage lotteryStage, LotteryStatistic lotteryStatistic)
	{
		lotteryStatistic.updateByLotteryStage(lotteryStage);
	}
}
