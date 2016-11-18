package org.seebug.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.seebug.pojo.Seebug;
import org.seebug.service.impl.SeebugServiceImpl;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class SeebugProcessor implements PageProcessor{
	
	private static final Logger log = Logger.getLogger(SeebugProcessor.class);
	
	private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(1000).setTimeOut(30 * 60 * 1000)
			//添加cookie之前一定要先设置主机地址，否则cookie信息不生效
			.setDomain("www.seebug.org")
			.addCookie("Hm_lpvt_6b15558d6e6f640af728f65c4a5bf687", "1478761751")
			.addCookie("Hm_lvt_6b15558d6e6f640af728f65c4a5bf687", "1478573076,1478659748,1478739792,1478740619")
			.addCookie("Hm_lvt_d7682ab43891c68a00de46e9ce5b76aa", "1478054037,1478595095")
			.addCookie("__jsluid", "c82d236bd942d89c9e896a6a63bba95f")
			.addCookie("csrftoken", "JAQRChaj4EPygXcVUBAD2Ii55ouMUp2l")
			.addCookie("messages", "\"99efc7bb67f870f74e5926f2bdb6f8204fc2f0aa$[[\\\"__json_message\\\"\\0540\\05425\\054\\\"Login succeeded. Welcome\\054 LJYuan.\\\"]\\054[\\\"__json_message\\\"\\0540\\05425\\054\\\"You are logged in as LJYuan.\\\"]\\054[\\\"__json_message\\\"\\0540\\05425\\054\\\"You are logged in as LJYuan.\\\"]\\054[\\\"__json_message\\\"\\0540\\05425\\054\\\"You are logged in as LJYuan.\\\"]]\"")
			.addCookie("sessionid", "9sw4xpcf0m1j3ehdgrrexuxur5ciflze")
			//设置浏览器代理
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
            //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch, br")
            .addHeader("Connection", "keep-alive")
            //.setHttpProxyPool(httpProxy.getHttpProxyList())
            .setHttpProxy(new HttpHost("183.129.178.14",8080))
            .addHeader("Referer", "https://www.seebug.org/")
            .setCharset("utf-8");
	
	private static int size = 0;// 共抓取到的文章数量
	private static int successTotal = 0;
    private static List<Seebug> seebugList = new ArrayList<Seebug>();
    private static long startMill = System.currentTimeMillis();
    private static int AA = 0;

	@Override
	public void process(Page page) {
		//分页列表页URL
    	List<String> urls = page.getHtml().css("ul.pagination").links().all();
    	page.addTargetRequests(urls);
    	if(urls.size()>0){
    		//System.out.println("==============================");
    		System.out.println("urls:"+urls.toString());
    		System.out.println("==============================");
    	}
    	//String seebug2 = page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").toString();
    	//每个分页中文章页URL
    	
        page.addTargetRequests(page.getHtml().xpath("//div[@class='table-responsive']//tr//td[@class='vul-title-wrapper']/a/@href").all());
        long end = (System.currentTimeMillis() - startMill)/1000;
        if(end >= 20 || AA==15 ){
        	try {
				Thread.sleep(60*1000);
				startMill = System.currentTimeMillis();
				AA=0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        Seebug seebug = new Seebug();
        Html html = page.getHtml();
        seebug.setBugName(html.xpath("//h1[@id='j-vul-title']/text()").toString());
        seebug.setBugId(html.xpath("//section[@id='j-vul-basic-info']//div[@class='row']//dl/dd[@class='text-gray']/a/text()").toString());
        seebug.setBugFindDate(html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][1]//dl[2]/dd/text()").toString());
        seebug.setBugSubmitDate(html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][1]//dl[3]/dd/text()").toString());
        seebug.setBugType(html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][2]//dl[1]/dd/a/text()").toString());
        String affectsComponent = html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][2]//dl[2]/dd/a/text()").toString();
        if(affectsComponent != null && !affectsComponent.trim().isEmpty()){
        	seebug.setAffectsComponent(affectsComponent);
        }
        seebug.setBugAuthor(html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][2]//dl[3]/dd/a/text()").toString());
        seebug.setBugSubmitter(html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][2]//dl[4]/dd/allText()").toString());
        String cveid = html.xpath("//dl[@data-type='CVE-ID']/dd/a/text()").toString();
        if(cveid != null && cveid.indexOf("补充") == -1){
        	seebug.setCveId(cveid);
        }
        String cnnvdid = html.xpath("//dl[@data-type='CNNVD-ID']/dd/a/text()").toString();
        if(cnnvdid != null && cnnvdid.indexOf("补充") == -1 ){
        	seebug.setCnnvdId(cnnvdid);
        }
        String cnvdid = html.xpath("//dl[@data-type='CNVD-ID']/dd/a/text()").toString();
        if(cnvdid != null && cnvdid.indexOf("补充") == -1 ){
        	seebug.setCnvdId(cnvdid);
        }
        String dork = html.xpath("//dl[@data-type='Dork']/dd/a/text()").toString();
        if(dork != null && dork.indexOf("补充") == -1 ){
        	seebug.setZoomEyeDork(dork);
        }
        seebug.setBugDescribe(html.xpath("//div[@id='j-md-detail']/tidyText()").toString());
        seebug.setReferenceURL(html.xpath("//section[@class='vul-detail-section']//div[@class='panel-body']/ul/allText()").toString());
        String level = html.xpath("//section[@id='j-vul-basic-info']//div[@class='col-md-4'][1]//dl[4]/dd/div/@class").toString();
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
        	if(seebugList.size() == 60){
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
        	size ++;AA++;
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
