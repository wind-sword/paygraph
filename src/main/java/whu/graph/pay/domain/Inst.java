package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label="收单机构")
public class Inst extends Node{
	@Property(name="Org_code")
	protected String Org_code="";
	String label="收单机构";
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getOrg_code() {
		return Org_code;
	}
	public void setOrg_code(String org_code) {
		Org_code = org_code;
	}
	public Map<String,Object> map(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("label",label);
		map.put("Org_code", Org_code);
		return map;
	}
}
