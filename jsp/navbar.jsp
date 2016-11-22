<div class="navBar">
  <ul class="nav">
    <li><a href="${context}/main">Jake's Tutorials</a></li>
    <li>
      <a href="#">Categories</a>
      <div class="megaMenu">
        <c:forEach var="category" items="${categories}">
            <h3>${category.name()}</h3>
            <ul>
              <c:forEach var="tutorial" items="${category.tutorials()}">
                <li><a href="${context}/tutorial?id=${tutorial.id()}">${tutorial.title()}</a></li>
              </c:forEach>
            </ul>
        </c:forEach>
      </div>
    </li>
    <li><a href="${context}/images">Images</a></li>
    <li>
      <a href="#">Dashboard</a>
      <div>
        <h3>Tutorials</h3>
        <ul>
          <li><a href="${context}/addtutorial">Add</a></li>
          <li><a href="${context}/updatetutorial">Update</a></li>
          <li><a href="${context}/deletetutorial">Delete</a></li>
        </ul>
        <h3>Categories</h3>
        <ul>
          <li><a href="${context}/addcategory">Add</a></li>
          <li><a href="${context}/deletecategory">Delete</a></li>
        </ul>
      </div>
    </li>
  </ul>
</div>

