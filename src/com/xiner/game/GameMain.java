package com.xiner.game;

import com.xiner.game.ball.DoubleBall;
import com.xiner.game.net.Search;
import com.xiner.game.util.LotteryManager;

public class GameMain
{

	public static void main(String[] args)
	{
		LotteryManager lotteryManager = new LotteryManager();
		
		 Search search = new Search();		
		 search.searchData("http://baidu.lecai.com/lottery/draw/list/50?type=range&start=2015001&end=2015101");

		DoubleBall db = new DoubleBall();
		db.createDoubleBall();
		db.paintBall();
	}

}
