<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/sgit/layout/home.jsp" title="Repositories">
<s:layout-component name="body">
	<ul class="unstyled" id="repositories">
		<c:forEach items="${actionBean.repos}" var="repo">		 	
			<li>
				<s:link beanclass="sgit.action.Log">
					<s:param name="repository" value="${repo.name}" />
					${repo.name}					
				</s:link> <br />
				${repo.lastCommit.shortMessage}
				<c:choose><c:when test="${repo.lastCommit != null}">
					<em class="time">${repo.lastCommit.commitTime}</em>
				</c:when><c:otherwise>
					<em>No commits yet</em>					
				</c:otherwise></c:choose>					
			</li>
		</c:forEach>
	</ul>
	<script type="text/javascript">
	  $(function(){timeConversion();});
	</script>	
</s:layout-component>
</s:layout-render>