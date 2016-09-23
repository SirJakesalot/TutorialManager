<div class="sidebar">
  <h2>Admin</h2>
  <div id="adminContainer">
    <ul id="adminList">
      <li><a href="${context}/main">Main</a></li>
      <li><a href="${context}/images">Images</a></li>
      <li><a href="${context}/dashboard">Dashboard</a></li>
      <li><a href="${context}/upload">Upload</a></li>
    </ul>
  </div>
  <h2>Tutorials</h2>
  <div id="catContainer">
    <ul id="catList">
      <c:forEach var="category" items="${categories}">
        <li class="collapsed" onclick="expandCategory(this);" id="${category.id()}">${category.name()}
          <ul style="display: none">
            <c:forEach var="tutorial" items="${category.tutorials()}">
              <li><a href="${context}/tutorial?id=${tutorial.id()}">${tutorial.title()}</a></li>
            </c:forEach>
          </ul>
        </li>
      </c:forEach>
    </ul>
  </div>
</div>
<button class="expandSidebar" onclick="expandSidebar(this);"><img src="${context}/css/img/menu.png"></button>
