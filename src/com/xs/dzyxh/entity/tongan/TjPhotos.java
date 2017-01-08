package com.xs.dzyxh.entity.tongan;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "TJ_PHOTOS")
public class TjPhotos {
	private String idno;
	private byte[] photoXy;
	private byte[] photoSfz;
	private byte[] photoYsqm;
	private byte[] photoXyqm;
	private Date tjrq;
	
	@Id
	@Column(name="IDNO")
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_XY", columnDefinition = "BLOB")
	public byte[] getPhotoXy() {
		return photoXy;
	}
	public void setPhotoXy(byte[] photoXy) {
		this.photoXy = photoXy;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_SFZ", columnDefinition = "BLOB")
	public byte[] getPhotoSfz() {
		return photoSfz;
	}
	public void setPhotoSfz(byte[] photoSfz) {
		this.photoSfz = photoSfz;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_YSQM", columnDefinition = "BLOB")
	public byte[] getPhotoYsqm() {
		return photoYsqm;
	}
	public void setPhotoYsqm(byte[] photoYsqm) {
		this.photoYsqm = photoYsqm;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_XYQM", columnDefinition = "BLOB")
	public byte[] getPhotoXyqm() {
		return photoXyqm;
	}
	public void setPhotoXyqm(byte[] photoXyqm) {
		this.photoXyqm = photoXyqm;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Temporal(TemporalType.DATE)
	@Column(name = "TJRQ", length = 7)
	public Date getTjrq() {
		return tjrq;
	}
	public void setTjrq(Date tjrq) {
		this.tjrq = tjrq;
	}
	
	
}
