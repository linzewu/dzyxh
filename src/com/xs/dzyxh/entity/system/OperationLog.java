package com.xs.dzyxh.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.dzyxh.entity.BaseEntity;

/**
 * 操作日志
 * @author linzewu
 *
 */
@Scope("prototype")
@Component("operationLog")
@Entity
@Table(name = "TC_OperationLogs")
public class OperationLog extends BaseEntity {
	
	public static Integer OPERATION_RESULT_SUCCESS=1;
	
	public static Integer OPERATION_RESULT_ERROR=-1;
	
	
	/**
	 * 操作耗时
	 */
	@Column
	private Long actionTime;
	
	
	/**
	 * 操作时间
	 */
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operationDate;
	
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operationDateEnd;
	
	public Date getOperationDateEnd() {
		return operationDateEnd;
	}

	public void setOperationDateEnd(Date operationDateEnd) {
		this.operationDateEnd = operationDateEnd;
	}

	/**
	 * 操作人
	 */
	@Column(length=30)
	private String operationUser;
	
	/**
	 * 操作类型
	 */
	@Column(length=100)
	private String operationType;
	
	/**
	 * 内容
	 */
	@Column(length=200)
	private String content;
	
	/**
	 * 地址
	 */
	@Column(length=20)
	private String ipAddr;
	
	@Column(length=255)
	private String actionUrl;
	
	//操作结果
	@Column
	private Integer operationResult;
	
	//错误信息
	@Column(length=2000)
	private String failMsg;
	
	//所属模块
	@Column(length=200)
	private String module;
	
	//操作条件
	@Column(length=8000)
	private String operationCondition;
	
	/**
	 * 是否核心功能
	 */
	@Column(length=1)
	private String coreFunction;
	
	@Column
	private Integer status;
	


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public String getOperationType() {
		return operationType;
	}

	public String getContent() {
		return content;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public Integer getOperationResult() {
		return operationResult;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public String getModule() {
		return module;
	}

	public String getOperationCondition() {
		return operationCondition;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setOperationResult(Integer operationResult) {
		this.operationResult = operationResult;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setOperationCondition(String operationCondition) {
		this.operationCondition = operationCondition;
	}

	public Long getActionTime() {
		return actionTime;
	}

	public void setActionTime(Long actionTime) {
		this.actionTime = actionTime;
	}

	public String getCoreFunction() {
		return coreFunction;
	}

	public void setCoreFunction(String coreFunction) {
		this.coreFunction = coreFunction;
	}
	
	

}
