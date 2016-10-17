<%@ include file="header.jsp" %>
  <div class="page">
  <div class="content">
    <h1>${tutorial.title()}</h1>
    ${fn:replace(tutorial.content(), "\\n","")}
    <hr>
    <div class="fb-comments" data-href="${fbdata_url}" data-width="700"></div>
  </div>
  </div>
  <script>
    loadFB();
  </script>
<%@ include file="footer.jsp" %>
