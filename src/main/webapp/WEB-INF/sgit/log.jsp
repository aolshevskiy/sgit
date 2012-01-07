<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp">
<s:layout-component name="body">
<ul class="unstyled" id="log">
	<c:forEach items="${actionBean.log}" var="commit">
	<c:set target="${actionBean}" property="commit" value="${commit}" />
	<li><a href="">${actionBean.abbreviation}</a> <span>${commit.shortMessage}</span></li>	      
  </c:forEach>
</ul>	
</s:layout-component>
</s:layout-render>