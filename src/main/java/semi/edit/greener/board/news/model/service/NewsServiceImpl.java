package semi.edit.greener.board.news.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.news.model.dao.NewsDAO;
import semi.edit.greener.board.news.model.vo.News;
import semi.edit.greener.notice.model.vo.Notice;

@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsDAO nwDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getListCount() {
		return nwDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectFixedList() {
		return nwDAO.selectFixedList(sqlSession);
	}
	
	@Override
	public ArrayList<News> selectList(BoardPageInfo pi) {
		return nwDAO.selectList(sqlSession, pi);
	}

	@Override
	public News selectDetail(int bNo) {
		return nwDAO.selectDetail(sqlSession, bNo);
	}

	@Override
	public int newsInsert(News nw) {
		return nwDAO.newsInsert(sqlSession, nw);
	}

	@Override
	public int updateNews(News nw) {
		return nwDAO.updateNews(sqlSession, nw);
	}

	@Override
	public int newsDelete(int bNo) {
		return nwDAO.newsDelete(sqlSession, bNo);
	}



}
