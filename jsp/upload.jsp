<%@ include file="header.jsp" %>
  <h1>File Upload</h1>
  <p>${message}</p>
  <div class="upload">
  <form method="POST" action="file_upload" enctype="multipart/form-data">
    <input type="file" name="file" multiple/>
    <input type="submit" value="Upload"/>
  </form>
  </div>
<%@ include file="footer.jsp" %>
