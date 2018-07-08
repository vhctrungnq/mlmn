package vn.com.vhc.vmsc2.statistics.web.filter;

public class S7linksetFilter {
	
	private String linksetid;
    private String nodeid;
    private String linksetName;


    public String getLinksetid() {
        return linksetid;
    }
    
    public void setLinksetid(String linksetid) {
        this.linksetid = linksetid.trim();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid.trim();
    }

    public String getLinksetName() {
        return linksetName;
    }

    public void setLinksetName(String linksetName) {
        this.linksetName = linksetName.trim();
    }
}
