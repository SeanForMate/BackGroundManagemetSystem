package com.znh.model;

import java.util.Date;

public class SkinTheme {
	
    private String skinThemeId;

    private String skinName;

    private String logoColor;

    private String topColor;

    private String leftColor;

    private String createPerson;

    private Date createDate;

    public SkinTheme() {
		super();
	}

    public SkinTheme(String skinThemeId, String skinName, String logoColor, String topColor, String leftColor) {
        this.skinThemeId = skinThemeId;
        this.skinName = skinName;
        this.logoColor = logoColor;
        this.topColor = topColor;
        this.leftColor = leftColor;
    }

    public String getSkinThemeId() {
        return skinThemeId;
    }

    public void setSkinThemeId(String skinThemeId) {
        this.skinThemeId = skinThemeId == null ? null : skinThemeId.trim();
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName == null ? null : skinName.trim();
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor == null ? null : topColor.trim();
    }

    public String getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(String leftColor) {
        this.leftColor = leftColor == null ? null : leftColor.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLogoColor() {
        return logoColor;
    }

    public void setLogoColor(String logoColor) {
        this.logoColor = logoColor;
    }
}