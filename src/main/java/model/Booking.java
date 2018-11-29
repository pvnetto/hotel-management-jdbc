package model;

import java.sql.Date;

public class Booking {
	private int idBooking;
	private int idCustomer;
	private int idRoom;
	private int idBill;
	private Date startDate;
	private Date endDate;
	
	public Booking() {}
	
	public Booking(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getIdBooking() {
		return idBooking;
	}
	
	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	
	public int getIdBill() {
		return idBill;
	}

	public void setIdBill(int idBill) {
		this.idBill = idBill;
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
