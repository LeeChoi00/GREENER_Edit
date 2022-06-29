package semi.edit.greener.notice.controller;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import semi.edit.greener.common.Pagination;
import semi.edit.greener.notice.model.service.NoticeService;
import semi.edit.greener.notice.model.vo.Notice;
import semi.edit.greener.notice.model.vo.NoticePageInfo;

@SessionAttributes("fixedList")
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService nService;
	
	// 공지사항 게시판으로 이동 + 목록 부르기
	@RequestMapping("list.no")
	public String noticeList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 페이징
		// 1. 전체 게시글 수
		int listCount = nService.getListCount();

		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 2. 페이징 정보
		NoticePageInfo npi = Pagination.getNoticePageInfo(currentPage, listCount); 
		
		
		// 3. 공지사항 게시판 리스트
		ArrayList<Notice> list = nService.selectNoticeList(npi);
		
//		// 4. 상단 고정 게시글 리스트    => 변경점: if문 추가/ 다른 게시판이 먼저 클릭되었는지 여부에 따라 실행 
//		if(model.getAttribute("fixedList") == null) {
//			ArrayList<Notice> fixedList = nService.selectFixedList();		
//			// 어느 게시판이든 쓸 수 있도록 세션 정보로 넣기			
//			model.addAttribute("fixedList", fixedList);
//		} else {
//			model.getAttribute("fixedList");
//		}
		
		// 4. 상단 고정 게시글 리스트
		ArrayList<Notice> fixedList = nService.selectFixedList();		
		// 어느 게시판이든 쓸 수 있도록 세션 정보로 넣기			
		model.addAttribute("fixedList", fixedList);
		
		
		// 5. 이동
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("npi", npi);
			return "noticeList";
		} else {
			model.addAttribute("msg", "공지사항 조회에 실패했습니다.");
			return "../common/errorPage";
		}	
	}
	
	// 게시물 상세보기
	@RequestMapping("detail.no")
	public ModelAndView selectNotice(@RequestParam("no") String no, ModelAndView mv) {
		Notice notice = nService.selectNotice(no);
		
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("noticeDetail");
		} else {
			mv.addObject("msg", "공지사항 조회에 실패했습니다.");
			mv.setViewName("../common/errorPage");
		}
		
		return mv;
	}
	
	// 공지사항 글쓰기(관리자만 가능)
	// 1. 공지사항 글쓰기 페이지로 이동
	@RequestMapping("writeNoticeForm.no")
	public String noticeWriteForm() {
		return "noticeWriteForm";
	}
	
	// 2. 공지사항 글쓰기 
	@RequestMapping("insert.no")
	public String insertNotice(@ModelAttribute Notice n, Model model) {
		int result = nService.insertNotice(n);
		
		if(result>0) {
			return "redirect:list.no";
		} else {
			model.addAttribute("msg", "공지사항 추가에 실패했습니다.");
			return "../common/errorPage";
		}
	}
	
	// 공지사항 수정(관리자만 가능)
	// 1. 공지사항 수정 페이지로 이동
	@RequestMapping("noticeUpdateForm.no")
	public ModelAndView noticeUpdateForm(@ModelAttribute Notice n, ModelAndView mv) {
		mv.addObject("notice", n);
		mv.setViewName("noticeUpdateForm");
		
		return mv;
	}
	
	// 2. 공지사항 수정
	@RequestMapping("update.no")
	public String noticeUpdate(@ModelAttribute Notice n, Model model) {
		int result = nService.updateNotice(n);
		
		if(result > 0) {
			return "redirect:detail.no?no=" + n.getNoticeNo();
		} else {
			model.addAttribute("msg", "게시물 변경에 실패했습니다.");
			return "../common/errorPage";			
		}	
		
	}
	
	// 공지사항 삭제
	@RequestMapping("deleteNotice.no")
	public String noticeDelete(@RequestParam("no") String no, Model model) {
		int result = nService.deleteNotice(no);
		
		if(result >0) {
			return "redirect:list.no";
		} else {
			model.addAttribute("msg", "게시물 삭제에 실패했습니다.");
			return "../common/errorPage";
		}
	}
	
	
	
	
	
	
}
