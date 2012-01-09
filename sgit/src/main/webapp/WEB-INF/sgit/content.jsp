<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp" appendStyle="${actionBean.styleDefs}">
<s:layout-component name="body">
<div id="content-header">
<s:link beanclass="sgit.action.Log">
	<s:param name="repository">${actionBean.repository.name}</s:param>
	<s:param name="branch">${actionBean.branch}</s:param>
	<s:param name="path">${actionBean.path}</s:param>
	View log	
</s:link>
</div>
${actionBean.content}
</s:layout-component>
</s:layout-render>