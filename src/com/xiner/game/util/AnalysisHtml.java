package com.xiner.game.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xiner.game.ball.Ball;
import com.xiner.game.ball.Ball.EBallColor;
import com.xiner.game.ball.DoubleBall;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.data.CommonData;

public class AnalysisHtml
{

	public AnalysisHtml()
	{
	}

	public void analysis(String html)
	{
		Document doc = Jsoup.parse(html);
		// 获得historylist结点
		Elements linksElements = doc.getElementsByClass("historylist");
		Element historyElement = linksElements.get(0);
		// 获得tbody结点
		Elements tbodylist = historyElement.getElementsByTag("tbody");
		Element tbodyElement = tbodylist.get(2);
		// 获取每期号码信息结点
		Elements trlist = tbodyElement.getElementsByTag("tr");

		int index = 0;
		for (Element element : trlist)
		{
			if (index % 4 == 0)
			{
				// 获得一期号码的各信息结点
				Elements tdList = element.getElementsByTag("td");

				LotteryStage lotteryStage = new LotteryStage();
				DoubleBall dblBall = new DoubleBall();
				int tdIndex = 0;
				for (Element tdElement : tdList)
				{
					switch (tdIndex)
					{
					case 0:
					{// stage
						String stage = tdElement.text();
						lotteryStage.setStage(stage);
					}
						break;
					case 1:
					{// date
						String calendar = tdElement.text();
						calendar = calendar.substring(0, calendar.length() - 3);
						lotteryStage.setCalendar(calendar);
					}
						break;
					case 3:
					{// red
						Elements redList = tdElement.getElementsByTag("em");
						for (int i = 0; i < redList.size(); i++)
						{
							Element redElem = redList.get(i);
							dblBall.setRedBall(new Ball(EBallColor.s_eColorRed, Integer.valueOf(redElem.text()).intValue()), i);
						}
					}
						break;
					case 4:
					{// blue
						Elements blueList = tdElement.getElementsByTag("em");
						for (int i = 0; i < blueList.size(); i++)
						{
							Element redElem = blueList.get(i);
							if (i == 0)
							{
								dblBall.setBlueBall(new Ball(EBallColor.s_eColorRed, Integer.valueOf(redElem.text()).intValue()));
							}
							else if (i == 1)
							{
								dblBall.setLuckBall(new Ball(EBallColor.s_eColorRed, Integer.valueOf(redElem.text()).intValue()));
							}
						}
					}
						break;
					}//switch
					tdIndex++;
				}// tdList
				
				lotteryStage.setDoubleBall(dblBall);
				LotteryManager.getInstance().addLotteryStage(CommonData.Lottery_KEY_Analysis, lotteryStage);
			}//if %4
			index++;
		}//trlist
	}

}