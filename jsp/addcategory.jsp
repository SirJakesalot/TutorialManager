<%@ include file="header.jsp" %>
<center>
  <h1>Add Category</h1>
  <input id="name" type="text" />
  <input id="addCategory" class="dashboardAction" type="button" value="Add Category" onclick="addCategory('${context}/api/addcategory');" />
</center>
<%@ include file="footer.jsp" %>
