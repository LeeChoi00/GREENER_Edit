package semi.edit.greener.board.recipe.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import semi.edit.greener.board.model.vo.BoardPageInfo;
import semi.edit.greener.board.model.vo.Image;
import semi.edit.greener.board.recipe.model.vo.Recipe;
import semi.edit.greener.notice.model.vo.Notice;

@Repository
public class RecipeDAO {
	// Recipe 게시판의 총 게시물 수 구하기
	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("recipeMapper.getListCount");
	}
	
	// 상단 고정 게시물 가져오기
	public ArrayList<Notice> selectFixedList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectFixedList");
	}
	
	// 게시판으로 이동하기
	public ArrayList<Recipe> selectRpList(SqlSessionTemplate sqlSession, BoardPageInfo pi) {
		// 1. 건너뛸 페이지 수
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		
		// 2. RowBounds 구하기
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("recipeMapper.selectRpList", null, rowBounds);
	}

	public ArrayList<Image> selectImageList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("recipeMapper.selectImageList");
	}
	
	// 작성한 게시물 저장
	// 1. board, recipe_bd에 사진 외 정보 저장
	public int insertRecipe(SqlSessionTemplate sqlSession, Recipe rp) {
		int bResult = sqlSession.insert("recipeMapper.insertBoard", rp);
		int rppResult = 0;
		if(bResult >0) {
			rppResult = sqlSession.insert("recipeMapper.insertRecipe", rp);
		}
		
		
		return rppResult;
	}
	
	// 2. image에 recipe 게시물 사진 저장
	public int insertRecipe(SqlSessionTemplate sqlSession, ArrayList<Image> imageList) {
		int sum = 0;
		
		for(int i = 0; i < imageList.size(); i++) {
			int result = sqlSession.insert("recipeMapper.insertImage", imageList.get(i));
			sum = sum + result;
			System.out.println("중간 sum값 -" + i + "번째 :" +  sum);
		}		
		
		return sum;
	}
	





}

























