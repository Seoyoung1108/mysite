package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	private DataSource dataSource;
	
	public GuestbookRepository(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public int insert(GuestbookVo vo) {
		int count =0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values (null,?,?,?,now())");				
		) {
			// 4. parameter binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
	
	public List<GuestbookVo> findAll(){
		List<GuestbookVo> result = new ArrayList<>();
		
		try (
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select id, name, contents, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc");	
			) {

			// 5. SQL 실행
			ResultSet rs=pstmt.executeQuery();
			
			// 6. 결과 처리
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setId(id);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				result.add(vo);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}
	
	public int deleteByIdAndPassword(Long id, String password) {
		int count =0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");				
		) {
			// 4. parameter binding
			pstmt.setLong(1, id);
			pstmt.setString(2, password);
			
			// 5. SQL 실행
			count = pstmt.executeUpdate(); // 데이터 변경
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
		
	}
}
