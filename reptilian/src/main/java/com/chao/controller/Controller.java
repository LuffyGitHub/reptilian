/**
 * 功能描述：
 * @Package: com.chao.controller 
 * @author: luffy 
 * @date: 2018年8月7日 下午2:26:27 
 */
package com.chao.controller;


import com.chao.main.MyCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
* @ClassName: Controller.java
* @Description: 该类的功能描述
*
* @author: luffy
* @date: 2018年8月7日 下午2:26:27
*/
public class Controller {
	
	public static void main(String[] args) throws Exception {
		
		String crawlStorageFolder = "F:/crawler";
		int numberOfCrawlers = 10;
		CrawlConfig config = new CrawlConfig();
		//设置爬虫数据存储位置
        config.setCrawlStorageFolder(crawlStorageFolder);
        /*
         * 为这个爬虫实例实例化控制器。
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        //实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        //规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        //实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
 
        /*
         * 对于每个爬行，需要添加一些种子URL。这些都是第一次
		 * 获取的URL，然后爬行器开始跟随链接
		 * 在这些页面中找到
         */
        controller.addSeed("http://newhouse.gz.fang.com/house/s/");

        /*
         * 开始爬行。这是一个阻塞操作，意思是你的代码
         * 只有在爬行完成后才会到达这条线。
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
        
        System.out.println("低于2W楼盘数量" +MyCrawler.count);

	}
}
