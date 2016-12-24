package com.xs.dzyxh.entity.tongan;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "SQ_PHOTOS", schema = "TONGAN")
public class SqPhotos {
	private SqPhotosId id;
	private Date sqrq;
	private byte[] photoMgzp;
	private byte[] photoQrqm;
	private byte[] photoDlrqm;
	private byte[] photoSfzzm;
	private byte[] photoSfzfm;
	private byte[] photoDlrsfzzm;
	private byte[] photoDlrsfzfm;
	private byte[] photoJzzzm;
	private byte[] photoJzzfm;
	private byte[] photoTjb;
	private byte[] photoSqb;
	private Character sjbj;
	private Date czrq;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "sfzmhm", column = @Column(name = "SFZMHM", nullable = false, length = 20)),
			@AttributeOverride(name = "qh", column = @Column(name = "QH", nullable = false, length = 20)) ,
			@AttributeOverride(name = "jxdm", column = @Column(name = "JXDM", nullable = false, length = 20)) 
			})
	public SqPhotosId getId() {
		return id;
	}
	public void setId(SqPhotosId id) {
		this.id = id;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Temporal(TemporalType.DATE)
	@Column(name = "SQRQ", length = 7)
	public Date getSqrq() {
		return sqrq;
	}
	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_MGZP", columnDefinition = "BLOB")
	public byte[] getPhotoMgzp() {
		return photoMgzp;
	}
	public void setPhotoMgzp(byte[] photoMgzp) {
		this.photoMgzp = photoMgzp;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_SQRQM", columnDefinition = "BLOB")
	public byte[] getPhotoQrqm() {
		return photoQrqm;
	}
	public void setPhotoQrqm(byte[] photoQrqm) {
		this.photoQrqm = photoQrqm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_DLRQM", columnDefinition = "BLOB")
	public byte[] getPhotoDlrqm() {
		return photoDlrqm;
	}
	public void setPhotoDlrqm(byte[] photoDlrqm) {
		this.photoDlrqm = photoDlrqm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_SFZZM", columnDefinition = "BLOB")
	public byte[] getPhotoSfzzm() {
		return photoSfzzm;
	}
	public void setPhotoSfzzm(byte[] photoSfzzm) {
		this.photoSfzzm = photoSfzzm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_SFZFM", columnDefinition = "BLOB")
	public byte[] getPhotoSfzfm() {
		return photoSfzfm;
	}
	public void setPhotoSfzfm(byte[] photoSfzfm) {
		this.photoSfzfm = photoSfzfm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_DLRSFZZM", columnDefinition = "BLOB")
	public byte[] getPhotoDlrsfzzm() {
		return photoDlrsfzzm;
	}
	public void setPhotoDlrsfzzm(byte[] photoDlrsfzzm) {
		this.photoDlrsfzzm = photoDlrsfzzm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_DLRSFZFM", columnDefinition = "BLOB")
	public byte[] getPhotoDlrsfzfm() {
		return photoDlrsfzfm;
	}
	public void setPhotoDlrsfzfm(byte[] photoDlrsfzfm) {
		this.photoDlrsfzfm = photoDlrsfzfm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_JZZZM", columnDefinition = "BLOB")
	public byte[] getPhotoJzzzm() {
		return photoJzzzm;
	}
	public void setPhotoJzzzm(byte[] photoJzzzm) {
		this.photoJzzzm = photoJzzzm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_JZZFM", columnDefinition = "BLOB")
	public byte[] getPhotoJzzfm() {
		return photoJzzfm;
	}
	public void setPhotoJzzfm(byte[] photoJzzfm) {
		this.photoJzzfm = photoJzzfm;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_TJB", columnDefinition = "BLOB")
	public byte[] getPhotoTjb() {
		return photoTjb;
	}
	public void setPhotoTjb(byte[] photoTjb) {
		this.photoTjb = photoTjb;
	}
	

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_SQB", columnDefinition = "BLOB")
	public byte[] getPhotoSqb() {
		return photoSqb;
	}
	public void setPhotoSqb(byte[] photoSqb) {
		this.photoSqb = photoSqb;
	}
	
	@Column(name = "BSL", length = 1)
	public Character getSjbj() {
		return sjbj;
	}
	public void setSjbj(Character sjbj) {
		this.sjbj = sjbj;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Temporal(TemporalType.DATE)
	@Column(name = "CZRQ", length = 7)
	public Date getCzrq() {
		return czrq;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	
}