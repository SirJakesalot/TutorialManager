<%@ include file="header.jsp" %>
<h1>Images</h1>
<div class="images_content">
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
	    <input class="update" type="submit" value="Update" onclick="updateImage(this, '${context}/api/updateimage');" />
	    <input class="delete" type="submit" value="Delete" onclick="deleteImage(this, '${context}/api/deleteimage');"/>
	  </div>
    </div>
  </c:forEach>
  <div class="upload">
    <h:form method="POST" action="file_upload" enctype="multipart/form-data">
      <input id="files" type="file" multiple />
	  <div id="uploadLabel">
        <label class="uploadBtn" for="files">+</label>
	  </div>
	</h:form>
	
	<input class="submit" type="submit" value="Upload" onclick="fileUpload('${context}/api/fileupload');"/>
  </div>
</div>
<script>
  $("input:text").bind("keyup change", function () {
    console.log("Should change");
    $(this).css({'border' : '2px solid red'});
	$(this).siblings(".actions").children(".update").prop("disabled", false);
  });
  
</script>

<%@ include file="footer.jsp" %>
