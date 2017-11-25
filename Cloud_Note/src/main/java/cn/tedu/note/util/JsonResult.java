package cn.tedu.note.util;

import java.io.Serializable;

import cn.tedu.note.service.PasswordException;
import cn.tedu.note.service.UserNotFoundException;

public class JsonResult implements Serializable{
	public static final int SUCCESS=0;
	public static final int ERROR=1;
	
	private int state;
	//错误信息
	private String message;
	//返回正确的时候的数据
	private Object data;
	
	public JsonResult(){
		
	}

	public JsonResult(Object data) {
		state=SUCCESS;
		this.data = data;
	}

	public JsonResult(Throwable e) {
		state=ERROR;
		message=e.getMessage();
	}
	public JsonResult(int state,Throwable e) {
		this.state=state;
		this.message=e.getMessage();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "state=" + state + ", message=" + message + ", data=" + data;
	}
	
	public JsonResult(String error){
		state=ERROR;
		this.message=error;
	}
	
}
