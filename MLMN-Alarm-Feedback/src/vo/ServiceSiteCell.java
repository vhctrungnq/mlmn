package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceSiteCell {

	 private String bscid;
	 private String cellid;
	 private Date sdate;
	 private Date edate;
	 private String tgMCH;
	 private String siteid;
	 private String province;
	 private String district;
	 private String address;
	 private String causebySy;
	 private String causeby;
	 
	public String getBscid() {
		return bscid;
	}
	public void setBscid(String bscid) {
		this.bscid = bscid;
	}
	public String getCellid() {
		return cellid;
	}
	public void setCellid(String cellid) {
		this.cellid = cellid;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getTgMCH() {
		return tgMCH;
	}
	public void setTgMCH(String tgMCH) {
		this.tgMCH = tgMCH;
	}
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
  public String getSdateStr()
    {
    	if (sdate!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(sdate));
    	return null;
    }
    public String getEdateStr()
    {
    	if (edate!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(edate));
    		
    	return null;
    }
	
	public String getCauseby() {
		return causeby;
	}
	public void setCauseby(String causeby) {
		this.causeby = causeby;
	}
	public String getCausebySy() {
		return causebySy;
	}
	public void setCausebySy(String causebySy) {
		this.causebySy = causebySy;
	}
	 
}
