package com.znh.model;

import java.util.Date;

public class SecondLevelMenu {
    private String secondMenuId;

    private String firstMenu_Id;

    private String secondMenuEnglishName;

    private String secondMenuName;

    private String secondMenuIcon;

    private Integer secondMenuIndex;

    private Date createDateTime;

    private String createPerson;

    private Date updateDateTime;

    private String updatePerson;

    private Permissions permissions;

    public String getSecondMenuId() {
        return secondMenuId;
    }

    public void setSecondMenuId(String secondMenuId) {
        this.secondMenuId = secondMenuId == null ? null : secondMenuId.trim();
    }

    public String getFirstMenu_Id() {
        return firstMenu_Id;
    }

    public void setFirstMenu_Id(String firstMenu_Id) {
        this.firstMenu_Id = firstMenu_Id == null ? null : firstMenu_Id.trim();
    }

    public String getSecondMenuEnglishName() {
        return secondMenuEnglishName;
    }

    public void setSecondMenuEnglishName(String secondMenuEnglishName) {
        this.secondMenuEnglishName = secondMenuEnglishName == null ? null : secondMenuEnglishName.trim();
    }

    public String getSecondMenuName() {
        return secondMenuName;
    }

    public void setSecondMenuName(String secondMenuName) {
        this.secondMenuName = secondMenuName == null ? null : secondMenuName.trim();
    }

    public String getSecondMenuIcon() {
        return secondMenuIcon;
    }

    public void setSecondMenuIcon(String secondMenuIcon) {
        this.secondMenuIcon = secondMenuIcon == null ? null : secondMenuIcon.trim();
    }

    public Integer getSecondMenuIndex() {
        return secondMenuIndex;
    }

    public void setSecondMenuIndex(Integer secondMenuIndex) {
        this.secondMenuIndex = secondMenuIndex;
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

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}