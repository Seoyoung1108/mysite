<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.contents, newLine, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<sec:authorize access="isAuthenticated()">
				    	<sec:authentication property="principal" var="authUser"/>		    
						<a href="${pageContext.request.contextPath }/board/reply/${vo.id}">답글</a>						
					</sec:authorize>
					<a href="${pageContext.request.contextPath }/board">글목록</a>		
					<!-- 고치기~ -->
					<c:if test="${authUser.id == vo.userId }">
						<a href="${pageContext.request.contextPath }/board/modify/${vo.id}">글수정</a>
					</c:if>
					
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>