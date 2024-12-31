package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public int reply(BoardVo vo) {
		update(vo);
		return sqlSession.insert("board.reply", vo);
	}
	
	public int findAll(){
		List<BoardVo> result = new ArrayList<>();
		int count =0;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select b.id, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth, b.user_id, u.name"
						+" from board as b, user as u"
						+" where b.user_id = u.id"
						+" order by g_no desc, o_no asc");	
			) {

			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			rs.last();
			count= rs.getRow();

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
		
		return count;
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
		return sqlSession.selectOne("board.findById", id);
	}

	public int modify(BoardVo vo) {
		return sqlSession.update("board.modify", vo);
	}
	
	public int hitUp(BoardVo vo) {
		return sqlSession.update("board.hitUp", vo);
	}
	
	public int delete(Long id) {
		return sqlSession.delete("board.delete",id);
	}
}
