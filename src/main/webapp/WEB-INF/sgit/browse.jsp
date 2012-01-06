<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/sgit/layout/fluid.jsp" title="Browse">
<s:layout-component name="sidebar">
	<ul class="unstyled">
		<c:forEach items="${actionBean.paths}" var="entry">
			<li>
				<s:link beanclass="sgit.action.Browse">
					<s:param name="repository">${actionBean.repository.name}</s:param>
					<c:set var="path" value="${actionBean.path}/${entry.path}" />
					<c:if test="${entry.isParent}">
						<c:set var="path" value="${actionBean.parentPath}" />
					</c:if>
					<c:if test="${path != '/'}">					
						<s:param name="path">${path}</s:param>
					</c:if>					
					${entry.path}<c:if test="${entry.isDirectory && !entry.isParent}">/</c:if>
				</s:link>				
			</li>
		</c:forEach>
	</ul>
</s:layout-component>
<s:layout-component name="body">
	<h1>Body</h1>	
</s:layout-component>
</s:layout-render>