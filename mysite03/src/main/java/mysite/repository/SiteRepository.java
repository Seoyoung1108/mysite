package mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public int insert(SiteVo vo) {
		return sqlSession.insert("site.insert", vo);		
	}

	public SiteVo findRecent() {
		return sqlSession.selectOne("site.findRecent");		
	}

}
