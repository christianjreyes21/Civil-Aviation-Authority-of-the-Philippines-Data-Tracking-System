package CAAP.model;

import java.util.Date;

public class EventLogsBean {
	
	private int logID;
	private int controlNumber;
	private Date timestamp;
	private String username;
	public int getLogID() {
		return logID;
	}
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public int getControlNumber() {
		return controlNumber;
	}
	public void setControlNumber(int controlNumber) {
		this.controlNumber = controlNumber;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
