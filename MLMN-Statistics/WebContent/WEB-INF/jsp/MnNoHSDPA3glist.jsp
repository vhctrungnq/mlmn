<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>No HSDPA data cells month report</title>
<content tag="heading">NO HSDPA DATA CELLS MONTHLY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="/VMSC2-Statistics/report/radio3g/cell-nohsdpa-data/dy.htm?"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="/VMSC2-Statistics/report/radio3g/cell-nohsdpa-data/wk.htm?"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="/VMSC2-Statistics/report/radio3g/cell-nohsdpa-data/mn.htm?bscid=${bsc.bscid}}&cellid=${cell.cellid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
 <form:form commandName="filter" method="post" action="mn.htm" name = "frmSample" onSubmit = "return ValidateForm()">
  <table width="100%" class="form">
   <tr>
       <td align="left">
         		 	RNC <form:input path="bscid" size="10"/>&nbsp;&nbsp;
     			 	CELL <form:input path="cellid" size="10"/>&nbsp;&nbsp;
     			  	&nbsp;Từ tháng <select name="startMonth" id="startMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == startMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tháng <select name="endMonth" id="endMonth" onchange="xl()">
	            				<c:forEach var="month" items="${monthList}">
						              <c:choose>
						                <c:when test="${month == endMonth}">
						                    <option value="${month}" selected="selected">${month}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${month}">${month}</option>
						                </c:otherwise>
						              </c:choose>
						    </c:forEach>
			               	 </select>&nbsp;
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
             </td>
         </tr>  
  </table>
 </form:form>
 <br/>
</div> 
 

<div  style="overflow: auto;">
  <display:table name="${Vmncell3g}" id="Vmncell3g" requestURI="" pagesize="100" class="simple3" export="true">
		    <display:column property="bscid" titleKey="RNC"/>
		    <display:column property ="cellid"  titleKey="UCELL" />
		    <display:column property="month" titleKey="MONTH"/>
	 	    <display:column property="year" titleKey="YEAR"/>
		    <display:column property="speechTraff" titleKey="SPEECH_TRAFF"/>
		    <display:column property="hsupaUlThroughtput" titleKey="HSUPA_UL_THROUGHTPUT"/>
		    <display:column property="hsdpaDlThroughtput" titleKey="HSDPA_DL_THROUGHTPUT"/>
		</display:table>
 </div>
 
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar_en.js"></script>
<script type="text/javascript" src="/VMSC2-Statistics/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="/VMSC2-Statistics/styles/calendar-blue.css" />

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
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
	for(var i=1; i<= strLength;i++){
	  var lchar = val.value.charAt((strLength) - i);
	  var cCode = CalcKeyCode(lchar);

	  if (cCode < 48 || cCode > 57 ) {
	    var myNumber = val.value.substring(0, (strLength)-i);
	    val.value = myNumber;
	  }
	}
	  var sub = document.getElementById("submit");
		sub.focus();
	  return false;
	}

	var minYear=1900;
	var maxYear=2100;
	function isDate(dtStr){
		var year = dtStr;
		if (year.length != 4 || year==0 || year<minYear || year>maxYear || year != year.getfullyear()){
			alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
			return false;
		}
		return true;
	}

function ValidateForm(){
	var dt=document.frmSample.startYear;
	var dt1=document.frmSample.endYear;
	if (isDate(dt.value)==false){
		dt.focus();
		return false;
	}
	if (isDate(dt1.value)==false){
		dt1.focus();
		return false;
	}
    return true;
 }
</script>	
<script type="text/javascript">
$(function() {
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
		
		    lastXhr = $.getJSON( "/VMSC2-Statistics/ajax/getBsc3G.htm", request, function( data, status, xhr ) {
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
		   minLength: 2,
		   source: function( request, response ) {
		    var term = request.term;
		    if ( term in cacheCell ) {
		     response( cacheCell[ term ] );
		     return;
		    }
		
		    lastXhrCell = $.getJSON( "/VMSC2-Statistics/ajax/getCell3G.htm", request, function( data, status, xhr ) {
		     cacheCell[ term ] = data;
		     if ( xhr === lastXhrCell ) {
		      response( data );
		     }
		    });
		   }
		  });
		 });
</script>