package com.znh.model;

import java.util.Date;

public class Permissions {
    private String permissionsId;

    private String role_Id;

    private String secondLevelMenu_Id;

    private Date createDateTime;

    private String createPerson;

    private Date updateDateTime;

    private String updatePerson;

    public String getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(String permissionsId) {
        this.permissionsId = permissionsId == null ? null : permissionsId.trim();
    }

    public String getRole_Id() {
        return role_Id;
    }

    public void setRole_Id(String role_Id) {
        this.role_Id = role_Id == null ? null : role_Id.trim();
    }

    public String getSecondLevelMenu_Id() {
        return secondLevelMenu_Id;
    }

    public void setSecondLevelMenu_Id(String secondLevelMenu_Id) {
        this.secondLevelMenu_Id = secondLevelMenu_Id == null ? null : secondLevelMenu_Id.trim();
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