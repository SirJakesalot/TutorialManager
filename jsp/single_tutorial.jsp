<%@ include file="header.jsp" %>
  <h1>${tutorial.title()}</h1>
  <!-- Removing the br tags from content -->
  <div class="content">${fn:replace(tutorial.content(), "\\n","")}</div>
  <hr>
  <div class="fb-comments" data-href="${fbdata_url}" data-width="700"></div>
<%@ include file="footer.jsp" %>
