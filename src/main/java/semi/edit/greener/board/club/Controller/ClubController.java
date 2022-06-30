package semi.edit.greener.board.club.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import semi.edit.greener.board.club.model.Service.ClubService;
import semi.edit.greener.board.club.model.vo.Club;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.common.Pagination;
import semi.edit.greener.member.model.vo.Member;
import semi.edit.greener.notice.model.vo.Notice;

@Controller
public class ClubController {
	
	@Autowired
	private ClubService clService;
	
	@RequestMapping("list.cl")
	public String clubList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 1. Club 게시판의 전체 글 게시글 수 구하기
		int listCount = clService.getListCount();
		System.out.println("listcount : " + listCount);
		
		// 2. 현재 페이지 구하기
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 3. 페이징 계산
		BoardPageInfo pi = new Pagination().getBoardPageInfo(currentPage, listCount);
		
		// 4. 상단 고정 게시물 리스트
		ArrayList<Notice> fixedList = clService.selectFixedList();
		
		for(int i = 0; i<fixedList.size(); i++) {
			System.out.println("상단고정게시물 : " + fixedList.get(i));
		}
		
		// 5. 각 페이지 게시물 목록 가져오기
		ArrayList<Club> list = clService.selectList(pi);
		
		// 6. 이동
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("pi", pi);
			model.addAttribute("fixedList", fixedList);
			return "clubList";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}

	}
	
	@RequestMapping("detail.cl")
	public String clubDetail(@RequestParam("bNo") int bNo, Model model) {
		Club club = clService.selectClub(bNo);
		
		if(club != null) {
			model.addAttribute("club", club);
			return "clubDetail";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
	
	@RequestMapping("writeForm.cl")
	public String clubWriteForm() {
		return "clubWriteForm";
	}
	
	@RequestMapping("insert.cl")
	public String clubInsert(@ModelAttribute Club cl,Model model,
			HttpSession session) {

		// 세션에서 글쓴이 아이디 가져오기
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		String boardWriter = loginUser.getUserId();
		String nickName = loginUser.getNickName();
		
		cl.setBoardWriter(boardWriter);
		cl.setNickName(nickName);
		
		// db에 게시물 추가
		int result = clService.insertClub(cl);
		
		// 결과에 따라 페이지 이동
		if(result >0) {
			return "redirect:list.cl";
		} else {
			model.addAttribute("msg", "게시물 추가에 실패했습니다.");
			return "../../common/errorPage";
		}
		
	}
	
	@RequestMapping("updateForm.cl")
	public String clubUpdateForm(@ModelAttribute Club cl, Model model) {
		model.addAttribute("cl", cl);
		return "clubUpdateForm";
	}
	
	@RequestMapping("update.cl")
	public String updateClub(@ModelAttribute Club cl, Model model) {
		int result = clService.updateClub(cl);
		
		if(result >0) {
			model.addAttribute("club", cl);
			return "redirect:detail.cl?no=" + cl.getBoardNo();
		} else {
			model.addAttribute("msg", "게시물 수정에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
		
	@RequestMapping("deleteClub.cl")
	public String deleteClub(@RequestParam("no") int bNo, Model model) {
		int result = clService.deleteClub(bNo);
		if(result >0) {
			return "redirect:list.cl";
		} else {
			model.addAttribute("msg", "게시글 삭제에 실패했습니다");
			return "../../common/errorPage";
		}
	}

}





















