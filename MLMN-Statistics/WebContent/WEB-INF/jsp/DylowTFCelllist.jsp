<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL DAY REPORT</title>
<content tag="heading">LOW TRAFFIC CELL DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/bad-cell/lowtraffic/dy.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell/lowtraffic/wk.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/bad-cell/lowtraffic/mn.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
 <form:form commandName="filterlow" method="post" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
  <table width="100%" class="form">
   <tr>
       <td align="left">
         		 	BSC <form:input path="bscid" size="10"/>&nbsp;&nbsp;
     			 	CELL <form:input path="cellid" size="10"/>&nbsp;&nbsp;
     			  	Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
             </td>
         </tr>  
  </table>
 </form:form>
 <br/>
</div> 
 
<div  style="overflow: auto;">
  <display:table name="${Vrpdycell}" id="Vrpdycell" requestURI="" pagesize="100" class="simple3" export="true">
      <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
      <display:column property ="bscid"  titleKey="BSC" />
      <display:column property ="cellid"  titleKey="CELL" />
      <display:column property ="tDef"  titleKey="T_DEF" />
      <display:column property ="tAvail"  titleKey="T_AVAIL" />
      <display:column property ="tTrafh"  titleKey="T_TRAFH" />
      <display:column property ="traffTraf"  titleKey="TRAFF_TRAF" />
      <display:column property ="tUtil"  titleKey="TCH UTILITY RATE(%) < 20 %" />
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