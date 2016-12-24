package com.xs.dzyxh.entity.tongan;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name = "ZW_ENROLL_TEMP")
public class ZwEnrollTemp {
	private ZwEnrollTempId id;
	private Integer version;
	private Integer serviceType;
	private Integer serviceCode;
	private String revokeTime;
	private Integer tempType;
	private Integer tempSize;
	private byte[] template;
	private String createTime;
	private String modifyTime;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "authenInfo", column = @Column(name = "AUTHEN_INFO", nullable = false, length = 20)) 
			})
	public ZwEnrollTempId getId() {
		return id;
	}
	public void setId(ZwEnrollTempId id) {
		this.id = id;
	}
	
	@Column(name = "VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Column(name = "SERVICE_TYPE")
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	
	@Column(name = "SERVICE_CODE")
	public Integer getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	@Column(name = "REVOKE_TIME", length = 20)
	public String getRevokeTime() {
		return revokeTime;
	}
	public void setRevokeTime(String revokeTime) {
		this.revokeTime = revokeTime;
	}
	
	@Column(name = "TEMP_TYPE")
	public Integer getTempType() {
		return tempType;
	}
	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}
	
	@Column(name = "TEMP_SIZE")
	public Integer getTempSize() {
		return tempSize;
	}
	public void setTempSize(Integer tempSize) {
		this.tempSize = tempSize;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TEMPLATE", columnDefinition = "BLOB")
	public byte[] getTemplate() {
		return template;
	}
	public void setTemplate(byte[] template) {
		this.template = template;
	}
	
	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "MODIFY_TIME", length = 20)
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}	
