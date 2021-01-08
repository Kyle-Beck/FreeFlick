package com.moviestreaming.springsecurity.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class RandomMovieDAOImpl implements RandomMovieDAO {

	@Override
	public int getRandomID(String username) {
		Connection conn = null;
		ResultSet rs;
		PreparedStatement ps = null;
		try {
			String query = "SELECT movieID from movie_details\r\n" + 
					"where movieID not in(select movieID from history where username = ?)\r\n" + 
					"ORDER BY RAND()\r\n" + 
					"LIMIT 1";
			
			conn = DriverManager.getConnection(
					"CENSORED FOR SECURITY");
			
			ps = conn.prepareStatement(query);
			ps.setNString(1, username);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		// If an exception is thrown, return the ID for 20,000 Leagues Under the Sea
		return 9171;
		
	}

}
