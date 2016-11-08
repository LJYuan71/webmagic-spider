package cn.org.cnvd.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.seebug.pojo.Seebug;
import org.seebug.processor.SeebugProcessor;
import org.seebug.service.SeebugService;
import org.seebug.service.impl.SeebugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.cnvd.pojo.Cnvd;
import cn.org.cnvd.service.impl.CnvdServiceImpl;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CnvdProcessor implements PageProcessor {
	

	private static final Logger log = Logger.getLogger(SeebugProcessor.class);
	
	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(1000).setTimeOut(30 * 60 * 1000)
			//设置浏览器代理
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            //.addHeader("Connection", "keep-alive")
            //添加cookie之前一定要先设置其对应的主机地址，否则cookie信息不生效
            .setDomain("www.cnvd.org.cn")
            .addHeader("Referer", "http://www.cnvd.org.cn/")
            .addCookie("FSSBBIl1UgzbN7N80D", "uk2falqQwhojO2UwhUrQZszqfO0UeXIhJWjlW2tz")
            .addCookie("FSSBBIl1UgzbN7N80K", "0oLuZMWO1eMK2fBRd3cRerWrRWERsiqqHMBJsEYL5FccAAJvguwI_4uAfnwCbjbtUW")
            .addCookie("FSSBBIl1UgzbN7N80S", "E0qMqdPlBLgXYMngLcSKZ")
            .addCookie("JSESSIONID", "22AB0C23FCB0CE5A4940D211CDF199D0")
            .addCookie("__jsl_clearance", "1478315123.54|0|qogrpu8jsCRkiYMG%2FqaODzQz4ic%3D")
            .addCookie("__jsluid", "243d6ce3a83b298cd3d1168fffca4ac2")
            .addCookie("bdshare_firstime", "1478225070474")
            .setCharset("utf-8");
	
	private static int size = 0;// 共抓取到的文章数量
    private static List<Cnvd> cnvdList = new ArrayList<Cnvd>();

	@Override
	public void process(Page page) {
		//分页列表页URL
    	//List<String> urls = page.getHtml().css("div.list").links().all();
    	//page.addTargetRequests(urls);
    	/*
    	if(urls.size()>0){
    		System.out.println("urls:"+urls.toString());
    		System.out.println("==============================");
    	}*/
    	//每个分页中文章页URL
    	String cnvdLink = page.getHtml().xpath("//tbody[@id='tr']").links().toString();
    	System.out.println("cnvdLink:"+cnvdLink);
        //page.addTargetRequests(page.getHtml().xpath("//tbody[@id='tr']").links().all());
        
        Cnvd cnvd = new Cnvd();
        int i = 0;
        if(i==0 && cnvdLink != null){
        	//log.info("cnvd:cnvdLink != null  "+page.getHtml());i++;
        }
        if(i==1 && cnvdLink == null){
        	//log.info("cnvd:cnvdLink == null  "+page.getHtml());i++;
        }
        cnvd.setCnvdTitle(page.getHtml().xpath("//div[@class='blkContainerSblk']/h1/text()").toString());
        String submitter = page.getHtml().xpath("//div[@class='artInfo']/span[2]/text()").toString();
        if(submitter != null){
        	cnvd.setBugSubmitter(submitter.replace("报送者:",""));
        }
        cnvd.setCnvdId(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[1]/td[2]/text()").toString());
        cnvd.setPublishDate(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[2]/td[2]/text()").toString());
        cnvd.setHarmLevel(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[3]/td[2]/text()").toString());
        cnvd.setHarmDescribe(page.getHtml().xpath("//div[@id='showDiv']/table/tbody/allText()").toString());
        String bugScore = page.getHtml().xpath("//div[@id='showDiv']/table/div/text()").toString();
        if(bugScore != null ){
        	cnvd.setBugScore(bugScore.replace("漏洞评分 ：", ""));
        }
        cnvd.setEffectProduct(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[4]/td[2]/allText()").toString());
        cnvd.setBugtraqId(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[5]/td[2]/a/text()").toString());
        cnvd.setCveId(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[6]/td[2]/a/text()").toString());
        cnvd.setBugDescribe(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[7]/td[2]/text()").toString());
        cnvd.setReferenceLink(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[8]/td[2]/a/text()").toString());
        cnvd.setBugSolutions(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[9]/td[2]/text()").toString());
        cnvd.setBugFinder(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[10]/td[2]/text()").toString());
        cnvd.setVendorPatches(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[11]/td[2]/text()").toString());
        cnvd.setValidateInfo(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[12]/td[2]/text()").toString());
        cnvd.setSubmittDate(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[13]/td[2]/text()").toString());
        cnvd.setIncludedDate(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[14]/td[2]/text()").toString());
        cnvd.setUpdateTime(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[15]/td[2]/text()").toString());
        cnvd.setBugAccessory(page.getHtml().xpath("//table[@class='gg_detail']/tbody/tr[16]/td[2]/text()").toString());
        
        
        System.out.println("cnvd:"+cnvd.toString());
        if(cnvd != null && cnvd.getCnvdId() == null){
        	page.setSkip(true);
        }else {
        	synchronized(this){
        		cnvdList.add(cnvd);
        	if(cnvdList.size() == 1000){
        		int total = -1;
        		try {
        			//total = new CnvdServiceImpl().addCnvdBatch(cnvdList);
        			cnvdList.clear();
        			log.info("执行保存数据到数据库成功！本次批量执行了"+total+"条数据的保存操作。");
				} catch (Exception e) {
					log.info("执行保存数据到数据库失败！");
				}
        	}
        	}
        	size ++;
        	System.out.println("cnvdID:"+cnvd.getCnvdId()+"   cnvdListSize:"+cnvdList.size());
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
		CnvdProcessor.size = size;
	}

	public static List<Cnvd> getCnvdList() {
		return cnvdList;
	}

	public static void setCnvdList(List<Cnvd> cnvdList) {
		CnvdProcessor.cnvdList = cnvdList;
	}

	
	

}
