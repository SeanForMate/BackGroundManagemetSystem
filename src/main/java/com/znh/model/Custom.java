package com.znh.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 客户类
 * @author Sean
 *
 */
public class Custom {

	private String customerId;//客户ID（主键）
	private String pyCode;//助记码
	private String customerName;//客户简称
	private String companyName;//公司名称
	private String companySite;//公司地址
	private String companyPhone;//公司电话
	private String linkMan;//联系人
	private String linkPhone;//联系电话
	private String fax;//传真
	private String customerSort;//客户分类
	private String customerQuale;//客户性质
	private String customerZone;//客户分区
	private String address;//投递地址
	private String zipCode;//邮政编码
	private String bankName;//开户银行
	private String bankAccout;//银行账号
	private String email;//邮箱
	private String companyHomePage;//公司主页
	private String remark;//备注
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createDateTime;
	private String createPerson;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateDateTime;
	private String updatePerson;
	private CustomType customType;
	private CustomCharacter customCharacter;
	private CustomZone customZone;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPyCode() {
		return pyCode;
	}
	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanySite() {
		return companySite;
	}
	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCustomerSort() {
		return customerSort;
	}
	public void setCustomerSort(String customerSort) {
		this.customerSort = customerSort;
	}
	public String getCustomerQuale() {
		return customerQuale;
	}
	public void setCustomerQuale(String customerQuale) {
		this.customerQuale = customerQuale;
	}
	public String getCustomerZone() {
		return customerZone;
	}
	public void setCustomerZone(String customerZone) {
		this.customerZone = customerZone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccout() {
		return bankAccout;
	}
	public void setBankAccout(String bankAccout) {
		this.bankAccout = bankAccout;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyHomePage() {
		return companyHomePage;
	}
	public void setCompanyHomePage(String companyHomePage) {
		this.companyHomePage = companyHomePage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public CustomType getCustomType() {
		return customType;
	}
	public void setCustomType(CustomType customType) {
		this.customType = customType;
	}
	public CustomCharacter getCustomCharacter() {
		return customCharacter;
	}
	public void setCustomCharacter(CustomCharacter customCharacter) {
		this.customCharacter = customCharacter;
	}
	public CustomZone getCustomZone() {
		return customZone;
	}
	public void setCustomZone(CustomZone customZone) {
		this.customZone = customZone;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	@Override
	public String toString() {
		return "Custom [customerId=" + customerId + ", pyCode=" + pyCode + ", customerName=" + customerName
				+ ", companyName=" + companyName + ", companySite=" + companySite + ", companyPhone=" + companyPhone
				+ ", linkMan=" + linkMan + ", linkPhone=" + linkPhone + ", fax=" + fax + ", customerSort="
				+ customerSort + ", customerQuale=" + customerQuale + ", customerZone=" + customerZone + ", address="
				+ address + ", zipCode=" + zipCode + ", bankName=" + bankName + ", bankAccout=" + bankAccout
				+ ", email=" + email + ", companyHomePage=" + companyHomePage + ", remark=" + remark
				+ ", createDateTime=" + createDateTime + ", createPerson=" + createPerson + ", updateDateTime="
				+ updateDateTime + ", updatePerson=" + updatePerson + ", customType=" + customType
				+ ", customCharacter=" + customCharacter + ", customZone=" + customZone + "]";
	}
}
