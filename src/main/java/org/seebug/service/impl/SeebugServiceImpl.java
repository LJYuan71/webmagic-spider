package org.seebug.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.seebug.dao.SeebugDao;
import org.seebug.pojo.Seebug;
import org.seebug.service.SeebugService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SeebugServiceImpl implements SeebugService {
	
	private static final Logger log = Logger.getLogger(SeebugServiceImpl.class);
	
	@Override
	public int addSeebugBatch(List<Seebug> seebugs) throws Exception{
		ApplicationContext ctx=null;
		int total = 0;
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext-common.xml");
			SeebugDao seebugDao = (SeebugDao) ctx.getBean("seebugDao");
			System.out.println("================执行数据库保存数据操作==================");
			total = seebugDao.addSeebugBatch(seebugs);
		} catch (Exception e) {
			log.error("执行保存数据到数据库失败！！", e);
		}
		return total;
	}


}
