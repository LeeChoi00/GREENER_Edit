package semi.edit.greener.board.recipe.model.service;

import java.util.ArrayList;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.comment.model.vo.Comment;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.board.recipe.model.vo.Recipe;
import semi.edit.greener.notice.model.vo.Notice;

public interface RecipeService {

	int getListCount();

	ArrayList<Notice> selectFixedList();

	ArrayList<Recipe> selectRpList(BoardPageInfo pi);

	ArrayList<Image> selectImageList();

	int insertChallenge(Recipe rp, ArrayList<Image> imageList);

	Recipe selectRecipe(int bNo);

	ArrayList<Image> selectBoardImages(int bNo);

	ArrayList<Comment> selectBoardComments(int bNo);

}
