package model;

import java.sql.Date;

public class Booking {
	private int idBooking;
	private Date startDate;
	private Date endDate;
	
	public int getIdBooking() {
		return idBooking;
	}
	
	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
