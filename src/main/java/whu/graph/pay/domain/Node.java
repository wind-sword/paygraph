package whu.graph.pay.domain;

import java.util.Map;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Node extends Entity{
	public String getLabel() {
		return "";
	}

	public Map<String, Object> map() {
		// TODO Auto-generated method stub
		return null;
	}
}
