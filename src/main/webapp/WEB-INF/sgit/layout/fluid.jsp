<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<<s:layout-definition>
<s:layout-render name="/WEB-INF/sgit/layout/default.jsp" layout="fluid">
<s:layout-component name="body">
	<c:set var="layoutType">container-fluid</c:set>
	<%@include file="/WEB-INF/sgit/header.jsp" %>
	<div class="container-fluid">	
	<div class="sidebar">	
	<s:layout-component name="sidebar" />
	</div>
	<div class="content">
	<s:layout-component name="body" />
	</div>
	</div>
</s:layout-component>
</s:layout-render>
</s:layout-definition>
