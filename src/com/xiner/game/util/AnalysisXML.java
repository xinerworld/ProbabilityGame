package com.xiner.game.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xiner.game.ball.Ball;
import com.xiner.game.ball.Ball.EBallColor;
import com.xiner.game.ball.DoubleBall;
import com.xiner.game.ball.LotteryBox;
import com.xiner.game.ball.LotteryContain;
import com.xiner.game.ball.LotteryStage;
import com.xiner.game.ball.LotteryStatistic;
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
	
	private final String EM_Stage = "stage";
	private final String EM_Stage_Att_Start = "start";
	private final String EM_Stage_Att_End = "end";
	private final String EM_Total = "total";
	private final String EM_RedCount = "redcount";
	private final String EM_RedStatistic = "redstatistic";
	private final String EM_BlueCount = "bluecount";
	private final String EM_BlueStatistic = "bluestatistic";

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
				LotteryManager.getInstance().addLotteryStage(CommonData.Lottery_KEY_XML, lotteryStage);
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
		LotteryStatistic lotteryStatistic = new LotteryStatistic();
		
		//stage
		Element stageEM = root.element(EM_Stage);
		Attribute stageAttStart = stageEM.attribute(EM_Stage_Att_Start);
		Attribute stageAttEnd = stageEM.attribute(EM_Stage_Att_End);
		lotteryStatistic.setStartStage(stageAttStart.getValue());
		lotteryStatistic.setEndStage(stageAttEnd.getValue());
		
		//total
		Element totalEM = root.element(EM_Total);
		String total = totalEM.getText();
		lotteryStatistic.setTotal(Integer.valueOf(total).intValue());
		
		//red count
		Element redCount = root.element(EM_RedCount);
		String strRedCounts = redCount.getText();
		int[] redCountAry = getIntAry(strRedCounts);
		lotteryStatistic.setRedCount(redCountAry);
		
		//red statistic
		Element redStatistic = root.element(EM_RedStatistic);
		String strRedStatsc = redStatistic.getText();
		float[] redStatscAry = getFloatAry(strRedStatsc);
		lotteryStatistic.setRedStatistic(redStatscAry);
		
		//blue count
		Element blueCount = root.element(EM_BlueCount);
		String strBlueCounts = blueCount.getText();
		int[] blueCountAry = getIntAry(strBlueCounts);
		lotteryStatistic.setBlueCount(blueCountAry);
		
		//blue statistic
		Element blueStatistic = root.element(EM_BlueStatistic);
		String strBlueStatsc = blueStatistic.getText();
		float[] blueStatscAry = getFloatAry(strBlueStatsc);
		lotteryStatistic.setBlueStatistic(blueStatscAry);
	}
	
	private int[] getIntAry(String strNum)
	{
		String[] strNumAry = strNum.split(",");
		int[] nums = new int[strNumAry.length];
		
		for (int i = 0; i < nums.length; i++)
		{
			nums[i] = Integer.valueOf(strNumAry[i]).intValue();
		}
		
		return nums;
	}
	
	private float[] getFloatAry(String strNum)
	{
		String[] strNumAry = strNum.split(",");
		float[] nums = new float[strNumAry.length];
		
		for (int i = 0; i < nums.length; i++)
		{
			nums[i] = Float.valueOf(strNumAry[i]).floatValue();
		}
		
		return nums;
	}
	
	/**
	 * LotteryContain export to xml by xml path
	 * @param xmlPath: xml full path
	 * @param lotteryContain: LotteryContain
	 */
	public void exportXml(String xmlPath, LotteryContain lotteryContain)
	{
		Map<Integer, LotteryBox> containMap = lotteryContain.getLotteryContain();
		
		for (LotteryBox entryBox : containMap.values())
		{
			exportXml(xmlPath, entryBox);
		}
	}
	
	/**
	 * LotteryBox export to xml by xml path
	 * @param xmlPath: xml full path
	 * @param lotteryBox: LotteryBox
	 */
	public void exportXml(String xmlPath, LotteryBox lotteryBox)
	{
		Document doc = DocumentHelper.createDocument();
		
		//create root
		Element root = DocumentHelper.createElement(EM_Lottery);
		doc.setRootElement(root);

		//create box
		Element boxElement = DocumentHelper.createElement(EM_Box);
		String year = lotteryBox.getYear();
		boxElement.addText(year);
		root.add(boxElement);

		//get stage list by order
		List<Map.Entry<String, LotteryStage>> stageList = lotteryBox.getLotteryBoxByOrder();
		for (Map.Entry<String, LotteryStage> stageEntry : stageList)
		{
			LotteryStage lotteryStage = stageEntry.getValue();

			String stage = lotteryStage.getStage();
			String moon = lotteryStage.getMoon();
			String date = lotteryStage.getDate();
			DoubleBall dblBall = lotteryStage.getDoubleBall();
			
			Element paperElement = DocumentHelper.createElement(EM_Paper);
			paperElement.addAttribute(EM_Paper_Att_Stage, stage);
			paperElement.addAttribute(EM_Paper_Att_Moon, moon);
			paperElement.addAttribute(EM_Paper_Att_Date, date);			
			exportDoubleBall(paperElement, dblBall);
			
			boxElement.add(paperElement);
		}
		
		//write xml
		String xmlName = xmlPath+year+".xml";
		writeXml(xmlName, doc);
	}
	
	/**
	 * export doubleBall to Element
	 * @param paper: Element
	 * @param dblBall: doubleBall
	 */
	private void exportDoubleBall(Element paper, DoubleBall dblBall)
	{
		//create red element
		Element rballElement = DocumentHelper.createElement(EM_RedBall);
		Ball[] balls = dblBall.getRedBall();
		for (int i = 0; i < balls.length; i++)
		{
			Element remNumElement = DocumentHelper.createElement(EM_Num);
			remNumElement.addText( String.valueOf(balls[i].m_nBallNum) );
			
			rballElement.add(remNumElement);
		}
		
		paper.add(rballElement);
		
		//create blue element
		Element bballElement = DocumentHelper.createElement(EM_BlueBall);
		Ball blueBall = dblBall.getBlueBall();
		Element blueNumElement = DocumentHelper.createElement(EM_Num);
		blueNumElement.addText( String.valueOf(blueBall.m_nBallNum) );
		bballElement.add(blueNumElement);
		
		paper.add(bballElement);
		
		//create luck element
		Ball luckBall = dblBall.getLuckBall();
		if (luckBall != null)
		{
			Element lballElement = DocumentHelper.createElement(EM_LuckBall);
			Element luckNumElement = DocumentHelper.createElement(EM_Num);
			luckNumElement.addText( String.valueOf(luckBall.m_nBallNum) );
			lballElement.add(luckNumElement);
			
			paper.add(lballElement);
		}
	}

	/**
	 * write xml
	 * @param xmlPath: xml path
	 * @param document: xml document
	 */
	private void writeXml(String xmlPath, Document document)
	{
		try
		{
			OutputFormat format = new OutputFormat("    ",true);  
			format.setEncoding("UTF-8");//设置编码格式  
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(xmlPath),format);
     
			xmlWriter.write(document);  
			xmlWriter.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
