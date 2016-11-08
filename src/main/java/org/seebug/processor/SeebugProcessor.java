package org.seebug.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.seebug.pojo.Seebug;
import org.seebug.service.impl.SeebugServiceImpl;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SeebugProcessor implements PageProcessor{
	
	private static final Logger log = Logger.getLogger(SeebugProcessor.class);
	
	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(1000).setTimeOut(30 * 60 * 1000)
			//设置浏览器代理
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            //.addHeader("Connection", "keep-alive")
            .addHeader("Referer", "https://www.seebug.org/")
            .setCharset("utf-8");
	
	private static int size = 0;// 共抓取到的文章数量
	private static int successTotal = 0;
    private static List<Seebug> seebugList = new ArrayList<Seebug>();

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
    	//String seebug2 = page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").toString();
    	//每个分页中文章页URL
    	System.out.println("suubugLink:"+page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").toString());
        page.addTargetRequests(page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").all());
        Seebug seebug = new Seebug();
        seebug.setBugName(page.getHtml().xpath("//h1[@id='j-vul-title']/text()").toString());
        seebug.setBugId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl/dd[@class='text-gray']/a/text()").toString());
        seebug.setBugFindDate(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[2]/dd/text()").toString());
        seebug.setBugSubmitDate(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[3]/dd/text()").toString());
        seebug.setBugType(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[5]/dd/a/text()").toString());
        seebug.setCveId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CVE-ID']/dd/a/allText()").toString());
        seebug.setCnnvdId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CNNVD-ID']/dd/allText()").toString());
        seebug.setCnvdId(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl[@data-type='CNVD-ID']/dd/allText()").toString());
        seebug.setBugAuthor(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][2]//dl[4]/dd/a/text()").toString());
        seebug.setBugSubmitter(page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][2]//dl[5]/dd/allText()").toString());
        seebug.setBugOutline(page.getHtml().xpath("//div[@class='padding-md']/allText()").toString());
        seebug.setZoomEyeDork(page.getHtml().xpath("//dl[@data-type='Dork']/dd/a/text()").toString());
        seebug.setAffectsComponent(page.getHtml().xpath("//section[@id='j-vul-basic-info']/dl[2]/dd/allText()").toString());
        String level = page.getHtml().xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-6'][1]//dl[4]/dd/div/@class").toString();
        if (level != null) {
			if(level.indexOf("low") != -1){
				seebug.setBugLevel(1);
			}else if(level.indexOf("mid") != -1){
				seebug.setBugLevel(2);
			}else if(level.indexOf("high") !=-1 ){
				seebug.setBugLevel(3);
			}
		}
        if(seebug != null && seebug.getBugId() == null){
        	page.setSkip(true);
        }else {
        	synchronized(this){
        	seebugList.add(seebug);
        	if(seebugList.size() == 1000){
        		int total = -1;
        		try {
        			total = new SeebugServiceImpl().addSeebugBatch(seebugList);
        			seebugList.clear();
        			successTotal += total;
        			log.info("执行保存数据到数据库成功！本次批量执行了"+total+"条数据的保存操作。");
				} catch (Exception e) {
					log.info("执行保存数据到数据库失败！");
				}
        	}
        	}
        	size ++;
        	System.out.println("seebugID:"+seebug.getBugId()+"   seebugListSize:"+seebugList.size());
		}
		
		
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		SeebugProcessor.size = size;
	}

	public static List<Seebug> getSeebugList() {
		return seebugList;
	}

	public static void setSeebugList(List<Seebug> seebugList) {
		SeebugProcessor.seebugList = seebugList;
	}

	public static int getSuccessTotal() {
		return successTotal;
	}

	public static void setSuccessTotal(int successTotal) {
		SeebugProcessor.successTotal = successTotal;
	}
	
	

}
