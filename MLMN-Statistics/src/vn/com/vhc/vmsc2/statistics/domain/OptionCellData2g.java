package vn.com.vhc.vmsc2.statistics.domain;

public class OptionCellData2g {

	
	private String sdatetime;
	private String edatetime;
	private Integer countRow;
	
	private String bscid;
	private String cellid;
	private Float pdchAllocated;
	private Float pdchUsed;
	private Float packetChAllocAtt;
	private Float packetChAllocFail;
	private Float packetChAllocSucr;
	private Float ulTbfReq;
	private Float ulTbfFail;
	private Float ulTbfSucr;
	private Float dlTbfReq;
	private Float dlTbfFail;
	private Float dlTbfSucr;
	private Float gprsUlData;
	private Float gprsDlData;
	private Float edgeUlData;
	private Float edgeDlData;
	private Float gprsUlDataThroughput;
	private Float gprsDlDataThroughput;
	private Float edgeUlDataThroughput;
	private Float edgeDlDataThroughput;
	
	public String getSdatetime() {
		return sdatetime;
	}

	public void setSdatetime(String sdatetime) {
		this.sdatetime = sdatetime;
	}

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

	public Float getPdchUsed() {
		return pdchUsed;
	}

	public void setPdchUsed(Float pdchUsed) {
		this.pdchUsed = pdchUsed;
	}

	public Float getPacketChAllocAtt() {
		return packetChAllocAtt;
	}

	public void setPacketChAllocAtt(Float packetChAllocAtt) {
		this.packetChAllocAtt = packetChAllocAtt;
	}

	public Float getPacketChAllocFail() {
		return packetChAllocFail;
	}

	public void setPacketChAllocFail(Float packetChAllocFail) {
		this.packetChAllocFail = packetChAllocFail;
	}

	public Float getPacketChAllocSucr() {
		return packetChAllocSucr;
	}

	public void setPacketChAllocSucr(Float packetChAllocSucr) {
		this.packetChAllocSucr = packetChAllocSucr;
	}

	public Float getUlTbfReq() {
		return ulTbfReq;
	}

	public void setUlTbfReq(Float ulTbfReq) {
		this.ulTbfReq = ulTbfReq;
	}

	public Float getUlTbfFail() {
		return ulTbfFail;
	}

	public void setUlTbfFail(Float ulTbfFail) {
		this.ulTbfFail = ulTbfFail;
	}

	public Float getUlTbfSucr() {
		return ulTbfSucr;
	}

	public void setUlTbfSucr(Float ulTbfSucr) {
		this.ulTbfSucr = ulTbfSucr;
	}

	public Float getDlTbfReq() {
		return dlTbfReq;
	}

	public void setDlTbfReq(Float dlTbfReq) {
		this.dlTbfReq = dlTbfReq;
	}

	public Float getDlTbfFail() {
		return dlTbfFail;
	}

	public void setDlTbfFail(Float dlTbfFail) {
		this.dlTbfFail = dlTbfFail;
	}

	public Float getDlTbfSucr() {
		return dlTbfSucr;
	}

	public void setDlTbfSucr(Float dlTbfSucr) {
		this.dlTbfSucr = dlTbfSucr;
	}

	public Float getGprsUlData() {
		return gprsUlData;
	}

	public void setGprsUlData(Float gprsUlData) {
		this.gprsUlData = gprsUlData;
	}

	public Float getGprsDlData() {
		return gprsDlData;
	}

	public void setGprsDlData(Float gprsDlData) {
		this.gprsDlData = gprsDlData;
	}

	public Float getEdgeUlData() {
		return edgeUlData;
	}

	public void setEdgeUlData(Float edgeUlData) {
		this.edgeUlData = edgeUlData;
	}

	public Float getEdgeDlData() {
		return edgeDlData;
	}

	public void setEdgeDlData(Float edgeDlData) {
		this.edgeDlData = edgeDlData;
	}

	public Float getGprsUlDataThroughput() {
		return gprsUlDataThroughput;
	}

	public void setGprsUlDataThroughput(Float gprsUlDataThroughput) {
		this.gprsUlDataThroughput = gprsUlDataThroughput;
	}

	public Float getGprsDlDataThroughput() {
		return gprsDlDataThroughput;
	}

	public void setGprsDlDataThroughput(Float gprsDlDataThroughput) {
		this.gprsDlDataThroughput = gprsDlDataThroughput;
	}

	public Float getEdgeUlDataThroughput() {
		return edgeUlDataThroughput;
	}

	public void setEdgeUlDataThroughput(Float edgeUlDataThroughput) {
		this.edgeUlDataThroughput = edgeUlDataThroughput;
	}

	public Float getEdgeDlDataThroughput() {
		return edgeDlDataThroughput;
	}

	public void setEdgeDlDataThroughput(Float edgeDlDataThroughput) {
		this.edgeDlDataThroughput = edgeDlDataThroughput;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	private String region;
	private String province;
	private String district;

	public String getEdatetime() {
		return edatetime;
	}

	public void setEdatetime(String edatetime) {
		this.edatetime = edatetime;
	}

	public Integer getCountRow() {
		return countRow;
	}

	public void setCountRow(Integer countRow) {
		this.countRow = countRow;
	}

	public Float getPdchAllocated() {
		return pdchAllocated;
	}

	public void setPdchAllocated(Float pdchAllocated) {
		this.pdchAllocated = pdchAllocated;
	}

}
