package com.znh.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {
	private String userId;
	private String userName;
	private String password;
	private String salt;
	private String realName;
	private String linkPhone;
	private Integer sex;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginDateTime;
	private String skinTheme_Id;
	private String createPerson;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDateTime;
	private String updatePerson;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateDateTime;
	private Role role;
	private UserRole userRole;
	private SkinTheme skinTheme;

	public User() {
		super();
	}

	public User(String userId, String userName, String realName, Date lastLoginDateTime,
			String skinTheme_Id) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		this.lastLoginDateTime = lastLoginDateTime;
		this.skinTheme_Id = skinTheme_Id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone == null ? null : linkPhone.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getSkinTheme_Id() {
		return skinTheme_Id;
	}

	public void setSkinTheme_Id(String skinTheme_Id) {
		this.skinTheme_Id = skinTheme_Id == null ? null : skinTheme_Id.trim();
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson == null ? null : createPerson.trim();
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson == null ? null : updatePerson.trim();
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SkinTheme getSkinTheme() {
		return skinTheme;
	}

	public void setSkinTheme(SkinTheme skinTheme) {
		this.skinTheme = skinTheme;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", salt=" + salt
				+ ", realName=" + realName + ", linkPhone=" + linkPhone + ", sex=" + sex
				+ ", lastLoginDateTime=" + lastLoginDateTime + ", skinTheme_id=" + skinTheme_Id + ", createPerson="
				+ createPerson + ", createDateTime=" + createDateTime + ", updatePerson=" + updatePerson
				+ ", updateDateTime=" + updateDateTime + "]";
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}