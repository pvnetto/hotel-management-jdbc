package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Rating;

public class RatingDAO extends AbstractDAO {

	public RatingDAO() throws SQLException {
		super();
	}
	
	public void insertRating(Rating rating) throws SQLException {
		String insertRatingSQL = "INSERT INTO Avaliacao values(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRatingSQL);
		pst.setInt(1, rating.getIdRoom());
		pst.setInt(2, rating.getIdCustomer());
		pst.setInt(3, rating.getStars());
		pst.setString(4, rating.getComment());
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Successfully inserted a room rating!");
	}
	
}
