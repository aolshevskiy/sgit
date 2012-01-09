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
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-9918641-4']);
  _gaq.push(['_trackPageview']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</head>
<body id="${layout}-layout">
<s:layout-component name="body" />
<script type="text/javascript" src="${contextPath}/static/sgit/date.extensions.js"></script>
<script type="text/javascript" src="${contextPath}/static/sgit/script.js"></script>
</body>
</html>
</s:layout-definition>