package semi.edit.greener.board.challenge.model.vo;

import java.sql.Date;

import semi.edit.greener.board.model.vo.Board;

public class Challenge extends Board{
	private String chalName;	//챌린지 이름
	private String chalPlace;	//장소
	
	public Challenge() {}

	public Challenge(int boardNo, String boardTitle, String boardContent, Date createDate, Date modifyDate,
			int boardCount, int boardLike, int boardId, String boardStatus, String boardWriter, String nickname,
			 String chalName, String chalPlace) {
		super(boardNo, boardTitle, boardContent, createDate, modifyDate, boardCount, boardLike, boardId, boardStatus,
				boardWriter, nickname);
		this.chalName = chalName;
		this.chalPlace = chalPlace;
	}

	public Challenge(String chalName, String chalPlace) {
		super();
		this.chalName = chalName;
		this.chalPlace = chalPlace;
	}

	public String getChalName() {
		return chalName;
	}

	public void setChalName(String chalName) {
		this.chalName = chalName;
	}

	public String getChalPlace() {
		return chalPlace;
	}

	public void setChalPlace(String chalPlace) {
		this.chalPlace = chalPlace;
	}

	@Override
	public String toString() {
		return super.toString() + "ChallengeBoard [chalName=" + chalName + ", chalPlace=" + chalPlace + "]";
	}
}
