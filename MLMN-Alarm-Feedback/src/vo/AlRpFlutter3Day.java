package vo;

import java.util.Date;

public class AlRpFlutter3Day {
	private Integer id;
	private Date day;
	private String bscid;
	private String site;
	private String teamProcess;

	private Integer items;
	private Integer totalTime;

	private Integer items1;
	private Integer totalTime1;

	private Integer items2;
	private Integer totalTime2;

	private String transType;
	
/*---------------------Get and Set-----------------------------*/
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getBscid() {
		return bscid;
	}

	public void setBscid(String bscid) {
		this.bscid = bscid;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTeamProcess() {
		return teamProcess;
	}

	public void setTeamProcess(String teamProcess) {
		this.teamProcess = teamProcess;
	}

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getItems1() {
		return items1;
	}

	public void setItems1(Integer items1) {
		this.items1 = items1;
	}

	public Integer getTotalTime1() {
		return totalTime1;
	}

	public void setTotalTime1(Integer totalTime1) {
		this.totalTime1 = totalTime1;
	}

	public Integer getItems2() {
		return items2;
	}

	public void setItems2(Integer items2) {
		this.items2 = items2;
	}

	public Integer getTotalTime2() {
		return totalTime2;
	}

	public void setTotalTime2(Integer totalTime2) {
		this.totalTime2 = totalTime2;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

}
