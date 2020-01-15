package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity
public class Relationship extends Entity{
	public static String label="";

	public String getLabel() {
		return label;
	}

	public Entity getStartNode() {
		// TODO Auto-generated method stub
		return null;
	}
	public Entity getEndNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getJyje() {
		return 0.0;
	}
	
//	public String getJyje() {
//		return "";
//	}
	
	public int getJycs() {
		return 0;
	}
	
}
