package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Rating;

public class RatingDAO extends AbstractDAO {

	public RatingDAO() throws SQLException {
		super();
	}
	
	public void insertRating(Rating rating) throws SQLException {
		String insertRatingSQL = "INSERT INTO Avaliacao values(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRatingSQL);
		pst.setInt(1, rating.getIdBooking());
		pst.setInt(2, rating.getStars());
		pst.setString(3, rating.getComment());
		pst.setDate(4, rating.getRatingDate());
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Successfully inserted a room rating!");
	}
	
	public void viewPositiveRatings() throws SQLException {
		String viewPositiveRatingsSQL = "SELECT * FROM vwAvaliacoesPositivas";
		PreparedStatement pst = connection.prepareStatement(viewPositiveRatingsSQL);
		
		ResultSet resultSet = pst.executeQuery();
		System.out.println("> Listing positive ratings: ");
		System.out.println("=========");
		while(resultSet.next()) {
			System.out.println("Nome: " + resultSet.getString("Nome"));
			System.out.println("Quarto #" + resultSet.getInt("NumeroQuarto"));
			System.out.println("Nota: " + resultSet.getInt("Nota"));
			System.out.println("Comentário: " + resultSet.getString("Comentario"));
			System.out.println("=========");
		}
		
		connection.commit();
		pst.close();
		
		System.out.println("> Finished printing vwAvaliacoesPositivas");
	}
	
}
