package whu.graph.pay.service;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.session.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import whu.graph.pay.domain.Binding;
import whu.graph.pay.domain.Relationship;
import whu.graph.pay.domain.Use;
import whu.graph.pay.repository.RelationshipRepository;


@Service
public class RelationshipService {
	@Autowired RelationshipRepository relationshipRepository;
	UtilService us=new UtilService();

	@Autowired
	Session session;

	
	@Transactional(readOnly=true)//通过节点Id扩展节点
	public Map<String,List<Map<String, Object>>> extendById(Long id,Map<String,List<Map<String, Object>>> old) {
		Collection<Relationship> result=relationshipRepository.extendById(id);
		System.out.print(result.size()+"\n");
		return us.toD3FormatByExtend(result,old,id);
	}
	
//	public Map<String,List<Map<String, Object>>> extendById1(Long id,Map<String,List<Map<String, Object>>> old) {
//		Collection<Use> result=relationshipRepository.extendById1(id);
//		return us.toD3FormatByExtend1(result,old,id);
//	}
	
}
