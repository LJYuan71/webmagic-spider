package com.ljyuan.test;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.org.cnnvd.dao.CnnvdDao;
import cn.org.cnnvd.pojo.Cnnvd;

public class TestCnnvd {

	public static void main(String[] args) {
		List<Cnnvd> cnnvds = new ArrayList<Cnnvd>();
		
		Cnnvd cnnvd = new Cnnvd();
		cnnvd.setCnnvdId("1");
		Cnnvd cnnvd2 = new Cnnvd();
		cnnvd.setCnnvdId("2");
		Cnnvd cnnvd3 = new Cnnvd();
		cnnvd.setCnnvdId("3");
		Cnnvd cnnvd4 = new Cnnvd();
		cnnvd.setCnnvdId("4");
		cnnvds.add(cnnvd);
		cnnvds.add(cnnvd2);
		cnnvds.add(cnnvd3);
		cnnvds.add(cnnvd4);
		
		ApplicationContext ctx=null;
		int total = 0;
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext-common.xml");
			CnnvdDao cnnvdDao = (CnnvdDao) ctx.getBean("cnnvdDao");
			System.out.println("================执行数据库保存数据操作==================");
			total = cnnvdDao.addCnnvdBatch(cnnvds);
			System.out.println("================执行数据库保存数据操作==================");
		} catch (Exception e) {
			//log.error("执行保存数据到数据库失败！！", e);
			System.out.println(e.getMessage());
		}

	}

}
