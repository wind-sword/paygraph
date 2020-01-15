package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "签约")
public class Sign extends Relationship{
	@StartNode
	protected Inst StartNode;
	@EndNode
	protected SecB EndNode;
	
	public Inst getStartNode() {
		return StartNode;
	}
	public SecB getEndNode() {
		return EndNode;
	}
	
	public static String label="签约";

	public String getLabel() {
		return label;
	}
}
