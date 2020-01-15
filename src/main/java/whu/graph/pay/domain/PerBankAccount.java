package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label="个人银行账户")
public class PerBankAccount extends BankAccount{
	@Property(name="Org_codeCst_code")
	protected String Org_codeCst_code;
	
	@Property(name="Acc_name")
	protected String Acc_name;
	
	@Property(name="Acc_no")
	protected String Acc_no;
	
	@Property(name="Id_name")
	protected String Id_name;
	
	@Property(name="Id_type_no")
	protected String Id_type_no;
	
	protected String label="个人银行账户";
	
	public String getOrg_codeCst_code() {
		return Org_codeCst_code;
	}

	public String getAcc_name() {
		return Acc_name;
	}

	public String getAcc_no() {
		return Acc_no;
	}

	public String getId_name() {
		return Id_name;
	}
	public String getId_type_no() {
		return Id_type_no;
	}
	
	public String getLabel() {
		return label;
	}
	public PerBankAccount(String Org_codeCst_code,String Acc_name,String Acc_no,String Id_name,String Id_type_no) {
		this.Org_codeCst_code=Org_codeCst_code;
		this.Acc_name=Acc_name;
		this.Acc_no=Acc_no;
		this.Id_name=Id_name;
		this.Id_type_no=Id_type_no;
	}

	public Map<String, Object> map(){          
		Map<String, Object> result = new HashMap<String, Object>();		
			result.put("id",id);
			result.put("label",label);
			result.put("Org_codeCst_code",Org_codeCst_code);
			result.put("Acc_name",Acc_name);
			result.put("Id_name", Id_name);
			result.put("Acc_no",Acc_no);
			result.put("Id_type_no",Id_type_no);			
		return result;
	}
	
	public Map<String, Object> mapro(){          
		Map<String, Object> result = new HashMap<String, Object>();		
		
			result.put("Org_codeCst_code",Org_codeCst_code);
			result.put("Acc_name",Acc_name);
			result.put("Id_name", Id_name);
			result.put("Acc_no",Acc_no);
			result.put("Id_type_no",Id_type_no);			
		return result;
	}
}
