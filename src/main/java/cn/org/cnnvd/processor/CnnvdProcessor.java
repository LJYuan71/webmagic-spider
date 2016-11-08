package cn.org.cnnvd.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.org.cnnvd.pojo.Cnnvd;
import cn.org.cnnvd.service.impl.CnnvdServiceImpl;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CnnvdProcessor implements PageProcessor {
	

	private static final Logger log = Logger.getLogger(CnnvdProcessor.class);
	
	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(1000).setTimeOut(30 * 60 * 1000)
			//设置浏览器代理
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            //.addHeader("Connection", "keep-alive")
            //添加cookie之前一定要先设置其对应的主机地址，否则cookie信息不生效
            //.setDomain("www.cnnvd.org.cn")
            .addHeader("Referer", "http://www.cnnvd.org.cn/")
            //.addCookie("FSSBBIl1UgzbN7N80D", "uk2falqQwhojO2UwhUrQZszqfO0UeXIhJWjlW2tz")
            //.addCookie("FSSBBIl1UgzbN7N80K", "0oLuZMWO1eMK2fBRd3cRerWrRWERsiqqHMBJsEYL5FccAAJvguwI_4uAfnwCbjbtUW")
            //.addCookie("FSSBBIl1UgzbN7N80S", "E0qMqdPlBLgXYMngLcSKZ")
            //.addCookie("JSESSIONID", "22AB0C23FCB0CE5A4940D211CDF199D0")
            //.addCookie("__jsl_clearance", "1478315123.54|0|qogrpu8jsCRkiYMG%2FqaODzQz4ic%3D")
            //.addCookie("__jsluid", "243d6ce3a83b298cd3d1168fffca4ac2")
            //.addCookie("bdshare_firstime", "1478225070474")
            .setCharset("utf-8");
	
	private static int size = 0;// 共抓取到的文章数量
	private static int successTotal = 0;//成功保存到数据库的条数
    private static List<Cnnvd> cnnvdList = new ArrayList<Cnnvd>();

	@Override
	public void process(Page page) {
		//分页列表页URL
    	//List<String> urls = page.getHtml().regex("http://www\\.cnnvd\\.org\\.cn/vulnerability/index/p/\\d+").all();
		List<String> urls = page.getHtml().css("div.dispage").links().all();
    	page.addTargetRequests(urls);
    	if(urls.size()>0){
    		System.out.println("urls:"+urls.toString());
    		System.out.println("==============================");
    	}
    	//每个分页中文章页URL
    	//String cnnvdLink = page.getHtml().xpath("//table[@class='qtld_details sortable']//td[@width='45%']/a/@href").all().toString();
    	//System.out.println("cnvdLink:"+cnnvdLink);
        page.addTargetRequests(page.getHtml().xpath("//table[@class='qtld_details sortable']//td[@width='45%']/a/@href").all());
        
        Cnnvd cnnvd = new Cnnvd();
        cnnvd.setCnnvdName(page.getHtml().xpath("//table[@class='details']/tbody/tr[1]/td[2]/text()").toString());
        cnnvd.setCnnvdId(page.getHtml().xpath("//table[@class='details']/tbody/tr[2]/td[2]/text()").toString());
        cnnvd.setPublishDate(page.getHtml().xpath("//table[@class='details']/tbody/tr[3]/td[2]/a/text()").toString());
        cnnvd.setUpdateTime(page.getHtml().xpath("//table[@class='details']/tbody/tr[4]/td[2]/a/text()").toString());
        cnnvd.setHarmLevel(page.getHtml().xpath("//table[@class='details']/tbody/tr[5]/td[2]/a/text()").toString());
        cnnvd.setBugType(page.getHtml().xpath("//table[@class='details']/tbody/tr[6]/td[2]/a/text()").toString());
        cnnvd.setMenaceType(page.getHtml().xpath("//table[@class='details']/tbody/tr[7]/td[2]/a/text()").toString());
        cnnvd.setCveId(page.getHtml().xpath("//table[@class='details']/tbody/tr[8]/td[2]/a/text()").toString());
        cnnvd.setBugSynopsis(page.getHtml().xpath("//table[@id='__01']//table/tbody/tr[2]//div[@class='cont_details']/allText()").toString());
        cnnvd.setBugNotice(page.getHtml().xpath("//table[@id='__01']//table/tbody/tr[3]//div[@class='cont_details']/tidyText()").toString());
        cnnvd.setReferenceURL(page.getHtml().xpath("//table[@id='__01']//table/tbody/tr[4]//div[@id='top3']/tidyText()").toString());
        cnnvd.setPatches(page.getHtml().xpath("//table[@width='230']//table[1]//table/allText()").toString());
        cnnvd.setEffectEntity(page.getHtml().xpath("//table[@width='230']//table[2]//table/allText()").toString());
        
        if(cnnvd != null && cnnvd.getCnnvdId() == null){
        	page.setSkip(true);
        }else {
        	synchronized(this){
        		cnnvdList.add(cnnvd);
        	if(cnnvdList.size() == 1000){
        		int total = -1;
        		try {
        			total = new CnnvdServiceImpl().addCnnvdBatch(cnnvdList);
        			cnnvdList.clear();
        			log.info("执行保存数据到数据库成功！本次批量执行了"+total+"条数据的保存操作。");
        			successTotal += total;
				} catch (Exception e) {
					log.info("执行保存数据到数据库失败！");
				}
        	}
        	}
        	size ++;
        	System.out.println("cnnvdID:"+cnnvd.getCnnvdId()+"   cnnvdListSize:"+cnnvdList.size());
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
		CnnvdProcessor.size = size;
	}

	public static List<Cnnvd> getCnnvdList() {
		return cnnvdList;
	}


	public static int getSuccessTotal() {
		return successTotal;
	}

	public static void setSuccessTotal(int successTotal) {
		CnnvdProcessor.successTotal = successTotal;
	}

	public static void setCnnvdList(List<Cnnvd> cnnvdList) {
		CnnvdProcessor.cnnvdList = cnnvdList;
	}

	
	

}
