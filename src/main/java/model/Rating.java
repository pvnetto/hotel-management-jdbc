package model;

public class Rating {
	
	private int idRating;
	private int idRoom;
	private int idCustomer;
	private int stars;
	private String comment;
	
	public Rating(int stars, String comment) {
		this.stars = stars;
		this.comment = comment;
	}
	
	public int getIdRating() {
		return idRating;
	}
	
	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}
	
	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getStars() {
		return stars;
	}
	
	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
