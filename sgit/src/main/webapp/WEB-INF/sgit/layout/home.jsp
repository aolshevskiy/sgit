<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<<s:layout-definition>
<s:layout-render name="/WEB-INF/sgit/layout/default.jsp" layout="home">
<s:layout-component name="body">
	<c:set var="layoutType">container</c:set>
	<%@include file="/WEB-INF/sgit/header.jsp"%>	
	<div class="container">	
	<s:layout-component name="body" />
	</div>
</s:layout-component>
</s:layout-render>
</s:layout-definition>
