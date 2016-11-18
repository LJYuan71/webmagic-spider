package io.webmagic.common.timer;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.seebug.pojo.Seebug;
import org.seebug.processor.SeebugProcessor;
import org.seebug.service.SeebugService;
import org.seebug.service.impl.SeebugServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.org.cnnvd.pojo.Cnnvd;
import cn.org.cnnvd.processor.CnnvdProcessor;
import cn.org.cnnvd.service.impl.CnnvdServiceImpl;
import cn.org.cnvd.pojo.Cnvd;
import cn.org.cnvd.processor.CnvdProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

/**
 * 定义所有的定时任务
 * @author Administrator
 *
 */
@Component
public class ToTimer {
	
	private static final Logger log = Logger.getLogger(ToTimer.class);
	
	@Scheduled(cron = "0 18 11 11 11 ? ")
	//public static void main(String[] args){
	public void runSeebug(){
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		System.out.println(startTime+"开始执行定时爬虫任务！！！");
		int size = 0;
		long startTimeMil = System.currentTimeMillis();
		try {
			log.info(startTime+"开始执行Seebug的爬虫任务！");
			Spider.create(new SeebugProcessor()).
			addUrl("https://www.seebug.org/vuldb/vulnerabilities").
			thread(1).run();
		} catch (Exception e) {
			log.error("执行Seebug的Spider.create失败！！", e);
		}
		//把剩下的不足的那部分保存到数据库
		int successTotal = SeebugProcessor.getSuccessTotal();//保存到数据库的成功条数
		try {
			List<Seebug> seebugList = SeebugProcessor.getSeebugList();
			if(seebugList != null){
				successTotal += new SeebugServiceImpl().addSeebugBatch(seebugList);
				seebugList.clear();
				log.error("执行保存剩余数据到数据库成功！！");
			}
		} catch (Exception e) {
			log.error("执行保存剩余数据到数据库失败！！", e);
		}
		long totalTome = (System.currentTimeMillis()-startTimeMil)/1000;
		size = SeebugProcessor.getSize();
		log.info("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已成功保存到数据库"+successTotal+"条，请查收！");
        System.out.println("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库"+successTotal+"条，请查收！");
	}
	
	//@Scheduled(cron = "0 49 16 * * ? ")
	public void runCnvd(){
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		System.out.println(startTime+"开始执行定时爬虫任务！！！");
		int size = 0;
		long startTimeMil = System.currentTimeMillis();
		try {
			log.info(startTime+"开始执行Cnvd的爬虫任务！");
			Spider.create(new CnvdProcessor()).
			addUrl("http://www.cnvd.org.cn/flaw/list.htm?flag=true").
			//addUrl("http://telecom.cnvd.org.cn/").
			addUrl("http://www.cnvd.org.cn/flaw/show/CNVD-2016-10615").
			//addPipeline(new JsonFilePipeline("E:\\webmagic\\")).
			thread(5).run();
		} catch (Exception e) {
			log.error("执行Cnvdd的Spider.create失败！！", e);
		}
		//把剩下的不足1000的那部分保存到数据库
		try {
			List<Cnvd> cnvdList = CnvdProcessor.getCnvdList();
			if(cnvdList != null){
				//new CnvdServiceImpl().addCnvdBatch(cnvdList);
				cnvdList.clear();
				log.error("执行保存剩余数据到数据库成功！！");
			}
		} catch (Exception e) {
			log.error("执行保存剩余数据到数据库失败！！", e);
		}
		long totalTome = (System.currentTimeMillis()-startTimeMil)/1000;
		size = CnvdProcessor.getSize();
		log.info("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库，请查收！");
        System.out.println("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库，请查收！");
	}
	
	//@Scheduled(cron = "0 50 17 9 * ? ")
	public void runCnnvd(){
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		System.out.println(startTime+"开始执行定时爬虫任务！！！");
		int size = 0;
		long startTimeMil = System.currentTimeMillis();
		try {
			log.info(startTime+"开始执行Cnnvd的爬虫任务！");
			Spider.create(new CnnvdProcessor()).
			//addUrl("http://www.cnvd.org.cn/flaw/list.htm?flag=true").
			//addUrl("http://telecom.cnvd.org.cn/").
			addUrl("http://www.cnnvd.org.cn/vulnerability").
			//addPipeline(new JsonFilePipeline("E:\\webmagic\\")).
			thread(5).run();
		} catch (Exception e) {
			log.error("执行Cnnvd的Spider.create失败！！", e);
		}
		//把剩下的不足1000的那部分保存到数据库
		int successTotal = CnnvdProcessor.getSuccessTotal();//保存到数据库的成功条数
		try {
			List<Cnnvd> cnnvdList = CnnvdProcessor.getCnnvdList();
			if(cnnvdList != null){
				successTotal += new CnnvdServiceImpl().addCnnvdBatch(cnnvdList);
				cnnvdList.clear();
				log.error("执行保存剩余数据到数据库成功！！");
			}
		} catch (Exception e) {
			log.error("执行保存剩余数据到数据库失败！！", e);
		}
		long totalTome = (System.currentTimeMillis()-startTimeMil)/1000;
		size = CnnvdProcessor.getSize();
		log.info("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已保存到数据库"+successTotal+"条，请查收！");
        System.out.println("【本次爬虫结束】,爬虫任务开始于"+startTime+",结束于"+new Timestamp(System.currentTimeMillis())+"。共抓取" + size + "篇文章,耗时约" + totalTome + "秒,已成功保存到数据库"+successTotal+"条，请查收！");
	}

}
