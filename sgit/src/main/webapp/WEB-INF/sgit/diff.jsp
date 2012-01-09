<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp" appendStyle="${actionBean.styleDefs}">
	<s:layout-component name="body">
	${actionBean.diff}
	</s:layout-component>
</s:layout-render>