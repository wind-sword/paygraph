package whu.graph.pay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import whu.graph.pay.service.AccountService;
import whu.graph.pay.service.RelationshipService;
import whu.graph.pay.service.UpdateService;
import whu.graph.pay.service.UtilService;


@RestController
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Searchcontroller {
	final AccountService accountService;
	final RelationshipService relationshipService;
	UtilService us = new UtilService();
	public static Map<String, List<Map<String, Object>>> saveOld = new HashMap<String, List<Map<String, Object>>>();// 保存查询结果
	@Autowired
	UpdateService uds;
	@Autowired
	public Searchcontroller(AccountService accountService,RelationshipService relationshipService) {		
		this.accountService = accountService;
		this.relationshipService=relationshipService;
	}
	
	@RequestMapping(value = "/findByCountName.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<Map<String, Object>>> findByCountName(@Param("name") String name) {
		saveOld = accountService.findByCountName(name.equals("") ? "武汉" : name);
		return saveOld;
	}
	
	@RequestMapping(value = "/findByCountNumber.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<Map<String, Object>>> findByCountNumber(@Param("name") String name) {
		saveOld = accountService.findByCountNumber(name.equals("") ? "1" : name);
		return saveOld;		
	}
	
	@RequestMapping(value = "/extendById.do", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" }) // 通过id扩展相应节点
	@ResponseBody
	public Map<String, List<Map<String, Object>>> extendById(@Param("id") Long id, @Param("nodes") String nodes,
			@Param("links") String links) {

		saveOld.clear();
		saveOld = us.update(nodes, links);
		saveOld = relationshipService.extendById(id, saveOld);
		return saveOld;
	}
	
	@RequestMapping(value = "/findByCompanyName.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<Map<String, Object>>> findByCompanyName(@Param("name") String name) {
		saveOld = accountService.findByCompanyName(name.equals("") ? "武汉" : name);
		return saveOld;
	}
	
	@RequestMapping(value = "/findByCompanyJgdm.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<Map<String, Object>>> findByCompanyNo(@Param("name") String name) {
		saveOld = accountService.findByCompanyNo(name.equals("") ? "武汉" : name);
		return saveOld;
	}
	
	@RequestMapping(value = "/graph.do", method = RequestMethod.POST) // 更新图数据
	@ResponseBody
	public String graph(@RequestParam(value = "limit", required = false) Integer limit)throws Exception {
		uds.updateData();
		return "[]";
	}

}
