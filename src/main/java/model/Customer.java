package model;

import java.sql.Date;

public class Customer extends PhysicalPerson {
	
	private int idCustomer;
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
}
