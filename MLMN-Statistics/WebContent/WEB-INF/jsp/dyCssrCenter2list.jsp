<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL DAY REPORT</title>
<content tag="heading">CELL SUMMARY CENTER 2 DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/hr.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startHour=${startHour}&startDate=${startDate}&endHour=${endHour}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/center2/dy.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/wk.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startWeek=${startWeek}&startYear=${startYear}&endWeek=${endWeek}&endYear=${endYear}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio/center2/mn.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
  <table style="width:100%;" class="form">
 <form:form method="post" commandName="filter" action="dy.htm" name="frmSample" onSubmit="return ValidateForm()">
   <tr>
       <td align="left">
         		 	BSC <form:input value="${bscid}" id="bscid" name="bscid" path="bscid" size="10" />
					&nbsp;CELL <form:input value="${cellid}" id="cellid" name="cellid" path="cellid" size="10" />    			 	
     			  	Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="16" maxlength="16" onchange ="javascript:checkNumber(document.frmSample.startDate);"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="16" maxlength="16" onchange ="javascript:checkNumber(document.frmSample.endDate);"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
             </td>
         </tr>  
 </form:form>
 </table>
 <br/>
</div> 
 
<div  style="overflow: auto;">
	  <display:table name="${Vrpdycell}" id="Vrpdycell" requestURI="" pagesize="100" class="simple3" export="true">
	     <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
	    <display:column property ="bscid"  titleKey="BSCID" />
	    <display:column property ="cellid"  titleKey="CELLID" />
	    <display:column property="tDrpr" titleKey="T_DRPR"/>
	    <display:column property="sDrpr" titleKey="S_DRPR"/>
	    <display:column property="cssr" titleKey="CSSR"/>
	  </display:table>
 </div>
 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
	function xl(){
		var sub=document.getElementById("submit");
		sub.focus();
	}

	function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i=0; i<=strlength; i++){
		  var lchar = val.value.charAt((strLength) - i);
		  var cCode = CalcKeyCode(lchar);
		  if (cCode < 48 || cCode > 57 ) {
		    var myNumber = val.value.substring(0, (strLength) - i);
		    val.value = myNumber;
	 	 }
	  }
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}
</script>	
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
		  var cacheCell = {}, lastXhrCell;
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