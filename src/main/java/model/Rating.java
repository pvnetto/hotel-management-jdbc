package model;

import java.sql.Date;

public class Rating {
	
	private int idBooking;
	private int stars;
	private String comment;
	private Date ratingDate;
	
	public Rating() {}
	
	public Rating(int stars, String comment) {
		this.stars = stars;
		this.comment = comment;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
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

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}
	
}
