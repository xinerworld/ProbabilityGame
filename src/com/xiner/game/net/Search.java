package com.xiner.game.net;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xiner.game.util.AnalysisHtml;

public class Search
{
	private ClientLink m_oClientLink;
	
	private AnalysisHtml m_oAnalysisHtml;

	public Search()
	{
		m_oClientLink = new ClientLink();
		m_oAnalysisHtml = new AnalysisHtml();
	}

	public void searchData(String url)
	{
		String html = null;

		try
		{
			//获取网页内容
			html = m_oClientLink.getHtmlByUrl(url);			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (html != null && !"".equals(html))
		{
			//解析网页内容
			m_oAnalysisHtml.analysis(html);
			
			Document doc = Jsoup.parse(html);
			Elements linksElements = doc.select("div#page>div#content>div#main>div.left>div#recommend>ul>li>a");
			
			for (Element ele : linksElements)
			{
				String href = ele.attr("href");
				String title = ele.text();
				System.out.println(href + "," + title);
			}
		}
	}
}
