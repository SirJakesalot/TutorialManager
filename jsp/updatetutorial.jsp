<%@ include file="header.jsp" %>
<center><h1>Update Tutorial</h1></center>

<!-- selection of all tutorials in the database -->
<center>
  <select id="selectTutorial" onchange="getTutorial(this.value, '${context}/api/selecttutorial');">
    <option value="-1"></option>
      <c:forEach var="tutorial" items="${tutorials}">
        <option value="${tutorial.id()}">${tutorial.title()}</option>
      </c:forEach>
  </select>
</center>

<!-- table with a tutorials modifiable fields -->
<table class="tableTutorial">
  <tr>
    <th>Id</th>
    <td><div id="id"></div></td>
  </tr>
  <tr>
    <th>Category</th>
    <td>
	  <div id="tutorialCategories">
	    <c:forEach var="category" items="${categories}">
		  <input id="cat_${category.id()}" type="checkbox" value="${category.id()}" disabled>${category.name()}</input>
	    </c:forEach>
	  </div>
    </td>
  </tr>
  <tr>
    <th>Title</th>
    <td><input id="title" type="text" disabled /></td>
  </tr>
  <tr>
    <th>Content</th>
    <!--<td><textarea rows="25" disabled><pre id="content" class="prettyprint lang-html"></pre></textarea></td>-->
    <td><div id="editorPage"><div id="editor"></div></div></td>
  </tr>
  <tr>
    <td colspan="2"><input class="dashboardAction" type="button" value="Update Tutorial" onclick="updateTutorial('${context}/api/updatetutorial');"/></td>
  </tr>
</table>

<script>
  $(document).ready(function() {
    var editor = ace.edit("editor");
    editor.getSession().setMode("ace/mode/html");
    editor.$blockScrolling = Infinity;
    $( "#editorPage" ).resizable({
      resize: function( event, ui ) {
        editor.resize();
      }
    });
  });
</script>

<%@ include file="footer.jsp" %>
