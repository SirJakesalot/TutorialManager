<%@ include file="header.jsp" %>
  <h1>Tutorials</h1>
  <table class="tutorial_table">
    <c:forEach var="tutorial" items="${tutorials}" >
      <tr>
        <td><a href="tutorial?tutorial_id=${tutorial.id()}">${tutorial.title()}</a></td>
      </tr>
    </c:forEach>
  </table>
<%@ include file="footer.jsp" %>
