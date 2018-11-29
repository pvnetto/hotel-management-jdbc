package model;

public class Bill {
	private int idBill;
	private float totalValue;
	
	public Bill() {}
	
	public Bill(float totalValue) {
		this.totalValue = totalValue;
	}

	public int getIdBill() {
		return idBill;
	}

	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}

	public float getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}
	
}
