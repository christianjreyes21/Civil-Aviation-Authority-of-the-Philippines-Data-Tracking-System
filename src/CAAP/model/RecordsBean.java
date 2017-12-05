package CAAP.model;

import java.sql.Date;

public class RecordsBean {

	private int id;
	private String mark;
	private String company;
	private String destination;
	private String author;
	private String receivedBy;
	private Date dateInLatest;
	private Date dateInOriginal;
	private Date dateOut;
	private String remarks;
	private String subject;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReceivedBy() {
		return receivedBy;
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	public Date getDateInLatest() {
		return dateInLatest;
	}
	public void setDateInLatest(Date dateInLatest) {
		this.dateInLatest = dateInLatest;
	}
	public Date getDateInOriginal() {
		return dateInOriginal;
	}
	public void setDateInOriginal(Date dateInOriginal) {
		this.dateInOriginal = dateInOriginal;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
