<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>MSC DAY REPORT</title> 
<content tag="heading">MSC MGW RETAINABILITY STS DATA DAILY REPORT</content>
 
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/Retaina-stsdata/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/Retaina-stsdata/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
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
<div  id="doublescroll">
		<display:table name="${DyMscretain}" id="DyMscretain" requestURI="" pagesize="100" class="simple3" export="true">
			<display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>		   
		    <display:column title="SYSTEM" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core/Retaina-stsdata/hr.htm?mscid=${DyMscretain.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${DyMscretain.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${DyMscretain.day}"/>">${DyMscretain.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="Day" />
		    <display:column property="orgNbinitFault" titleKey="Orig NbInit Faults"/>
		    <display:column property="orgNbinit" titleKey="Orig NbInit"/>
		    <display:column property="gcpUptimeSecond" titleKey="GCP Link Uptime Seconds"/>
		    <display:column property="aal2TermSeizure" titleKey="AAL2 terminate siezure successrate perVMGw (%)"/>
		    <display:column property="ipTermSeizure" titleKey="IP terminate siezure successrate perVMGw (%)"/>
		    <display:column property="orgInitSucc" titleKey="Origin Connection Initial successrate perVMGw (%)"/>
		    <display:column property="iutermAal2Bear" titleKey="Iu Term Aal2 Bear Estab FailRerm"/>
		    <display:column property="orgAal2Bear" titleKey="Orig Aal2 Bear Estab FailRerm"/>
		    <display:column property="ntermAal2Bear" titleKey="NbTerm Aal2 Bear Estab FailRerm"/>
		    <display:column property="aal2TermReq" titleKey="AAL2 Term Req"/>
		    <display:column property="aal2TermRej" titleKey="AAL2 Term Rej"/>
		    <display:column property="recIpbcpRej" titleKey="Rec Ipbcp Reject Msg"/>
		    <display:column property="sentIpbcpRej" titleKey="Sent Ipbcp Reject Msg"/>
		    <display:column property="recIpbcpConfused" titleKey="Rec Ipbcp Confused Msg"/>
		    <display:column property="recFaultyAccept" titleKey="Rec Faulty Ipbcp Accept Msg"/>
		    <display:column property="orgIpbcpBear" titleKey="Orig Ipbcp Bear Super vTmrExp"/>
		    <display:column property="recBctpFail" titleKey="Rec Bctp Protocol Faiture"/>
		    <display:column property="recIpbcpReq" titleKey="Rec Ipbcp Request Msg"/>
		    <display:column property="sentIpbcpReq" titleKey="Sent Ipbcp Request Msg"/>
		    <display:column property="totReceivedGcp" titleKey="Total Recived GCP messages"/>
		    <display:column property="totSentGcp" titleKey="Total Sent GCP message"/>
		    <display:column property="currentBusyIp" titleKey="Current Busy_IP Termirate PerVMGw"/>
		    <display:column property="aal2Bearer" titleKey="AAL2 Bearer establish succ rate (%)"/>
		    <display:column property="ipbcpBearer" titleKey="IPBCP Bearer establish succ rate (%)"/>
		</display:table>
	</div>

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
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>