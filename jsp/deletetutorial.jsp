<%@ include file="header.jsp" %>
<center><h1>Delete Tutorial</h1></center>
<center>
  <select id="selectTutorial" onchange="getTutorial(this.value, '${context}/api/selecttutorial');">
    <option value="-1"></option>
      <c:forEach var="tutorial" items="${tutorials}">
        <option value="${tutorial.id()}">${tutorial.title()}</option>
      </c:forEach>
  </select>
</center>
    <input id="deleteTutorial" class="dashboardAction" type="button" value="Delete Tutorial" onclick="deleteTutorial('${context}/api/deletetutorial');" disabled />
  </tr>
</table>

<%@ include file="footer.jsp" %>
