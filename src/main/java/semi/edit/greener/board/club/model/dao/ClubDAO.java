package semi.edit.greener.board.club.model.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.RowBounds;
import semi.edit.greener.board.club.model.vo.Club;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.notice.model.vo.Notice;

@Repository
public class ClubDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("clubMapper.getListCount");
	}

	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectFixedList");
	}

	public ArrayList<Club> selectList(SqlSessionTemplate sqlSession, BoardPageInfo pi) {
		// 1. 건너뛸 페이지 수 
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		
		// 2. RowBounds 구하기
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("clubMapper.selectList", null, rowBounds);
	}

	public Club selectClub(SqlSessionTemplate sqlSession, int bNo) {
		int bResult = sqlSession.update("clubMapper.updateCount", bNo);
		
		Club clResult = new Club();
		if(bResult>0) {
			clResult = sqlSession.selectOne("clubMapper.selectClub", bNo);
		}
		return clResult;
	}

	public int insertClub(SqlSessionTemplate sqlSession, Club cl) {
		return sqlSession.insert("clubMapper.insertBoard", cl);
	}

	public int updateClub(SqlSessionTemplate sqlSession, Club cl) {
		int bResult = sqlSession.update("clubMapper.updateBoard", cl);
		int clResult = 0;
		System.out.println("bResult : " + bResult);
		if(bResult>0) {
			clResult = sqlSession.update("clubMapper.updateClub", cl);
		}
		System.out.println("bREsult + clResult : " + (bResult+clResult) );
		return clResult;
	}

	public int deleteClub(SqlSessionTemplate sqlSession, int bNo) { 
		return sqlSession.update("clubMapper.deleteBoard", bNo);
	}



}
