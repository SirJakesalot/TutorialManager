<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="true" %>
<%@ page isELIgnored="false" %>
<%@ page import="tutorialdb_model.Tutorial"%>

<c:set var="context" value="${pageContext.request.contextPath}" />

<html>
<head>
  <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
  <script type='text/javascript' language="javascript" src="${context}/javascript/general.js"></script>
  <script type='text/javascript' language="javascript" src="${context}/javascript/images.js"></script>
  <script type='text/javascript' language="javascript" src="${context}/javascript/dashboard.js"></script>
  <script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js"></script>
  <link rel="stylesheet" href="${context}/css/images.css" type="text/css" media="screen, projection"/>
  <link rel="stylesheet" href="${context}/css/dashboard.css" type="text/css" media="screen, projection"/>
  <link rel="stylesheet" href="${context}/css/sidebar.css" type="text/css" media="screen, projection"/>
  <link rel="stylesheet" href="${context}/css/navBar.css" type="text/css" media="screen, projection"/>
  <link rel="stylesheet" href="${context}/css/general.css" type="text/css" media="screen, projection"/>
  <title>${title}</title>
  <link rel="shortcut icon" type="image/x-icon" href="${context}/css/img/JakesTutorials.bmp" />
</head>
<body>
<%@ include file="navbar.jsp" %>
<div id="msgs"></div>
