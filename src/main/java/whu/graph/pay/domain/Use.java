package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "使用")
public class Use extends Relationship{
	@StartNode
	protected SecB StartNode;
	@EndNode
	protected PayAccount EndNode;
	
	public SecB getStartNode() {
		return StartNode;
	}
	public PayAccount getEndNode() {
		return EndNode;
	}
	
	public static String label="使用";

	public String getLabel() {
		return label;
	}
}
