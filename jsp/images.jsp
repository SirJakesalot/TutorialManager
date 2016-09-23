<%@ include file="header.jsp" %>
<div class="content">
  <h1>Images</h1>
  <p>${message}</p>

  <c:forEach var="fileName" items="${fileNames}">
    <c:set var="filePath" value="${context}/${uploadDir}/${fileName}"/>
    <div class="figure">
      <div class="image">
        <a href="${uploadDir}/${fileName}">
          <img src="${uploadDir}/${fileName}" alt="${context}/img/NotAvailable.jpg"/>
	    </a>
      </div>
	  <input class="fileName" id="${fileName}" type="text" value="${fileName}" />
	  <div class="actions">
	    <input class="update" type="submit" value="Update" onclick="updateImage(this, '${context}/images_update');" />
	    <input class="delete" type="submit" value="Delete" onclick="deleteImage(this, '${context}/images_delete');"/>
	  </div>
    </div>
  </c:forEach>
  <div class="figure">
    <div class="upload">
      <input id="uploadFile" type="file" multiple />
      <label for="uploadFile"></label>
	  <input id="upload" type="submit" value="Upload"/>
    </div>
  </div>
  <!--<div class="figure">
    <div class="image">
      <input id="uploadFile" type="file" multiple />
      <label for="uploadFile"><img src="${context}/img/FileUpload.png" /></label>
      <input id="upload" type="submit" value="Upload"/>
    </div>
  </div> -->
</div>
<script>
  $("input:text").bind("keyup change", function () {
    console.log("Should change");
    $(this).css({'border' : '2px solid red'});
	$(this).siblings(".actions").children(".update").prop("disabled", false);
  });
</script>

<%@ include file="footer.jsp" %>
