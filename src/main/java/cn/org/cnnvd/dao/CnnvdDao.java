package cn.org.cnnvd.dao;

import java.util.List;

import cn.org.cnnvd.pojo.Cnnvd;

public interface CnnvdDao {
	
	public int addCnnvdBatch(List<Cnnvd> cnnvds);

}
