package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VRFbProcessDay {

	private Date fbDate;
	private Integer daXuLy;
	private Integer dangXuLy;
	private Integer chuaXuLy;
	public Date getFbDate() {
		return fbDate;
	}
	public void setFbDate(Date fbDate) {
		this.fbDate = fbDate;
	}
	public Integer getChuaXuLy() {
		return chuaXuLy;
	}
	public void setChuaXuLy(Integer chuaXuLy) {
		this.chuaXuLy = chuaXuLy;
	}
	private Integer total;
	
	public Integer getDaXuLy() {
		return daXuLy;
	}
	public void setDaXuLy(Integer daXuLy) {
		this.daXuLy = daXuLy;
	}
	public Integer getDangXuLy() {
		return dangXuLy;
	}
	public void setDangXuLy(Integer dangXuLy) {
		this.dangXuLy = dangXuLy;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public String getFbDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if(fbDate != null)
			return df.format(fbDate);
		else		
			return null;
	}
}
