<%@include file="/WEB-INF/sgit/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/sgit/layout/browse.jsp">
<s:layout-component name="body">
<ul class="unstyled" id="log">
	<c:forEach items="${actionBean.log}" var="commit">	
	<li>
		<a href="">${fn:substring(commit.name,0,8)}</a> <span class="message">${commit.shortMessage}</span> <span class="time">${commit.commitTime}</span>				
	</li>	      
  </c:forEach>
</ul>
<script type="text/javascript">
	$(function(){timeConversion();});
</script>	
</s:layout-component>
</s:layout-render>