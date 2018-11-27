package model;

public abstract class LegalPerson extends Person {
	
	private int idLegalPerson;
	protected String cnpj;
	protected String companyName;
	
	public int getIdLegalPerson() {
		return idLegalPerson;
	}
	
	public void setIdLegalPerson(int idLegalPerson) {
		this.idLegalPerson = idLegalPerson;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
