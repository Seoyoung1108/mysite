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
	public int insert(BoardVo vo) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values (null, ?, ?, 0, now(), (select if(g_no is null, 1, max(g_no)+1) from board as g_no),1,0,?)");				
		) {
			// 4. parameter binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserId());
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	public int reply(BoardVo vo) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("update board set o_no=o_no+1 where g_no=? and o_no>=?");
			PreparedStatement pstmt2 = conn.prepareStatement("insert into board values (null, ?, ?, 0, now(), ?,?,?,?)");				
		) {
			// 4. parameter binding
			pstmt1.setLong(1, vo.getGNo());
			pstmt1.setLong(2, vo.getONo());
			count = pstmt1.executeUpdate(); // 데이터 변경
			
			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setLong(3, vo.getGNo());
			pstmt2.setLong(4, vo.getONo());
			pstmt2.setLong(5, vo.getDepth());
			pstmt2.setLong(6, vo.getUserId());
			
			// 5. SQL 실행
			count = pstmt2.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	public List<BoardVo> findAll(){
		List<BoardVo> result = new ArrayList<>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select b.id, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth, b.user_id, u.name"
						+" from board as b, user as u"
						+" where b.user_id = u.id"
						+" order by g_no desc, o_no asc");	
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
				String userName = rs.getString(10);

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
				vo.setUserName(userName);
				result.add(vo);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}
	
	public List<BoardVo> findByPage(int page){
		List<BoardVo> result = new ArrayList<>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select b.id, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth, b.user_id, u.name"
						+" from board as b, user as u"
						+" where b.user_id = u.id"
						+" order by g_no desc, o_no asc"
						+" limit ?, 5");	
			) {
			
			// 4. parameter binding
			pstmt.setInt(1,0+5*(page-1));

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
				String userName = rs.getString(10);

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
				vo.setUserName(userName);
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

	public BoardVo findById(Long id) {
		BoardVo vo = null;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select id, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, depth, user_id from board where id=?");	
		) {
			
			pstmt.setLong(1, id);
			
			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			
			// 6. 결과 처리
			if(rs.next()) {
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userId = rs.getLong(9);
				
				vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGNo(gNo);
				vo.setONo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo;
	}

	public int modify(BoardVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title=?, contents=? where id=?");
		) {

				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, vo.getId());
				
				System.out.println(vo.getTitle()+vo.getContents()+vo.getId());
				result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return result;	
		
	}
	
	public int hitUp(BoardVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set hit=? where id=?");
		) {

				pstmt.setLong(1, vo.getHit());
				pstmt.setLong(2, vo.getId());
				
				result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return result;	
		
	}
	
	public int delete(Long id, Long gNo, Long oNo) {
		int count =0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where g_no=? and o_no>=?");				
		) {
			// 4. parameter binding
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo);
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}

	

	

	
}
