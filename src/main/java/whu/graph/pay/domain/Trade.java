package whu.graph.pay.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type ="总交易")
public class Trade extends Relationship{
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
	@Property(name="交易金额")
	protected double jyje;
	
	@Property(name="交易次数")
	protected int jycs;
	
//	@Property(name="交易金额")
//	protected String jyje;

	public double getJyje() {
		return jyje;
	}
	public int getJycs() {
		return jycs;
	}
	public void setJyje(double jyje) {
		this.jyje = jyje;
	}
	public void setJycs(int jycs) {
		this.jycs = jycs;
	}

//	public String getJyje() {
//		return jyje;
//	}
//	public void setJyje(String jyje) {
//		this.jyje = jyje;
//	}

	
	
	public static String label="总交易";

	public String getLabel() {
		return label;
	}
}
