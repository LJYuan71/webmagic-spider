package org.seebug.timer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.seebug.pojo.Seebug;
import org.seebug.processor.SeebugProcessor;
import org.seebug.service.SeebugService;
import org.seebug.service.impl.SeebugServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

/**
 * 定时任务
 * @author Administrator
 *
 */
@Component
public class ToTimer {
	
	private static final Logger log = Logger.getLogger(ToTimer.class);
	
	//@Scheduled(cron = "0 40 18 * * ? ")
	public void runSeebug(){
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		System.out.println(startTime+"开始执行定时爬虫任务！！！");
		int size = 0;
		long startTimeMil = System.currentTimeMillis();
		try {
			log.info(startTime+"开始执行Seebug的爬虫任务！");
			SeebugProcessor seebugProcessor = new SeebugProcessor();
			Spider.create(seebugProcessor).
			addUrl("https://www.seebug.org/vuldb/vulnerabilities").
			thread(5).run();
		} catch (Exception e) {
			log.error("执行Spider.create失败！！", e);
		}
		//把剩下的不足1000的那部分保存到数据库
		try {
			List<Seebug> seebugList = SeebugProcessor.getSeebugList();
			if(seebugList != null){
				new SeebugServiceImpl().addSeebugBatch(seebugList);
				seebugList.clear();
				log.error("执行保存剩余数据到数据库成功！！");
			}
		} catch (Exception e) {
			log.error("执行保存剩余数据到数据库失败！！", e);
		}
		long totalTome = (System.currentTimeMillis()-startTimeMil)/1000;
		size = SeebugProcessor.getSize();
		log.info("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库，请查收！");
        System.out.println("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库，请查收！");
	}

}
