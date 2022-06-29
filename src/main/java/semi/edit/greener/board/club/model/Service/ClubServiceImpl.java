package semi.edit.greener.board.club.model.Service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.club.model.dao.ClubDAO;
import semi.edit.greener.board.club.model.vo.Club;
import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.notice.model.vo.Notice;

@Service
public class ClubServiceImpl implements ClubService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private ClubDAO clDAO;

	@Override
	public int getListCount() {
		return clDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectFixedList() {
		return clDAO.selectFixedList(sqlSession);
	}

	@Override
	public ArrayList<Club> selectList(BoardPageInfo pi) {
		return clDAO.selectList(sqlSession, pi);
	}

	@Override
	public Club selectClub(int bNo) {
		return clDAO.selectClub(sqlSession, bNo);
	}

	@Override
	public int insertClub(Club cl) {
		return clDAO.insertClub(sqlSession, cl);
	}

	@Override
	public int updateClub(Club cl) {
		return clDAO.updateClub(sqlSession, cl);
	}

	@Override
	public int deleteClub(int bNo) {
		return clDAO.deleteClub(sqlSession, bNo);
	}

}
