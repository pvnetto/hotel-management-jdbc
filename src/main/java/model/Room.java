package model;

public class Room {
	
	private int idRoom;
	private int roomNumber;
	private boolean isBusy;
	
	public int getIdRoom() {
		return idRoom;
	}
	
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
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
