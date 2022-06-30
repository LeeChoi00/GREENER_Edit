package semi.edit.greener.board.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import semi.edit.greener.board.challenge.model.vo.Challenge;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.notice.model.vo.Notice;

public interface ChallengeService {

	int getListCount();

	ArrayList<Notice> selectFixedList();

	ArrayList<Challenge> selectChList(BoardPageInfo pi);

	ArrayList<Image> selectImageList();

	int insertChallenge(Challenge ch, ArrayList<Image> imageList);

	Challenge selectChallengeBoard(int bNo);

	ArrayList<Image> selectFiles(int bNo);


	int updateBoard(Challenge ch);

	int updateFiles(ArrayList<Image> updateFiles, int bNo);

	int deleteChallenge(int bNo);
	
	// 검색
	int getSearchListCount(String keyword);

	ArrayList<Challenge> searchList(String keyword);









	

	

}
