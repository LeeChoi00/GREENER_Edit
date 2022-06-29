package semi.edit.greener.board.challenge.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import semi.edit.greener.board.challenge.model.vo.Challenge;
import semi.edit.greener.board.challenge.service.ChallengeService;
import semi.edit.greener.board.model.vo.Board;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.common.Pagination;
import semi.edit.greener.notice.model.vo.Notice;
import semi.edit.greener.member.model.vo.Member;

@Controller
public class ChallengeController {
	@Autowired
	private ChallengeService chService;
	
	// Challenge 게시판 들어가기 
	@RequestMapping("list.ch")
	private String challengeList(@RequestParam(value="page", required=false) Integer page, Model model) {
		// 1. Challenge 게시판의 전체 게시글 수 구하기
		int listCount = chService.getListCount();
		
		// 2. 현재 페이지 구하기
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 3. 페이징 계산
		BoardPageInfo pi = new Pagination().getBoardPageInfo(currentPage, listCount);
		
		// 4. 상단 고정 게시물 리스트
		ArrayList<Notice> fixedList = chService.selectFixedList();		
		// 게시판에서 쓸 수 있도록 세션 정보로 넣기		=> 세션정보에 꼭 넣어야 하는가?	
//		model.addAttribute("fixedList", fixedList);
		
		// 5. 각 페이지 게시물 목록 가져오기
		ArrayList<Challenge> list = chService.selectChList(pi);
		
		// 6. 각 게시물 사진 파일 가져오기
		ArrayList<Image> imageList = chService.selectImageList();
		
		// 7. 페이지 이동
		if(list != null) {
			model.addAttribute("fixedList", fixedList);
			model.addAttribute("pi", pi);
			model.addAttribute("list", list);
			model.addAttribute("imageList", imageList);
			return "challengeList";
		} else {
			model.addAttribute("msg", "게시판 조회에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
	
	// 게시물 글쓰기
	// 1. 게시물 글쓰기 페이지 이동
	@RequestMapping("challengeWriteForm.ch")
	public String challengeWriteForm() {
		return "challengeWriteForm";
	}
	
	// 2. 게시물 작성 글 등록
	@RequestMapping("insert.ch")
	public String insertChallenge(@ModelAttribute Challenge ch,
			@RequestParam(value="thumbnailImg", required=false) ArrayList<MultipartFile> thumbnailImg,
			HttpServletRequest request, Model model, HttpSession session) {
//		public String insertChallenge(@ModelAttribute Challenge ch,
//				@RequestParam(value="thumbnailImg1", required=false) MultipartFile thumbnailImg1,
//				@RequestParam(value="thumbnailImg2", required=false) MultipartFile thumbnailImg2,
//				@RequestParam(value="thumbnailImg3", required=false) MultipartFile thumbnailImg3,
//				HttpServletRequest request, Model model) {
//		
//		System.out.println("사진 1 : " + thumbnailImg1.getOriginalFilename());
//		System.out.println("사진 2 : " + thumbnailImg2.getOriginalFilename());
//		System.out.println("사진 3 : " + thumbnailImg3.getOriginalFilename());
//		
//		
//		ArrayList<MultipartFile> thumbnailImg = new ArrayList<>();
//		
//		
//		thumbnailImg.add(thumbnailImg1);
//		thumbnailImg.add(thumbnailImg2);
//		thumbnailImg.add(thumbnailImg3);
		
		System.out.println("사진 갯수 :" + thumbnailImg.size());
		System.out.println(thumbnailImg.get(2));
		System.out.println(thumbnailImg.get(2).isEmpty());
		
		// HashMap<String, Object> imageList = new HashMap<>();
		
		// 2.1 저장 이미지 파일 담을 arraylist와 객체 생성
		ArrayList<Image> imageList = new ArrayList();
		Image img = new Image();
		
		
		// 2.2 이미지 파일 Image 객체화 + 사진 파일 recipe_images에 저장 후 arraylist에 담기
//		System.out.println("사진 저장");
		for(int i = 0; i < thumbnailImg.size(); i++) {
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
			
//			System.out.println("저장 순서 " + i + "번째 : " + imageList.get(i));
		}
		
//		System.out.println(imageList.get(1));
//		
//		System.out.println("dao imageList 사이즈 : " + imageList.size());
//		for(int i = 0; i < imageList.size(); i++) {
//			System.out.println("imageList" + i + "버째 : " + imageList.get(i));
//		}
		
		// 2.3 챌린지 종류 확정
		String challenge = ch.getChalName();
		String chalName = null;
		switch(challenge) {
			case "1": chalName = "용기내 챌린지"; break;
			case "2": chalName = "플로깅 챌린지"; break;
			case "3": chalName = "제로웨이스트 챌린지"; break;
			case "4": chalName = "캔크러시 챌린지"; break;		
		}
		ch.setChalName(chalName);
		
		// 2.4 글쓴이(boardWriter) 
//		System.out.println("loginUser : " + ((Member)session.getAttribute("loginUser")).getUserId());
		String boardWriter = ((Member)session.getAttribute("loginUser")).getUserId();
		ch.setBoardWriter(boardWriter);
		
		
//		System.out.println("ch" + ch);
		
		//  2.5 board table에 게시물 저장
		int imgResult = chService.insertChallenge(ch, imageList);
		
		System.out.println("result : " + imgResult);
		
		if(imgResult == imageList.size()) {
			return "redirect:list.ch";
		} else {
			model.addAttribute("msg", "Challenge 게시물 등록에 실패했습니다.");
			return "../../common/errorPage";
		}
	}
	
	// 이미지 폴더에 Image 객체에 저장 //
	private Image saveFile(MultipartFile file, HttpServletRequest request) {
		// 1. 이미지 폴더 경로
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		String savePath = root + "\\challenge_images";
		
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
		img.setFileType(1);
		
		System.out.println("img.getFilePath()" + img.getFilePath());
		System.out.println("img.getOriginName()" + img.getOriginName());
		System.out.println("img.getChangeName()" + img.getChangeName());
		System.out.println("img.getFileType()" + img.getFileType());
		

		return img;
	}
	
	// 4. 게시물 상세보기
	@RequestMapping("detail.ch")
	public ModelAndView challengeDetail(@RequestParam("bNo") int bNo, ModelAndView mv) {
		Challenge chBoard = chService.selectChallengeBoard(bNo);
		ArrayList<Image> fileList = chService.selectFiles(bNo);
		
//		System.out.println("fileList 확인");
//		for(int i = 0; i<fileList.size();i++) {
//			System.out.println(fileList.get(i));
//		}
		
		if(chBoard != null && fileList != null) {
			mv.addObject("chBoard", chBoard);
			mv.addObject("fileList", fileList);
			mv.setViewName("challengeDetail");
		} else {
			mv.addObject("msg", "게시판 조회에 실패했습니다.");
			mv.setViewName("../../common/errorPage");
		}
		
		return mv;
	}
	
	// 5. 게시물 수정
	// 5.1 게시물 수정 페이지로 이동
	@RequestMapping("updateForm.ch")
	public String challengeUpdateForm(@ModelAttribute Challenge ch,
			/*
			 * @RequestParam("tFileNo") int tFileNo,
			 * 
			 * @RequestParam("cFileNo") int[] cFileNos,
			 */
			Model model) {
		
		model.addAttribute("ch", ch);
		/*
		 * model.addAttribute("tFileNo", tFileNo); model.addAttribute("cFileNos",
		 * cFileNos);
		 */
		
	
		
		return "challengeUpdateForm";
		
	}
	
	// 5.2  게시물 수정
	@RequestMapping("update.ch")
	public String challengeUpdate(@ModelAttribute Challenge ch, 
			@RequestParam(value="thumbnailImg", required=false) ArrayList<MultipartFile> thumbnailImg,
			HttpServletRequest request, Model model, HttpSession session) {
		// 사진 수정
		// 1. 수정 파일 존재 여부 확인 후 수정할 파일 리스트에 추가
		int bNo = ch.getBoardNo();
		ArrayList<Image> updateFiles = new ArrayList<Image>();
		Image newImg = new Image();
		for(int i = 0; i<thumbnailImg.size()-1;i++) {
			if(!thumbnailImg.get(i).isEmpty()) {
				newImg = saveFile(thumbnailImg.get(i), request);
				
				// 썸네일 여부 표시
				if(i==0) {
					newImg.setFileLevel(0);
				} else {
					newImg.setFileLevel(1);
				}
				
				// 저장할 게시물 번호 지정
				newImg.setBoardNo(bNo);
				
				// 저장할 게시물 list에 추가
				updateFiles.add(newImg);

			}
		}
		
		// 기존 파일 정보 가져오기
		ArrayList<Image> originalFiles=chService.selectFiles(bNo);
		
		
		// 기존 파일 삭제 후 수정 파일 추가
		// 1. 수정 파일 존재여부 확인
		int exSum = 0;  // exist 총합이 0이면 수정파일 없음
		for(int i = 0; i<thumbnailImg.size()-1; i++) {
			if(thumbnailImg.get(i) !=null) {
				exSum +=1;
			}
		}
		
		// 2. 수정 파일 존재 시 실행
		if(exSum >0) {
			// 2.1 수정 파일 존재시 기존파일 삭제 후 수정 파일 추가
			int imgResult = chService.updateFiles(updateFiles, bNo);
		
			// 2.2 실제 기존 파일도 삭제
			for(int i = 0; i<originalFiles.size()-1 ; i++) {
				File deleteOriginalFile = new File(originalFiles.get(i).getFilePath()+ "\\" + originalFiles.get(i).getChangeName());
				deleteOriginalFile.delete();
			}
		}
		
		// 사진 외 정보 수정
		int boardResult = chService.updateBoard(ch);
		
		ArrayList<Image> fileList = chService.selectFiles(bNo);
		
		if(boardResult <2) {
			model.addAttribute("msg", "게시물 수정에 실패했습니다.");
			return "../../common/errorPage";				
		} else {
			model.addAttribute("chBoard", ch);
			model.addAttribute("fileList", fileList);
			return "redirect:detail.ch?bNo=" + bNo;
		}
	}
	
	// 6. 게시물 삭제
	@RequestMapping("delete.ch")
	public String deleteChallenge(@RequestParam("bNo") int bNo, Model model) {
		// board 상태 N으로 수정 
		int result = chService.deleteChallenge(bNo);
		
		if(result<2) {
			model.addAttribute("msg", "게시물 수정에 실패했습니다.");
			return "../../common/errorPage";
		} else {
			return "redirect:list.ch";				
		}
	}

}


























































