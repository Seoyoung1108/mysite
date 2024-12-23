package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;


public class BoardDao {
	public void insert(BoardVo vo) {
		// TODO Auto-generated method stub
		
	}
	
	public List<BoardVo> findAll(){
		List<BoardVo> result = new ArrayList<>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select id, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, depth, user_id from board");	
			) {

			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			
			// 6. 결과 처리
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userId = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGNo(gNo);
				vo.setONo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				result.add(vo);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
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
