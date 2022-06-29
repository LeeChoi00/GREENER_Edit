package semi.edit.greener.notice.model.service;

import java.util.ArrayList;

import semi.edit.greener.notice.model.vo.Notice;
import semi.edit.greener.notice.model.vo.NoticePageInfo;

public interface NoticeService {

	int getListCount();

	ArrayList<Notice> selectNoticeList(NoticePageInfo npi);

	ArrayList<Notice> selectFixedList();

	Notice selectNotice(String no);

	int updateNotice(Notice n);

	int insertNotice(Notice n);

	int deleteNotice(String no);
	
	

	

}
