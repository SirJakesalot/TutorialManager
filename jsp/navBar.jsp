<div class="navBar">
  <ul class="nav">
    <li><a href="${context}/main">Jake's Tutorials</a></li>
    <li>
      <a href="#">Categories</a>
      <div>
        <c:forEach var="category" items="${categories}">
          <div class="nav-column">
            <h3>${category.name()}</h3>
            <ul>
              <c:forEach var="tutorial" items="${category.tutorials()}">
                <li><a href="${context}/tutorial?id=${tutorial.id()}">${tutorial.title()}</a></li>
              </c:forEach>
            </ul>
          </div>
        </c:forEach>
      </div>
    </li>
    <li><a href="${context}/images">Images</a></li>
    <li><a href="${context}/dashboard">Dashboard</a></li>
  </ul>
</div>

