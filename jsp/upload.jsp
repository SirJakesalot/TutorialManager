<%@ include file="header.jsp" %>
  <h1>File Upload</h1>
  <p>${message}</p>
  <div style="text-align: center;width: 15%;">
  <form method="POST" action="file_upload" enctype="multipart/form-data">
    <input type="file" name="file" size="60" />
    <input type="submit" value="Upload"/>
  </form>
  </div>
<%@ include file="footer.jsp" %>
