package com.znh.model;

import java.util.Date;

public class Commodity {
    private String commodityId;

    private String commodityName;

    private String commodityReferred;

    private String commodityOrigin;

    private String commodityUnit;

    private String commodityPriceUnit;

    private String commodityPrice;

    private String commoditySize;

    private String commodityWrap;

    private String commodityBatchNo;

    private String commodityApprovalNo;

    private String commodityRemark;

    private String vendor_Id;

    private Date createDateTime;

    private String createPerson;

    private Date updateDateTime;

    private String updatePerson;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    public String getCommodityReferred() {
        return commodityReferred;
    }

    public void setCommodityReferred(String commodityReferred) {
        this.commodityReferred = commodityReferred == null ? null : commodityReferred.trim();
    }

    public String getCommodityOrigin() {
        return commodityOrigin;
    }

    public void setCommodityOrigin(String commodityOrigin) {
        this.commodityOrigin = commodityOrigin == null ? null : commodityOrigin.trim();
    }

    public String getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        this.commodityUnit = commodityUnit == null ? null : commodityUnit.trim();
    }

    public String getCommodityPriceUnit() {
        return commodityPriceUnit;
    }

    public void setCommodityPriceUnit(String commodityPriceUnit) {
        this.commodityPriceUnit = commodityPriceUnit == null ? null : commodityPriceUnit.trim();
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice == null ? null : commodityPrice.trim();
    }

    public String getCommoditySize() {
        return commoditySize;
    }

    public void setCommoditySize(String commoditySize) {
        this.commoditySize = commoditySize == null ? null : commoditySize.trim();
    }

    public String getCommodityWrap() {
        return commodityWrap;
    }

    public void setCommodityWrap(String commodityWrap) {
        this.commodityWrap = commodityWrap == null ? null : commodityWrap.trim();
    }

    public String getCommodityBatchNo() {
        return commodityBatchNo;
    }

    public void setCommodityBatchNo(String commodityBatchNo) {
        this.commodityBatchNo = commodityBatchNo == null ? null : commodityBatchNo.trim();
    }

    public String getCommodityApprovalNo() {
        return commodityApprovalNo;
    }

    public void setCommodityApprovalNo(String commodityApprovalNo) {
        this.commodityApprovalNo = commodityApprovalNo == null ? null : commodityApprovalNo.trim();
    }

    public String getCommodityRemark() {
        return commodityRemark;
    }

    public void setCommodityRemark(String commodityRemark) {
        this.commodityRemark = commodityRemark == null ? null : commodityRemark.trim();
    }

    public String getVendor_Id() {
        return vendor_Id;
    }

    public void setVendor_Id(String vendor_Id) {
        this.vendor_Id = vendor_Id == null ? null : vendor_Id.trim();
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
}