<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Repositories list</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/static/sgit/style.css" />
</head>
<body>
<ul>
	<c:forEach items="${actionBean.repos}" var="repo">
		<li>${repo}</li>
	</c:forEach>
</ul>
</body>
</html>
