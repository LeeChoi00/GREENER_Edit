package semi.edit.greener.board.report.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.board.model.vo.Board;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.report.model.vo.Report;
import semi.edit.greener.notice.model.vo.Notice;

@Repository
public class ReportDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("reportMapper.getListCount");
	}

	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectCursor("noticeMapper.selectFixedList");
	}

	public ArrayList<Report> selectRtList(SqlSessionTemplate sqlSession, BoardPageInfo pi) {
		// 1. 건너뛸 페이지 수 => offset + 1 번 게기물부터 가져오기
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		
		// 2. Rowbounds 구하기
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());		
		
		return (ArrayList)sqlSession.selectList("reportMapper.selectRtList", null, rowBounds);
	}

	public Report selectReport(SqlSessionTemplate sqlSession, int rNo) {
		return sqlSession.selectOne("reportMapper.selectReport", rNo);
	}

	public int selectBoardId(SqlSessionTemplate sqlSession, int bNo) {
		return sqlSession.selectOne("reportMapper.selectBoardId", bNo);
	}

	public int insertReport(SqlSessionTemplate sqlSession, Report rt) {
		return sqlSession.insert("reportMapper.insertReport", rt);
	}




}
