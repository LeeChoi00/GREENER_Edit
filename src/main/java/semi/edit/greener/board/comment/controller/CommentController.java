package semi.edit.greener.board.comment.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import semi.edit.greener.board.comment.model.service.CommentService;
import semi.edit.greener.board.comment.model.vo.Comment;

@Controller
public class CommentController {
	
	@Autowired 
	CommentService cmService;
	
	
	@RequestMapping("insertComment.cm")
	public void insertComment(@ModelAttribute Comment cm, Model model, HttpServletResponse response) {
		
		// 댓글 저장하면서 동시에 댓글 리스트 가져오기(ajax)
		ArrayList<Comment> comList = cmService.insertComment(cm);

		
		// 가져온 comList recipeDetail로 보내기
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(comList, response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
