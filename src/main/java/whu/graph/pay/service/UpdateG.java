package whu.graph.pay.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import whu.graph.pay.domain.BankAccount;
import whu.graph.pay.domain.DGSecB;
import whu.graph.pay.domain.ElseSecB;
import whu.graph.pay.domain.GTSecB;
import whu.graph.pay.domain.OServC;
import whu.graph.pay.domain.PayAccount;
import whu.graph.pay.domain.PerBankAccount;
import whu.graph.pay.domain.PerPayAccount;
import whu.graph.pay.domain.ProvAccount;
import whu.graph.pay.domain.UnitBankAccount;
import whu.graph.pay.domain.UnitPayAccount;
import whu.graph.pay.domain.XWSecB;





public class UpdateG {
	static Map<String,Long> shmap=new HashMap<String,Long>();
	static Map<String,Long> zhmap=new HashMap<String,Long>();
	static Map<String,Long> insmap=new HashMap<String,Long>();
	
	public static void txtReader(BufferedReader br,String typestr,long insid) {		
		try {
//			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(f),"GBK"));
			
			if(typestr.equals("M2")) {//备付金账户
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
				String s=null;
				int count=0;
				Map<String,Object> maps = new HashMap<>();
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					
					
					maps.put("Acc_no", tmp[3]);
					maps.put("Acc_type", tmp[1]);
					maps.put("Acc_name", tmp[2]);
					maps.put("OC_time", tmp[6]+tmp[7]);
					
					long praid=bi.createNode(maps, Label.label("备付金账户"));
					bi.createRelationship(insid, praid, DynamicRelationshipType.withName("持有"), null);
				}
				bi.shutdown();
				System.out.print("bfj success!\r\n");
			}
			
			if(typestr.equals("M3")) {//外包服务机构
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));
				String s=null;
				int count=0;
				Map<String,Object> maps = new HashMap<>();
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					
					
