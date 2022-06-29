package semi.edit.greener.board.recipe.model.vo;

import java.sql.Date;

import semi.edit.greener.board.model.vo.Board;

public class Recipe extends Board{
	private int boardNo; 
	private String menuName; //레시피 메뉴명
	private String ingredient; //재료명
	
	
	public Recipe() {}


	public Recipe(int boardNo, String boardTitle, String boardContent, Date createDate, Date modifyDate, int boardCount,
			int boardLike, int boardId, String boardStatus, String boardWriter, String nickName,
			 String menuName, String ingredient) {
		super(boardNo, boardTitle, boardContent, createDate, modifyDate, boardCount, boardLike, boardId, boardStatus,
				boardWriter, nickName);
		this.menuName = menuName;
		this.ingredient = ingredient;
	}

	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public String getIngredient() {
		return ingredient;
	}


	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}


	@Override
	public String toString() {
		return "Recipe [boardNo=" + boardNo + ", menuName=" + menuName + ", ingredient=" + ingredient
				+ ", getNickName()=" + getNickName() + ", getBoardNo()=" + getBoardNo() + ", getBoardTitle()="
				+ getBoardTitle() + ", getBoardContent()=" + getBoardContent() + ", getCreateDate()=" + getCreateDate()
				+ ", getModifyDate()=" + getModifyDate() + ", getBoardCount()=" + getBoardCount() + ", getBoardLike()="
				+ getBoardLike() + ", getBoardId()=" + getBoardId() + ", getBoardStatus()=" + getBoardStatus()
				+ ", getBoardWriter()=" + getBoardWriter() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}



}
