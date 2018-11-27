package model;

public class Room {
	
	private int idRoom;
	private int idRoomType;
	private int roomNumber;
	private boolean isBusy;
	
	public int getIdRoom() {
		return idRoom;
	}
	
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	
	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public boolean isBusy() {
		return isBusy;
	}
	
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	
}
