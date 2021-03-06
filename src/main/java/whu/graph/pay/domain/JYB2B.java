package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type ="zjy2")
public class JYB2B extends Relationship{
	@StartNode
	protected BankAccount StartNode;
	@EndNode
	protected BankAccount EndNode;
	
	public BankAccount getStartNode() {
		return StartNode;
	}
	public BankAccount getEndNode() {
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
	
	public static String label="zjy2";

	public String getLabel() {
		return label;
	}
}
