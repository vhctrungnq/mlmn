<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>CELL LAST 30 DAYS REPORT</title>

<div id = "chart"></div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
${chart }
