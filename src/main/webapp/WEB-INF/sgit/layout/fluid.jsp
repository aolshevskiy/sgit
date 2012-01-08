<%@include file="/WEB-INF/sgit/taglibs.jsp" %>
<s:layout-definition>
<s:layout-render name="/WEB-INF/sgit/layout/default.jsp" layout="fluid">
<s:layout-component name="body">
	<c:set var="layoutType">container-fluid</c:set>	
	<c:set var="navigation">
	  <ul class="nav">
	  	<li class="dropdown">
	  		<a class="dropdown-toggle" href="#">${actionBean.branch}</a>
	  		<ul class="dropdown-menu">
	  			<c:forEach items="${actionBean.branchList}" var="branch">
	  				<li>
	  					<s:link beanclass="sgit.action.Log">
	  						<s:param name="repository">${actionBean.repository.name}</s:param>
	  						<s:param name="branch">${branch}</s:param>
	  						${branch}
	  					</s:link>
	  				</li>
	  			</c:forEach>
	  		</ul>
	  	</li>
	  </ul>
	</c:set>
	<%@include file="/WEB-INF/sgit/header.jsp" %>			
	<%@include file="/WEB-INF/sgit/breadcrumbs.jsp" %>
	<div class="container-fluid">	
	<div class="sidebar">	
	<s:layout-component name="sidebar" />
	</div>
	<div class="content">
	<s:layout-component name="body" />
	</div>
	</div>
	<script type="text/javascript">$(function(){$("#topbar").dropdown();});</script>
	<script type="text/javascript" src="${contextPath}/static/sgit/bootstrap-dropdown.js"></script>
</s:layout-component>
</s:layout-render>
</s:layout-definition>
