package vn.com.vhc.vmsc2.statistics.web.filter;

public class IPBBFilter {
	private String startDate; // dd/MM/yyyy HH:mm
	private String endDate;
	private String direction;
	private String link;
	
	public void setStartDate(String startDate) {
		if(startDate != null)
			startDate = startDate.trim();
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		if(endDate != null)
			endDate = endDate.trim();
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	 public void setDirection(String direction) {
	        this.direction = direction.trim();
	    }

	 public String getDirection() {
	        return direction;
	    }
	 
	 public String getLink() {
	        return link;
	    }
	 
	 public void setLink(String link) {
	        this.link = link.trim();
	    }
}
