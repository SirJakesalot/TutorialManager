<%@ include file="header.jsp" %>
  <h1>Images</h1>
  <div class="photo_gallery">
  <c:forEach var="filename" items="${filenames}">
    <figure "tutorial_figure">
      <a href="${context}/img/${filename}"><img src="${context}/img/${filename}"/></a>
        <figcaption><input type="text" value="${filename}" id="${context}/img/${filename}" /></figcaption>
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
