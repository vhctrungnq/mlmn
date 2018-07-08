package vn.com.vhc.vmsc2.statistics.web.filter;

public class MnLinksetReportFilter {
	  private String fromNode;

	    private String toNode;

	    private Integer month;

	    private Integer year;

	    public String getFromNode() {
	        return fromNode;
	    }

	    public void setFromNode(String fromNode) {
	        this.fromNode = fromNode.trim();
	    }

	    public String getToNode() {
	        return toNode;
	    }

	    public void setToNode(String toNode) {
	        this.toNode = toNode.trim();
	    }

	    public Integer getMonth() {
	        return month;
	    }

	    public void setMonth(Integer month) {
	        this.month = month;
	    }

	    public Integer getYear() {
	        return year;
	    }

	    public void setYear(Integer year) {
	        this.year = year;
	    }
}
