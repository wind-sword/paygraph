package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;


@RelationshipEntity(type = "绑定")
public class Binding extends Relationship{
	@StartNode
	protected PayAccount StartNode;
	@EndNode
	protected BankAccount EndNode;
	
	public PayAccount getStartNode() {
		return StartNode;
	}
	public BankAccount getEndNode() {
		return EndNode;
	}
	
	public static String label="绑定";

	public String getLabel() {
		return label;
	}
}
