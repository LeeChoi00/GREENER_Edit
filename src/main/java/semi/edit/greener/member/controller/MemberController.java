package semi.edit.greener.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import semi.edit.greener.member.model.service.MemberService;
import semi.edit.greener.member.model.vo.Member;


@SessionAttributes("loginUser")
@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	// 로그인, 로그아웃
	// 1. 로그인 창으로 이동
	@RequestMapping("login.me")  
	public String login() {
		return "login";
	}
	
	// 2. 로그인 + 암호화
	@RequestMapping("login2.me")
	public String login2(@ModelAttribute Member m, Model model, HttpSession session) {	
		Member loginUser = mService.login(m);
		
//		System.out.println("비밀번호 : " + bcrypt.encode(m.getUserPwd()));
		
		if(loginUser == null) {
			model.addAttribute("msg", "로그인에 실패했습니다");
			return  "../common/errorPage";
		}
		
		boolean match = bcrypt.matches(m.getUserPwd(), loginUser.getUserPwd());
		

		if(match) {
			model.addAttribute("loginUser", loginUser);
			session.setMaxInactiveInterval(1200);
			return "redirect:home.go";
		} else {
			model.addAttribute("msg", "로그인에 실패했습니다");
			return  "../common/errorPage";
		}
		
	}
	
	
	// 3. 로그아웃
	@RequestMapping("logout.me")
	public String logout(SessionStatus status) {
		status.setComplete();	
		return "redirect:home.go";
	}
	
	// 회원가입
	// 1. 회원가입 창으로 이동
	@RequestMapping("signUpForm.me")
	public String signUpForm() {
		return "signUp";
	}
	
	// 2. 회원가입 + 암호화
	@RequestMapping("insert.me")
	public String insertMember(@ModelAttribute Member m) {
		String enctPwd = bcrypt.encode(m.getUserPwd());
		m.setUserPwd(enctPwd);
		
		int result = mService.insertMember(m);
		
		if(result>0) {
			return "redirect:home.go";
		} else {
			return "../common/errorPage";
		}
	}
	
	// 3. 중복 확인
	
	// 3.1 아이디 중복 확인 
	// 3.1.1 아이디 중복 확인 창
	@RequestMapping("DCheckIdForm.me")
	public String DCheckIdForm() {
		return "dCheckIdForm";
	}
	
	// 3.1.2 아이디 중복 확인
	@RequestMapping("dCheckId.me")
	public ModelAndView dCheckId(@RequestParam("inputId") String inputId, ModelAndView mv) {
		int result = mService.checkId(inputId);
		
		mv.addObject("result", result);
		mv.addObject("inputId", inputId);
		mv.setViewName("dCheckId");	
		
		return mv;
	}
	
	// 3.2 이메일 중복 확인
	// 3.2.1 이메일 인증 버튼
	@RequestMapping("emailDCheck.me")
	public void dCheckEmail(@RequestParam("userEmail") String email, HttpServletResponse response) throws IOException {
		int result = mService.emailDCheck(email);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
			
		if(result>0) {
			out.print("이미 존재하는 이메일입니다");
		} else {
			out.print("사용 가능한 이메일입니다");
		}

	}
	
	
	// 3.2.2 인증 확인 버튼
	@RequestMapping("checkEmail.me")
	public void emailCheck(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
		// 보내는 사람(관리자) 메일
		String sender="";  
		String password="";
		
		// 받는 사람(회원가입 희망자) 메일
		String receiver = email;
		String title="GREENER 회원가입 이메일 인증";
		
		// 메일로 보내는 인증 번호
		int random = (int)(Math.random() * 1000000);
		String content = "<h3>인증번호를 입력해 주세요. 인증 번호는  " + random + "입니다";
		
		// 메일 보내기
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});
		
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			message.setSubject(title);
			message.setText(content, "UTF-8", "html");
			
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(random);			
	}
	
	// 3.3 전화번호 중복확인
	@RequestMapping("dCheckPhone.me")
	public void dCheckPhone(@RequestParam("userPhone") String userPhone, HttpServletResponse response) throws IOException {
		int result = mService.checkPhone(userPhone);
		
		response.setCharacterEncoding("UTF-8");
		if(result >0) {
			response.getWriter().print("이미 존재하는 전화번호입니다");
		} else {
			response.getWriter().print("사용 가능한 전화번호입니다");
		}
	}
	
	// 3.4 닉네임 중복확인
	@RequestMapping("nickNameCheck.me")
	public void nickNameCheck(@RequestParam("userNickName") String userNickName, HttpServletResponse response) throws IOException {
		int result = mService.checkNickName(userNickName);
		
		response.setCharacterEncoding("UTF-8");
		if(result >0) {
			response.getWriter().print("이미 존재하는 닉네임입니다");
		} else {
			response.getWriter().print("사용 가능한 닉네임입니다");
		}
		
	}
	
	// 로그인 창 : 아이디 찾기
	// 1. 아이디 찾기 창
	@RequestMapping("searchIdForm.me")
	public String searchIdForm() {
		return "searchIdForm";
	}
	
	// 2. 이메일로 아이디 찾기
	// 2.1 이메일로 아이디 찾기 창
	@RequestMapping("searchIdByEmailForm.me")
	public String searchIdByEmailForm() {
		return "searchIdByEmailForm";
	}
	
	// 2.2 이메일로 아이디 찾기
	@RequestMapping("searchIdByEmail.me")
	public ModelAndView searchIdByEmail(@ModelAttribute Member m, ModelAndView mv) {
		String searchedId = mService.searchIdByEmail(m);

		m.setUserId(searchedId);
		mv.addObject("member", m);
		mv.setViewName("searchIdResult");

		
		return mv;
	}
	
	// 3. 전화번호로 아이디 찾기
	// 3.1 전화번호로 아이디 찾기 창
	@RequestMapping("searchIdByPhoneForm.me")
	public String searchIdByPhoneForm() {
		return "searchIdByPhoneForm";
	}
	
	// 3.2 전화번호로 아이디 찾기
	@RequestMapping("searchIdByPhone.me")
	public ModelAndView SearchIdByPhone(@ModelAttribute Member m, ModelAndView mv) {
		String searchedId = mService.searchIdByPhone(m);
		
//		// 변경 전(세미 프로젝트 original 버전)
//		if(searchedId != null) {
//			mv.addObject("member", m);
//			mv.addObject("searchedId", searchedId);
//			mv.setViewName("searchIdResult");
//		} else {
//			mv.addObject("msg", "아이디를 찾는데 실패했습니다");
//			mv.setViewName("../common/errorPage");
//		}
		
		m.setUserId(searchedId);
		mv.addObject("member", m);
		mv.setViewName("searchIdResult");
		return mv;
	}
	
	// 로그인 창 비밀번호 찾기
	// 1. 비밀번호 찾기 창
	@RequestMapping("createNewPwdForm.me")
	public String createNewPwdForm() {
		return "createNewPwdForm";
	}
	
	// 2. 비밀번호 찾기 - 이메일 인증
	@RequestMapping("newPwdEmailCheck.me")
	public void newPwdEmailCheck(@ModelAttribute Member m, ModelAndView mv, HttpServletResponse response) throws JsonIOException, IOException {
		// 1.사용자 존재 여부 확인
		int result = mService.emailVaild(m);
		
		
		// 2. 사용자 부존재시
		if(result <1) {
			mv.addObject("msg", "존재하는 사용자가 아닙니다.");
			mv.setViewName("../common/errorPage");
		}
		
		// 3. 사용자 존재 확인 후 인증번호 이메일 발송
		// 3.1 보내는 사람/받는 사람 정보
		String sender = "";
		String password="";
		
		// 받는 사람(회원가입 희망자) 메일
		String receiver = m.getEmail();
		
		// 3.2 인증번호 생성 + 보낼 메시지
		int validNumber = (int)(Math.random() * 1000000);
		
		String title = "Greener 새 비밀번호 생성 인증번호 안내입니다";
		String content = "인증번호는 <span style='color:red;'>"+ validNumber + "</span>입니다. <br> 인증번호란에 입력해주세요.";
		
		// 3.3 이메일 보내기
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.auth", "true");
			
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});
			
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			message.setSubject(title);
			message.setText(content, "UTF-8", "html");
			
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		// 4. 인증번호 변수 validNumber를 다시 jsp로 돌려보내기
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		gson.toJson(validNumber, response.getWriter());
	}
	
	// 3. 비밀번호 생성을 위해 회원가입 당시 질문&답 인증
	// 3.1 뷰 이동
	@RequestMapping("createNewPwdQuestion.me")
	public String createNewPwdQuestion(@RequestParam("userId") String userId, Model model) {
		model.addAttribute("userId", userId);
		return "createNewPwdQuestion";
	}
	
	// 3.2 비밀번호 질문&답 인증
	@RequestMapping("newPwdQCheck.me")
	public ModelAndView newPwdQCheck(@ModelAttribute Member m, ModelAndView mv) {
		int result = mService.newPwdQCheck(m);
		
		if(result >0) {
			mv.addObject("userId", m.getUserId());
			mv.setViewName("createNewPwd");
		} else {
			mv.addObject("result", result);
			mv.addObject("userId", m.getUserId());
			mv.setViewName("newPwdResult");
		}
		
		return mv;
	}
	
	// 3.3 새 비밀번호 생성
	@RequestMapping("updateNewPwd.me")
	public String updateNewPwd(@ModelAttribute Member m, Model model) {
		String encNewPwd = bcrypt.encode(m.getUserPwd());
		m.setUserPwd(encNewPwd);
		
		int result = mService.updateNewPwd(m);
		
		model.addAttribute("result", result);
		model.addAttribute("userId", m.getUserId());
		
		return "newPwdResult";
	}
	
	// ***************************************************************** //
	
	// 내 정보 페이지
	// 1. 내 정보 보기
	@RequestMapping("myPage.me")
	public String myPage() {
		return "myPage";
	}
	
	// 2. 내 정보 수정 뷰로 이동
	@RequestMapping("updateMemberForm.me")
	public String updateMemberForm() {
		return "memberUpdateForm";
	}
	
	// 3. 내 정보 수정
	@RequestMapping("updateMember.me")
	public String updateMember(@ModelAttribute Member m, Model model) {
		// 3.1 정보 수정
		int result = mService.updateMember(m);
		
		// 3.2 사용자(loginUser)에 대해 비밀번호 정보까지 가져오기
		Member loginUser = mService.login(m);
		
		// 3.3 수정 성공 여부
		if(result>0) {
			// 세션의 loginUser 수정
			model.addAttribute("loginUser", loginUser);
			
			return "redirect:myPage.me";
		} else {
			model.addAttribute("msg", "회원 정보 수정에 실패했습니다.");
			return "../common/errorPage";
		}
	}
	
	// 비밀번호 변경
	// 1. 비밀번호 변경 뷰로 이동
	@RequestMapping("updatePwdForm.me")
	public String updatePwdForm() {
		return "pwdUpdateForm";
	}
	
	// 2. 비밀번호 변경
	@RequestMapping("updatePwd.me")
	public String updatePwd(@RequestParam("userPwd") String userPwd, @RequestParam("newPwd") String newPwd,
			Model model, SessionStatus status) {	
		// 2.1 로그인 정보를 가져와 비교하기
		Member m = (Member)model.getAttribute("loginUser");
		boolean match = bcrypt.matches(userPwd, m.getUserPwd());
		
		if(!match) {
			model.addAttribute("msg", "비밀번호가 불일치합니다.");
			return "../common/errorPage";
		}
		
		// 2.2 db에서 비밀번호 변경
		String userId = m.getUserId();
		m = new Member();
		m.setUserId(userId);
		m.setUserPwd(bcrypt.encode(newPwd));
		
		int result = mService.updatePwd(m);
		
		if(result >0 ) {
			// 2.3 로그아웃
			status.setComplete();
			
			return "redirect:home.go";
		} else {
			model.addAttribute("msg", "비밀번호 수정에 실패했습니다.");
			return "../common/errorPage";
		}
	}
	
	// 탈퇴
	@RequestMapping("delete.me")
	public String deleteMember(Model model, SessionStatus status) {
		String userId = ((Member)model.getAttribute("loginUser")).getUserId();
		
		int result = mService.deleteMember(userId);
		
		if(result >0 ) {
			status.setComplete();
			
			return "redirect:home.go";
		} else {
			model.addAttribute("msg", "회원 탈퇴에 실패했습니다.");
			
			return "../common/errorPage";
		}
	}
	
	
}


















