<%@ include file="header.jsp" %>
<center>
  <h1>Delete Category</h1>
  <select id="selectCategory">
    <c:forEach var="category" items="${categories}">
      <option value="${category.id()}">${category.name()}</option>
    </c:forEach>
  </select>
  <input id="deleteCategory" class="dashboardAction" type="button" value="Delete Category" onclick="deleteCategory('${context}/api/deletecategory');" />
</center>
<%@ include file="footer.jsp" %>
