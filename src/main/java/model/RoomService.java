package model;

public class RoomService {
	
	private int idRoomService;
	private Room room;
	private Employee employee;
	private String description;
	
	public RoomService(Room room, Employee employee, String description) {
		this.room = room;
		this.employee = employee;
		this.description = description;
	}

	public int getIdRoomService() {
		return idRoomService;
	}

	public void setIdRoomService(int idRoomService) {
		this.idRoomService = idRoomService;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
