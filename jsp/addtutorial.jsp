<%@ include file="header.jsp" %>
<center><h1>Add Tutorial</h1></center>

<!-- table with a tutorial's modifiable fields -->
<table class="tableTutorial">
  <tr>
    <th>Category</th>
    <td>
	  <div id="categories">
	    <c:forEach var="category" items="${categories}">
		  <input id="cat_${category.id()}" type="checkbox" value="${category.id()}">${category.name()}</input>
	    </c:forEach>
	  </div>
    </td>
  </tr>
  <tr>
    <th>Title</th>
    <td><input id="title" type="text"/></td>
  </tr>
  <tr>
    <th>Content</th>
    <td><textarea id="content" rows="25"></textarea></td>
  </tr>
  <tr>
    <td colspan="2"><input id="addTutorial" class="dashboardAction" type="button" value="Add Tutorial" onclick="addTutorial('${context}/api/addtutorial');" /></td>
  </tr>
</table>
<%@ include file="footer.jsp" %>
