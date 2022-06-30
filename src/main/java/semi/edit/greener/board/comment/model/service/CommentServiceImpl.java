package semi.edit.greener.board.comment.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.comment.model.dao.CommentDAO;
import semi.edit.greener.board.comment.model.vo.Comment;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDAO cmDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public ArrayList<Comment> insertComment(Comment cm) {
		return cmDAO.insertComment(sqlSession, cm);
	}



}
