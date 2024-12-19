<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="mysite.vo.UserVo"%>
<%@ page import="mysite.dao.UserDao" %>
<%
	UserVo vo = (UserVo)session.getAttribute("authUser");
	UserVo vo2 = new UserDao().findById(vo.getId());
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="<%=request.getContextPath() %>/user">
					<input type='hidden' name="a" value="update">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="<%= vo2.getName()%>">

					<label class="block-label" for="email">이메일</label>
					<h4><%= vo2.getEmail() %></h4>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">

					<fieldset>
						<legend>성별</legend>
						<%
							// gender 값을 가져옴. 세션이나 request 속성에서 가져올 수 있음.
							String gender = vo2.getGender(); // 예: request에 저장된 gender 값
						%>
						<label>여</label>
						<input type="radio" name="gender" value="female" <%= "female".equals(gender) ? "checked" : "" %>>

						<label>남</label>
						<input type="radio" name="gender" value="male" <%= "male".equals(gender) ? "checked" : "" %>>
					</fieldset>
					
					<input type="submit" value="수정하기">		
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>