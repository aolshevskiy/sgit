<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<s:layout-definition>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Simple Git - ${title}</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/static/sgit/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/static/sgit/style.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/static/sgit/highlight.css" />
<script type="text/javascript" src="${contextPath}/static/sgit/jquery-1.7.1.min.js"></script>
<style> 
${appendStyle}
</style>
</head>
<body id="${layout}-layout">
<s:layout-component name="body" />
<script type="text/javascript" src="${contextPath}/static/sgit/date.extensions.js"></script>
<script type="text/javascript" src="${contextPath}/static/sgit/script.js"></script>
</body>
</html>
</s:layout-definition>