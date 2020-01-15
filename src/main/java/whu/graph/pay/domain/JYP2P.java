package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type ="zjy1")
public class JYP2P extends Relationship{
	@StartNode
	protected PayAccount StartNode;
	@EndNode
	protected PayAccount EndNode;
	
	public PayAccount getStartNode() {
		return StartNode;
	}
	public PayAccount getEndNode() {
		return EndNode;
	}
	@Property(name="交易金额")
	protected double je;
	
	@Property(name="交易次数")
	protected int count;
	
	public double getJyje() {
		return je;
	}
	public int getJycs() {
		return count;
	}
	
	public static String label="zjy1";

	public String getLabel() {
		return label;
	}
}
