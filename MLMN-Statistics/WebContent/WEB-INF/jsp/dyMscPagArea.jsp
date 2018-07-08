<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>msc Day report</title>
<content tag="heading">MSC PAGING FOR AREA DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core-era/msc-paging/hr.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core-era/msc-paging/dy.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
</div>
<div  id="doublescroll">
		<display:table name="${dyMSCPagarea}" id="dyMSCPagarea" requestURI="" pagesize="100" class="simple3" export="true" defaultsort="1" >
		    <display:column property="mscid" titleKey="SYSTEM" headerClass="hide" class="hide"/>
		    <display:column title="SYSTEM" media="html">
		   	 	<a href="${pageContext.request.contextPath}/report/core-era/msc-paging/hr.htm?mscid=${dyMSCPagarea.mscid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMSCPagarea.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyMSCPagarea.day}"/>">${dyMSCPagarea.mscid}</a>
		    </display:column>
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property="nlapag1lotot" titleKey="NLAPAG1LOTOT"/>
		    <display:column property="nlapag2lotot" titleKey="NLAPAG2LOTOT"/>
		    <display:column property="nlapag1resucc" titleKey="NLAPAG1RESUCC"/>
		    <display:column property="nlapag2resucc" titleKey="NLAPAG2RESUCC"/>
		    <display:column property="nlapagerr" titleKey="NLAPAGERR"/>
		    <display:column property="pagingSucc" titleKey="Paging Succ (%)" sortable="true"/>
		</display:table>
	</div>

<script type = "text/javascript">
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
		/* var cache = {},
		lastXhr;
		$( "#mscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMscid.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			} */
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