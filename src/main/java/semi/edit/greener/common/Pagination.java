package semi.edit.greener.common;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.notice.model.vo.NoticePageInfo;

public class Pagination {
	public static NoticePageInfo getNoticePageInfo(int currentPage, int listCount) {
		int pageLimit = 5;
		int boardLimit = 10;
		int maxPage;
		int startPage;
		int endPage;
		
		maxPage = (int)Math.ceil((double)listCount/pageLimit);
//		startPage = ((currentPage-1)*pageLimit)+1;
		startPage = (currentPage-1)/pageLimit*pageLimit+1;
		endPage = startPage+pageLimit-1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		NoticePageInfo npi = new NoticePageInfo(currentPage, listCount, pageLimit, boardLimit, maxPage, startPage, endPage);
				
		return npi;
	}
	
	public static BoardPageInfo getBoardPageInfo(int currentPage, int listCount) {
		int pageLimit = 5;
		int boardLimit = 10;
		int maxPage;
		int startPage;
		int endPage;
		
		maxPage = (int)Math.ceil((double)listCount/pageLimit);
		startPage = (currentPage-1)/pageLimit*pageLimit+1;
		endPage = startPage+pageLimit-1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		BoardPageInfo pi = new BoardPageInfo(currentPage, listCount, pageLimit, boardLimit, maxPage, startPage, endPage);
				
		return pi;		
	}
}
