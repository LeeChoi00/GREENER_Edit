package semi.edit.greener.board.report.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import semi.edit.greener.board.model.vo.Board;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.report.model.service.ReportService;
import semi.edit.greener.board.report.model.vo.Report;
import semi.edit.greener.common.Pagination;
import semi.edit.greener.member.model.vo.Member;
import semi.edit.greener.notice.model.vo.Notice;

@Controller
public class ReportController {
	@Autowired
	private ReportService rtService;
	
	// Report 게시판으로 이동
	@RequestMapping("list.rt")
	public String reportList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 페이징
		// 1. Report 게시판의 총 게시글 수 구하기
		int listCount = rtService.getListCount();
		
		// 2. 현재 페이지 구하기
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 3. 페이징 계산
		BoardPageInfo pi = new Pagination().getBoardPageInfo(currentPage, listCount);
		
		// 4. 각 페이지 게시물 목록 가져오기
		ArrayList<Report> list = rtService.selectRtList(pi);
		
		// 5. 페이지 이동
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("pi", pi);
			return "reportList";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";			
		}
	}
	
	// Report 게시물 상세보기
	@RequestMapping("detail.rt")
	public String reportDetail(@RequestParam("rNo") int rNo, Model model) {
		// 1. 상세보기 정보 가져오기
		Report report = rtService.selectReport(rNo);
		System.out.println("report : " + report); 
		System.out.println("bNo : " + report.getReportBNo());
		
		// 2. 신고당한 게시물이 속한 게시판을 가리키는 정보 가져오기
		int bNo = report.getReportBNo();
		int boardId = rtService.selectBoardId(bNo);
		System.out.println("boardId : " + boardId);
		
		if(report != null && boardId > 0) {
			model.addAttribute("report", report);
			model.addAttribute("boardId", boardId);
			return "reportDetail";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
	
	// 각 게시물에서 신고하기
	// 1. 신고 페이지로 이동
	@RequestMapping("reportForm.rt")
	public String reportForm(@RequestParam("reportBNo") int reportBNo, Model model) {
		model.addAttribute("reportBNo", reportBNo);
		return "reportForm";
	}
	
	// 2. 신고
	@RequestMapping("insertReport.rt")
	public String insertReport(@ModelAttribute Report rt, HttpSession session, Model model) {
		// 1. 신고자 id 받아와서 Report 객체에 넣기
		String reportUser = ((Member)session.getAttribute("loginUser")).getUserId();
		
		rt.setReportUser(reportUser);
		
		// 2. db에 신고 정보 등록하기
		int result = rtService.insertReport(rt);
		
		// 3. 페이지 이동
		if(result>0) {
			model.addAttribute("msg", "신고 완료되었습니다");
		} else {
			model.addAttribute("msg", "신고에 실패하였습니다");
		}
		return "reportForm";
	}
}











































