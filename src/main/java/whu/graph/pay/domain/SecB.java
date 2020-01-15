package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label="特约商户")
public class SecB extends Node{
	
	protected String label="特约商户";
	
	@Property(name="Org_codeCst_code1")
	protected String Org_codeJoin_code1="";
	
	@Property(name="Acc_name")
	protected String Acc_name="";
	
	@Property(name="Id_name")
	protected String Id_name="";
	
	@Property(name="Id_type_no")
	protected String Id_type_no="";
	
	@Property(name="Add_code")
	protected String Add_code="";
	
	@Property(name="DateAmtCount")
	protected String DateAmtCount="";

	public String getLabel() {
		return label;
	}

	public String getOrg_codeJoin_code1() {
		return Org_codeJoin_code1;
	}

	public String getAcc_name() {
		return Acc_name;
	}

	public String getId_name() {
		return Id_name;
	}

	public String getId_type_no() {
		return Id_type_no;
	}

	public String getAdd_code() {
		return Add_code;
	}

	public String getDateAmtCount() {
		return DateAmtCount;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOrg_codeJoin_code1(String org_codeJoin_code1) {
		Org_codeJoin_code1 = org_codeJoin_code1;
	}

	public void setAcc_name(String acc_name) {
		Acc_name = acc_name;
	}

	public void setId_name(String id_name) {
		Id_name = id_name;
	}

	public void setId_type_no(String id_type_no) {
		Id_type_no = id_type_no;
	}

	public void setAdd_code(String add_code) {
		Add_code = add_code;
	}

	public void setDateAmtCount(String dateAmtCount) {
		DateAmtCount = dateAmtCount;
	}

	public Map<String, Object> map(){          
		Map<String, Object> result = new HashMap<String, Object>();		
			result.put("id",id);
			result.put("label",label);
			result.put("Org_codeJoin_code1",Org_codeJoin_code1);
			result.put("Acc_name",Acc_name);
			result.put("Id_name", Id_name);
			result.put("Id_type_no",Id_type_no);	
			result.put("Add_code",Add_code);
			result.put("DateAmtCount",DateAmtCount);
		return result;
	}
}
