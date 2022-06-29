package semi.edit.greener.notice.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.notice.model.dao.NoticeDAO;
import semi.edit.greener.notice.model.vo.Notice;
import semi.edit.greener.notice.model.vo.NoticePageInfo;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDAO nDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getListCount() {
		return nDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectNoticeList(NoticePageInfo npi) {
		return nDAO.selectNoticeList(sqlSession, npi);
	}

	@Override
	public ArrayList<Notice> selectFixedList() {
		return nDAO.selectFixedList(sqlSession);
	}

	@Override
	public Notice selectNotice(String no) {
		return nDAO.selectNotice(sqlSession, no);
	}

	@Override
	public int updateNotice(Notice n) {
		return nDAO.updateNotice(sqlSession, n);
	}

	@Override
	public int insertNotice(Notice n) {
		return nDAO.insertNotice(sqlSession, n);
	}

	@Override
	public int deleteNotice(String no) {
		return nDAO.deleteNotice(sqlSession, no);
	}
}
