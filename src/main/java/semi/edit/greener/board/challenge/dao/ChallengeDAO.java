package semi.edit.greener.board.challenge.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.board.challenge.model.vo.Challenge;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.notice.model.vo.Notice;

@Repository
public class ChallengeDAO {
	// 게시판 이동하기
	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("challengeMapper.getListCount");
	}

	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectFixedList");
	}

	public ArrayList<Challenge> selectChList(SqlSessionTemplate sqlSession, BoardPageInfo pi) {
		// 1. 건너뛸 페이지 수 => offset + 1 번 게기물부터 가져오기
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		
		// 2. Rowbounds 구하기
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("challengeMapper.selectChList", null, rowBounds);
	}

	public ArrayList<Image> selectImageList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("challengeMapper.selectImageList");
	}
	
	// 작성한 게시물 저장
	// 1. board, challenge_bd에 사진 외 정보 저장
	public int insertChallenge(SqlSessionTemplate sqlSession, Challenge ch) {
		int bResult = sqlSession.insert("challengeMapper.insertBoard", ch);
		int chResult = 0;
		if(bResult >0) {
			chResult = sqlSession.insert("challengeMapper.insertChallenge", ch);
		}
		
		return chResult;
	}
	
	// 2. image에 challenge게시물 사진 저장
	public int insertImage(SqlSessionTemplate sqlSession, ArrayList<Image> imageList) {
		
		int sum = 0;
		
		
//		System.out.println("dao imageList 사이즈 : " + imageList.size());
//		for(int i = 0; i < imageList.size()-1; i++) {
//			System.out.println("imageList" + i + "버째 : " + imageList.get(i));
//		}
		
		for(int i = 0; i < imageList.size(); i++) {
			int result = sqlSession.insert("challengeMapper.insertImage", imageList.get(i));
			sum = sum + result;
			System.out.println("중간 sum값 -" + i + "번째 :" +  sum);
		}
		
		System.out.println("sum : " + sum);
		return sum;
	}

	// 게시물 상세보기
	public Challenge selectChallengeBoard(SqlSessionTemplate sqlSession, int bNo) {
		int cResult = sqlSession.update("challengeMapper.updateCount", bNo);
		Challenge chResult = null;      ///////////////////??????????????????????????????
		if(cResult>0) {
			chResult = sqlSession.selectOne("challengeMapper.selectChallengeBoard", bNo);
		}
		return chResult;
	}

	public ArrayList<Image> selectFiles(SqlSessionTemplate sqlSession, int bNo) {
		return (ArrayList)sqlSession.selectList("challengeMapper.selectFiles", bNo);
	}
	
	// 게시물 수정
	// 1. 사진 외 정보 수정
	public int updateBoard(SqlSessionTemplate sqlSession, Challenge ch) {
		int bpResult = sqlSession.update("challengeMapper.updateBoard", ch); //BOARD변경
		int chResult = sqlSession.update("challengeMapper.updateChallenge", ch);
		return bpResult + chResult;
	}
	
	// 2. 기존 사진 정보 삭제 후 수정 사진 정보 넣기
	public int updateFiles(SqlSessionTemplate sqlSession, ArrayList<Image> updateFiles, int bNo) {
		int deleteResult = sqlSession.delete("challengeMapper.deleteOldFiles", bNo);
		int updateResult = 0;
		int sum = 0;
		if(deleteResult >0) {
			for(int i = 0; i < updateFiles.size(); i++) {
				updateResult = sqlSession.insert("challengeMapper.updateNewFiles", updateFiles.get(i));
			}

			sum = sum + updateResult;
		}
		return deleteResult+sum;
	}
	
	// 게시물 삭제

	public int deleteChellenge(SqlSessionTemplate sqlSession, int bNo) {
		int deleteBoard = sqlSession.update("challengeMapper.deleteChallenge", bNo);
		int deleteImage = sqlSession.update("challengeMapper.deleteOldFiles", bNo);
		return deleteBoard+deleteImage;
	}
	
	// 검색
	public int getSearchListCount(SqlSessionTemplate sqlSession, String keyword) {
		return sqlSession.selectOne("challengeMapper.getSearchListCount", keyword);
	}

	public ArrayList<Challenge> searchList(SqlSessionTemplate sqlSession, String keyword) {
		return (ArrayList)sqlSession.selectList("challengeMapper.searchList", keyword);
	}











	
	





}
