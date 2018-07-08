package vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class FbProcessFilter {


	private String fbType;
	
	private String networkType;
	private String fbContent;
	private String causedby;
	private String responseContent;
	private Integer id;
	private String createdBy;
	private String modifiedBy;
	private Date createDate;
	private Date modifyDate;
	
	public String getFbType() {
		return fbType;
	}
	public void setFbType(String fbType) {
		this.fbType = fbType;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getFbContent() {
		return fbContent;
	}
	public void setFbContent(String fbContent) {
		if (fbContent!=null)
			this.fbContent = fbContent.trim();
		else
			this.fbContent = fbContent;
		
	}
	public String getCausedby() {
		return causedby;
	}
	public void setCausedby(String causedby) {
		if (causedby!=null)
			this.causedby = causedby.trim();
		else
			this.causedby = causedby;
		
	}
	public String getResponseContent() {
		return responseContent;
	}
	public void setResponseContent(String responseContent) {
		if (responseContent!=null)
			this.responseContent = responseContent.trim();
		else
			this.responseContent = responseContent;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
