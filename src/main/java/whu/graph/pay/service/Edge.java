package whu.graph.pay.service;


public class Edge {
	
    private long startid;
    private long endid;
    private String jyje;
	
	public long getStartid() {
		return startid;
	}

	public long getEndid() {
		return endid;
	}

	public String getJyje() {
		return jyje;
	}

	public void setStartid(long startid) {
		this.startid = startid;
	}

	public void setEndid(long endid) {
		this.endid = endid;
	}

	public void setJyje(String jyje) {
		this.jyje = jyje;
	}

	public Edge(long startid,long endid,String jyje) {
		this.startid=startid;
		this.endid=endid;
		this.jyje=jyje;
	}
}
