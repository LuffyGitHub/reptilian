/**
 * 功能描述：
 * @Package: com.chao.main 
 * @author: luffy 
 * @date: 2018年8月7日 下午2:14:27 
 */
package com.chao.main;

import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.drew.lang.StringUtil;
import com.sleepycat.utilint.StringUtils;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
* @ClassName: MyCrawler.java
* @Description: 该类的功能描述
*
* @author: luffy
* @date: 2018年8月7日 下午2:14:27
*/
public class MyCrawler extends WebCrawler{
	
	 public static int count = 0;
	 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|zip|gz))$");

	 /** 
	* @Description: 
	*
	* 该方法接收两个参数。第一个参数是页面。
	* 我们发现了这个新的URL，第二个参数是
	* 新的URL。您应该实现这个函数来指定是否
	* 给定的URL应该被爬行或不（基于爬行逻辑）。
	* 在这个例子中，我们指示爬虫忽略URL
	* 有CSS，JS，GIT，…扩展和只接受启动的URL
	* 用“http://www. cIC.ui.eDu/”。在这种情况下，我们不需要
	* 引用页面参数进行决策。
	* @author: luffy
	* @date: 2018年8月7日 下午2:18:17 
	*/
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		
		String href = url.getURL().toLowerCase();
		
		boolean flag = !FILTERS.matcher(href).matches() && href.startsWith("http://newhouse.gz.fang.com/house/s/");
		
		return flag;
	}
	
	/** 
	* @Description: 页面下载完成调用此方法
	*
	* @param:参数一
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @author: luffy
	* @date: 2018年8月7日 下午2:21:08 
	*/
	@Override
	public  void visit(Page page) {
		
		String url = page.getWebURL().getURL();
		
		//抓取符合html页面
		if(page.getParseData() instanceof HtmlParseData){
			//取出html解析数据转换
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            
            String html = htmlParseData.getHtml();
            
            Document doc = Jsoup.parse(html);
            
            Elements elements  = doc.select("div[class=clearfix]");
            for (Element element : elements) {
	            String name = element.select("div[class=nlcd_name]").text();
	            String price = element.select("div[class=nhouse_price]").text();
	            if(price.equals("")){
	            	price = "价格待定";
	            }
	            String address = element.select("div[class=address]").text();
            	if(!name.equals("")){
            		if(address.contains("增城")||address.contains("从化")){
	            			System.out.println("楼盘: " + name + "--->>价格: " + price.replace("�O", "m2") + "--->>地址:" + address);
	            			count ++;
            		}
	            }
            }
		}
		
	}
}
