package com.znh.model;

import java.util.Date;
import java.util.List;

public class FirstLevelMenu {
    private String firstMenuId;

    private String firstMenuEnglishName;

    private String firstMenuName;

    private String firstMenuIcon;

    private Integer firstMenuIndex;

    private Date createDateTime;

    private String createPerson;

    private Date updateDateTime;

    private String updatePerson;

    private List<SecondLevelMenu> secondLevelMenuList;

    public String getFirstMenuId() {
        return firstMenuId;
    }

    public void setFirstMenuId(String firstMenuId) {
        this.firstMenuId = firstMenuId == null ? null : firstMenuId.trim();
    }

    public String getFirstMenuEnglishName() {
        return firstMenuEnglishName;
    }

    public void setFirstMenuEnglishName(String firstMenuEnglishName) {
        this.firstMenuEnglishName = firstMenuEnglishName == null ? null : firstMenuEnglishName.trim();
    }

    public String getFirstMenuName() {
        return firstMenuName;
    }

    public void setFirstMenuName(String firstMenuName) {
        this.firstMenuName = firstMenuName == null ? null : firstMenuName.trim();
    }

    public String getFirstMenuIcon() {
        return firstMenuIcon;
    }

    public void setFirstMenuIcon(String firstMenuIcon) {
        this.firstMenuIcon = firstMenuIcon == null ? null : firstMenuIcon.trim();
    }

    public Integer getFirstMenuIndex() {
        return firstMenuIndex;
    }

    public void setFirstMenuIndex(Integer firstMenuIndex) {
        this.firstMenuIndex = firstMenuIndex;
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

    public List<SecondLevelMenu> getSecondLevelMenuList() {
        return secondLevelMenuList;
    }

    public void setSecondLevelMenuList(List<SecondLevelMenu> secondLevelMenuList) {
        this.secondLevelMenuList = secondLevelMenuList;
    }
}