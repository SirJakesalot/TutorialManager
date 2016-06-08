<%@ include file="header.jsp" %>
  <h1>File Upload</h1>
  <p>${message}</p>
  <form method="POST" action="Upload" enctype="multipart/form-data">
    Select file for upload: <input type="file" name="file" size="60" />
    <input type="submit" value="Upload" />
  </form>
<%@ include file="footer.jsp" %>
