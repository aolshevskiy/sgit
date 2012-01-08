<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp">
<s:layout-component name="body">
<ul class="unstyled" id="log">
	<c:forEach items="${actionBean.log}" var="commit">
	<c:set target="${actionBean}" property="commit" value="${commit}" />	
	<li>
		<s:link beanclass="sgit.action.Diff">
			<s:param name="repository">${actionBean.repository.name}</s:param>
			<s:param name="branch">${actionBean.branch}</s:param>			
			<s:param name="path">${actionBean.path}</s:param>			
			<s:param name="id">${actionBean.abbrev}</s:param>
			${actionBean.abbrev}
		</s:link> 
		<span class="message">${commit.shortMessage}</span> <span class="time">${commit.commitTime}</span>				
	</li>	      
  </c:forEach>
</ul>
<script type="text/javascript">
	$(function(){timeConversion();});
</script>	
</s:layout-component>
</s:layout-render>