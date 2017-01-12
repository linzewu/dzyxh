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
@Table(name = "TJ_USER")
public class TjUser {
	private TjUserId id;
	private byte[] dwyztp;
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "xm", column = @Column(name = "XM", nullable = false, length = 20)),
			@AttributeOverride(name = "yymc", column = @Column(name = "YYMC", nullable = false, length = 200)) })
	public TjUserId getId() {
		return id;
	}

	public void setId(TjUserId id) {
		this.id = id;
	}
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "DWYZTP", columnDefinition = "BLOB")
	public byte[] getDwyztp() {
		return dwyztp;
	}

	public void setDwyztp(byte[] dwyztp) {
		this.dwyztp = dwyztp;
	}

}
