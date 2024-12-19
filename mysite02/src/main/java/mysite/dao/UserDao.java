package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysite.vo.UserVo;

public class UserDao {

	public int insert(UserVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into users(id,name,email,password,gender,reg_date) values(null, ?, ?, ?, ?, now())");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
	}

	public UserVo findById(Long id) {
		UserVo userVo = null;

		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select name,email,gender from users where id=?");
		) {
			pstmt.setLong(1, id);


			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);

				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return userVo;
	}
	public void updateWithPassword(Long id,UserVo vo) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE users SET name = ?, gender = ?, password = ? WHERE id = ?"
				);
		) {
			// Update문에 필요한 값을 설정
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setString(3, vo.getPassword());
			pstmt.setLong(4, id);

			// Update 실행
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("User information(yes password) updated successfully.");
			} else {
				System.out.println("No user found with the given ID.");
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}


	public void updateWithOutPassword(Long id,UserVo vo) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE users SET name = ?, gender = ? WHERE id = ?"
				);
		) {
			// Update문에 필요한 값을 설정
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setLong(3, id);

			// Update 실행
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("User information(no password) updated successfully.");
			} else {
				System.out.println("No user found with the given ID.");
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, password,email,gender from users where email=? and password=?");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return userVo;
	}	
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.2:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
