package com.xs.dzyxh.entity.tongan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "DRV_TEMP_MID", schema = "TONGAN")
public class DrvTempMid {
	private String sfzmhm;
	private String sfzmmc;
	private String xm;
	private String xb;
	private Date csrq;
	private String gj;
	private String djzsxxdz;
	private String djzsxzqh;
	private String lxzsxxdz;
	private String lxzsxzqh;
	private String lxzsyzbm;
	private String lxdh;
	private String zzzm;
	private String zkcx;
	private String jxmc;
	private Integer sg;
	private Integer zsl;
	private Integer ysl;
	private String bsl;
	private String zxz;
	private String yxz;
	private String qbqg;
	private Date tjrq;
	private String tjyymc;
	private String sjbj;
	private String hmcd;
	private String ly;
	private String qh;
	private String yddh;
	private String dzxx;
	private String zzsb;
	private String zzzl;
	private String zysfjz;
	private String yysfjz;
	private String zetl;
	private String yetl;
	private String qgjb;
	private String zsz;
	private String ysz;
	private String tlsfjz;
	private String dyslze;
	private Integer yyspsy;
	private String sfjyjb;
	private String jyjbqk;
	private String sfngzzzl;
	private String jxdm;
	private String ywlx;
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	@Column(name = "SFZMHM", length = 18)
	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	@Column(name = "SFZMMC", length = 1)
	public String getSfzmmc() {
		return sfzmmc;
	}

	public void setSfzmmc(String sfzmmc) {
		this.sfzmmc = sfzmmc;
	}

	@Column(name = "XM", length = 30)
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	@Column(name = "XB", length = 1)
	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CSRQ", length = 7)
	public Date getCsrq() {
		return csrq;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}

	@Column(name = "GJ", length = 50)
	public String getGj() {
		return gj;
	}

	public void setGj(String gj) {
		this.gj = gj;
	}

	@Column(name = "DJZSXXDZ", length = 128)
	public String getDjzsxxdz() {
		return djzsxxdz;
	}

	public void setDjzsxxdz(String djzsxxdz) {
		this.djzsxxdz = djzsxxdz;
	}

	@Column(name = "DJZSXZQH", length = 10)
	public String getDjzsxzqh() {
		return djzsxzqh;
	}

	public void setDjzsxzqh(String djzsxzqh) {
		this.djzsxzqh = djzsxzqh;
	}

	@Column(name = "LXZSXXDZ", length = 128)
	public String getLxzsxxdz() {
		return lxzsxxdz;
	}

	public void setLxzsxxdz(String lxzsxxdz) {
		this.lxzsxxdz = lxzsxxdz;
	}

	@Column(name = "LXZSXZQH", length = 10)
	public String getLxzsxzqh() {
		return lxzsxzqh;
	}

	public void setLxzsxzqh(String lxzsxzqh) {
		this.lxzsxzqh = lxzsxzqh;
	}

	@Column(name = "LXZSYZBM", length = 10)
	public String getLxzsyzbm() {
		return lxzsyzbm;
	}

	public void setLxzsyzbm(String lxzsyzbm) {
		this.lxzsyzbm = lxzsyzbm;
	}

	@Column(name = "LXDH", length = 20)
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	@Column(name = "ZZZM", length = 18)
	public String getZzzm() {
		return zzzm;
	}

	public void setZzzm(String zzzm) {
		this.zzzm = zzzm;
	}

	@Column(name = "ZKCX", length = 6)
	public String getZkcx() {
		return zkcx;
	}

	public void setZkcx(String zkcx) {
		this.zkcx = zkcx;
	}

	@Column(name = "JXMC", length = 50)
	public String getJxmc() {
		return jxmc;
	}

	public void setJxmc(String jxmc) {
		this.jxmc = jxmc;
	}

	@Column(name = "SG")
	public Integer getSg() {
		return sg;
	}

	public void setSg(Integer sg) {
		this.sg = sg;
	}

	@Column(name = "ZSL")
	public Integer getZsl() {
		return zsl;
	}

	public void setZsl(Integer zsl) {
		this.zsl = zsl;
	}

	@Column(name = "YSL")
	public Integer getYsl() {
		return ysl;
	}

	public void setYsl(Integer ysl) {
		this.ysl = ysl;
	}

	@Column(name = "BSL", length = 1)
	public String getBsl() {
		return bsl;
	}

	public void setBsl(String bsl) {
		this.bsl = bsl;
	}

	@Column(name = "ZXZ", length = 1)
	public String getZxz() {
		return zxz;
	}

	public void setZxz(String zxz) {
		this.zxz = zxz;
	}

	@Column(name = "YXZ", length = 1)
	public String getYxz() {
		return yxz;
	}

