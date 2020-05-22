package com.znh.model;

import java.util.Date;

public class Vendor {
    private String vendorId;

    private String vendorName;

    private String vendorAbbreviation;

    private String telephone;

    private String linkMan;

    private String linkPhone;

    private String address;

    private String zipCode;

    private String bankAccout;

    private String fax;

    private String email;

    private Date createDateTime;

    private String createPerson;

    private Date updateDateTime;

    private String updatePerson;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId == null ? null : vendorId.trim();
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public String getVendorAbbreviation() {
        return vendorAbbreviation;
    }

    public void setVendorAbbreviation(String vendorAbbreviation) {
        this.vendorAbbreviation = vendorAbbreviation == null ? null : vendorAbbreviation.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getBankAccout() {
        return bankAccout;
    }

    public void setBankAccout(String bankAccout) {
        this.bankAccout = bankAccout == null ? null : bankAccout.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson == null ? null : updatePerson.trim();
    }

	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", vendorName=" + vendorName + ", vendorAbbreviation="
				+ vendorAbbreviation + ", telephone=" + telephone + ", linkMan=" + linkMan + ", linkPhone=" + linkPhone
				+ ", address=" + address + ", zipCode=" + zipCode + ", bankAccout=" + bankAccout + ", fax=" + fax
				+ ", email=" + email + ", createDateTime=" + createDateTime + ", createPerson=" + createPerson
				+ ", updateDateTime=" + updateDateTime + ", updatePerson=" + updatePerson + "]";
	}
}