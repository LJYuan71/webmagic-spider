package cn.org.cnnvd.service.impl;

import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.org.cnnvd.dao.CnnvdDao;
import cn.org.cnnvd.pojo.Cnnvd;
import cn.org.cnnvd.service.CnnvdService;

public class CnnvdServiceImpl implements CnnvdService {
	
	private static final Logger log = Logger.getLogger(CnnvdServiceImpl.class);
	
	@Override
	public int addCnnvdBatch(List<Cnnvd> cnnvds) {
		ApplicationContext ctx=null;
		int total = 0;
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext-common.xml");
			CnnvdDao cnnvdDao = (CnnvdDao) ctx.getBean("cnnvdDao");
			System.out.println("================执行数据库保存数据操作==================");
			total = cnnvdDao.addCnnvdBatch(cnnvds);
		} catch (Exception e) {
			log.error("执行保存数据到数据库失败！！", e);
		}
		return total;
	}

}
