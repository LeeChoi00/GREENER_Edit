package semi.edit.greener.member.model.service;

import semi.edit.greener.member.model.vo.Member;

public interface MemberService {

	Member login(Member m);

	int insertMember(Member m);

	int checkId(String inputId);

	int emailDCheck(String email);

	int checkPhone(String userPhone);

	int checkNickName(String userNickName);

	String searchIdByEmail(Member m);

	String searchIdByPhone(Member m);

	int emailVaild(Member m);

	int newPwdQCheck(Member m);

	int updateNewPwd(Member m);

	int updateMember(Member m);

	int updatePwd(Member m);

	int deleteMember(String userId);

	



}
