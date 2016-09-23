<%@ include file="header.jsp" %>

  <!-- Removing the br tags from content -->
  <div class="content">
    <h1>${tutorial.title()}</h1>
    ${fn:replace(tutorial.content(), "\\n","")}
    <hr>
    <div class="fb-comments" data-href="${fbdata_url}" data-width="700"></div>
  </div>
  <script>
    window.fbAsyncInit = function() {
      FB.init({
        appId      : '1417659781582505',
        xfbml      : true,
        version    : 'v2.6'
      });
    };

    (function(d, s, id){
       var js, fjs = d.getElementsByTagName(s)[0];
       if (d.getElementById(id)) {return;}
       js = d.createElement(s); js.id = id;
       js.src = "//connect.facebook.net/en_US/sdk.js";
       fjs.parentNode.insertBefore(js, fjs);
     }(document, 'script', 'facebook-jssdk'));
  </script>
<%@ include file="footer.jsp" %>
