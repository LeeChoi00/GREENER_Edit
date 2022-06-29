package semi.edit.greener.board.club.model.Service;

import java.util.ArrayList;

import semi.edit.greener.board.club.model.vo.Club;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.notice.model.vo.Notice;

public interface ClubService {

	int getListCount();

	ArrayList<Notice> selectFixedList();

	ArrayList<Club> selectList(BoardPageInfo pi);

	Club selectClub(int bNo);

	int insertClub(Club cl);

	int updateClub(Club cl);

	int deleteClub(int bNo);



}
