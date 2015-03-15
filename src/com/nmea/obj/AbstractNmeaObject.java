package com.nmea.obj;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNmeaObject {
	
	public static final String GGA_PROTOL = "GGA";
	public static final String GLL_PROTOL = "GLL";
	public static final String RMC_PROTOL = "RMC";
	public static final String VDM_PROTOL = "VDM";
	
	protected String msgId;
	
	protected String msgChecksum;
    protected String msg;
    protected List<String> msgFields = new ArrayList<String>();
	
	protected String objType;
	
	

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the msgChecksum
	 */
	public String getMsgChecksum() {
		return msgChecksum;
	}

	/**
	 * @param msgChecksum the msgChecksum to set
	 */
	public void setMsgChecksum(String msgChecksum) {
		this.msgChecksum = msgChecksum;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the msgFields
	 */
	public List<String> getMsgFields() {
		return msgFields;
	}

	/**
	 * @param msgFields the msgFields to set
	 */
	public void setMsgFields(List<String> msgFields) {
		this.msgFields = msgFields;
	}

	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	
}
