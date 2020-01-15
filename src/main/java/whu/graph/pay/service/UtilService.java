package whu.graph.pay.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import whu.graph.pay.domain.PayAccount;
import whu.graph.pay.domain.Relationship;
import whu.graph.pay.domain.SecB;
import whu.graph.pay.domain.Inst;
import whu.graph.pay.domain.BankAccount;

public class UtilService {
	public Map<String, List<Map<String, Object>>> map(String key1, List<Map<String, Object>> value1, String key2, List<Map<String, Object>> value2) {
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}
	
	public Map<String, List<Map<String, Object>>> update(String nodes,String links){
		
		
		
		List<Map<String,Object>> new_nodes=new ArrayList<>();
		
		JSONArray json_node=JSONArray.fromObject(nodes);
		
		if(json_node.size()>0){
				for(int i=0;i<json_node.size();i++){
					JSONObject job = json_node.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					Map<String, Object> result = new HashMap<String, Object>();
					result=job;			
					new_nodes.add(result);
				}
				
		}

		List<Map<String,Object>> new_links=new ArrayList<>();
		
		JSONArray json_link=JSONArray.fromObject(links);
		
		if(json_link.size()>0){
				for(int i=0;i<json_link.size();i++){
					JSONObject job = json_link.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					Map<String, Object> result = new HashMap<String, Object>();
					int source=0,target=0;
					result=job;
					
					JSONObject sourceJs=(JSONObject) job.get("source");
					//int sourceId=sourceJs.getInt("id");
					String sourceId=sourceJs.getString("id");
					for(int j=0;j<new_nodes.size();j++) {
						if(sourceId.equals(new_nodes.get(j).get("id").toString())) {
							source=j;
						}
					}
					result.put("source",source);
					JSONObject targetJs=(JSONObject) job.get("target");
					
					//int targetId=targetJs.getInt("id");
					String targetId=targetJs.getString("id");
					for(int j=0;j<new_nodes.size();j++) {
						if(targetId.equals(new_nodes.get(j).get("id").toString())) {
							target=j;
						}
					}
					result.put("target", target);
					
					new_links.add(result);
				}
				
		}

		
		return map("nodes",new_nodes,"links",new_links);
	}
	
	public int getIndex(Map<String, Object> node,List<Map<String, Object>> nodes) {
		int index=-1;
		int length=nodes.size();

		for(int i=0;i<length;i++) {
			
			if(node.get("id").toString().equals(nodes.get(i).get("id").toString())){
				index=i;
				break;
			}
			
			
		}
		return index;
	}
	
