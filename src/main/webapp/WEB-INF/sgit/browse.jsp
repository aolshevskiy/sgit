<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/sgit/layout/fluid.jsp" title="Browse">
<s:layout-component name="sidebar">
	<ul class="unstyled">
		<c:forEach items="${actionBean.entries}" var="entry">
		  <c:set target="${actionBean}" property="entry" value="${entry}" />
			<li>
				<s:link beanclass="sgit.action.Browse">
					<s:param name="repository">${actionBean.repository.name}</s:param>
					<c:if test="${actionBean.absolutePath != ''}">					
						<s:param name="path">${actionBean.absolutePath}</s:param>
					</c:if>										
					${entry.name}<c:if test="${entry.isDirectory && !entry.isParent}">/</c:if>
				</s:link>				
			</li>
		</c:forEach>
	</ul>
</s:layout-component>
<s:layout-component name="body">
	<h1>Body - ${actionBean.isSubtree} - ${actionBean.basename}</h1>	
</s:layout-component>
</s:layout-render>