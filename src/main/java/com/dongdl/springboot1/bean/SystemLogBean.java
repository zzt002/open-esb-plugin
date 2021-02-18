package com.dongdl.springboot1.bean;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2018/6/20 14:23
 * @version 1.0
 * @description log
 */
public class SystemLogBean extends BaseBean{
	private String title;
	private String action;
	/**
	 * operationStatus 1->success 0->fail
	 */
	private Integer operationStatus;
	private String ip;
	private String url;
	private String param;
	private String message;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(Integer operationStatus) {
		this.operationStatus = operationStatus;
	}
}
