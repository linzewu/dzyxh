package com.xs.dzyxh.entity.driver;
// Generated 2016-11-13 14:50:54 by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * TmDrivingExamination generated by hbm2java
 */
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "TM_DRIVING_EXAMINATION", schema = "ARC_DRIVER")
public class DrivingExamination implements java.io.Serializable {

	private String id;
	private String dabh;
	private BigDecimal sg;
	private BigDecimal zsl;
	private BigDecimal ysl;
	private Character bsl;
	private Character zetl;
	private Character yetl;
	private Character zsz;
	private Character ysz;
	private Character zxz;
	private Character yxz;
	private Character qgjb;
	private Date tjrq;
	private String tjyymc;
	private String lsh;
	private Date gxsj;
	private String xh;
	private Character tlsfjz;
	private String fzjg;
	private Character dyslze;
	private BigDecimal yyspsy;
	private Character zysfjz;
	private Character yysfjz;
	private String sqzjcxdh;
	private String yjdz;
	private String yjdh;
	private Character sfjyjb;
	private String jyjbqk;
	private String wtdlrxm;
	private String wtdlrsfzmc;
	private String wtdlrsfzmhm;
	private String wtdlrlxdz;
	private String wtdlrlxdh;
	private String sqfs;
	private String sqrqzzp;
	private String ysqzzp;
	private String dlrqzzp;
	private Character csbj;
	
	private Date kssj;
	
	private Date jssj;
	
	private Date cjsj;
	
	private String sfzmhm;
	
	private String xm;
	
	private String xb;
	
	private Date csrq;
	
	private String gj;
	
	private String lxdh;
	
	
	
	@Column(name = "XM", length = 30)
	public String getXm() {
		return xm;
	}

	@Column(name = "XB", length = 20)
	public String getXb() {
		return xb;
	}

	@Column
	public Date getCsrq() {
		return csrq;
	}
	
	@Column(name = "GJ", length = 20)
	public String getGj() {
		return gj;
	}

	@Column(name = "LXDH", length = 20)
	public String getLxdh() {
		return lxdh;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}

