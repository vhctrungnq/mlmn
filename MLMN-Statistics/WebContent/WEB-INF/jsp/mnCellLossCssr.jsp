<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL MONTH REPORT</title>
<content tag="heading">CELL LOW CSSR MONTHLY REPORT</content>

<ul class="ui-tabs-nav">
 <!--  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/hr.htm?"><span>Báo cáo giờ</span></a></li> -->
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/dy.htm?"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/wk.htm?"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/mn.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startMonth=${startMonth}&startYear=${startYear}&endMonth=${endMonth}&endYear=${endYear}"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="mn.htm" name="frmSample" onSubmit="return ValidateFormYear()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					BSC <form:input path="bscid" size="10" />
					&nbsp;CELL <form:input path="cellid" size="10" />
	                &nbsp;Từ tháng  <select name="startMonth" id="startMonth" onchange = "xl()">
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
	            	&nbsp;Tới tháng  <select name="endMonth" id="endMonth" onchange = "xl()">
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
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id = "submit"value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
	
	
<div  style="overflow: auto;">
		<display:table name="${mnCellLossCssr}" id="MnCellLossCssr" requestURI="" pagesize="100" class="simple3" export="true">		    
		    <display:column property="month" titleKey="MONTH"/>
		    <display:column property="year" titleKey="YEAR"/>
		    <display:column property ="bscid"  titleKey="BSC" />
		    <display:column property ="cellid"  titleKey="CELL" />
		    <display:column property ="cellname"  titleKey="CELLNAME" />
		    <display:column property="tDef" titleKey="T_DEF"/>
		    <display:column property="tAvail" titleKey="T_AVAIL"/>
		    <display:column property="tAtts" titleKey="T_ATTS"/>
		    <display:column property="tSeizs" titleKey="T_SEIZS"/>
		    <display:column property="tDrps" titleKey="T_DRPS"/>
		    <display:column property="tBlks" titleKey="T_BLKS"/>
		    <display:column property="sAtts" titleKey="S_ATTS"/>
		    <display:column property="sDef" titleKey="S_DEF"/>
		    <display:column property="sSeizs" titleKey="S_SEIZS"/>
		    <display:column property="sDrps" titleKey="S_DRPS"/>
		    <display:column property="sBlks" titleKey="S_BLKS"/>
		    <display:column property="cssr" titleKey="CSSR"/>
		</display:table>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>

<script type="text/javascript">
	function CalcKeyCode(aChar) {
	  var character = aChar.substring(0,1);
	  var code = aChar.charCodeAt(0);
	  return code;
	}

	function checkNumber(val) {
	  var strPass = val.value;
	  var strLength = strPass.length;
	  for(var i =0;i<strLength+1;i++){
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
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
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

<script type="text/javascript">
	$(function() {
		var cachebsc = {},cachecell = {},
		lastXhrBsc,
		lastXhCell;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachebsc ) {
					response( cachebsc[ term ] );
					return;
				}

				lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cachebsc[ term ] = data;
					if ( xhr === lastXhrBsc ) {
						response( data );
					}
				});
			}
		});
		$( "#cellid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachecell ) {
					response( cachecell[ term ] );
					return;
				}

				lastXhCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cachecell[ term ] = data;
					if ( xhr === lastXhCell ) {
						response( data );
					}
				});
			}
		});
	});
</script>
