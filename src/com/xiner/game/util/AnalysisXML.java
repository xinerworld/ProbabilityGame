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
			
			analysisDom(doc);
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
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
	
	
	private void analysisDom(Document doc)
	{
		Element root = doc.getRootElement();
		
		//get year
		Attribute rootAttYear = root.attribute("year");
		String year = rootAttYear.getValue();
		
		//get stage list
		List<Element> stageList = root.elements("stage");
		for (Element stageElement : stageList)
		{
			Attribute emAttStage = stageElement.attribute("value");
			Attribute emAttMoon = stageElement.attribute("moon");
			Attribute emAttDate = stageElement.attribute("date");
			String stage = emAttStage.getValue();
			String moon = emAttMoon.getValue();
			String date = emAttDate.getValue();
			
			//get doubleball
			DoubleBall dblBall = analysisStage(stageElement);
			
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
	
	/**
	 * analysis stage element
	 * @param stageElement: element of stage
	 * @return: DoubleBall class
	 */
	private DoubleBall analysisStage(Element stageElement)
	{
		DoubleBall dblBall = new DoubleBall();
		
		//get red ball
		Element rball = stageElement.element("rball");
		List<Element> redBallList = rball.elements();
		int i = 0;
		for (Element redBallElement : redBallList)
		{
			String redNum = redBallElement.getText();
			Ball ball = new Ball(EBallColor.s_eColorRed, Integer.valueOf(redNum).intValue());
			dblBall.setRedBall(ball, i);
		}
		
		//get blue ball
		Element bball = stageElement.element("bball");
		Element bballElement = bball.element("em");
		String blueNum = bballElement.getText();
		Ball blueBall = new Ball(EBallColor.s_eColorBlue, Integer.valueOf(blueNum).intValue());
		dblBall.setBlueBall(blueBall);
		
		//get luck ball
		Element lball = stageElement.element("lball");
		if (lball != null)
		{
			Element lballElement = lball.element("em");
			String luckNum = lballElement.getText();
			Ball luckBall = new Ball(EBallColor.s_eColorBlue, Integer.valueOf(luckNum).intValue());
			luckBall.changeBallType(Ball.s_nBallType_LUCK);
			dblBall.setLuckBall(luckBall);
		}
		
		return dblBall;
	}
}
