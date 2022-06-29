package semi.edit.greener.board.recipe.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.board.recipe.model.service.RecipeService;
import semi.edit.greener.board.recipe.model.vo.Recipe;
import semi.edit.greener.common.Pagination;
import semi.edit.greener.member.model.vo.Member;
import semi.edit.greener.notice.model.vo.Notice;

@Controller
public class RecipeController {
	@Autowired
	private RecipeService rpService;
	
	// recipe 게시판 들어가기
	@RequestMapping("list.rp")
	public String recipeList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 1. 총 게시물 수 가져오기
		int listCount = rpService.getListCount();
		
		// 2. 현재 페이지 구하기
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 3. 페이징 계산
		BoardPageInfo pi = new Pagination().getBoardPageInfo(currentPage, listCount);
		
		// 4. 상단 고정 게시물 리스트
		ArrayList<Notice> fixedList = rpService.selectFixedList();	
		
		// 5. 각 페이지 게시물 목록 가져오기
		ArrayList<Recipe> rList = rpService.selectRpList(pi);
		
		// 6. 각 게시물 사진 파일 가져오기
		ArrayList<Image> imageList = rpService.selectImageList();
		
		// 7. 페이지 이동
		if(rList != null) {
			model.addAttribute("fixedList", fixedList);
			model.addAttribute("pi", pi);
			model.addAttribute("rList", rList);
			model.addAttribute("imageList", imageList);
			return "recipeList";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
	
	// 게시물 작성(글쓰기) 
	// 1. 게시물 글쓰기 페이지로 이동
	@RequestMapping("writeForm.rp")
	public String recipeWriteForm() {
		return "recipeWriteForm";
	}
	
	// 2. 게시물 작성 글 등록
	@RequestMapping("insert.rp")
	public String insertRecipe(@ModelAttribute Recipe rp,
			@RequestParam(value="thumbnailImg", required=false) ArrayList<MultipartFile> thumbnailImg,
			HttpServletRequest request, Model model, HttpSession session) {
		
		// 2.1 저장 이미지 파일 담을 arraylist와 객체 생성
		ArrayList<Image> imageList = new ArrayList();
		Image img = new Image();
		
		// 2.2 이미지 파일 Image 객체화 + 사진 파일 recipe_images에 저장 후 arraylist에 담기
		for(int i = 0; i< thumbnailImg.size();i++) {
			if(!thumbnailImg.get(i).isEmpty()) {
				img = saveFile(thumbnailImg.get(i), request);
				
				// 이미지 객체 file level 정하기
				if(i == 0) {
					img.setFileLevel(0);
				} else {
					img.setFileLevel(1);
				}
				// imageList에 img객체 넣기
				imageList.add(img);
			}
		}
		
		// 2.3 글쓴이(boardWriter)
		String boardWriter = ((Member)session.getAttribute("loginUser")).getUserId();
		rp.setBoardWriter(boardWriter);		
		
		// 2.4 board table에 게시물 저장
		int imgResult = rpService.insertChallenge(rp, imageList);	
		
		// 2.5 저장 결과에 따른 페이지 이동
		if(imgResult == imageList.size()) {
			return "redirect:list.rp";
		} else {
			model.addAttribute("msg", "Recipe 게시물 등록에 실패했습니다.");
			return "../../common/errorPage";
		}		
		
	}
	
	// 파일 저장 + 이미지 객체화 //
	private Image saveFile(MultipartFile file, HttpServletRequest request) {
		// 1. 이미지 파일 폴더 경로
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String savePath = root + "\\recipe_images";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// 2. 변경 파일 이름(renamedFileName) 지정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String originalFileName = file.getOriginalFilename();
		String renamedFileName = sdf.format(new Date(System.currentTimeMillis())) +
				originalFileName.substring(originalFileName.lastIndexOf("."));
		
		// 3. 파일 경로 설정
		String renamedPath = folder + "\\" + renamedFileName;
		
		// 4. 파일 경로에 파일 저장
		try {
			file.transferTo(new File(renamedPath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 5. Image 객체에 저장
		Image img = new Image();
		img.setFilePath(savePath);
		img.setOriginName(originalFileName);
		img.setChangeName(renamedFileName);
		img.setFileType(0);		 // recipe 사진
		
		return img;
	}

	// 게시물 상세보기 => semi 원본에 없음;;;;
	@RequestMapping("detail.rp")
	public String recipeDetail() {
		return null;
		
	}
}

































