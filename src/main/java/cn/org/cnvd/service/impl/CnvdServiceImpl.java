package cn.org.cnvd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.seebug.dao.SeebugDao;
import org.seebug.pojo.Seebug;
import org.seebug.service.impl.SeebugServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.org.cnvd.dao.CnvdDao;
import cn.org.cnvd.pojo.Cnvd;
import cn.org.cnvd.service.CnvdService;

public class CnvdServiceImpl implements CnvdService {
	
	private static final Logger log = Logger.getLogger(SeebugServiceImpl.class);
	
	@Override
	public int addCnvdBatch(List<Cnvd> cnvds) {
		ApplicationContext ctx=null;
		int total = 0;
		try {
			ctx=new ClassPathXmlApplicationContext("applicationContext-common.xml");
			CnvdDao cnvdDao = (CnvdDao) ctx.getBean("cnvdDao");
			System.out.println("================执行数据库保存数据操作==================");
			total = cnvdDao.addCnvdBatch(cnvds);
		} catch (Exception e) {
			log.error("执行保存数据到数据库失败！！", e);
		}
		return total;
	}

}
