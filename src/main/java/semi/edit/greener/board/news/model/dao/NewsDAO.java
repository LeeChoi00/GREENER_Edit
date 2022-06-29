package semi.edit.greener.board.news.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.board.club.model.vo.Club;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.news.model.vo.News;
import semi.edit.greener.notice.model.vo.Notice;

@Repository
public class NewsDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("newsMapper.getListCount");
	}

	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectFixedList");
	}

	public ArrayList<News> selectList(SqlSessionTemplate sqlSession, BoardPageInfo pi) {
		// 1. 건너뛸 페이지 수
		int offset = (pi.getCurrentPage()-1)* pi.getBoardLimit();
		
		// 2. RowBounds 구하기
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("newsMapper.selectList", null, rowBounds);
	}

	public News selectDetail(SqlSessionTemplate sqlSession, int bNo) {
		int bResult = sqlSession.update("clubMapper.updateCount", bNo);
		News nwResult = new News();
		if(bResult>0) {
			nwResult = sqlSession.selectOne("newsMapper.selectDetail", bNo);
		}
		return nwResult;
	}

	public int newsInsert(SqlSessionTemplate sqlSession, News nw) {
		return sqlSession.insert("newsMapper.newsInsert", nw);
	}

	public int updateNews(SqlSessionTemplate sqlSession, News nw) {
		 int bResult = sqlSession.update("newsMapper.updateBoard", nw);
		 int nwResult = sqlSession.update("newsMapper.updateNews", nw);
		return bResult+nwResult;
	}

	public int newsDelete(SqlSessionTemplate sqlSession, int bNo) {
		return sqlSession.update("newsMapper.newsDelete", bNo);
	}

}
