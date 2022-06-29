package semi.edit.greener.board.news.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.news.model.service.NewsService;
import semi.edit.greener.board.news.model.vo.News;
import semi.edit.greener.common.Pagination;
import semi.edit.greener.member.model.vo.Member;
import semi.edit.greener.notice.model.vo.Notice;

@Controller
public class NewsController {
	@Autowired
	private NewsService nwService;
	
	// News 게시판으로 이동
	@RequestMapping("list.nw")
	public String newsList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 1. News 총 게시물 수 구하기
		int listCount = nwService.getListCount();
		
		// 2. 현재 페이지 구하기
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 3. 페이징 계산
		BoardPageInfo pi = new Pagination().getBoardPageInfo(currentPage, listCount);
		
		// 4. 상단 고정 게시물리스트
		ArrayList<Notice> fixedList = nwService.selectFixedList();
		
		for(int i = 0; i<fixedList.size(); i++) {
			System.out.println("상단고정게시물 : " + fixedList.get(i));
		}
		
		// 5. 각 페이지 게시물 목록 가져오기
		ArrayList<News> list = nwService.selectList(pi);
		
		// 6. 이동
		if(list!=null) {
			model.addAttribute("list", list);
			model.addAttribute("pi", pi);
			model.addAttribute("fixedList", fixedList);
			return "newsBoard";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}
		
		
	}
	
	// News 게시물 상세보기
	@RequestMapping("detail.nw")
	public String newsDetail(@RequestParam("bNo") int bNo, Model model) {
		News news = nwService.selectDetail(bNo);
		
		if(news != null) {
			model.addAttribute("news", news);
			return "newsBoardDetail";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";			
		}
	}
	
	// 게시물 글쓰기 페이지 이동
	@RequestMapping("writeForm.nw")
	public String newsWriteForm() {
		return "newsWriteForm";
	}
	
	// 게시물 추가
	@RequestMapping("insert.nw")
	public String newsInsert(@ModelAttribute News nw, Model model, HttpSession session) {
		String boardWriter = ((Member)session.getAttribute("loginUser")).getUserId();
		nw.setBoardWriter(boardWriter);
		
		int result = nwService.newsInsert(nw);
		
		if(result >0) {
			return "redirect:list.nw";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";				
		}
	}
	
	// 게시물 수정
	// 1. 게시물 수정 페이지로 이동
	@RequestMapping("updateForm.nw")
	public String newsUpdateForm(@ModelAttribute News nw, Model model) {
		model.addAttribute("nw", nw);
		return "newsUpdateForm";
	}
	
	// 2. 게시물 수정
	@RequestMapping("update.nw")
	public String updateNews(@ModelAttribute News nw, Model model) {
		int result = nwService.updateNews(nw);
		
		if(result<2) {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";			
		} else {
			return "redirect:detail.nw?bNo=" + nw.getBoardNo();
		}
	}
	
	// 게시물 삭제
	@RequestMapping("deleteNews.nw")
	public String newsDelete(@RequestParam("bNo") int bNo, Model model) {
		int result = nwService.newsDelete(bNo);
		
		if(result>0) {
			return "redirect:list.nw";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";			
		}
	}
}



































