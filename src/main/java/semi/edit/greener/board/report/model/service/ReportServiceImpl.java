package semi.edit.greener.board.report.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.model.vo.Board;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.report.model.dao.ReportDAO;
import semi.edit.greener.board.report.model.vo.Report;
import semi.edit.greener.notice.model.vo.Notice;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired 
	private ReportDAO rtDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getListCount() {
		return rtDAO.getListCount(sqlSession);
	}


	@Override
	public ArrayList<Report> selectRtList(BoardPageInfo pi) {
		return rtDAO.selectRtList(sqlSession, pi);
	}


	@Override
	public Report selectReport(int rNo) {
		return rtDAO.selectReport(sqlSession, rNo);
	}


	@Override
	public int selectBoardId(int bNo) {
		return rtDAO.selectBoardId(sqlSession, bNo);
	}


	@Override
	public int insertReport(Report rt) {
		return rtDAO.insertReport(sqlSession, rt);
	}




	
	
}
