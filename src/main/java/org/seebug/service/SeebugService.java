package org.seebug.service;

import java.util.List;

import org.seebug.pojo.Seebug;

public interface SeebugService {
	
	public int addSeebugBatch(List<Seebug> seebugs) throws Exception;

}
