package model;

public class Phone {
	
	private int idPhone;
	private int idPerson;
	private String telephoneNumber;
	
	public Phone(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public int getIdPhone() {
		return idPhone;
	}
	
	public void setIdPhone(int idPhone) {
		this.idPhone = idPhone;
	}
	
	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
}
