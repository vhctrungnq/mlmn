package vn.com.vhc.sts.cp;

import java.util.Date;

public class SmppQueue {
	private String isdn;
	private String code;
	private Date issueDatetime;
	private String serviceType;
	private String replyMessage;
	private String isdnActive;
	private String receiveMessage;
	private String error;
	private int dxl;
	private int blConfirmed;
	private String tinhtrang;
	private Date ngayKk;

	public String getIsdn() {
		return isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getIssueDatetime() {
		return issueDatetime;
	}

	public void setIssueDatetime(Date issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getIsdnActive() {
		return isdnActive;
	}

	public void setIsdnActive(String isdnActive) {
		this.isdnActive = isdnActive;
	}

	public String getReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getDxl() {
		return dxl;
	}

	public void setDxl(int dxl) {
		this.dxl = dxl;
	}

	public int getBlConfirmed() {
		return blConfirmed;
	}

	public void setBlConfirmed(int blConfirmed) {
		this.blConfirmed = blConfirmed;
	}

	public String getTinhtrang() {
		return tinhtrang;
	}

	public void setTinhtrang(String tinhtrang) {
		this.tinhtrang = tinhtrang;
	}

	public Date getNgayKk() {
		return ngayKk;
	}

	public void setNgayKk(Date ngayKk) {
		this.ngayKk = ngayKk;
	}

}
