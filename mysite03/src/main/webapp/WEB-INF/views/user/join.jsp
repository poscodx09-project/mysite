<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
	<div id="content">
		<div id="user">
			<form:form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/join" modelAttribute="user">

				<!-- Name Field -->
				<label class="block-label" for="name"><spring:message code="user.join.label.name"/></label>
				<form:input id="name" path="name" />
				<form:errors path="name" cssClass="error" />

				<!-- Email Field -->
				<label class="block-label" for="email"><spring:message code="user.join.label.email"/></label>
				<form:input id="email" path="email" />
				<form:errors path="email" cssClass="error" />
				<input type="button" value="<spring:message code='user.join.label.email.check'/>" />

				<!-- Password Field -->
				<label class="block-label"><spring:message code="user.join.label.password"/></label>
				<form:password path="password" />
				<form:errors path="password" cssClass="error" />

				<!-- Gender Field -->
				<fieldset>
					<legend><spring:message code="user.join.label.gender"/></legend>
					<label><spring:message code="user.join.label.gender.female"/></label>
					<form:radiobutton path="gender" value="female" />
					<label><spring:message code="user.join.label.gender.male"/></label>
					<form:radiobutton path="gender" value="male" />
					<form:errors path="gender" cssClass="error" />
				</fieldset>

				<!-- Terms Agreement -->
				<fieldset>
					<legend><spring:message code="user.join.label.terms"/></legend>
					<form:checkbox id="agree-prov" path="agreeProv" value="true" />
					<label><spring:message code="user.join.label.terms.message"/></label>
					<form:errors path="agreeProv" cssClass="error" />
				</fieldset>

				<!-- Submit Button -->
				<input type="submit" value="<spring:message code='user.join.button.signup'/>" />
			</form:form>
		</div>
	</div>
	<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
	<c:import url="/WEB-INF/views/includes/footer.jsp"/>
</div>
</body>
</html>
