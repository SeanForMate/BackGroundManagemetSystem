package com.znh.model.commons;

public class Message {
	
	private String uuid;
	private String formUser;
	private String formUserName;
	private String sendUser;
	private String msgText;
	public Message() {
		super();
	}
	public Message(String uuid, String formUser,String formUserName, String sendUser, String msgText) {
		super();
		this.uuid = uuid;
		this.formUser = formUser;
		this.formUserName = formUserName;
		this.sendUser = sendUser;
		this.msgText = msgText;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFormUser() {
		return formUser;
	}
	public void setFormUser(String formUser) {
		this.formUser = formUser;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getFormUserName() {
		return formUserName;
	}
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
}
