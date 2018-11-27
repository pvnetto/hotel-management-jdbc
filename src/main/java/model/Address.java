package model;

public class Address {
	
	private int idAddress;
	private int idPerson;
	private String street;
	private String borough;
	private int number;
	private String cep;
	private String address2;
	
	public Address(String street, String borough, int number, String cep, String address2) {
		this.street = street;
		this.borough = borough;
		this.number = number;
		this.cep = cep;
		this.address2 = address2;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}
	
	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getBorough() {
		return borough;
	}
	
	public void setBorough(String borough) {
		this.borough = borough;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
}
