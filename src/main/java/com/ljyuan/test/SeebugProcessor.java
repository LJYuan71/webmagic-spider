package com.ljyuan.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  <br>
 * @since 0.5.1
 */
public class SeebugProcessor implements PageProcessor {

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(1*1000).setTimeOut(300 * 60 * 1000)
            //.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
            //.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            //.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .setCharset("utf-8");
    
    private static int size = 0;// 共抓取到的文章数量
    private static List<SeebugRepo> seebugList = new ArrayList<SeebugRepo>();

    @Override
    public void process(Page page) {
    	//分页列表页URL
    	List<String> urls = page.getHtml().css("ul.pagination").links().all();
    	page.addTargetRequests(urls);
    	if(urls.size()>0){
    		System.out.println("==============================");
    		System.out.println("urls:"+urls.toString());
    		System.out.println("==============================");
    	}
    	//每个分页中文章页URL
        page.addTargetRequests(page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").all());
        
        SeebugRepo seebugRepo = new SeebugRepo();
        seebugRepo.setBugName(page.getHtml().xpath("//h1[@id='j-vul-title']/text()").toString());
        seebugRepo.setBugId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl/dd[@class='text-gray']/a/text()").toString());
        seebugRepo.setBugFindDate(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[2]/dd/text()").toString());
        seebugRepo.setBugSubmitDate(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[3]/dd/text()").toString());
        seebugRepo.setBugType(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[5]/dd/a/text()").toString());
        seebugRepo.setCveId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CVE-ID']/dd/a/allText()").toString());
        seebugRepo.setCnnvdId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CNNVD-ID']/dd/allText()").toString());
        seebugRepo.setCnvdId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CNVD-ID']/dd/allText()").toString());
        seebugRepo.setBugAuthor(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][2]//dl[4]/dd/a/text()").toString());
        seebugRepo.setBugSubmitter(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][2]//dl[5]/dd/allText()").toString());
        seebugRepo.setBugOutline(page.getHtml().xpath("//div[@class='padding-md']/allText()").toString());
        seebugRepo.setZoomEyeDork(page.getHtml().xpath("//dl[@data-type='Dork']/dd/a/text()").toString());
        seebugRepo.setAffectsComponent(page.getHtml().xpath("//section[@id='j-vul-basic-info']/dl[2]/dd/allText()").toString());
        String level = page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[4]/dd/div/@class").toString();
        if (level != null) {
			if(level.indexOf("low") != -1){
				seebugRepo.setBugLevel(1);
			}else if(level.indexOf("mid") != -1){
				seebugRepo.setBugLevel(2);
			}else if(level.indexOf("high") !=-1 ){
				seebugRepo.setBugLevel(3);
			}
		}
        if(seebugRepo != null && seebugRepo.getBugId() == null){
        	page.setSkip(true);
        }else {
        	//System.out.println("Bug_name:"+seebugRepo.getBug_name());
        	//page.putField("seebugRepo", seebugRepo);
        	synchronized(this){
        	seebugList.add(seebugRepo);
        	if(seebugList.size() == 1000){
        		new SeebugDao().add(seebugList);
        		seebugList.clear();
        	}
        	}
        	size ++;
        	System.out.println("seebugID:"+seebugRepo.getBugId()+"   seebugListSize:"+seebugList.size());
		}
		
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
    	Timestamp startTime2 = new Timestamp(System.currentTimeMillis());
    	Spider.create(new SeebugProcessor()).
                addUrl("https://www.seebug.org/vuldb/vulnerabilities").
               // scheduler(new RedisScheduler("127.0.0.1")).
                //addUrl("https://www.seebug.org/vuldb/topics/2").
                //addPipeline(new JsonFilePipeline("E:\\webmagic\\")).
                thread(5).run();
        if(seebugList!=null){
        	new SeebugDao().add(seebugList);
        	seebugList.clear();
        }
        long totalTime = (System.currentTimeMillis()-startTime)/1000;
        System.out.println("【爬虫结束】共抓取" + size + "篇文章,共耗时"+totalTime+"秒,已保存到数据库，请查收！");
        System.out.println("此次爬虫开始于"+startTime2+",结束于"+new Timestamp(System.currentTimeMillis()));
    }
}
