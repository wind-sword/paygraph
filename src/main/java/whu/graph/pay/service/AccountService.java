package whu.graph.pay.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whu.graph.pay.domain.ElseSecB;
import whu.graph.pay.domain.Node;
import whu.graph.pay.domain.PerPayAccount;
import whu.graph.pay.repository.PerPayAccountRepository;


@Service
public class AccountService {
	@Autowired PerPayAccountRepository  perpayaccountRepository;
	
	private Map<String, List<Map<String, Object>>> toD3FormatByrel(Collection<Node> Node) {
		
		UtilService us=new UtilService();
		
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();	
		
		Iterator<Node> result = Node.iterator();
		
		while (result.hasNext()) {				
			Node account=result.next();
			nodes.add(account.map());								
		}					
			return us.map("nodes", nodes, "links", rels);				
	}
	


	public Map<String, List<Map<String, Object>>> findByCountName(String accname) {
		Collection<Node> result=perpayaccountRepository.findByAccName(accname);
		return toD3FormatByrel(result);
	}
	
	public Map<String, List<Map<String, Object>>> findByCountNumber(String accno) {
		Collection<Node> result=perpayaccountRepository.findByAccNo(accno);
		return toD3FormatByrel(result);
	}
	
	public Map<String, List<Map<String, Object>>> findByCompanyName(String cname) {
		Collection<Node> result=perpayaccountRepository.findByComName(cname);
		return toD3FormatByrel(result);
	}
	
	public Map<String, List<Map<String, Object>>> findByCompanyNo(String cno) {
		Collection<Node> result=perpayaccountRepository.findByComNo(cno);
		return toD3FormatByrel(result);
	}
}
