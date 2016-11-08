package cn.org.cnvd.dao;

import java.util.List;

import cn.org.cnvd.pojo.Cnvd;

public interface CnvdDao {
	
	public int addCnvdBatch(List<Cnvd> cnvds);

}
