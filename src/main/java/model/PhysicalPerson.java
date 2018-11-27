package model;

import java.sql.Date;

public abstract class PhysicalPerson extends Person {
	
	private int idPhysicalPerson;
	protected String cpf;
	protected String rg;
	protected Date birthDate;
	protected String gender;
	
	public int getIdPhysicalPerson() {
		return idPhysicalPerson;
	}

	public void setIdPhysicalPerson(int idPhysicalPerson) {
		this.idPhysicalPerson = idPhysicalPerson;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
}
