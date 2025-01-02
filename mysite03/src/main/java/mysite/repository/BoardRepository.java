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
		List<BoardVo> result = sqlSession.selectList("board.findAll");
		return result.size();
	}
	
	public List<BoardVo> findByPage(int page){
		int param=0+5*(page-1);
		return sqlSession.selectList("board.findByPage",param);
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
