<%@ include file="header.jsp" %>
<center><h1>Add Tutorial</h1></center>

<!-- table with a tutorials modifiable fields -->
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
    <td><div id="editorPage"><div id="editor"></div></div></td>
  </tr>
  <tr>
    <td colspan="2"><input id="addTutorial" class="dashboardAction" type="button" value="Add Tutorial" onclick="addTutorial('${context}/api/addtutorial');" /></td>
  </tr>
</table>

<script>
  $(document).ready(function() {
    var editor = ace.edit("editor");
    editor.getSession().setMode("ace/mode/html");
    $( "#editorPage" ).resizable({
      resize: function( event, ui ) {
        editor.resize();
      }
    });
  });
</script>

<%@ include file="footer.jsp" %>
