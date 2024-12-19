package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;

public class UserDao {
	public int updateById(UserVo vo, Long id) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update user set name = ?, password = ?, gender = ? where id = ?");				
		) {
			// 4. parameter binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, id);
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	public int updateByIdNoPassword(UserVo vo, Long id) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update user set name = ?, gender = ? where id = ?");				
		) {
			// 4. parameter binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setLong(3, id);
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	public UserVo findById(Long id) {
		UserVo vo = null;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select name, email, gender from user where id=?");	
		) {
			
			pstmt.setLong(1, id);
			
			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			
			// 6. 결과 처리
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				vo = new UserVo();
				vo.setId(id);
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo; // 로그인 실패 시 null 반환
	}
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo vo = null;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select id, name from user where email=? and password=?");	
		) {
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			
			// 6. 결과 처리
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				vo = new UserVo();
				vo.setId(id);
				vo.setName(name);

			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo; // 로그인 실패 시 null 반환
	}
	
	public int insert(UserVo vo) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into user values (null,?,?,?,?,curdate())");				
		) {
			// 4. parameter binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
		// 1. JDBC Driver 로딩
		Class.forName("org.mariadb.jdbc.Driver");
		
		// 2. 연결하기
		String url = "jdbc:mariadb://192.168.0.153:3306/webdb";
		conn = DriverManager.getConnection(url, "webdb", "webdb");	
		
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}
	

}