					maps.put("Acc_name", tmp[2]);
					maps.put("Id_name", tmp[7]);
					maps.put("Id_type_no", tmp[8]+tmp[9]);
					
				
					long ahid=bi.createNode(maps, Label.label("外包服务机构"));
					bi.createRelationship(insid, ahid, DynamicRelationshipType.withName("合作"), null);
				}
				bi.shutdown();
				System.out.print("wbfwjg success!\r\n");
			}
			
			if(typestr.equals("M1")) {//特约商户
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));
				String s=null;
				int count=0;
				Map<String, Object> result = new HashMap<String, Object>();	
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					if(tmp[7].equals("10")) {
						
								
						result.put("Org_codeJoin_code1",tmp[0]+tmp[4]);
						result.put("Acc_name",tmp[6]);
						result.put("Id_name", tmp[11]);
						result.put("Id_type_no",tmp[12]+tmp[13]);	
						result.put("Add_code",tmp[17]);
						result.put("DateAmtCount","");
						
						
						long dgid=bi.createNode(result, Label.label("对公商户"));
						bi.createRelationship(insid, dgid, DynamicRelationshipType.withName("签约"), null);
						shmap.put(tmp[0]+tmp[4],dgid);
					}
					else if(tmp[7].equals("11")) {
						
						result.put("Org_codeJoin_code1",tmp[0]+tmp[4]);
						result.put("Acc_name",tmp[6]);
						result.put("Id_name", tmp[11]);
						result.put("Id_type_no",tmp[12]+tmp[13]);	
						result.put("Add_code",tmp[17]);
						result.put("DateAmtCount","");
						
						
						long gtid=bi.createNode(result, Label.label("个体商户"));
						bi.createRelationship(insid, gtid, DynamicRelationshipType.withName("签约"), null);
						shmap.put(tmp[0]+tmp[4],gtid);
					}
					else if(tmp[7].equals("12")) {
						
						result.put("Org_codeJoin_code1",tmp[0]+tmp[4]);
						result.put("Acc_name",tmp[6]);
						result.put("Id_name", tmp[11]);
						result.put("Id_type_no",tmp[12]+tmp[13]);	
						result.put("Add_code",tmp[17]);
						result.put("DateAmtCount","");
						
						
						long xid=bi.createNode(result, Label.label("小微商户"));
						bi.createRelationship(insid, xid, DynamicRelationshipType.withName("签约"), null);
						shmap.put(tmp[0]+tmp[4],xid);
					}
					else{
						
						result.put("Org_codeJoin_code1",tmp[0]+tmp[4]);
						result.put("Acc_name",tmp[6]);
						result.put("Id_name", tmp[11]);
						result.put("Id_type_no",tmp[12]+tmp[13]);	
						result.put("Add_code",tmp[17]);
						result.put("DateAmtCount","");
						
						
						long eid=bi.createNode(result, Label.label("其他商户"));
						bi.createRelationship(insid, eid, DynamicRelationshipType.withName("签约"), null);
						shmap.put(tmp[0]+tmp[4],eid);
					}
				}
				bi.shutdown();
				System.out.print("sh success!\r\n");
			}
			
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
        }		
	}
	
	public static void txtReadergo(BufferedReader br,String typestr) {		
		try {
//			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(f),"GBK"));
	
			
			if(typestr.equals("M4")) {//单位支付账户
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));	
				String s=null;
				int count=0;
				Map<String, Object> result = new HashMap<String, Object>();		
				Map<String, Object> resultub = new HashMap<String, Object>();
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
		
					
					result.put("Org_codeCst_code",tmp[0]+tmp[2]);
					result.put("Acc_name",tmp[3]);
					result.put("Acc_no",tmp[4]);
					result.put("Id_type_no",tmp[19]+tmp[20]);	
					
					long upid=bi.createNode(result,Label.label("单位支付账户"));
					
							
					
					resultub.put("Org_codeCst_code",tmp[0]+tmp[2]);
					resultub.put("Acc_name","");
					resultub.put("Id_name", tmp[31]);
					resultub.put("Acc_no",tmp[18]);
					resultub.put("Id_type_no",tmp[19]+tmp[20]);	
					
					
					long ubid=bi.createNode(resultub, Label.label("单位银行账户"));
					bi.createRelationship(upid, ubid, DynamicRelationshipType.withName("绑定"), null);
					zhmap.put(tmp[4], upid);
					System.out.print(zhmap.size());
				}
				bi.shutdown();
				System.out.print("unitpayaccount success!\r\n");
			}
			
			
			if(typestr.equals("M5")) {//个人支付账户
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));	
				String s=null;
				int count=0;
				Map<String, Object> result = new HashMap<String, Object>();		
				Map<String, Object> resultpb = new HashMap<String, Object>();		
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					
					
					result.put("Org_codeCst_code",tmp[0]+tmp[2]);
					result.put("Acc_name",tmp[3]);
					result.put("Acc_no",tmp[4]);
					result.put("Id_type_no",tmp[19]+tmp[20]);	
					
					long ppid=bi.createNode(result, Label.label("个人支付账户"));
					
					resultpb.put("Org_codeCst_code",tmp[0]+tmp[2]);
					resultpb.put("Acc_name","");
					resultpb.put("Id_name", tmp[19]);
					resultpb.put("Acc_no","");
					resultpb.put("Id_type_no",tmp[11]+tmp[12]);	
					
					
					long pbid=bi.createNode(resultpb,  Label.label("个人银行账户"));
					bi.createRelationship(ppid, pbid, DynamicRelationshipType.withName("绑定"), null);
					zhmap.put(tmp[4], ppid);
				}
				bi.shutdown();
				System.out.print("perpayaccount success!\r\n");
			}
			
			
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
        }		
	}
	
	public static void txtReaderRel(BufferedReader br,String typestr) {		
		try {
//			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(f),"GBK"));
		
			
			if(typestr.equals("M1")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				Map<String, Object> result = new HashMap<String, Object>();	
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					String shcode=tmp[0]+tmp[4];
					long shid=shmap.get(shcode);
					String zhcode=tmp[23];
					if(zhmap.containsKey(zhcode)) {
						long zhid=zhmap.get(zhcode);
						bi.createRelationship(shid, zhid, DynamicRelationshipType.withName("使用"), null);
					}else {
							
						result.put("Org_codeCst_code","");
						result.put("Acc_name","");
						result.put("Acc_no",zhcode);
						result.put("Id_type_no","");	
						
						long newpid=bi.createNode(result,Label.label("支付账户"));
						zhmap.put(zhcode, newpid);
						bi.createRelationship(shid, newpid, DynamicRelationshipType.withName("使用"), null);
					}
				}
				bi.shutdown();
				System.out.print("sh use account success!\r\n");
			}
			
			
			if(typestr.equals("D1")||typestr.equals("D2")||typestr.equals("D3")||typestr.equals("D4")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					if(tmp.length<12)//2020.1.2
						continue;
					String zhcode=tmp[10];
					String shcode=tmp[0]+tmp[1];
					if(!shmap.containsKey(shcode))
						continue;
					if(tmp[11].equals("11")) {
						if(zhmap.containsKey(zhcode)) {
							long zhid=zhmap.get(zhcode);					
							long shid=shmap.get(shcode);						
							Map<String,Object> proper=new HashMap<String,Object>();
							proper.put("交易金额", tmp[5]);
							bi.createRelationship(shid, zhid, DynamicRelationshipType.withName("交易"), proper);
						}else if(!(zhmap.containsKey(zhcode))){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Acc_no",zhcode);
							result.put("Id_type_no","");	
							
							long shid=shmap.get(shcode);
							long newpid=bi.createNode(result,Label.label("支付账户"));
							zhmap.put(zhcode, newpid);
							Map<String,Object> proper=new HashMap<String,Object>();
							proper.put("交易金额", tmp[5]);
							bi.createRelationship(shid, newpid, DynamicRelationshipType.withName("交易"), proper);
						}
					}else if(tmp[11].equals("10")) {//unexecuted for no bankaccountzh
						if(zhmap.containsKey(zhcode)&&shmap.containsKey(shcode)) {
							long zhid=zhmap.get(zhcode);							
							long shid=shmap.get(shcode);						
							Map<String,Object> proper=new HashMap<String,Object>();
							proper.put("交易金额", tmp[5]);
							bi.createRelationship(shid, zhid, DynamicRelationshipType.withName("交易"), proper);
						}else if(shmap.containsKey(shcode)&&!(zhmap.containsKey(zhcode))){
							
							Map<String, Object> result = new HashMap<String, Object>();					
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Id_name", "");
							result.put("Acc_no",zhcode);
							result.put("Id_type_no","");	
							
							
							long shid=shmap.get(shcode);
							long newbid=bi.createNode(result,Label.label("银行账户"));
							zhmap.put(zhcode, newbid);
							Map<String,Object> proper=new HashMap<String,Object>();
							proper.put("交易金额", tmp[5]);
							bi.createRelationship(shid, newbid, DynamicRelationshipType.withName("交易"), proper);
						}
					}
					
				}
				bi.shutdown();
				System.out.print("jy success!\r\n");
			}
			
			if(typestr.equals("D8")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					String zhcode1=tmp[5];
					String zhcode2=tmp[14];
					String jyje=tmp[17];
					Map<String,Object> proper=new HashMap<String,Object>();
					proper.put("交易金额", jyje);
					if(tmp[18].equals("10")) {
						
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[11].equals("10"))
								bi.createRelationship(zhid2, zhid1, DynamicRelationshipType.withName("jy3"), proper);
							else {
								bi.createRelationship(zhid2, zhid1, DynamicRelationshipType.withName("jy1"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");		
							
													
							long newupid=bi.createNode(result,Label.label("单位支付账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[11].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																	
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship( newbid, newupid,DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									

									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship( newpid, newupid,DynamicRelationshipType.withName("jy1"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[11].equals("10"))
									bi.createRelationship( zhmap.get(zhcode2),newupid, DynamicRelationshipType.withName("jy3"), proper);
								else {
									bi.createRelationship(zhmap.get(zhcode2),newupid,  DynamicRelationshipType.withName("jy1"), proper);
								}								
							}
						}
						
					}
						
					else if(tmp[18].equals("11")) {
						
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[11].equals("10"))
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy3"), proper);
							else {
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy1"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");	
							
													
							long newupid=bi.createNode(result,Label.label("单位支付账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[11].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship(newupid, newbid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									

									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship(newupid, newpid, DynamicRelationshipType.withName("jy1"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[11].equals("10"))
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy3"), proper);
								else {
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy1"), proper);
								}								
							}
						}
					
					}
				}
				bi.shutdown();
				System.out.print("d8account jy success!\r\n");
			}
			
			if(typestr.equals("D9")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					String zhcode1=tmp[5];
					String zhcode2=tmp[14];
					String jyje=tmp[18];
					Map<String,Object> proper=new HashMap<String,Object>();
					proper.put("交易金额", jyje);
					if(tmp[19].equals("10")) {
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[11].equals("10"))
								bi.createRelationship( zhid2, zhid1,DynamicRelationshipType.withName("jy3"), proper);
							else {
								bi.createRelationship( zhid2, zhid1,DynamicRelationshipType.withName("jy1"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");		
							
										
							long newupid=bi.createNode(result,Label.label("个人支付账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[11].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
															
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship( newbid, newupid,DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									
									
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship( newpid, newupid,DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[11].equals("10"))
									bi.createRelationship( zhmap.get(zhcode2), newupid,DynamicRelationshipType.withName("jy3"), proper);
								else
								{
									bi.createRelationship( zhmap.get(zhcode2),newupid, DynamicRelationshipType.withName("jy2"), proper);
								}
							}
						}
					}
						
					else if(tmp[19].equals("11")){
						
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[11].equals("10"))
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy3"), proper);
							else {
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy1"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");		
							
												
							long newupid=bi.createNode(result,Label.label("个人支付账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[11].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship(newupid, newbid, DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									
									
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship(newupid, newpid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[11].equals("10"))
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy3"), proper);
								else
								{
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy2"), proper);
								}
							}
						}
					
					}
				}
				bi.shutdown();
				System.out.print("d9account jy success!\r\n");
			}
			
			if(typestr.equals("Da")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					String zhcode1=tmp[5];
					String zhcode2=tmp[19];
					String jyje=tmp[26];
					Map<String,Object> proper=new HashMap<String,Object>();
					proper.put("交易金额", jyje);
					if(tmp[27].equals("10")) {
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[16].equals("10"))
								bi.createRelationship( zhid2,zhid1, DynamicRelationshipType.withName("jy2"), proper);
							else {
								bi.createRelationship( zhid2,zhid1, DynamicRelationshipType.withName("jy3"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Id_name", "");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");	
							
											
							long newupid=bi.createNode(result,Label.label("单位银行账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[16].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");		
									
														
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship( newbid,newupid, DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");			
									
									
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship(newpid, newupid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[16].equals("10"))
									bi.createRelationship( zhmap.get(zhcode2),newupid, DynamicRelationshipType.withName("jy2"), proper);
								else {
									bi.createRelationship( zhmap.get(zhcode2),newupid, DynamicRelationshipType.withName("jy3"), proper);
								}
							}
						}
					}
					else if(tmp[27].equals("11"))
					{
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[16].equals("10"))
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy2"), proper);
							else {
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy3"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Id_name", "");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");	
							
													
							long newupid=bi.createNode(result,Label.label("单位银行账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[16].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship(newupid, newbid, DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									
								
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship(newupid, newpid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[16].equals("10"))
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy2"), proper);
								else {
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy3"), proper);
								}
							}
						}
					
					}
				}
				bi.shutdown();
				System.out.print("daaccount jy success!\r\n");
			}
			
			if(typestr.equals("Db")) {
				BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//				BatchInserter bi = BatchInserters.inserter(new File("C:\\Users\\zcs\\Desktop\\zyds"));
				String s=null;
				int count=0;
				while((s=br.readLine()) != null) {
					if(count==0) {
						count++;
						continue;
					}
					String[] tmp=s.split("\\|");
					String zhcode1=tmp[5];
					String zhcode2=tmp[19];
					String jyje=tmp[25];
					Map<String,Object> proper=new HashMap<String,Object>();
					proper.put("交易金额", jyje);
					if(tmp[26].equals("10")) {
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[16].equals("10"))
								bi.createRelationship( zhid2, zhid1,DynamicRelationshipType.withName("jy2"), proper);
							else {
								bi.createRelationship( zhid2, zhid1,DynamicRelationshipType.withName("jy3"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Id_name", "");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");		
							
													
							long newupid=bi.createNode(result,Label.label("个人银行账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[16].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship( newbid,newupid, DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");	
									
									
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship( newpid,newupid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[16].equals("10"))
									bi.createRelationship( zhmap.get(zhcode2), newupid,DynamicRelationshipType.withName("jy2"), proper);
								else {
									bi.createRelationship( zhmap.get(zhcode2), newupid,DynamicRelationshipType.withName("jy3"), proper);
								}
							}
						}
					}
					else if(tmp[26].equals("11"))
					{
						if(zhmap.containsKey(zhcode1)&&zhmap.containsKey(zhcode2)) {
							long zhid1=zhmap.get(zhcode1);
							long zhid2=zhmap.get(zhcode2);
							if(tmp[16].equals("10"))
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy2"), proper);
							else {
								bi.createRelationship(zhid1, zhid2, DynamicRelationshipType.withName("jy3"), proper);
							}
						}else if(!zhmap.containsKey(zhcode1)){
							
							Map<String, Object> result = new HashMap<String, Object>();		
							
							result.put("Org_codeCst_code","");
							result.put("Acc_name","");
							result.put("Id_name", "");
							result.put("Acc_no",zhcode1);
							result.put("Id_type_no","");	
							
													
							long newupid=bi.createNode(result,Label.label("个人银行账户"));
							zhmap.put(zhcode1, newupid);
							if(!zhmap.containsKey(zhcode2)) {
								if(tmp[16].equals("10")) {
									
									Map<String, Object> resultb = new HashMap<String, Object>();					
									resultb.put("Org_codeCst_code","");
									resultb.put("Acc_name","");
									resultb.put("Id_name", "");
									resultb.put("Acc_no",zhcode2);
									resultb.put("Id_type_no","");	
									
																
									long newbid=bi.createNode(resultb,Label.label("银行账户"));															
									bi.createRelationship(newupid, newbid, DynamicRelationshipType.withName("jy2"), proper);
									zhmap.put(zhcode2, newbid);	
								}else {
									
									Map<String, Object> resultp = new HashMap<String, Object>();		
									resultp.put("Org_codeCst_code","");
									resultp.put("Acc_name","");
									resultp.put("Acc_no",zhcode2);
									resultp.put("Id_type_no","");		
									
								
									long newpid=bi.createNode(resultp,Label.label("支付账户"));
									bi.createRelationship(newupid, newpid, DynamicRelationshipType.withName("jy3"), proper);
									zhmap.put(zhcode2, newpid);	
								}
									
							}else {
								if(tmp[16].equals("10"))
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy2"), proper);
								else {
									bi.createRelationship(newupid, zhmap.get(zhcode2), DynamicRelationshipType.withName("jy3"), proper);
								}
							}
						}
						
					}
				}
				bi.shutdown();
				System.out.print("dbaccount jy success!\r\n");
			}
			
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
        }		
	}
	
	public void readZipFile(File f) throws Exception {
		File[] fp=f.listFiles();
		for(File txtf:fp){
        	String name =txtf.getName();
        	System.out.print(name+"\n");
            String type=name.substring(name.lastIndexOf("_")-13, name.lastIndexOf("_")-11);
            BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(txtf),"GBK"));
            long insid = 0;
            String instcode=name.substring(name.lastIndexOf("_")-28, name.lastIndexOf("_")-14);
            if(!insmap.containsKey(instcode)) {
            	shmap.clear();
            	zhmap.clear();
            	BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
            	Map<String,Object> propert=new HashMap<String,Object>();
            	propert.put("Org_code", instcode);
            	insid=bi.createNode(propert, Label.label("收单机构"));
            	insmap.put(instcode, insid);
            	bi.shutdown();
            }
            switch(type) {
            	case "M1":txtReader(br,type,insmap.get(instcode));
            		break;
            	case "M2":txtReader(br,type,insmap.get(instcode));
            		break;
            	case "M3":txtReader(br,type,insmap.get(instcode));
            		break;
            	case "M4":txtReadergo(br,type);
            		break;
            	case "M5":txtReadergo(br,type);
        		break;	
            }
                    
                
        }
       
    }
	public void readZipFileRel(File f) throws Exception {
		File[] fp=f.listFiles();
		for(File txtf:fp){
        	String name =txtf.getName();
        	System.out.print(name+"\n");
            String type=name.substring(name.lastIndexOf("_")-13, name.lastIndexOf("_")-11);
            BufferedReader br =new BufferedReader( new InputStreamReader(new FileInputStream(txtf),"GBK"));
            switch(type) {
            	case "M1":txtReaderRel(br,type);
            		break;
            	case "D1":txtReaderRel(br,type);
        		break;	
            	case "D2":txtReaderRel(br,type);
        		break;	
            	case "D3":txtReaderRel(br,type);
        		break;	
            	case "D4":txtReaderRel(br,type);
        		break;	
            	case "D8":txtReaderRel(br,type);
        		break;	
        		case "D9":txtReaderRel(br,type);
        		break;	
        		case "Da":txtReaderRel(br,type);
        		break;	
        		case "Db":txtReaderRel(br,type);
        		break;	
            }
            
        }
      
    }
}
