package com.znh.model;
/**
 * 客户分类
 * @author Sean
 *
 */
public class CustomType {
	private String customTypeId;		// UUID
	private String customLevel;		// 客户等级
	private String levelRemark;				// 等级说明
	public String getCustomTypeId() {
		return customTypeId;
	}
	public void setCustomTypeId(String customTypeId) {
		this.customTypeId = customTypeId;
	}
	public String getCustomLevel() {
		return customLevel;
	}
	public void setCustomLevel(String customLevel) {
		this.customLevel = customLevel;
	}
	public String getLevelRemark() {
		return levelRemark;
	}
	public void setLevelRemark(String levelRemark) {
		this.levelRemark = levelRemark;
	}
	@Override
	public String toString() {
		return "CustomType [customTypeId=" + customTypeId + ", customLevel=" + customLevel + ", levelRemark="
				+ levelRemark + "]";
	}
}
