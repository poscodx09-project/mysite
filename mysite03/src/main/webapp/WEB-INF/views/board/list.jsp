<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
	<div id="content">
		<div id="board">
			<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
				<input type="text" id="kwd" name="kwd" value="${kwd}" placeholder="검색어를 입력하세요">
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
				<c:set var="count" value="${fn:length(boardList) }" />
				<c:forEach items="${boardList}" var="vo" varStatus="status">
					<tr>
						<td>${vo.id}</td>
						<td style="text-align:left; padding-left:${vo.depth * 20}px">
							<!-- 답글일 경우 이미지 추가 -->
							<c:if test="${vo.depth > 0}">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png" alt="답글 아이콘">
							</c:if>
							<a href="${pageContext.request.contextPath }/board/view/${vo.id}">${vo.title}</a>
						</td>
						<td>${vo.writer}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<c:if test="${sessionScope.authUser != null && sessionScope.authUser.id == vo.userId}">
							<td><a href="${pageContext.request.contextPath }/board/delete/${vo.id}" class="del"><img src="${pageContext.request.contextPath }/assets/images/recycle.png" alt="삭제 아이콘"></a></td>
						</c:if>
					</tr>
				</c:forEach>


			</table>

			<!-- pager 추가 -->
			<div class="pager">
				<ul>
					<!-- 이전 페이지 -->
					<c:if test="${currentPage > 1}">
						<li><a href="${pageContext.request.contextPath }/board?page=${currentPage - 1}">◀</a></li>
					</c:if>

					<!-- 페이지 번호 -->
					<c:forEach var="page" begin="${beginPage}" end="${endPage}">
						<c:choose>
							<c:when test="${page == currentPage}">
								<li class="selected">${page}</li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board?page=${page}">${page}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<!-- 다음 페이지 -->
					<c:if test="${currentPage < totalPages}">
						<li><a href="${pageContext.request.contextPath }/board?page=${currentPage + 1}">▶</a></li>
					</c:if>
				</ul>
			</div>

			<!-- pager 추가 -->

			<div class="bottom">
				<a href="${pageContext.request.contextPath }/board/add?type=original" id="new-book">글쓰기</a>
			</div>
		</div>
	</div>
	<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
	<c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>