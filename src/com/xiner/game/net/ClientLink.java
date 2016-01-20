package com.xiner.game.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientLink
{

	public ClientLink()
	{
	}

	public String getHtmlByUrl(String url) throws Exception
	{
		String html = null;

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try
		{
			// 以get方式请求该URL
			HttpGet httpget = new HttpGet(url);

			CloseableHttpResponse responce = httpClient.execute(httpget);
			// 返回码
			int resStatu = responce.getStatusLine().getStatusCode();
			if (resStatu == HttpStatus.SC_OK)
			{// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null)
				{
					// 获得html源代码
					html = EntityUtils.toString(entity);
				}
			}
			responce.close();
		} finally
		{
			httpClient.close();
		}

		return html;
	}
}
