<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp">
<s:layout-component name="body">
<ul class="unstyled">
	<c:forEach items="${actionBean.log}" var="commit">
	<li>${commit}</li>      
  </c:forEach>
</ul>	
</s:layout-component>
</s:layout-render>