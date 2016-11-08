package com.ljyuan.test;

import java.io.Serializable;

/**
 * https://www.seebug.org/
 * @author Administrator
 *
 */
public class SeebugRepo implements Serializable {
	private static final long serialVersionUID = 1L;
	//漏洞编号
	private String bugId;
	//漏洞名称
	private String bugName;
	//漏洞发现时间
	private String bugFindDate;
	//漏洞提交时间
	private String bugSubmitDate;
	//漏洞等级
	private Integer bugLevel;
	//漏洞类型
	private String bugType;
	private String cveId;
	private String cnnvdId;
	private String cnvdId;
	//漏洞作者
	private String bugAuthor;
	//漏洞提交人
	private String bugSubmitter;
	//漏洞描述
	private String bugDescribe;
	//ZoomEye Dork
	private String zoomEyeDork;
	//影响组件
	private String affectsComponent;
	//漏洞概要
	private String bugOutline;
	public String getBugId() {
		return bugId;
	}
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	public String getBugName() {
		return bugName;
	}
	public void setBugName(String bugName) {
		this.bugName = bugName;
	}
	public String getBugFindDate() {
		return bugFindDate;
	}
	public void setBugFindDate(String bugFindDate) {
		this.bugFindDate = bugFindDate;
	}
	public String getBugSubmitDate() {
		return bugSubmitDate;
	}
	public void setBugSubmitDate(String bugSubmitDate) {
		this.bugSubmitDate = bugSubmitDate;
	}
	public Integer getBugLevel() {
		return bugLevel;
	}
	public void setBugLevel(Integer bugLevel) {
		this.bugLevel = bugLevel;
	}
	public String getBugType() {
		return bugType;
	}
	public void setBugType(String bugType) {
		this.bugType = bugType;
	}
	public String getCveId() {
		return cveId;
	}
	public void setCveId(String cveId) {
		this.cveId = cveId;
	}
	public String getCnnvdId() {
		return cnnvdId;
	}
	public void setCnnvdId(String cnnvdId) {
		this.cnnvdId = cnnvdId;
	}
	public String getCnvdId() {
		return cnvdId;
	}
	public void setCnvdId(String cnvdId) {
		this.cnvdId = cnvdId;
	}
	public String getBugAuthor() {
		return bugAuthor;
	}
	public void setBugAuthor(String bugAuthor) {
		this.bugAuthor = bugAuthor;
	}
	public String getBugSubmitter() {
		return bugSubmitter;
	}
	public void setBugSubmitter(String bugSubmitter) {
		this.bugSubmitter = bugSubmitter;
	}
	public String getBugDescribe() {
		return bugDescribe;
	}
	public void setBugDescribe(String bugDescribe) {
		this.bugDescribe = bugDescribe;
	}
	public String getZoomEyeDork() {
		return zoomEyeDork;
	}
	public void setZoomEyeDork(String zoomEyeDork) {
		this.zoomEyeDork = zoomEyeDork;
	}
	public String getAffectsComponent() {
		return affectsComponent;
	}
	public void setAffectsComponent(String affectsComponent) {
		this.affectsComponent = affectsComponent;
	}
	public String getBugOutline() {
		return bugOutline;
	}
	public void setBugOutline(String bugOutline) {
		this.bugOutline = bugOutline;
	}
	@Override
	public String toString() {
		return "SeebugRepo [bugId=" + bugId + ", bugName=" + bugName + ", bugFindDate=" + bugFindDate
				+ ", bugSubmitDate=" + bugSubmitDate + ", bugLevel=" + bugLevel + ", bugType=" + bugType + ", cveId="
				+ cveId + ", cnnvdId=" + cnnvdId + ", cnvdId=" + cnvdId + ", bugAuthor=" + bugAuthor + ", bugSubmitter="
				+ bugSubmitter + ", bugDescribe=" + bugDescribe + ", zoomEyeDork=" + zoomEyeDork + ", affectsComponent="
				+ affectsComponent + ", bugOutline=" + bugOutline + "]";
	}
	
	
}
