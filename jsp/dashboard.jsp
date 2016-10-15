<!-- TODO
rm redundancy id=info and class=info
-->
<%@ include file="header.jsp" %>

<center><h1>Admin Dashboard</h1></center>
<!-- display message -->
<!--
<div onclick="addInfoMsg();">Add Info Message</div>
<div onclick="addErrorMsg();">Add Error Message</div>
<div onclick="addWarningMsg();">Add Warning Message</div>
<div onclick="addSuccessMsg();">Add Success Message</div>
<div id="msgs"></div>
-->

<!-- selection of all tutorials in the database -->
<center>
  <select id="selectTutorial" onchange="getTutorial(this.value, '${context}/tutorial_select');">
    <option value="-2"></option>
    <option value="-1">&ltNEW TUTORIAL&gt</option>
      <c:forEach var="tutorial" items="${tutorials}">
        <option value="${tutorial.id()}">${tutorial.title()}</option>
      </c:forEach>
  </select>
</center>

<!-- table with a tutorial's modifiable fields -->
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
    <td><input id="title" type="text" disabled/></td>
  </tr>
  <tr>
    <th>Content</th>
    <td><textarea rows="25" id="content" disabled></textarea></td>
  </tr>
  <tr>
    <td><input id="deleteTutorial" type="button" value="Delete Tutorial" onclick="deleteTutorial();" disabled/></td>
    <td><input id="updateTutorial" type="button" value="Update Tutorial" class="submit" onclick="updateTutorialInfo('${context}/tutorial_update');" disabled/></td>
  </tr>
</table>
<script>
    function deleteTutorial() {
		var dashboard_delete_url = "${context}/tutorial_delete"
        var idval = encodeURIComponent($("#selectTutorial").val());
        var params = "id=" + idval;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                console.log(xmlhttp.responseText);
                var arry = JSON.parse(xmlhttp.responseText);
                removeTutorialSelect(arry);
            }
        };
        xmlhttp.open("POST", dashboard_delete_url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(params);
    }
</script>
<%@ include file="footer.jsp" %>
