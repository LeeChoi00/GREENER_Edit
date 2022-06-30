package semi.edit.greener.board.comment.model.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.board.comment.model.vo.Comment;

@Repository
public class CommentDAO {
	
	// 댓글 작성 등록 후 댓글 리스트 가져오기
	public ArrayList<Comment> insertComment(SqlSessionTemplate sqlSession, Comment cm) {
		int insertResult = sqlSession.insert("commentMapper.insertComment", cm);
		ArrayList<Comment> comList = null;
		if(insertResult >0) {
			comList = (ArrayList)sqlSession.selectList("commentMapper.selectCommentList", cm);
		}
		return comList;
	}
	


}
