package com.elextec.mdm.common.entity;

import com.elextec.mdm.common.entity.constant.ResponseCodeEnum;

public class VoResponse {
	private Integer code;
	private String message;
	private Object data;
	private Boolean success;
	
	public VoResponse(){
		this.setCode(ResponseCodeEnum.CodeSuccess);
		this.setSuccess(Boolean.TRUE);
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
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public void setNull(VoResponse voRes){
		voRes.setCode(ResponseCodeEnum.CodeInputDataException);
		voRes.setSuccess(false);
	}
	public void setFail(VoResponse voRes){
		voRes.setCode(ResponseCodeEnum.CodeFail);
		voRes.setSuccess(false);
	}
}
