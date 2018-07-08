<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title> CELLS DAY BLOCKING</title>
<content tag="heading">BAD CELLS SDCCH BLOCKING 2G </content>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form:form method="get" commandName="filter" action="${pageContext.request.contextPath}/report/blocking-sdcch/cell/dy.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}" name = "frmSample" onSubmit = "return ValidateForm()">
			  	  	BSC <form:input path="bscid" size="10"/>&nbsp;&nbsp;
     			 	CELL <form:input path="cellid" size="10"/>&nbsp;&nbsp;				
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          	  </form:form>
	            </td>
	        </tr>		
		</table>
		<br/>
	</div>
		<div  style="overflow: auto;">
		<display:table name="${vRpDyCellsBlkr2}" id="VRpDyCellsBlkr2" requestURI="" pagesize="100" class="simple3" export="true">
			<display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property="bscid" titleKey="BSCID"/>
		    <display:column property="cellid" titleKey="CELLID"/>
		    <display:column property="sBlkr" titleKey="S_BLKR"/>
		</display:table>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>	
<script type="text/javascript">
$(function() {
	$( "#startDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	$("#endDate").datepicker({
		dateFormat: "dd/mm/yy",
		showOn:"button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
   var cache = {},
  lastXhr;
  $( "#bscid" ).autocomplete({
   minLength: 2,
   source: function( request, response ) {
    var term = request.term;
    if ( term in cache ) {
     response( cache[ term ] );
     return;
    }

    lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
     cache[ term ] = data;
     if ( xhr === lastXhr ) {
      response( data );
     }
    });
   } 
  });
  var cacheCell= {},
  lastXhrCell;
  $( "#cellid" ).autocomplete({
   minLength: 3,
   source: function( request, response ) {
    var term = request.term;
    if ( term in cacheCell ) {
     response( cacheCell[ term ] );
     return;
    }

    lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
     cacheCell[ term ] = data;
     if ( xhr === lastXhrCell ) {
      response( data );
     }
    });
   }
  });
 });
</script>
