<div class="navBar">
  <ul class="nav">
    <li><a href="${context}/main">Jake's Tutorials</a></li>
    <li>
      <a href="#">Categories</a>
      <div>
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
      <a href="${context}/dashboard">Dashboard</a>
      <div>
        <h3>Functions</h3>
        <ul>
          <li><a href="${context}/addtutorial">Add Tutorial</a></li>
          <li><a href="${context}/updatetutorial">Update Tutorial</a></li>
          <li><a href="${context}/deletetutorial">Del Tutorial</a></li>
          <li><a href="${context}/addcategory">Add Category</a></li>
          <li><a href="${context}/deletecategory">Del Category</a></li>
        </ul>
      </div>
    </li>
  </ul>
</div>

