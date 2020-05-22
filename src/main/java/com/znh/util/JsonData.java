package com.znh.util;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * @author Think
 *
 */
public class JsonData {
	
	@JSONField(ordinal = 1)
	private Object code;
	
	@JSONField(ordinal = 2)
	private String msg;
	
	@JSONField(ordinal = 3)
	private Object data;
	
	@JSONField(ordinal = 4)
	private Object count;
	
	public JsonData() {
		// TODO Auto-generated constructor stub
	}

	public JsonData(Object code,String msg,Object data){
		this.code=code;
		this.data=data;
		this.msg=msg;
	}
	
	public JsonData(Object code,String msg,Object data,Object count){
		this.code=code;
		this.data=data;
		this.msg=msg;
		this.count = count;
	}

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getCount() {
		return count;
	}

	public void setCount(Object count) {
		this.count = count;
	}
	
}
