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
@Table(name = "ZW_INFO_DATA")
public class ZwInfoData {
	private ZwInfoDataId id;
	private String mainKey;
	private String revokeTime;
	private Integer infoType;
	private Integer infoSize;
	private byte[] info;
	private Integer security;
	private String createTime;
	private String modifyTime;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "USER_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "subKey", column = @Column(name = "SUB_KEY", nullable = false, length = 20)) 
			})
	public ZwInfoDataId getId() {
		return id;
	}

	public void setId(ZwInfoDataId id) {
		this.id = id;
	}

	@Column(name = "MAIN_KEY", length = 20)
	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
	
	@Column(name = "REVOKE_TIME", length = 20)
	public String getRevokeTime() {
		return revokeTime;
	}

	public void setRevokeTime(String revokeTime) {
		this.revokeTime = revokeTime;
	}

	@Column(name = "INFO_TYPE")
	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	@Column(name = "INFO_SIZE")
	public Integer getInfoSize() {
		return infoSize;
	}

	public void setInfoSize(Integer infoSize) {
		this.infoSize = infoSize;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "INFO", columnDefinition = "BLOB")
	public byte[] getInfo() {
		return info;
	}

	public void setInfo(byte[] info) {
		this.info = info;
	}

	@Column(name = "SECURITY")
	public Integer getSecurity() {
		return security;
	}

	public void setSecurity(Integer security) {
		this.security = security;
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
