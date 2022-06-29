package semi.edit.greener.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.member.model.dao.MemberDAO;
import semi.edit.greener.member.model.vo.Member;

@Service("mService")
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public Member login(Member m) {
		return mDAO.login(sqlSession, m);
	}

	@Override
	public int insertMember(Member m) {
		return mDAO.insertMember(sqlSession, m);
	}

	@Override
	public int checkId(String inputId) {
		return mDAO.checkId(sqlSession, inputId);
	}

	@Override
	public int emailDCheck(String email) {
		return mDAO.emailDCheck(sqlSession, email);
	}

	@Override
	public int checkPhone(String userPhone) {
		return mDAO.checkPhone(sqlSession, userPhone);
	}

	@Override
	public int checkNickName(String userNickName) {
		return mDAO.checkNickName(sqlSession, userNickName);
	}

	@Override
	public String searchIdByEmail(Member m) {
		return mDAO.searchIdByEmail(sqlSession, m);
	}

	@Override
	public String searchIdByPhone(Member m) {
		return mDAO.searchIdByPhone(sqlSession, m);
	}

	@Override
	public int emailVaild(Member m) {
		return mDAO.emailVaild(sqlSession, m);
	}

	@Override
	public int newPwdQCheck(Member m) {
		return mDAO.newPwdQCheck(sqlSession, m);
	}

	@Override
	public int updateNewPwd(Member m) {
		return mDAO.updateNewPwd(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		return mDAO.updateMember(sqlSession, m);
	}

	@Override
	public int updatePwd(Member m) {
		return mDAO.updatePwd(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return mDAO.deleteMember(sqlSession, userId);
	}



	


}
