package whu.graph.pay.domain;

import java.util.HashMap;
import java.util.Map;

public class OServC extends Node{
private String label="外包服务机构";
	
	
	private String Acc_name="";
	private String Id_name="";
	private String Id_type_no="";
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getAcc_name() {
		return Acc_name;
	}
	public void setAcc_name(String acc_name) {
		Acc_name = acc_name;
	}
	public String getId_name() {
		return Id_name;
	}
	public void setId_name(String id_name) {
		Id_name = id_name;
	}
	public String getId_type_no() {
		return Id_type_no;
	}
	public void setId_type_no(String id_type_no) {
		Id_type_no = id_type_no;
	}
	
	public OServC(String acc_name,String id_name,String id_type_no) {
		this.Acc_name=acc_name;
		this.Id_name=id_name;
		this.Id_type_no=id_type_no;
	}
	public Map<String,Object> map(){
		Map<String,Object> maps = new HashMap<>();
		maps.put("Acc_name", Acc_name);
		maps.put("Id_name", Id_name);
		maps.put("Id_type_no", Id_type_no);
		return maps;
	}
}
