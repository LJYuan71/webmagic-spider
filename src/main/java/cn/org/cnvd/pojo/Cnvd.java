package cn.org.cnvd.pojo;

import java.sql.Timestamp;

public class Cnvd {
	//id
	private Integer id;
	//cnvdId
	private String cnvdId;
	//标题
	private String cnvdTitle;
	//报送者
	private String bugSubmitter;
	//发布时间
	private String publishDate;
	//危害级别
	private String harmLevel;
	//危害详情
	private String harmDescribe;
	//漏洞评分
	private String bugScore;
	//影响产品
	private String effectProduct;
	//BUGTRAQ ID
	private String bugtraqId;
	//CVE ID
	private String cveId;
	//漏洞描述
	private String bugDescribe;
	//参考链接
	private String referenceLink;
	//漏洞解决方案
	private String bugSolutions;
	//漏洞发现者
	private String bugFinder;
	//厂商补丁
	private String vendorPatches  ;
	//验证信息
	private String validateInfo;
	//报送时间
	private String submittDate;
	//收录时间
	private String includedDate;
	//更新时间
	private String updateTime;
	//漏洞附件
	private String bugAccessory;
	//数据库入库时间
	private Timestamp timedate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnvdId() {
		return cnvdId;
	}
	public void setCnvdId(String cnvdId) {
		this.cnvdId = cnvdId;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getHarmLevel() {
		return harmLevel;
	}
	public void setHarmLevel(String harmLevel) {
		this.harmLevel = harmLevel;
	}
	public String getHarmDescribe() {
		return harmDescribe;
	}
	public void setHarmDescribe(String harmDescribe) {
		this.harmDescribe = harmDescribe;
	}
	public String getEffectProduct() {
		return effectProduct;
	}
	public void setEffectProduct(String effectProduct) {
		this.effectProduct = effectProduct;
	}
	public String getBugtraqId() {
		return bugtraqId;
	}
	public void setBugtraqId(String bugtraqId) {
		this.bugtraqId = bugtraqId;
	}
	public String getCveId() {
		return cveId;
	}
	public void setCveId(String cveId) {
		this.cveId = cveId;
	}
	public String getBugDescribe() {
		return bugDescribe;
	}
	public void setBugDescribe(String bugDescribe) {
		this.bugDescribe = bugDescribe;
	}
	public String getReferenceLink() {
		return referenceLink;
	}
	public void setReferenceLink(String referenceLink) {
		this.referenceLink = referenceLink;
	}
	public String getBugSolutions() {
		return bugSolutions;
	}
	public void setBugSolutions(String bugSolutions) {
		this.bugSolutions = bugSolutions;
	}
	public String getBugFinder() {
		return bugFinder;
	}
	public void setBugFinder(String bugFinder) {
		this.bugFinder = bugFinder;
	}
	public String getVendorPatches() {
		return vendorPatches;
	}
	public void setVendorPatches(String vendorPatches) {
		this.vendorPatches = vendorPatches;
	}
	public String getValidateInfo() {
		return validateInfo;
	}
	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}
	public String getSubmittDate() {
		return submittDate;
	}
	public void setSubmittDate(String submittDate) {
		this.submittDate = submittDate;
	}
	public String getIncludedDate() {
		return includedDate;
	}
	public void setIncludedDate(String includedDate) {
		this.includedDate = includedDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getBugAccessory() {
		return bugAccessory;
	}
	public void setBugAccessory(String bugAccessory) {
		this.bugAccessory = bugAccessory;
	}
	public Timestamp getTimedate() {
		return timedate;
	}
	public void setTimedate(Timestamp timedate) {
		this.timedate = timedate;
	}
	public String getBugSubmitter() {
		return bugSubmitter;
	}
	public void setBugSubmitter(String bugSubmitter) {
		this.bugSubmitter = bugSubmitter;
	}
	public String getCnvdTitle() {
		return cnvdTitle;
	}
	public void setCnvdTitle(String cnvdTitle) {
		this.cnvdTitle = cnvdTitle;
	}
	public String getBugScore() {
		return bugScore;
	}
	public void setBugScore(String bugScore) {
		this.bugScore = bugScore;
	}
	@Override
	public String toString() {
		return "Cnvd [id=" + id + ", cnvdId=" + cnvdId + ", cnvdTitle=" + cnvdTitle + ", bugSubmitter=" + bugSubmitter
				+ ", publishDate=" + publishDate + ", harmLevel=" + harmLevel + ", harmDescribe=" + harmDescribe
				+ ", bugScore=" + bugScore + ", effectProduct=" + effectProduct + ", bugtraqId=" + bugtraqId
				+ ", cveId=" + cveId + ", bugDescribe=" + bugDescribe + ", referenceLink=" + referenceLink
				+ ", bugSolutions=" + bugSolutions + ", bugFinder=" + bugFinder + ", vendorPatches=" + vendorPatches
				+ ", validateInfo=" + validateInfo + ", submittDate=" + submittDate + ", includedDate=" + includedDate
				+ ", updateTime=" + updateTime + ", bugAccessory=" + bugAccessory + ", timedate=" + timedate + "]";
	}
	
	
}
