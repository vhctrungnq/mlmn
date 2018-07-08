<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<title>MSC DAY REPORT</title>
<content tag="heading">MSC RETAINABILITY DAYLY REPORT</content>
 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/retainability/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/retainability/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
<div class="ui-tabs-panel">
	<form  method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width ="100%" class="form">
			<tr>
			    <td align="left">
					MSC
					<select name="mscid" id="mscid" onchange="xl()">
					<option value="">--Select MSC--</option>
					        <c:forEach var="msc" items="${mscList}">
					              <c:choose>
					                <c:when test="${msc.mscid == mscid}">
					                    <option value="${msc.mscid}" selected="selected">${msc.mscid}</option>
					                </c:when>
					                <c:otherwise>
					                    <option value="${msc.mscid}">${msc.mscid}</option>
					                </c:otherwise>
					              </c:choose>
					    </c:forEach>
					</select>
	               Từ Ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                Đến Ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${dyMscRetainability}" id="dyMscRetainability" requestURI="" pagesize="100" class="simple3" export="true">
		   	<display:column title="SYSTEM" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core-era/retainability/hr.htm?mscid=${dyMscRetainability.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscRetainability.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMscRetainability.day}"/>">${dyMscRetainability.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" />	    
		    <display:column property ="succrInterMsc"  titleKey="Inter-MSC Handover Success, GSM (%)"  sortable="true" />
			<display:column property ="succrInterMscInc"  titleKey="Inter MSC - Incoming HO Succ - GSM (%)"  sortable="true" />
			<display:column property ="succrInterMscOug"  titleKey="Inter MSC - Outgoing HO Succ - GSM (%)"  sortable="true" />
			<display:column property ="succrU2gInterMscHo"  titleKey="Inter-MSC Handover Success, U2G (%)"  sortable="true" />
			<display:column property ="succrG2uInterMscHo"  titleKey="Inter-MSC Handover Success, G2U (%)"  sortable="true" />
			<display:column property ="succrIntraMscHoGsm"  titleKey="Intra-MSC Handover Success, GSM (%)"  sortable="true" />
			<display:column property ="succrIntraMscHoU2g"  titleKey="Intra-MSC Handover Success, U2G (%)"  sortable="true" />
			<display:column property ="succrIntraMscHoG2u"  titleKey="Intra-MSC Handover Success, G2U (%)"  sortable="true" />
			<display:column property ="succrIntraMscReloc"  titleKey="Inter-MSC SRNS Relocation Success, WCDMA (%)"  sortable="true" />
			<display:column property ="sipDsnSquery"  titleKey="SIP DNS Query Success (%)"  sortable="true" />
			<display:column property ="sfbSgsSuccr"  titleKey="CSFB SGs Success (%)"  sortable="true" />
		</display:table>
	</div>
	<br/>
	<table style="width:99%">
		<tr>
			<td><div id="chartInterGSM" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartInterU2G" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartIntraGSM" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
		<tr>
			<td><div id="chartIntraU2G" style="width: 100%; margin: 1em auto"></div></td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>


${chartdivInterGSM}
${chartdivInterU2G}
${chartdivIntraGSM}
${chartdivIntraU2G}


<script language = "Javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
} 
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;}
		if (i==2) {this[i] = 29;}
   } 
   return this;
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strDay=dtStr.substring(0,pos1);
	var strMonth=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear;
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
	}
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	if (pos1==-1 || pos2==-1){
		alert("Không đúng định dạng : dd/mm/yyyy");
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Vui lòng nhập lại tháng");
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Vui lòng nhập lại ngày");
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Vui lòng nhập năm trong khoảng từ "+minYear+" đến "+maxYear);
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Nhập sai định dạng");
		return false;
	}
return true;
}

function ValidateForm(){
	var dt=document.frmSample.startDate;
	var dt1=document.frmSample.endDate;
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
		 $( "#startDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		
		$( "#endDate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		}); 

		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
	
	 $("select#mscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getMsc.htm",{mscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select MSC--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].mscid + '">' + j[i].mscid + '</option>';
				}
				$("#mscid").html(options);
				$('#mscid option:first').attr('selected', 'selected');
			});
		});
		${highlight}
	}); 
	
</script>
<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th colspan="2" ></th>';
	    trs=trs +'<th colspan="5" >Inter-MSC</th>';
	    trs=trs +'<th colspan="3" >Intra-MSC</th>';
	    trs=trs +'<th colspan="3" ></th>';
	    
		trs=trs +'<tr><th  >SYSTEM</th>';
	    trs=trs +'<th >Date</th>';
	    trs=trs +'<th >Inter-MSC Handover Success, GSM (%)</th>';
	    trs=trs +'<th  >Inter MSC - Incoming HO Succ - GSM (%)</th>';
	    trs=trs +'<th  >Inter MSC - Outgoing HO Succ - GSM (%)</th>';
	    trs=trs +'<th >Inter-MSC Handover Success, U2G (%)</th>';
	    trs=trs +'<th >Inter-MSC Handover Success, G2U (%)</th>';
	    trs=trs +'<th >Intra-MSC Handover Success, GSM (%)</th>';
	    trs=trs +'<th >Intra-MSC Handover Success, U2G (%)</th>';
	    trs=trs +'<th >Intra-MSC Handover Success, G2U (%)</th>';
	    trs=trs +'<th  >Inter-MSC SRNS Relocation Success, WCDMA (%)</th>';
	    trs=trs +'<th >SIP DNS Query Success (%)</th>';
	    trs=trs +'<th >CSFB SGs Success (%)</th>';
	$('#dyMscRetainability thead').html(trs);
	});
</script>
