package semi.edit.greener.board.recipe.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.board.recipe.model.dao.RecipeDAO;
import semi.edit.greener.board.recipe.model.vo.Recipe;
import semi.edit.greener.notice.model.vo.Notice;

@Service
public class RecipeServiceImpl implements RecipeService{
	@Autowired
	private RecipeDAO rpDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getListCount() {
		return rpDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectFixedList() {
		return rpDAO.selectFixedList(sqlSession);
	}

	@Override
	public ArrayList<Recipe> selectRpList(BoardPageInfo pi) {
		return rpDAO.selectRpList(sqlSession, pi);
	}

	@Override
	public ArrayList<Image> selectImageList() {
		return rpDAO.selectImageList(sqlSession);
	}

	@Override
	public int insertChallenge(Recipe rp, ArrayList<Image> imageList) {
		int rpResult = rpDAO.insertRecipe(sqlSession, rp);
		int imgResult = 0;
		if(rpResult >0) {
			imgResult = rpDAO.insertRecipe(sqlSession, imageList);
		}
		return imgResult;
	}
}
