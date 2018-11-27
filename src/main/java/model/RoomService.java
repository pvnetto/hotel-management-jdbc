package model;

public class RoomService {
	
	private int idRoomService;
	private Room room;
	private Admin admin;
	private Employee employee;
	private String description;
	
	public RoomService(Room room, Admin admin, Employee employee, String description) {
		this.room = room;
		this.admin = admin;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
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
