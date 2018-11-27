package model;

public class RoomType {
	
	private int idRoomType;
	private float dailyPrice;
	
	public RoomType(float dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public float getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPRice(float dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
}
