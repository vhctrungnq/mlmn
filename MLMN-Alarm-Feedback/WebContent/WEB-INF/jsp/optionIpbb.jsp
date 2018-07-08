<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<content tag="heading">BIỂU ĐỒ TẢI IPBB 30 NGÀY GẦN NHẤT</content>

<table width="100%" class="form">	
	<tr>
	<form:form method="get" action="luy-ke.htm">
	    <td style = "width:170px;">  
			<fmt:message key="general.region"/>	
			<select name="region" id = "region">
	      	<c:forEach var="item" items="${regionList}">
	           <c:choose>
	             <c:when test="${item.value == region}">
	                 <option value="${item.value}" selected="selected">${item.name}</option>
	             </c:when>
	             <c:otherwise>
	                 <option value="${item.value}">${item.name}</option>
	             </c:otherwise>
	           </c:choose>
	   		</c:forEach>
	  		</select>
	  	</td>
	    <td style = "width:140px;">  
			Site	
			<select name="site" id = "site">
	      	<c:forEach var="item" items="${siteList}">
	           <c:choose>
	             <c:when test="${item == site}">
	                 <option value="${item}" selected="selected">${item}</option>
	             </c:when>
	             <c:otherwise>
	                 <option value="${item}">${item}</option>
	             </c:otherwise>
	           </c:choose>
	   		</c:forEach>
	  		</select>
	  	</td>
	  	<td>	
	  		<input value="${min}" name="min" id="min" maxlength="3" class="wid3">&lt;
			    	Ngưỡng tải &lt;<input value="${max}" name="max" id="max" maxlength="3" class="wid3">&nbsp;&nbsp;&nbsp;
	        <input type="submit" class="button" name="submit" value="View Report" />
	     </td>
	   </form:form>     	      
	   </tr>  
</table>
<br>
<table width = "100%">	   
   <tr>
   	<td > <div id = "chartIn" style = "max-width:100%; height:500px"></div></td>
   </tr>	
   <tr style = "height:50px;"> </tr>
     <tr>
   	<td> <div id = "chartOut" style = "max-width:100%; height:500px"></div></td>
   </tr>	
</table>

<link href="/js/jquery/jquery-ui-1.8.23.custom.css" rel="stylesheet">
<script src="/js/jquery/jquery-ui.min-1.8.9.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highstock.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting_v4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${chartIn}
${chartOut}

<script  type="text/javascript">
toolBar = function (){
	 $('#load').remove();
	 $('.body-content').append('<span id="load">LOADING...</span>');
	 $('#load').fadeIn('normal', loadContent);

	 function loadContent() {
	  $('#result').load('', '', showNewContent());
	 }
	 
	 function showNewContent() {
	  $('#result').show('normal', hideLoader());
	 }
	 
	 function hideLoader() {
	  $('#load').fadeOut('normal');
	 }
	}

$("select#region").change(function(){
	 toolBar();
	 var region = $("select#region").val();
	 var options = "";
	 $.ajax({
		type: "GET",
 	  url: "${pageContext.request.contextPath}/ajax/getSiteByRegion.htm",
	  data:{
	   region: region
	  },
	  dataType: 'json',
	  contentType: 'application/json',
	  mimeType: 'application/json',
	  error: function(){
	   alert('Quá trình tải dữ liệu bị lỗi');
	  },
	  beforeSend: function(){},
	  complete: function(){},
	  success: function(siteList){
	   for (var i = 0; i < siteList.length; i++) {
		   options += '<option value="' + siteList[i] + '">' + siteList[i] + '</option>'; 
	   }
	   $("#site").html(options);
	  }
	 });
});
</script>
