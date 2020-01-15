package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="个体商户")
public class GTSecB extends SecB{
	protected String label="个体商户";
	public String getLabel() {
		return label;
	}
	public GTSecB(String Org_codeJoin_code1,String Acc_name,String Id_name,String Id_type_no,String Add_code,String DateAmtCount) {
		this.Org_codeJoin_code1=Org_codeJoin_code1;
		this.Acc_name=Acc_name;
		this.Id_name=Id_name;
		this.Id_type_no=Id_type_no;
		this.Add_code=Add_code;
		this.DateAmtCount=DateAmtCount;
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
	public Map<String, Object> mapro(){          
		Map<String, Object> result = new HashMap<String, Object>();		
			
			result.put("Org_codeJoin_code1",Org_codeJoin_code1);
			result.put("Acc_name",Acc_name);
			result.put("Id_name", Id_name);
			result.put("Id_type_no",Id_type_no);	
			result.put("Add_code",Add_code);
			result.put("DateAmtCount",DateAmtCount);
		return result;
	}
}