	public void setGj(String gj) {
		this.gj = gj;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	@Column(name="sfzmhm")
	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	@Id
	@Column(name="ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DABH", length = 12)
	public String getDabh() {
		return this.dabh;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	@Column(name = "SG", precision = 22, scale = 0)
	public BigDecimal getSg() {
		return this.sg;
	}

	public void setSg(BigDecimal sg) {
		this.sg = sg;
	}

	@Column(name = "ZSL", precision = 22, scale = 0)
	public BigDecimal getZsl() {
		return this.zsl;
	}

	public void setZsl(BigDecimal zsl) {
		this.zsl = zsl;
	}

	@Column(name = "YSL", precision = 22, scale = 0)
	public BigDecimal getYsl() {
		return this.ysl;
	}

	public void setYsl(BigDecimal ysl) {
		this.ysl = ysl;
	}

	@Column(name = "BSL", length = 1)
	public Character getBsl() {
		return this.bsl;
	}

	public void setBsl(Character bsl) {
		this.bsl = bsl;
	}

	@Column(name = "ZETL", length = 1)
	public Character getZetl() {
		return this.zetl;
	}

	public void setZetl(Character zetl) {
		this.zetl = zetl;
	}
	
	@Column(name = "YETL", length = 1)
	public Character getYetl() {
		return this.yetl;
	}

	public void setYetl(Character yetl) {
		this.yetl = yetl;
	}

	@Column(name = "ZSZ", length = 1)
	public Character getZsz() {
		return this.zsz;
	}

	public void setZsz(Character zsz) {
		this.zsz = zsz;
	}
	
	@Column(name = "YSZ", length = 1)
	public Character getYsz() {
		return this.ysz;
	}

	public void setYsz(Character ysz) {
		this.ysz = ysz;
	}

	@Column(name = "ZXZ", length = 1)
	public Character getZxz() {
		return this.zxz;
	}

	public void setZxz(Character zxz) {
		this.zxz = zxz;
	}

	@Column(name = "YXZ", length = 1)
	public Character getYxz() {
		return this.yxz;
	}

	public void setYxz(Character yxz) {
		this.yxz = yxz;
	}

	@Column(name = "QGJB", length = 1)
	public Character getQgjb() {
		return this.qgjb;
	}

	public void setQgjb(Character qgjb) {
		this.qgjb = qgjb;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TJRQ", length = 7)
	public Date getTjrq() {
		return this.tjrq;
	}

	public void setTjrq(Date tjrq) {
		this.tjrq = tjrq;
	}

	@Column(name = "TJYYMC", length = 20)
	public String getTjyymc() {
		return this.tjyymc;
	}

	public void setTjyymc(String tjyymc) {
		this.tjyymc = tjyymc;
	}

	@Column(name = "LSH", length = 20)
	public String getLsh() {
		return this.lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GXSJ", length = 7)
	public Date getGxsj() {
		return this.gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	@Column(name = "XH", length = 15)
	public String getXh() {
		return this.xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	@Column(name = "TLSFJZ", length = 1)
	public Character getTlsfjz() {
		return this.tlsfjz;
	}

	public void setTlsfjz(Character tlsfjz) {
		this.tlsfjz = tlsfjz;
	}

	@Column(name = "FZJG", length = 10)
	public String getFzjg() {
		return this.fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	@Column(name = "DYSLZE", length = 1)
	public Character getDyslze() {
		return this.dyslze;
	}

	public void setDyslze(Character dyslze) {
		this.dyslze = dyslze;
	}

	@Column(name = "YYSPSY", precision = 22, scale = 0)
	public BigDecimal getYyspsy() {
		return this.yyspsy;
	}

	public void setYyspsy(BigDecimal yyspsy) {
		this.yyspsy = yyspsy;
	}

	@Column(name = "ZYSFJZ", length = 1)
	public Character getZysfjz() {
		return this.zysfjz;
	}

	public void setZysfjz(Character zysfjz) {
		this.zysfjz = zysfjz;
	}

	@Column(name = "YYSFJZ", length = 1)
	public Character getYysfjz() {
		return this.yysfjz;
	}

	public void setYysfjz(Character yysfjz) {
		this.yysfjz = yysfjz;
	}

	@Column(name = "SQZJCXDH", length = 2)
	public String getSqzjcxdh() {
		return this.sqzjcxdh;
	}

	public void setSqzjcxdh(String sqzjcxdh) {
		this.sqzjcxdh = sqzjcxdh;
	}

	@Column(name = "YJDZ", length = 256)
	public String getYjdz() {
		return this.yjdz;
	}

	public void setYjdz(String yjdz) {
		this.yjdz = yjdz;
	}

	@Column(name = "YJDH", length = 20)
	public String getYjdh() {
		return this.yjdh;
	}

	public void setYjdh(String yjdh) {
		this.yjdh = yjdh;
	}

	@Column(name = "SFJYJB", length = 1)
	public Character getSfjyjb() {
		return this.sfjyjb;
	}

	public void setSfjyjb(Character sfjyjb) {
		this.sfjyjb = sfjyjb;
	}

	@Column(name = "JYJBQK", length = 20)
	public String getJyjbqk() {
		return this.jyjbqk;
	}

	public void setJyjbqk(String jyjbqk) {
		this.jyjbqk = jyjbqk;
	}

	@Column(name = "WTDLRXM", length = 32)
	public String getWtdlrxm() {
		return this.wtdlrxm;
	}

	public void setWtdlrxm(String wtdlrxm) {
		this.wtdlrxm = wtdlrxm;
	}

	@Column(name = "WTDLRSFZMC", length = 10)
	public String getWtdlrsfzmc() {
		return this.wtdlrsfzmc;
	}

	public void setWtdlrsfzmc(String wtdlrsfzmc) {
		this.wtdlrsfzmc = wtdlrsfzmc;
	}

	@Column(name = "WTDLRSFZMHM", length = 20)
	public String getWtdlrsfzmhm() {
		return this.wtdlrsfzmhm;
	}

	public void setWtdlrsfzmhm(String wtdlrsfzmhm) {
		this.wtdlrsfzmhm = wtdlrsfzmhm;
	}

	@Column(name = "WTDLRLXDZ")
	public String getWtdlrlxdz() {
		return this.wtdlrlxdz;
	}

	public void setWtdlrlxdz(String wtdlrlxdz) {
		this.wtdlrlxdz = wtdlrlxdz;
	}

	@Column(name = "WTDLRLXDH", length = 20)
	public String getWtdlrlxdh() {
		return this.wtdlrlxdh;
	}

	public void setWtdlrlxdh(String wtdlrlxdh) {
		this.wtdlrlxdh = wtdlrlxdh;
	}

	@Column(name = "SQFS", length = 10)
	public String getSqfs() {
		return this.sqfs;
	}

	public void setSqfs(String sqfs) {
		this.sqfs = sqfs;
	}

	@Column(name = "SQRQZZP", length = 32)
	public String getSqrqzzp() {
		return this.sqrqzzp;
	}

	public void setSqrqzzp(String sqrqzzp) {
		this.sqrqzzp = sqrqzzp;
	}

	@Column(name = "YSQZZP", length = 32)
	public String getYsqzzp() {
		return this.ysqzzp;
	}

	public void setYsqzzp(String ysqzzp) {
		this.ysqzzp = ysqzzp;
	}

	@Column(name = "DLRQZZP", length = 32)
	public String getDlrqzzp() {
		return this.dlrqzzp;
	}

	public void setDlrqzzp(String dlrqzzp) {
		this.dlrqzzp = dlrqzzp;
	}

	@Column(name = "CSBJ", length = 1)
	public Character getCsbj() {
		return this.csbj;
	}

	public void setCsbj(Character csbj) {
		this.csbj = csbj;
	}

	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getKssj() {
		return kssj;
	}

	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getJssj() {
		return jssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}
	
	@Column(name = "CJSJ")
	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	

}