	public Map<String, List<Map<String, Object>>> toD3FormatByExtend(Collection<Relationship> relationship,Map<String,List<Map<String, Object>>> old,Long id) {
		
		List<Map<String, Object>> nodes = old.get("nodes");
		List<Map<String, Object>> rels =  old.get("links");
		
		
		Map<String,Object> extendNode=new HashMap<>();
		
		extendNode.put("id", id);
		
		int i=getIndex(extendNode, nodes);
		
		for(int j=0;j<=rels.size()-1;j++) {
			if((int)rels.get(j).get("source")==i||(int)rels.get(j).get("target")==i) {
				rels.remove(j);
				j--;
			}
		}
		
		return toD3Format(relationship, nodes, rels);
		
	}
	
//public Map<String, List<Map<String, Object>>> toD3FormatByExtend1(Collection<Use> relationship,Map<String,List<Map<String, Object>>> old,Long id) {
//		
//		List<Map<String, Object>> nodes = old.get("nodes");
//		List<Map<String, Object>> rels =  old.get("links");
//		
//		
//		Map<String,Object> extendNode=new HashMap<>();
//		
//		extendNode.put("id", id);
//		
//		int i=getIndex(extendNode, nodes);
//		
//		for(int j=0;j<=rels.size()-1;j++) {
//			if((int)rels.get(j).get("source")==i||(int)rels.get(j).get("target")==i) {
//				rels.remove(j);
//				j--;
//			}
//		}
//		
//		return toD3Format1(relationship, nodes, rels);
//		
//	}
	private Map<String, List<Map<String, Object>>> toD3Format(Collection<Relationship> relationship,List<Map<String, Object>> nodes,List<Map<String, Object>> rels){

		Iterator<Relationship> result = relationship.iterator();
		while (result.hasNext()) {
			
			Relationship C=result.next();
			
			int source,target,index;
			Map<String, Object>  startNode=new HashMap<>();
			Map<String, Object> endNode=new HashMap<>();
			Map<String, Object> a=new HashMap<>();
			int sum=1;int no=1;
			boolean flagsum=true,flagno=true;
			int flag=0;
			/*startNode.clear();
			endNode.clear();
			a.clear();
			*/
			switch(C.getLabel()) {
			case "绑定":
				startNode=((PayAccount)C.getStartNode()).map();
				endNode = ((BankAccount)C.getEndNode()).map();
				break;
			case "使用":
				startNode=((SecB)C.getStartNode()).map();
				endNode = ((PayAccount)C.getEndNode()).map();
				break;
			case "签约":		
				startNode=((Inst)C.getStartNode()).map();
				endNode = ((SecB) C.getEndNode()).map();
				break;
			case "总交易":		
				startNode=((SecB)C.getStartNode()).map();
				endNode = ((PayAccount) C.getEndNode()).map();
				break;
			case "zjy1":		
				startNode=((PayAccount)C.getStartNode()).map();
				endNode = ((PayAccount) C.getEndNode()).map();
				break;
			case "zjy2":		
				startNode=((BankAccount)C.getStartNode()).map();
				endNode = ((BankAccount) C.getEndNode()).map();
				break;
			case "zjy3":		
				startNode=((BankAccount)C.getStartNode()).map();
				endNode = ((PayAccount) C.getEndNode()).map();
				break;
			}
			index=getIndex(startNode, nodes);
				if(index==-1) {
					nodes.add(startNode);
					source=nodes.size()-1;
				}else {
					source=index;
				}
			index=getIndex(endNode, nodes);
				if(index==-1) {
					nodes.add(endNode);
					target=nodes.size()-1;
				}else {
					target=index;
				}
			a=map("source", source, "target", target,C);

			for(int j=rels.size()-1;j>=0;j--){
				boolean hasa=(int)rels.get(j).get("source")==source&&(int)rels.get(j).get("target")==target;
				boolean hasb=(int)rels.get(j).get("source")==target&&(int)rels.get(j).get("target")==source;
				
				
				if(flag==0) {
					if(hasa||hasb){
						if(flagsum){
						sum=(int)rels.get(j).get("sum")+1;
						flagsum=false;
						}
						
						if(flagno){
						 no=(int)rels.get(j).get("no")+1;
						 flagno=false;
						}	
						rels.get(j).replace("sum", sum);	
					}
				}
			}
			a.put("sum",sum);
			a.put("no",no);
			rels.add(a);
		}
		return map("nodes", nodes, "links", rels);	

	}
//	private Map<String, List<Map<String, Object>>> toD3Format1(Collection<Use> relationship,List<Map<String, Object>> nodes,List<Map<String, Object>> rels){
//
//		Iterator<Use> result = relationship.iterator();
//		
//		while (result.hasNext()) {
//			
//			Use C=result.next();
//			
//			int source,target,index;
//			Map<String, Object>  startNode=new HashMap<>();
//			Map<String, Object> endNode=new HashMap<>();
//			Map<String, Object> a=new HashMap<>();
//			int sum=1;int no=1;
//			boolean flagsum=true,flagno=true;
//			int flag=0;
//			/*startNode.clear();
//			endNode.clear();
//			a.clear();
//			*/
//			switch(C.getLabel()) {
//			case "使用":
//				startNode=map((SecB)C.getStartNode());
//				endNode = map((PayAccount)C.getEndNode());
//				break;
//			}
//			index=getIndex(startNode, nodes);
//				if(index==-1) {
//					nodes.add(startNode);
//					source=nodes.size()-1;
//				}else {
//					source=index;
//				}
//			index=getIndex(endNode, nodes);
//				if(index==-1) {
//					nodes.add(endNode);
//					target=nodes.size()-1;
//				}else {
//					target=index;
//				}
//			a=map("source", source, "target", target,C);
//
//			for(int j=rels.size()-1;j>=0;j--){
//				boolean hasa=(int)rels.get(j).get("source")==source&&(int)rels.get(j).get("target")==target;
//				boolean hasb=(int)rels.get(j).get("source")==target&&(int)rels.get(j).get("target")==source;
//				
//				
//				if(flag==0) {
//					if(hasa||hasb){
//						if(flagsum){
//						sum=(int)rels.get(j).get("sum")+1;
//						flagsum=false;
//						}
//						
//						if(flagno){
//						 no=(int)rels.get(j).get("no")+1;
//						 flagno=false;
//						}	
//						rels.get(j).replace("sum", sum);	
//					}
//				}
//			}
//			a.put("sum",sum);
//			a.put("no",no);
//			rels.add(a);
//		}
//		return map("nodes", nodes, "links", rels);	
//
//	}
//	public Map<String, Object> map(PerPayAccount p){
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		result.put("id",p.getId());
//		result.put("Org_codeCst_code",p.getOrg_codeCst_code());
//		result.put("Acc_name",p.getAcc_name());
//		result.put("Acc_no",p.getAcc_no());
//		result.put("Id_type_no",p.getId_type_no());
//		result.put("label",p.getLabel());
//		return result;
//	}
//	public Map<String, Object> map(PerBankAccount p){
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		result.put("id",p.getId());
//		result.put("Org_codeCst_code",p.getOrg_codeCst_code());
//		result.put("Acc_name",p.getAcc_name());
//		result.put("Acc_no",p.getAcc_no());
//		result.put("Id_name", p.getId_name());
//		result.put("Id_type_no",p.getId_type_no());
//		result.put("label",p.getLabel());
//		return result;
//	}
//	
//	public Map<String, Object> map(SecB s){
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		result.put("id",s.getId());
//		result.put("Org_codeJoin_code1",s.getOrg_codeJoin_code1());
//		result.put("Acc_name",s.getAcc_name());
//		result.put("Id_name",s.getId_name());
//		result.put("Id_type_no",s.getId_type_no());
//		result.put("Add_code",s.getAdd_code());
//		result.put("DateAmtCount",s.getDateAmtCount());
//		result.put("label",s.getLabel());
//		return result;
//	}
//	
//	public Map<String, Object> map(PayAccount p){
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		result.put("id",p.getId());
//		result.put("Org_codeCst_code",p.getOrg_codeCst_code());
//		result.put("Acc_name",p.getAcc_name());
//		result.put("Acc_no",p.getAcc_no());
//		result.put("Id_type_no",p.getId_type_no());
//		result.put("label",p.getLabel());
//		return result;
//	}
	
	public Map<String, Object> map(String key1, Object value1, String key2, Object value2,Relationship r) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);

		result.put("label",r.getLabel());
		result.put("id",r.getId());
		result.put("jyje", r.getJyje());
		result.put("jycs", r.getJycs());
		
		return result;
	}
}
