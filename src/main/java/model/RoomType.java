package model;

public class RoomType {
	
	private int idRoomType;
	private String description;
	private float dailyPrice;
	
	public RoomType(float dailyPrice, String description) {
		this.dailyPrice = dailyPrice;
		this.description = description;
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPRice(float dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
}
