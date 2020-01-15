package whu.graph.pay.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.neo4j.io.fs.FileUtils;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.springframework.stereotype.Service;
@Service
public class UpdateService {
	//更新前删除数据库
		/**
		 * 格式化图数据库
		 * @throws IOException 
		 */
	public void Deletedatabase() throws IOException {
			
		FileUtils.deleteRecursively(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));

	}
	
//	public static void combineJY() {
//		List<Relationship> jylist=new ArrayList<Relationship>();
//		GraphDatabaseService gb = new GraphDatabaseFactory().
//				newEmbeddedDatabase(new File("C:\\Users\\zcs\\Desktop\\importest"));
//		Transaction tx = gb.beginTx();
//		try {
//			ResourceIterable<Relationship> relsR=gb.getAllRelationships();
//			Iterator<Relationship> relsI=relsR.iterator();
//			while(relsI.hasNext()) {
//				Relationship rel=relsI.next();
//				if(rel.getType().name().equals("交易"))
//				{
//					jylist.add(rel);
//				}
//			}
//			int c=1;
//			for(int i=0;i<jylist.size();i++) {
//				Node startnode=jylist.get(i).getStartNode();
//				Node endnode=jylist.get(i).getEndNode();
//				Relationship zjy=startnode.createRelationshipTo(endnode, RelationshipType.withName("总交易"));
//				int count=1;
//				double je=Double.parseDouble((String) jylist.get(i).getProperty("交易金额"));
//				zjy.setProperty("交易次数", count);
//				zjy.setProperty("交易金额", je);
//				for(int j=i+1;j<jylist.size();j++) {
//					if(jylist.get(j).getStartNodeId()==startnode.getId()&&jylist.get(j).getEndNodeId()==endnode.getId()) {					
//						double jeg=Double.parseDouble((String) jylist.get(j).getProperty("交易金额"));
//						je=je+jeg;
//						count++;
//						zjy.setProperty("交易金额", je);
//						zjy.setProperty("交易次数", count);
//						jylist.remove(j);
//						j=j-1;
//					}
//				}								
//				c++;
//			}
//			System.out.print("完成！总交易笔数"+c+"\n");
//			tx.success();
//		}
//		catch(Exception e) {
//			tx.failure();
//		}
//		finally {
//			tx.close();
//		}
//		gb.shutdown();
//	}
//	
//	public static void combineJY123(String type) {
//		List<Relationship> jylist=new ArrayList<Relationship>();
//		GraphDatabaseService gb = new GraphDatabaseFactory().
//				newEmbeddedDatabase(new File("C:\\Users\\zcs\\Desktop\\importest"));
//		Transaction tx = gb.beginTx();
//		try {
//			ResourceIterable<Relationship> relsR=gb.getAllRelationships();
//			Iterator<Relationship> relsI=relsR.iterator();
//			while(relsI.hasNext()) {
//				Relationship rel=relsI.next();
//				if(rel.getType().name().equals(type))
//				{
//					jylist.add(rel);
//				}
//			}
//			int c=1;
//			for(int i=0;i<jylist.size();i++) {
//				Node startnode=jylist.get(i).getStartNode();
//				Node endnode=jylist.get(i).getEndNode();
//				Relationship zjy=startnode.createRelationshipTo(endnode, RelationshipType.withName("z"+type));
//				int count=1;
//				double je=Double.parseDouble((String) jylist.get(i).getProperty("交易金额"));
//				zjy.setProperty("交易次数", count);
//				zjy.setProperty("交易金额", je);
//				for(int j=i+1;j<jylist.size();j++) {
//					if(jylist.get(j).getStartNodeId()==startnode.getId()&&jylist.get(j).getEndNodeId()==endnode.getId()) {					
//						double jeg=Double.parseDouble((String) jylist.get(j).getProperty("交易金额"));
//						je=je+jeg;
//						count++;
//						zjy.setProperty("交易金额", je);
//						zjy.setProperty("交易次数", count);
//						jylist.remove(j);
//						j=j-1;
//					}
//				}								
//				c++;
//			}
//			System.out.print("完成！z"+type+"笔数"+c+"\n");
//			tx.success();
//		}
//		catch(Exception e) {
//			tx.failure();
//		}
//		finally {
//			tx.close();
//		}
//		gb.shutdown();
//	}
	
	public static void combineJY() throws IOException {
		List<Edge> jylist=new ArrayList<Edge>();
		GraphDatabaseService gb = new GraphDatabaseFactory().
				newEmbeddedDatabase(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));
		Transaction tx = gb.beginTx();
		try {
			ResourceIterable<Relationship> relsR=gb.getAllRelationships();
			Iterator<Relationship> relsI=relsR.iterator();
			while(relsI.hasNext()) {
				Relationship rel=relsI.next();
				if(rel.getType().name().equals("交易"))
				{
					Edge e=new Edge(rel.getEndNodeId(),rel.getEndNodeId(),(String) rel.getProperty("交易金额"));
					jylist.add(e);
				}
			}			
			tx.success();
		}
		catch(Exception e) {
			tx.failure();
		}
		finally {
			tx.close();
		}
		gb.shutdown();
		System.out.print("抽取交易关系"+jylist.size()+"\n");
		
		BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
		int c=1;
		for(int i=0;i<jylist.size();i++) {
			long startid=jylist.get(i).getStartid();
			long endid=jylist.get(i).getEndid();
			Map<String,Object> pro=new HashMap<String,Object>();			
			int count=1;
			double je=Double.parseDouble(jylist.get(i).getJyje());
			pro.put("交易次数", count);
			pro.put("交易金额", je);
			long relid=bi.createRelationship(startid,endid, RelationshipType.withName("总交易"),pro);
			for(int j=i+1;j<jylist.size();j++) {
				if(jylist.get(j).getStartid()==startid&&jylist.get(j).getEndid()==endid) {					
					double jeg=Double.parseDouble(jylist.get(j).getJyje());
					je=je+jeg;
					count++;
					pro.put("交易次数", count);
					pro.put("交易金额", je);
					bi.setRelationshipProperties(relid,pro);
					jylist.remove(j);
					j=j-1;
				}
			}								
			c++;
		}
		bi.shutdown();
		System.out.print("完成！总交易笔数"+c+"\n");
		jylist.clear();
	}
	
	public static void combineJY123(String type) throws IOException {
		List<Edge> jylist=new ArrayList<Edge>();
		GraphDatabaseService gb = new GraphDatabaseFactory().
				newEmbeddedDatabase(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));
		Transaction tx = gb.beginTx();
		try {
			ResourceIterable<Relationship> relsR=gb.getAllRelationships();
			Iterator<Relationship> relsI=relsR.iterator();
			while(relsI.hasNext()) {
				Relationship rel=relsI.next();
				if(rel.getType().name().equals(type))
				{
					Edge e=new Edge(rel.getStartNodeId(),rel.getEndNodeId(),(String) rel.getProperty("交易金额"));
					jylist.add(e);
				}
			}
			
			tx.success();
		}
		catch(Exception e) {
			tx.failure();
		}
		finally {
			tx.close();
		}
		gb.shutdown();
		System.out.print("抽取jy关系"+jylist.size()+"\n");
		
		BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
		int c=1;
		for(int i=0;i<jylist.size();i++) {
			long startid=jylist.get(i).getStartid();
			long endid=jylist.get(i).getEndid();
			Map<String,Object> pro=new HashMap<String,Object>();
			int count=1;
			double je=Double.parseDouble(jylist.get(i).getJyje());
			pro.put("交易次数", count);
			pro.put("交易金额", je);
			long relid=bi.createRelationship(startid,endid, RelationshipType.withName("z"+type),pro);
			for(int j=i+1;j<jylist.size();j++) {
				if(jylist.get(j).getStartid()==startid&&jylist.get(j).getEndid()==endid) {					
					double jeg=Double.parseDouble(jylist.get(j).getJyje());
					je=je+jeg;
					count++;
					pro.put("交易次数", count);
					pro.put("交易金额", je);
					bi.setRelationshipProperties(relid,pro);
					jylist.remove(j);
					j=j-1;
				}
			}								
			c++;
		}
		bi.shutdown();
		System.out.print("完成！z"+type+"笔数"+c+"\n");
		jylist.clear();
	}
	
