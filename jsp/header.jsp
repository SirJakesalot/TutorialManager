<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<%@ page import="tutorialdb_model.Tutorial"%>

<c:set var="context" value="${pageContext.request.contextPath}" />

<html>
<head>
  <script type='text/javascript' language="javascript" src="${context}/javascript/jquery.min.js"></script>
  <script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js"></script>
  <link rel="stylesheet" href="${context}/css/style.css" type="text/css" media="screen, projection"/>
  <title>Jakes Tutorials</title>
</head>

<body>
  <%@ include file="nav_bar.jsp" %>
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