	public void setYxz(String yxz) {
		this.yxz = yxz;
	}

	@Column(name = "QBQG", length = 1)
	public String getQbqg() {
		return qbqg;
	}

	public void setQbqg(String qbqg) {
		this.qbqg = qbqg;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TJRQ", length = 7)
	public Date getTjrq() {
		return tjrq;
	}

	public void setTjrq(Date tjrq) {
		this.tjrq = tjrq;
	}

	@Column(name = "TJYYMC", length = 50)
	public String getTjyymc() {
		return tjyymc;
	}

	public void setTjyymc(String tjyymc) {
		this.tjyymc = tjyymc;
	}

	@Column(name = "SJBJ", length = 1)
	public String getSjbj() {
		return sjbj;
	}

	public void setSjbj(String sjbj) {
		this.sjbj = sjbj;
	}

	@Column(name = "HMCD", length = 1)
	public String getHmcd() {
		return hmcd;
	}

	public void setHmcd(String hmcd) {
		this.hmcd = hmcd;
	}

	@Column(name = "LY", length = 2)
	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	@Column(name = "QH", length = 10)
	public String getQh() {
		return qh;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}

	@Column(name = "YDDH", length = 20)
	public String getYddh() {
		return yddh;
	}

	public void setYddh(String yddh) {
		this.yddh = yddh;
	}

	@Column(name = "DZXX", length = 50)
	public String getDzxx() {
		return dzxx;
	}

	public void setDzxx(String dzxx) {
		this.dzxx = dzxx;
	}

	@Column(name = "ZZSB", length = 50)
	public String getZzsb() {
		return zzsb;
	}

	public void setZzsb(String zzsb) {
		this.zzsb = zzsb;
	}

	@Column(name = "ZZZL", length = 50)
	public String getZzzl() {
		return zzzl;
	}

	public void setZzzl(String zzzl) {
		this.zzzl = zzzl;
	}

	@Column(name = "ZYSFJZ", length = 1)
	public String getZysfjz() {
		return zysfjz;
	}

	public void setZysfjz(String zysfjz) {
		this.zysfjz = zysfjz;
	}

	@Column(name = "YYSFJZ", length = 1)
	public String getYysfjz() {
		return yysfjz;
	}

	public void setYysfjz(String yysfjz) {
		this.yysfjz = yysfjz;
	}

	@Column(name = "ZETL", length = 20)
	public String getZetl() {
		return zetl;
	}

	public void setZetl(String zetl) {
		this.zetl = zetl;
	}

	@Column(name = "YETL", length = 20)
	public String getYetl() {
		return yetl;
	}

	public void setYetl(String yetl) {
		this.yetl = yetl;
	}

	@Column(name = "QGJB", length = 40)
	public String getQgjb() {
		return qgjb;
	}

	public void setQgjb(String qgjb) {
		this.qgjb = qgjb;
	}

	@Column(name = "ZSZ", length = 20)
	public String getZsz() {
		return zsz;
	}

	public void setZsz(String zsz) {
		this.zsz = zsz;
	}

	@Column(name = "YSZ", length = 20)
	public String getYsz() {
		return ysz;
	}

	public void setYsz(String ysz) {
		this.ysz = ysz;
	}

	@Column(name = "TLSFJZ", length = 50)
	public String getTlsfjz() {
		return tlsfjz;
	}

	public void setTlsfjz(String tlsfjz) {
		this.tlsfjz = tlsfjz;
	}

	@Column(name = "DYSLZE", length = 1)
	public String getDyslze() {
		return dyslze;
	}

	public void setDyslze(String dyslze) {
		this.dyslze = dyslze;
	}

	@Column(name = "YYSPSY")
	public Integer getYyspsy() {
		return yyspsy;
	}

	public void setYyspsy(Integer yyspsy) {
		this.yyspsy = yyspsy;
	}

	@Column(name = "SFJYJB", length = 1)
	public String getSfjyjb() {
		return sfjyjb;
	}

	public void setSfjyjb(String sfjyjb) {
		this.sfjyjb = sfjyjb;
	}

	@Column(name = "JYJBQK", length = 2)
	public String getJyjbqk() {
		return jyjbqk;
	}

	public void setJyjbqk(String jyjbqk) {
		this.jyjbqk = jyjbqk;
	}

	@Column(name = "SFNGZZZL", length = 20)
	public String getSfngzzzl() {
		return sfngzzzl;
	}

	public void setSfngzzzl(String sfngzzzl) {
		this.sfngzzzl = sfngzzzl;
	}

	@Column(name = "JXDM", length = 20)
	public String getJxdm() {
		return jxdm;
	}

	public void setJxdm(String jxdm) {
		this.jxdm = jxdm;
	}

	@Column(name = "YWLX", length = 20)
	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

}
