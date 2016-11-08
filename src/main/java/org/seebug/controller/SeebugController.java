package org.seebug.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seebug.pojo.Seebug;
import org.seebug.processor.SeebugProcessor;
import org.seebug.service.SeebugService;
import org.seebug.service.impl.SeebugServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import us.codecraft.webmagic.Spider;

@Controller
@RequestMapping("/seebug")
public class SeebugController {
	
	@Resource
	private SeebugService seebugService;
	
	@RequestMapping("/seebugSpider")
	@ResponseBody
	public Map<String, Object> seebugSpider(HttpServletRequest request, HttpServletResponse response){
		int size = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		return map;
	}
	
	
	

}