//	public void addindex() throws IOException {
//		BatchInserter bi = BatchInserters.inserter(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));	
//		bi.createDeferredSchemaIndex(Label.label("个人支付账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("个人银行账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("单位支付账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("单位银行账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("个人支付账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("个人银行账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("单位支付账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("单位银行账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("支付账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("银行账户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("支付账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("银行账户")).on("Acc_no").create();
//		bi.createDeferredSchemaIndex(Label.label("对公商户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("个体商户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("其他商户")).on("Acc_name").create();
////		bi.createDeferredSchemaIndex(Label.label("小微商户")).on("Acc_name").create();
//		bi.createDeferredSchemaIndex(Label.label("对公商户")).on("Org_codeJoin_code1").create();
//		bi.createDeferredSchemaIndex(Label.label("个体商户")).on("Org_codeJoin_code1").create();
//		bi.createDeferredSchemaIndex(Label.label("其他商户")).on("Org_codeJoin_code1").create();
////		bi.createDeferredSchemaIndex(Label.label("小微商户")).on("Org_codeJoin_code1").create();
//		bi.shutdown();
//		System.out.print("索引创建完成！\n");
//	}
	
	public void addindex() {
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File("/app/neo4j-community-3.3.9/data/databases/graph.db"));
		try (org.neo4j.graphdb.Transaction tx1 =  graphDb.beginTx()) {
			Schema schema = graphDb.schema();
		    IndexDefinition indexDefinition;
		    
		    indexDefinition = schema.indexFor(Label.label("个人支付账户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("单位支付账户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("个人银行账户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("单位银行账户")).on("Acc_name").create();
		    
		    indexDefinition = schema.indexFor(Label.label("个人支付账户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("单位支付账户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("个人银行账户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("单位银行账户")).on("Acc_no").create();
		    
		    indexDefinition = schema.indexFor(Label.label("支付账户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("银行账户")).on("Acc_name").create();
		    
		    indexDefinition = schema.indexFor(Label.label("支付账户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("银行账户")).on("Acc_no").create();
		    
		    indexDefinition = schema.indexFor(Label.label("对公商户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("个体商户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("其他商户")).on("Acc_name").create();
		    indexDefinition = schema.indexFor(Label.label("小微商户")).on("Acc_name").create();
		    
		    indexDefinition = schema.indexFor(Label.label("对公商户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("个体商户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("其他商户")).on("Acc_no").create();
		    indexDefinition = schema.indexFor(Label.label("小微商户")).on("Acc_no").create();

            tx1.success();
		}catch (Exception e) {
			e.printStackTrace();		
		}
		
		System.out.println("创建索引完成!");
		
	}
	
	public void updateData() throws Exception {
		UpdateG ug=new UpdateG();
		Deletedatabase();
		
		File fp=new File("/nasdisk/zfjgtmp/work_dir");
		File[] efp=fp.listFiles();
		for(File f:efp) {
			if(!f.isDirectory()||f.getName().equals("temp"))
				continue;
			File[] fmonth=f.listFiles();
			for(File fin:fmonth) {
				ug.readZipFile(fin);
				ug.readZipFileRel(fin);
				System.out.print(f.getName()+fin.getName()+"文件夹传输\n");
			}		
		}
		
		combineJY();
		combineJY123("jy1");
		combineJY123("jy2");
		combineJY123("jy3");
		
		addindex();
		
		System.out.print("数据更新完成！");
	}	
}
