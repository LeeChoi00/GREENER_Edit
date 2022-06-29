<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="semi.edit.greener.notice.model.vo.Notice"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file = "/WEB-INF/views/common/Navbar.jsp" %>

	<div class="outer">
		<br>
		<h2 align="center">공지사항 수정</h2>
		<div class="tableArea">
			<form action="<%= request.getContextPath() %>/update.no" method="post">
				<table align="center">
					<tr>
						<th>제목</th>
						
						<td colspan="3">
							<input type="text" name ="noticeTitle" size="50" value="${notice.noticeTitle}" >
							<input type="hidden" name ="noticeNo" value="${notice.noticeNo}">
						</td>				
					</tr>
					<tr>
						<th>작성자</th>
						<td>${notice.nickName }</td>
						<th>작성일</th>
						<td><input type="date" name ="noticeDate" value='${notice.noticeDate}'></td>
					</tr>
					<tr>
						<th>상단 고정 여부</th>
						<td>
						<select id="fix" name="fix">
							<option value="N">게시판 상단에 고정하지 않기</option>
							<option value="Y">게시판 상단에 고정하기</option>
						</select>
						</td>
					</tr>
					<tr>
						<th>내용</th>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name ="noticeContent" cols="60" rows="15" style="resize:none;">${notice.noticeContent }</textarea>
						</td>
					</tr>
				</table>
				
				<br>
				
				<div align="center">
					<input type="submit" id="updateNoBtn" value="저장">
					<input type="button" onclick="location.href='javascript:history.go(-1);'" id="cancelBtn" value="취소">
				</div>
			</form>
		</div>
	</div>

<%@ include file = "/WEB-INF/views/common/FooterTopBtn.jsp" %>
</body>
</html>