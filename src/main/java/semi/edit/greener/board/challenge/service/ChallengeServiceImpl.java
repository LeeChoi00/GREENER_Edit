package semi.edit.greener.board.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.challenge.dao.ChallengeDAO;
import semi.edit.greener.board.challenge.model.vo.Challenge;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.notice.model.vo.Notice;

@Service
public class ChallengeServiceImpl implements ChallengeService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private ChallengeDAO chDAO;

	@Override
	public int getListCount() {
		return chDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectFixedList() {
		return chDAO.selectFixedList(sqlSession);
	}

	@Override
	public ArrayList<Challenge> selectChList(BoardPageInfo pi) {
		return chDAO.selectChList(sqlSession, pi);
	}

	@Override
	public ArrayList<Image> selectImageList() {
		return chDAO.selectImageList(sqlSession);
	}

	@Override
	public int insertChallenge(Challenge ch, ArrayList<Image> imageList) {
		int chResult = chDAO.insertChallenge(sqlSession, ch);
		int imgResult = 0;
		if(chResult >0) {
			imgResult = chDAO.insertImage(sqlSession, imageList);
		}
		
		return imgResult;
	}

	@Override
	public Challenge selectChallengeBoard(int bNo) {
		return chDAO.selectChallengeBoard(sqlSession, bNo);
	}

	@Override
	public ArrayList<Image> selectFiles(int bNo) {
		return chDAO.selectFiles(sqlSession,bNo);
	}



	@Override
	public int updateBoard(Challenge ch) {
		return chDAO.updateBoard(sqlSession, ch);
	}

	@Override
	public int updateFiles(ArrayList<Image> updateFiles, int bNo) {
		return chDAO.updateFiles(sqlSession, updateFiles, bNo);
	}

	@Override
	public int deleteChallenge(int bNo) {
		return chDAO.deleteChellenge(sqlSession, bNo);
	}
	
	// 검색
	@Override
	public int getSearchListCount(String keyword) {
		return chDAO.getSearchListCount(sqlSession, keyword);
	}

	@Override
	public ArrayList<Challenge> searchList(String keyword) {
		return chDAO.searchList(sqlSession, keyword);
	}

















}
