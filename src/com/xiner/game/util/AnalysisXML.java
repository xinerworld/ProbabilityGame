package com.xiner.game.util;

import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xiner.game.ball.Ball;
import com.xiner.game.ball.Ball.EBallColor;
import com.xiner.game.ball.DoubleBall;
import com.xiner.game.ball.LotteryBox;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.data.CommonData;

public class AnalysisXML
{
	private final String EM_Lottery = "lottery";
	private final String EM_Statistic = "statistic";
	private final String EM_Box = "box";
	private final String EM_Box_Att_Year = "year";
	private final String EM_Paper = "paper";
	private final String EM_Paper_Att_Stage = "stage";
	private final String EM_Paper_Att_Moon = "moon";
	private final String EM_Paper_Att_Date = "date";
	private final String EM_RedBall = "redBall";
	private final String EM_BlueBall = "blueBall";
	private final String EM_LuckBall = "luckBall";
	private final String EM_Num = "num";

	public AnalysisXML()
	{
	}
	
	/**
	 * import from xml by xml path
	 * @param xmlPath: xml full path
	 */
	public void importXml(String xmlPath)
	{
		try
		{
			//获取读取xml的对象。
			SAXReader sr = new SAXReader();
			Document doc = sr.read(xmlPath);
			
			//get root name
			Element root = doc.getRootElement();
			String rootName = root.getName();
			
			if (EM_Lottery.equals(rootName))
			{
				analysisLottery(root);
			}
			else if (EM_Statistic.equals(rootName))
			{
				analysisStatistic(root);
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}
	
	private void analysisLottery(Element root)
	{
		//get box list
		List<Element> boxList = root.elements(EM_Box);
		for (Element boxElement : boxList)
		{
			//get year
			Attribute boxAttYear = boxElement.attribute(EM_Box_Att_Year);
			String year = boxAttYear.getValue();
			
			//get paper list
			List<Element> paperList = boxElement.elements(EM_Paper);
			for (Element paperElement : paperList)
			{
				Attribute paperAttStage = paperElement.attribute(EM_Paper_Att_Stage);
				Attribute paperAttMoon = paperElement.attribute(EM_Paper_Att_Moon);
				Attribute paperAttDate = paperElement.attribute(EM_Paper_Att_Date);
				String stage = paperAttStage.getValue();
				String moon = paperAttMoon.getValue();
				String date = paperAttDate.getValue();
				
				//get doubleball
				DoubleBall dblBall = analysisPaper(paperElement);
				
				//create lotteryStage
				LotteryStage lotteryStage = new LotteryStage();
				lotteryStage.setYear(year);
				lotteryStage.setMoon(moon);
				lotteryStage.setDate(date);
				lotteryStage.setStage(stage);
				lotteryStage.setDoubleBall(dblBall);
				
				//put in lottery manager
				LotteryManager.getInstance().addLotteryStage(CommonData.Lottery_KEY_Total, lotteryStage);
			}
		}
	}
	
	/**
	 * analysis paper element
	 * @param paperElement: element of paper
	 * @return: DoubleBall class
	 */
	private DoubleBall analysisPaper(Element paperElement)
	{
		//create DoubleBall
		DoubleBall dblBall = new DoubleBall();
		
		//get red ball
		Element redBallElement = paperElement.element(EM_RedBall);
		List<Element> redBallList = redBallElement.elements();
		int i = 0;
		for (Element redNumElement : redBallList)
		{
			String redNum = redNumElement.getText();
			Ball ball = new Ball(EBallColor.s_eColorRed, Integer.valueOf(redNum).intValue());
			dblBall.setRedBall(ball, i);
		}
		
		//get blue ball
		Element blueBallElement = paperElement.element(EM_BlueBall);
		Element blueNumElement = blueBallElement.element(EM_Num);
		String blueNum = blueNumElement.getText();
		Ball blueBall = new Ball(EBallColor.s_eColorBlue, Integer.valueOf(blueNum).intValue());
		dblBall.setBlueBall(blueBall);
		
		//get luck ball
		Element luckBallElement = paperElement.element(EM_LuckBall);
		if (luckBallElement != null)
		{
			Element luckNumElement = luckBallElement.element(EM_Num);
			String luckNum = luckNumElement.getText();
			Ball luckBall = new Ball(EBallColor.s_eColorBlue, Integer.valueOf(luckNum).intValue());
			luckBall.changeBallType(Ball.s_nBallType_LUCK);
			dblBall.setLuckBall(luckBall);
		}
		
		return dblBall;
	}
	
	private void analysisStatistic(Element root)
	{
		
	}
	
	/**
	 * LotteryBox export to xml by xml path
	 * @param xmlPath: xml full path
	 */
	public void exportXml(String xmlPath, LotteryBox lotteryBox)
	{
		Document doc = DocumentHelper.createDocument();
		
		//create root
		Element root = DocumentHelper.createElement("lottery");
		root.addAttribute("year", lotteryBox.getYear());
		doc.setRootElement(root);
		
		List<Map.Entry<String, LotteryStage>> boxList = lotteryBox.getLotteryBoxByOrder();
		
		for (Map.Entry<String, LotteryStage> stageEntry : boxList)
		{
			LotteryStage lotteryStage = stageEntry.getValue();

			String stage = lotteryStage.getStage();
			String moon = lotteryStage.getMoon();
			String date = lotteryStage.getDate();
			DoubleBall dblBall = lotteryStage.getDoubleBall();
			
			Element stageElement = DocumentHelper.createElement("stage");
			stageElement.addAttribute("value", stage);
			stageElement.addAttribute("moon", moon);
			stageElement.addAttribute("date", date);			
			exportDoubleBall(stageElement, dblBall);
			
			root.add(stageElement);
		}
	}
	
	private void exportDoubleBall(Element stage, DoubleBall dblBall)
	{
		//create red element
		Element rballElement = DocumentHelper.createElement("rball");
		Ball[] balls = dblBall.getRedBall();
		for (int i = 0; i < balls.length; i++)
		{
			Element em = DocumentHelper.createElement("em");
			em.addText( String.valueOf(balls[i].m_nBallNum) );
			
			rballElement.add(em);
		}
		
		stage.add(rballElement);
		
		//create blue element
		Element bballElement = DocumentHelper.createElement("bball");
		Ball blueBall = dblBall.getBlueBall();
		Element bem = DocumentHelper.createElement("em");
		bem.addText( String.valueOf(blueBall.m_nBallNum) );
		bballElement.add(bem);
		
		stage.add(bballElement);
		
		//create luck element
		Ball luckBall = dblBall.getLuckBall();
		if (luckBall != null)
		{
			Element lballElement = DocumentHelper.createElement("lball");
			Element lem = DocumentHelper.createElement("em");
			lem.addText( String.valueOf(luckBall.m_nBallNum) );
			lballElement.add(lem);
			
			stage.add(lballElement);
		}
	}
}
