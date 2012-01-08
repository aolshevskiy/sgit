<ul class="breadcrumb" id="breadcrumbs">
	<li>		
		<s:link beanclass="sgit.action.Log">
			<s:param name="repository">${actionBean.repository.name}</s:param>
			${actionBean.repository.name}
		</s:link>/<s:link beanclass="sgit.action.Log">
			<s:param name="repository">${actionBean.repository.name}</s:param>
			<s:param name="branch">${actionBean.branch}</s:param>
			${actionBean.branch}
		</s:link>				
	</li>
	<c:forEach items="${actionBean.breadcrumbs}" var="crumb">
		<span class="divider">/</span>
		<c:choose><c:when test="${crumb.path != null}">		
		<li>
			<s:link beanclass="sgit.action.Log">
				<s:param name="repository">${actionBean.repository.name}</s:param>
				<s:param name="branch">${actionBean.branch}</s:param>
				<s:param name="path">${crumb.path}</s:param>
				${crumb.label}
			</s:link>			
		</li>
		</c:when><c:otherwise>
		<li class="active">${crumb.label}</li>
		</c:otherwise></c:choose>
	</c:forEach>
</ul>