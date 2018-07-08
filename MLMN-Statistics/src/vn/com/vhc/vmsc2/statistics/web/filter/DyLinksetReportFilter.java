package vn.com.vhc.vmsc2.statistics.web.filter;

import java.util.Date;

public class DyLinksetReportFilter {
	private Date day;
    private String fromNode;
    private String toNode;

   
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

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
}
