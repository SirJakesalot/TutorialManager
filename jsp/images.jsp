<%@ include file="header.jsp" %>
  <h1>Images</h1>
  <p>${message}</p>
  <div class="photo_gallery">
  <c:forEach var="appPath" items="${appPaths}">
    <c:set var="filePath" value="${context}/${appPath}"/>
    <figure "tutorial_figure">
      <a href="${appPath}"><img src="${appPath}"/></a>
        <figcaption><input type="text" value="${appPath}" /></figcaption>
    </figure>
  </c:forEach>
  <div>
  <script>
        $("input:text").bind("keyup change", function () {
            console.log("Should change");
            $(this).css({'border' : '2px solid red'});
        });
  </script>
<%@ include file="footer.jsp" %>
