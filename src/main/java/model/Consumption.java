package model;

public class Consumption {
	
	private int idConsumption;
	private int idBill;
	private float value;
	private String description;
	
	public Consumption() {}
	
	public Consumption(float value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getIdConsumption() {
		return idConsumption;
	}

	public void setIdConsumption(int idConsumption) {
		this.idConsumption = idConsumption;
	}

	public int getIdBill() {
		return idBill;
	}

	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
