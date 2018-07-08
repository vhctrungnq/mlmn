package vo.alarm.utils;

public class TimeLog {
	private String startDate; // dd/MM/yyyy HH:mm
	private String endDate;
	private int startRecord;
	private int endRecord;
	
	public void setStartDate(String startDate) {
		if (startDate!=null)
			startDate=startDate.trim();
		this.startDate = startDate;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		if (endDate!=null)
			endDate=endDate.trim();
		this.endDate = endDate;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

}
