package cn.org.cnnvd.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Cnnvd implements Serializable{
	private static final long serialVersionUID = 1L;
	//id
	private Integer id;
	//cnvd编号
	private String cnnvdId;
	//cnvd名称
	private String cnnvdName;
	//发布日期
	private String publishDate;
	//更新时间
	private String updateTime;
	//危害等级
	private String harmLevel;
	//漏洞类型
	private String bugType;
	//威胁类型
	private String menaceType;
	//CVE 编号
	private String cveId;
	//漏洞简介
	private String bugSynopsis;
	//漏洞公告
	private String bugNotice;
	//参考网址
	private String referenceURL;
	//补丁
	private String patches  ;
	//影响实体
	private String effectEntity;
	//数据库入库时间
	private Timestamp timedate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnnvdId() {
		return cnnvdId;
	}
	public void setCnnvdId(String cnnvdId) {
		this.cnnvdId = cnnvdId;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getHarmLevel() {
		return harmLevel;
	}
	public void setHarmLevel(String harmLevel) {
		this.harmLevel = harmLevel;
	}
	public String getBugType() {
		return bugType;
	}
	public void setBugType(String bugType) {
		this.bugType = bugType;
	}
	public String getMenaceType() {
		return menaceType;
	}
	public void setMenaceType(String menaceType) {
		this.menaceType = menaceType;
	}
	public String getCveId() {
		return cveId;
	}
	public void setCveId(String cveId) {
		this.cveId = cveId;
	}
	public String getBugSynopsis() {
		return bugSynopsis;
	}
	public void setBugSynopsis(String bugSynopsis) {
		this.bugSynopsis = bugSynopsis;
	}
	public String getBugNotice() {
		return bugNotice;
	}
	public void setBugNotice(String bugNotice) {
		this.bugNotice = bugNotice;
	}
	public String getReferenceURL() {
		return referenceURL;
	}
	public void setReferenceURL(String referenceURL) {
		this.referenceURL = referenceURL;
	}
	public String getPatches() {
		return patches;
	}
	public void setPatches(String patches) {
		this.patches = patches;
	}
	public String getEffectEntity() {
		return effectEntity;
	}
	public void setEffectEntity(String effectEntity) {
		this.effectEntity = effectEntity;
	}
	public Timestamp getTimedate() {
		return timedate;
	}
	public void setTimedate(Timestamp timedate) {
		this.timedate = timedate;
	}
	public String getCnnvdName() {
		return cnnvdName;
	}
	public void setCnnvdName(String cnnvdName) {
		this.cnnvdName = cnnvdName;
	}
	@Override
	public String toString() {
		return "Cnnvd [id=" + id + ", cnnvdId=" + cnnvdId + ", cnnvdName=" + cnnvdName + ", publishDate=" + publishDate
				+ ", updateTime=" + updateTime + ", harmLevel=" + harmLevel + ", bugType=" + bugType + ", menaceType="
				+ menaceType + ", cveId=" + cveId + ", bugSynopsis=" + bugSynopsis + ", bugNotice=" + bugNotice
				+ ", referenceURL=" + referenceURL + ", patches=" + patches + ", effectEntity=" + effectEntity
				+ ", timedate=" + timedate + "]";
	}
	
}
