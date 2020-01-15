package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

public class ProvAccount extends Node{
private String label="备付金账户";
	public String getLabel() {
	return label;
}
	private String Acc_no="";
	private String Acc_type="";
	private String Acc_name="";
	private String OC_time="";
	public ProvAccount(String Acc_no,String Acc_type,String Acc_name,String OC_time) {
		this.Acc_no=Acc_no;
		this.Acc_type=Acc_type;
		this.Acc_name=Acc_name;
		this.OC_time=OC_time;
	}
	public Map<String,Object> map(){
		Map<String,Object> maps = new HashMap<>();
		maps.put("Acc_no", Acc_no);
		maps.put("Acc_type", Acc_type);
		maps.put("Acc_name", Acc_name);
		maps.put("OC_time", OC_time);
		return maps;
	}
}
