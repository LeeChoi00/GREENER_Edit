package semi.edit.greener.board.report.model.service;

import java.util.ArrayList;

import semi.edit.greener.board.model.vo.Board;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.report.model.vo.Report;
import semi.edit.greener.notice.model.vo.Notice;

public interface ReportService {

	int getListCount();

	ArrayList<Report> selectRtList(BoardPageInfo pi);

	Report selectReport(int rNo);

	int selectBoardId(int bNo);

	int insertReport(Report rt);






}
