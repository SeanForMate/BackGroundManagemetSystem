package com.znh.model.commons;

public class ReponseMessage {
	
	private int code;				// 0 不在线消息	1 在线消息
	private Object msg;
	private Object data;
	
	public ReponseMessage() {
		super();
	}
	public ReponseMessage(int code, Object msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
