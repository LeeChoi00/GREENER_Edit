package semi.edit.greener.notice.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.notice.model.vo.Notice;
import semi.edit.greener.notice.model.vo.NoticePageInfo;

@Repository
public class NoticeDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("noticeMapper.getListCount");
	}

	public ArrayList<Notice> selectNoticeList(SqlSessionTemplate sqlSession, NoticePageInfo npi) {
		// 1. 건너뛸 페이지 수 => offset + 1 번 게기물부터 가져오기
		int offset = (npi.getCurrentPage() -1) * npi.getBoardLimit();
		
		// 2. Rowbounds 구하기
		RowBounds rowBounds = new RowBounds(offset, npi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("noticeMapper.selectNoticeList", null, rowBounds);
	}

	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectFixedList");
	}

	public Notice selectNotice(SqlSessionTemplate sqlSession, String no) {
		return sqlSession.selectOne("noticeMapper.selectNotice", no);
	}

	public int updateNotice(SqlSessionTemplate sqlSession, Notice n) {
		return sqlSession.update("noticeMapper.updateNotice", n);
	}

	public int insertNotice(SqlSessionTemplate sqlSession, Notice n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}

	public int deleteNotice(SqlSessionTemplate sqlSession, String no) {
		return sqlSession.delete("noticeMapper.deleteNotice", no);
	}


	

}
