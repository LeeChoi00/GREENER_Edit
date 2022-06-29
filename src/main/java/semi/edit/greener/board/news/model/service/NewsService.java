package semi.edit.greener.board.news.model.service;

import java.util.ArrayList;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.news.model.vo.News;
import semi.edit.greener.notice.model.vo.Notice;

public interface NewsService {

	int getListCount();

	ArrayList<Notice> selectFixedList();

	ArrayList<News> selectList(BoardPageInfo pi);

	News selectDetail(int bNo);

	int newsInsert(News nw);

	int updateNews(News nw);

	int newsDelete(int bNo);

	

}
