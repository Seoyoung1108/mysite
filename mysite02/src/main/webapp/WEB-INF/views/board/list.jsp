<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@page import="mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%pageContext.setAttribute("newLine", "\n");%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">			
						<tr>
							<td>${vo.id }</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}">
								<c:if test="${vo.depth!=0 }">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="">${vo.title }</a>
							</td>
							<td>**이름join</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><a href="" class="del">삭제</a></td>
						</tr>
					</c:forEach>
				</table>
				
				<table class="tbl-ex">
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${authUser!=null }">
					<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>					
				</c:if>			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>