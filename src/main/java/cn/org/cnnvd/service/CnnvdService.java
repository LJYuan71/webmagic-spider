package cn.org.cnnvd.service;

import java.util.List;

import cn.org.cnnvd.pojo.Cnnvd;

public interface CnnvdService {
	
	public int addCnnvdBatch(List<Cnnvd> Cnnvds);

}
