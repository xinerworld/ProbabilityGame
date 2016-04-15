package com.xiner.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.xiner.game.ball.DoubleBall;
import com.xiner.game.ball.LotteryContain;
import com.xiner.game.ball.LotteryStatistic;
import com.xiner.game.data.CommonData;
import com.xiner.game.net.Search;
import com.xiner.game.util.AnalysisXML;
import com.xiner.game.util.LotteryManager;

public class GameMain
{

	public static void main(String[] args)
	{
//		LotteryManager.create();
//
//		Search search = new Search();
//		search.searchData("http://baidu.lecai.com/lottery/draw/list/50?type=range&start=2015001&end=2015160");
//
//		LotteryContain lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());
//		AnalysisXML analysisXML = new AnalysisXML();
//		analysisXML.exportXml("E:/", lotteryContain);
//		
//		LotteryManager.getInstance().setKEY_Statistic(CommonData.KEY_Statistic_TEMP);
//		LotteryStatistic lotteryStatistic = LotteryManager.getInstance().getLotteryStatistic(LotteryManager.getInstance().getKEY_Statistic(), true);
//		LotteryManager.getInstance().lotteryContain2Statistic(lotteryContain, lotteryStatistic);
//		lotteryStatistic.updateCalcul();
//		analysisXML.exportXML("E:/statistic.xml", lotteryStatistic);
//		
//		LotteryManager.getInstance().setKEY_Lottery(CommonData.KEY_Lottery_AnalysisHTML);
//		analysisXML.importXml("E:/2015.xml");
//		lotteryContain = LotteryManager.getInstance().getLotteryContain(LotteryManager.getInstance().getKEY_Lottery());
//		analysisXML.exportXml("E:/abc", lotteryContain);
		
//		for (int i = 0; i < 100; i++)
//		{
//			DoubleBall db = new DoubleBall();
//			db.createDoubleBall();
//			db.paintBall();
//		}

        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -2);
        String date = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        System.out.println("date : "+date);
		System.out.println("date : "+getWeekday(date).charAt(2));
        System.out.println("date : "+getWeekday1(date));
	}

    public static int getWeekday1(String date)
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try
        {
            d = sd.parse(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(d);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }

	public static String getWeekday(String date)
	{// 必须yyyy-MM-dd
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		try
		{
			d = sd.parse(date);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return sdw.format(d);
	}
}
