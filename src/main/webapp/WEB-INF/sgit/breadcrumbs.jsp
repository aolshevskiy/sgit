<ul class="breadcrumb" id="breadcrumbs">
	<li>
		<c:choose><c:when test="${actionBean.path != ''}">
			<s:link beanclass="sgit.action.Log">
				<s:param name="repository">${actionBean.repository.name}</s:param>
				${actionBean.repository.name}
			</s:link>
			<span class="divider">/</span>
		</c:when><c:otherwise>
			<li class="active">${actionBean.repository.name}</li>	
		</c:otherwise></c:choose>
	</li>
	<c:forEach items="${actionBean.breadcrumbs}" var="crumb">
		<c:choose><c:when test="${crumb.path != null}">
		<li>
			<s:link beanclass="sgit.action.Log">
				<s:param name="repository">${actionBean.repository.name}</s:param>
				<s:param name="path">${crumb.path}</s:param>
				${crumb.label}
			</s:link>
			<span class="divider">/</span>
		</li>
		</c:when><c:otherwise>
		<li class="active">${crumb.label}</li>
		</c:otherwise></c:choose>
	</c:forEach>
</ul>