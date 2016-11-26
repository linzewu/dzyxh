package com.xs.dzyxh.entity.driver;
// Generated 2016-11-7 14:29:22 by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TmDrivingExamination generated by hbm2java
 */
@Entity
@Table(name = "TM_DRIVING_EXAMINATION", schema = "ARC_DRIVER")
public class DrivingExamination implements java.io.Serializable {

	private DrivingExaminationId id;//联合主键
	private String dabh;//档案编号
	private BigDecimal sg;//身高
	private BigDecimal zsl;//左视力
	private BigDecimal ysl;//右视力
	private Character bsl;//辨色力
	private Character ztl;//左听力
	private Character ytl;//右听力
	private Character zsz;//左上肢
	private Character ysz;//右上肢
	private Character zxz;//左下肢
	private Character yxz;//右下肢
	private Character qgjb;//躯干颈部
	private Date tjrq;//体检日期
	private String tjyymc;//体检医院名称
	private String lsh;//流水号
	private Date gxsj;//更新时间
	private String xh;//序号
	private Character tlsfjz;//听力是否校正
	private String fzjg;
	private Character dyslze;//单眼视力障碍
	private BigDecimal yyspsy;//优眼水平视野
	private Character zysfjz;//左眼是否矫正
	private Character yysfjz;//右眼是否矫正
	private String sqzjcxdh;//申请/已具有的准驾车型代号
	private String yjdz;//邮寄地址
	private String yjdh;//邮寄电话
	private Character sfjyjb;//是否具有疾病
	private String jyjbqk;//具有疾病或情况
	private String wtdlrxm;//委托代理人姓名
	private String wtdlrsfzmc;//委托代理人身份名称
	private String wtdlrsfzmhm;//委托代理人身份号码
	private String wtdlrlxdz;//委托代理人联系地址
	private String wtdlrlxdh;//委托代理人联系电话
	private String sqfs;//申请方式
	private String sqrqzzp;//申请人签字
	private String sqrqzp;//医生签字
	private String dlrqzzp;//代理人签字
	private Character csbj;
	private Date cjsj;// 创建时间
	private String tjbzp;//体检表照片
	

	public DrivingExamination() {
	}

	public DrivingExamination(DrivingExaminationId id) {
		this.id = id;
	}

	public DrivingExamination(DrivingExaminationId id, String dabh, BigDecimal sg, BigDecimal zsl, BigDecimal ysl,
			Character bsl, Character zxz, Character yxz, Character qgjb, Date tjrq,
			String tjyymc, String lsh, Date gxsj, String xh, Character tlsfjz, String fzjg, Character dyslze,
			BigDecimal yyspsy, Character zysfjz, Character yysfjz, String sqzjcxdh, String yjdz, String yjdh,
			Character sfjyjb, String jyjbqk, String wtdlrxm, String wtdlrsfzmc, String wtdlrsfzmhm, String wtdlrlxdz,
			String wtdlrlxdh, String sqfs, String sqrqzzp, String sqrqzp, String dlrqzzp, Character csbj, Date cjsj) {
		this.id = id;
		this.dabh = dabh;
		this.sg = sg;
		this.zsl = zsl;
		this.ysl = ysl;
		this.bsl = bsl;
		this.zxz = zxz;
		this.yxz = yxz;
		this.qgjb = qgjb;
		this.tjrq = tjrq;
		this.tjyymc = tjyymc;
		this.lsh = lsh;
		this.gxsj = gxsj;
		this.xh = xh;
		this.tlsfjz = tlsfjz;
		this.fzjg = fzjg;
		this.dyslze = dyslze;
		this.yyspsy = yyspsy;
		this.zysfjz = zysfjz;
		this.yysfjz = yysfjz;
		this.sqzjcxdh = sqzjcxdh;
		this.yjdz = yjdz;
		this.yjdh = yjdh;
		this.sfjyjb = sfjyjb;
		this.jyjbqk = jyjbqk;
		this.wtdlrxm = wtdlrxm;
		this.wtdlrsfzmc = wtdlrsfzmc;
		this.wtdlrsfzmhm = wtdlrsfzmhm;
		this.wtdlrlxdz = wtdlrlxdz;
		this.wtdlrlxdh = wtdlrlxdh;
		this.sqfs = sqfs;
		this.sqrqzzp = sqrqzzp;
		this.sqrqzp = sqrqzp;
		this.dlrqzzp = dlrqzzp;
		this.csbj = csbj;
		this.cjsj = cjsj;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "qh", column = @Column(name = "QH", nullable = false, length = 10)),
			@AttributeOverride(name = "sfzmhm", column = @Column(name = "SFZMHM", nullable = false, length = 20)),
			@AttributeOverride(name = "jxdm", column = @Column(name = "JXDM", nullable = false, length = 2)) })
	public DrivingExaminationId getId() {
		return this.id;
	}

	public void setId(DrivingExaminationId id) {
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

	@Column(name = "SQRQZP", length = 32)
	public String getSqrqzp() {
		return this.sqrqzp;
	}

	public void setSqrqzp(String sqrqzp) {
		this.sqrqzp = sqrqzp;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CJSJ", length = 7)
	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	@Column(name = "TJBZP", length = 32)
	public String getTjbzp() {
		return tjbzp;
	}

	public void setTjbzp(String tjbzp) {
		this.tjbzp = tjbzp;
	}
	@Column(name = "ZTL", length = 1)
	public Character getZtl() {
		return ztl;
	}

	public void setZtl(Character ztl) {
		this.ztl = ztl;
	}
	@Column(name = "YTL", length = 1)
	public Character getYtl() {
		return ytl;
	}

	public void setYtl(Character ytl) {
		this.ytl = ytl;
	}
	@Column(name = "ZSZ", length = 1)
	public Character getZsz() {
		return zsz;
	}

	public void setZsz(Character zsz) {
		this.zsz = zsz;
	}
	@Column(name = "YSZ", length = 1)
	public Character getYsz() {
		return ysz;
	}

	public void setYsz(Character ysz) {
		this.ysz = ysz;
	}

}
