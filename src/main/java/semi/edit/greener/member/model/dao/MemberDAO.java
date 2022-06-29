package semi.edit.greener.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.member.model.vo.Member;

@Repository("mDAO")
public class MemberDAO {

	public Member login(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.login", m);
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}

	public int checkId(SqlSessionTemplate sqlSession, String inputId) {
		return sqlSession.selectOne("memberMapper.checkId", inputId);
	}

	public int emailDCheck(SqlSessionTemplate sqlSession, String email) {
		return sqlSession.selectOne("memberMapper.emailDCheck", email);
	}

	public int checkPhone(SqlSessionTemplate sqlSession, String userPhone) {
		return sqlSession.selectOne("memberMapper.checkPhone", userPhone);
	}

	public int checkNickName(SqlSessionTemplate sqlSession, String userNickName) {
		return sqlSession.selectOne("memberMapper.checkNickName", userNickName);
	}

	public String searchIdByEmail(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.searchIdByEmail", m);
	}

	public String searchIdByPhone(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.searchIdByPhone", m);
	}

	public int emailVaild(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.emailVaild", m);
	}

	public int newPwdQCheck(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.newPwdQCheck", m);
	}

	public int updateNewPwd(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateNewPwd", m);
	}

	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember", m);
	}

	public int updatePwd(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updatePwd", m);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.update("memberMapper.deleteMember", userId);
	}

	

}
