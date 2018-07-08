<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    
	#doublescroll { overflow: auto; overflow-y: hidden; }    
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style> 
   
<title>${alarmTypeAlias}</title>
<content tag="heading">${alarmTypeAlias}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form method="post" action="list.htm?alarmType=${alarmType}">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid1 mwid60">
								<fmt:message key="baoCaoTongHop.tuNgay"/>	
							</td>
							<td class="wid15">
								<input type="text" id="startDate" name="startDate" value="${startDate}" class="wid70" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid1 mwid40"><fmt:message key="baoCaoTongHop.denNgay"/></td>
							<td class="wid15">
								<input type="text" id="endDate" name="endDate" value="${endDate}" class="wid70" maxlength="20"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<c:choose>
								<c:when test="${alarmType == null || alarmType == ''}">
									<td class="wid8 mwid80">
										<fmt:message key="baoCaoTongHop.loaiCanhBao"/>	
									</td>
									<td class="wid15">
										<select name="loaiCanhBao" id="loaiCanhBao" class="wid90">
					           				<option value="">--Tất cả--</option>
					           				<c:forEach var="items" items="${nameAlarmTypeList}">
								              <c:choose>
								                <c:when test="${items.value == nameAlarmTypeCBB}">
								                    <option value="${items.value}" selected="selected">${items.name}</option>
								                </c:when>
								                <c:otherwise>
								                    <option value="${items.value}">${items.name}</option>
								                </c:otherwise>
								              </c:choose>
										    </c:forEach>
					           			</select>
									</td>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form>
			</td>
			<td></td> 
		</tr>
</table>
<c:choose>
	<c:when test="${(nameAlarmTypeCBB == null || nameAlarmTypeCBB == '') && (alarmType == null || alarmType == '')}">
		<div style="width:100%;">
			<div class="w50fl">
				<div id="doublescroll">
					 <display:table name="${DOWN_SITE}" class="simple2" id="DOWN_SITE" requestURI="" export="true">
					 	<c:forEach var="i" items="${DOWN_SITEcount}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				<div id="doublescroll">
					<display:table name="${DOWN_SITE1}" class="simple2" id="DOWN_SITE1" requestURI="" export="true">
					 	<c:forEach var="i" items="${DOWN_SITEcount1}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartDOWN_SITE" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div class="w50fl">
				<div id="doublescroll">
					 <display:table name="${DOWN_CELL}" class="simple2" id="DOWN_CELL" requestURI="" export="true">
					 	<c:forEach var="i" items="${DOWN_CELLcount}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				<div id="doublescroll">
					<display:table name="${DOWN_CELL1}" class="simple2" id="DOWN_CELL1" requestURI="" export="true">
					 	<c:forEach var="i" items="${DOWN_CELLcount1}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartDOWN_CELL" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div class="w50fl">
				<div id="doublescroll">
					 <display:table name="${HARD_FAULT}" class="simple2" id="HARD_FAULT" requestURI="" export="true">
					 	<c:forEach var="i" items="${HARD_FAULTcount}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				<div id="doublescroll">
					<display:table name="${HARD_FAULT1}" class="simple2" id="HARD_FAULT1" requestURI="" export="true">
					 	<c:forEach var="i" items="${HARD_FAULTcount1}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartHARD_FAULT" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div class="w50fl">
				<div id="doublescroll">
					 <display:table name="${ANTENNA}" class="simple2" id="ANTENNA" requestURI="" export="true">
					 	<c:forEach var="i" items="${ANTENNAcount}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				<div id="doublescroll">
					<display:table name="${ANTENNA1}" class="simple2" id="ANTENNA1" requestURI="" export="true">
					 	<c:forEach var="i" items="${ANTENNAcount1}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartANTENNA" style="width: 98%;"></div>
			</div>
		</div>
		
		<c:choose>
		  <c:when test="${testAAA == 'true'}">
		  	<br style="clear:both;"/>
			<div style="width:100%;">
				<div class="w50fl">
					<div id="doublescroll">
						 <display:table name="${AAA}" class="simple2" id="AAA" requestURI="" export="true">
						 	<c:forEach var="i" items="${AAAcount}">
								<display:column property="[${i}]" class="centerColumnMana"/> 
							</c:forEach>
								
							<display:setProperty name="export.csv.include_header" value="false" />
							<display:setProperty name="export.excel.include_header" value="false" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
					<div id="doublescroll">
						<display:table name="${AAA1}" class="simple2" id="AAA1" requestURI="" export="true">
						 	<c:forEach var="i" items="${AAAcount1}">
								<display:column property="[${i}]" class="centerColumnMana"/> 
							</c:forEach>
								
							<display:setProperty name="export.csv.include_header" value="false" />
							<display:setProperty name="export.excel.include_header" value="false" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
				</div>
				<div align="center" class="w50fl">
					<div align="center" id="dlDataChartAAA" style="width: 98%;"></div>
				</div>
			</div>
		  </c:when>
	    </c:choose>
		
		<c:choose>
		  <c:when test="${testBBB == 'true'}">
		  	<br style="clear:both;"/>
			<div style="width:100%;">
				<div class="w50fl">
					<div id="doublescroll">
						 <display:table name="${BBB}" class="simple2" id="BBB" requestURI="" export="true">
						 	<c:forEach var="i" items="${BBBcount}">
								<display:column property="[${i}]" class="centerColumnMana"/> 
							</c:forEach>
								
							<display:setProperty name="export.csv.include_header" value="false" />
							<display:setProperty name="export.excel.include_header" value="false" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
					<div id="doublescroll">
						<display:table name="${BBB1}" class="simple2" id="BBB1" requestURI="" export="true">
						 	<c:forEach var="i" items="${BBBcount1}">
								<display:column property="[${i}]" class="centerColumnMana"/> 
							</c:forEach>
								
							<display:setProperty name="export.csv.include_header" value="false" />
							<display:setProperty name="export.excel.include_header" value="false" />
							<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
							<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
							<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
								
						</display:table>
					</div>
				</div>
				<div align="center" class="w50fl">
					<div align="center" id="dlDataChartBBB" style="width: 98%;"></div>
				</div>
			</div>
		  </c:when>
	    </c:choose>
		
		
	</c:when>
	<c:otherwise>
		<div style="width:100%;">
			<div class="w50fl">
				<div id="doublescroll">
					 <display:table name="${tongHop3GList}" class="simple2" id="item" requestURI="" export="true">
					 	<c:forEach var="i" items="${count}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
				<div id="doublescroll">
					<display:table name="${tongHop3GList1}" class="simple2" id="item1" requestURI="" export="true">
					 	<c:forEach var="i" items="${count1}">
							<display:column property="[${i}]" class="centerColumnMana"/> 
						</c:forEach>
							
						<display:setProperty name="export.csv.include_header" value="false" />
						<display:setProperty name="export.excel.include_header" value="false" />
						<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
						<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
						<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
							
					</display:table>
				</div>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChart" style="width: 98%;"></div>
			</div>
		</div>
	</c:otherwise>
</c:choose>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<link type="text/css" rel="Stylesheet" href="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.23.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min-1.8.9.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.jcountdown1.3.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/themes/grid.js"></script>
${dlDataChart}
${dlDataChartDOWN_SITE}
${dlDataChartDOWN_CELL}
${dlDataChartHARD_FAULT}
${dlDataChartANTENNA}
${dlDataChartAAA}
${dlDataChartBBB}

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDate",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
  var mytext = document.getElementById("startDate");
  mytext.focus();
}

onload = focusIt;
</script>
<script type="text/javascript">
$(document).ready(function(){
	var test = '<c:out value="${nameAlarmTypeCBB}"/>';
	var alarmType = '<c:out value="${alarmType}"/>';
	if((test == null || test == '') && (alarmType == null || alarmType == '')){
		var count = '<c:out value="${DOWN_SITEcountCausedby}"/>';
		if(count != ''){
			var trs= $('#DOWN_SITE tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_SITE thead tr').html(kq);
			$('#DOWN_SITE tbody tr').first().html('');
			var trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${DOWN_SITEcountCausedby}">${titleTableDOWN_SITE}</th></tr>';
			trs=trs +'<tr><th colspan="${DOWN_SITEcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${DOWN_SITEcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#DOWN_SITE thead').html(trs);
		}

		var count = '<c:out value="${DOWN_SITEcountDay}"/>';
		if(count != ''){
			var trs= $('#DOWN_SITE1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_SITE1 thead tr').html(kq);
			$('#DOWN_SITE1 tbody tr').first().html('');
			var trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${DOWN_SITEcountCausedby}">${titleTableDOWN_SITE}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${DOWN_SITEcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${DOWN_SITEdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#DOWN_SITE1 thead').html(trs1);
			$('#DOWN_SITE1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}


		var count = '<c:out value="${DOWN_CELLcountCausedby}"/>';
		if(count != ''){
			var trs= $('#DOWN_CELL tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_CELL thead tr').html(kq);
			$('#DOWN_CELL tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${DOWN_CELLcountCausedby}">${titleTableDOWN_CELL}</th></tr>';
			trs=trs +'<tr><th colspan="${DOWN_CELLcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${DOWN_CELLcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#DOWN_CELL thead').html(trs);
		}

		var count = '<c:out value="${DOWN_CELLcountDay}"/>';
		if(count != ''){
			var trs= $('#DOWN_CELL1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_CELL1 thead tr').html(kq);
			$('#DOWN_CELL1 tbody tr').first().html('');
			trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${DOWN_CELLcountDay}">${titleTableDOWN_CELL}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${DOWN_CELLcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${DOWN_CELLdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#DOWN_CELL1 thead').html(trs1);
			$('#DOWN_CELL1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}


		var count = '<c:out value="${HARD_FAULTcountCausedby}"/>';
		if(count != ''){
			var trs= $('#HARD_FAULT tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#HARD_FAULT thead tr').html(kq);
			$('#HARD_FAULT tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${HARD_FAULTcountCausedby}">${titleTableHARD_FAULT}</th></tr>';
			trs=trs +'<tr><th colspan="${HARD_FAULTcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${HARD_FAULTcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#HARD_FAULT thead').html(trs);
		}

		var count = '<c:out value="${HARD_FAULTcountDay}"/>';
		if(count != ''){
			var trs= $('#HARD_FAULT1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#HARD_FAULT1 thead tr').html(kq);
			$('#HARD_FAULT1 tbody tr').first().html('');
			trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${HARD_FAULTcountDay}">${titleTableHARD_FAULT}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${HARD_FAULTcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${HARD_FAULTdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#HARD_FAULT1 thead').html(trs1);
			$('#HARD_FAULT1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}


		var count = '<c:out value="${ANTENNAcountCausedby}"/>';
		if(count != ''){
			var trs= $('#ANTENNA tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#ANTENNA thead tr').html(kq);
			$('#ANTENNA tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${ANTENNAcountCausedby}">${titleTableANTENNA}</th></tr>';
			trs=trs +'<tr><th colspan="${ANTENNAcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${ANTENNAcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#ANTENNA thead').html(trs);
		}

		var count = '<c:out value="${ANTENNAcountDay}"/>';
		if(count != ''){
			var trs= $('#ANTENNA1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#ANTENNA1 thead tr').html(kq);
			$('#ANTENNA1 tbody tr').first().html('');
			trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${ANTENNAcountDay}">${titleTableANTENNA}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${ANTENNAcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${ANTENNAdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#ANTENNA1 thead').html(trs1);
			$('#ANTENNA1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}


		var count = '<c:out value="${AAAcountCausedby}"/>';
		if(count != ''){
			var trs= $('#AAA tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#AAA thead tr').html(kq);
			$('#AAA tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${AAAcountCausedby}">${titleTableAAA}</th></tr>';
			trs=trs +'<tr><th colspan="${AAAcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${AAAcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#AAA thead').html(trs);
		}

		var count = '<c:out value="${AAAcountDay}"/>';
		if(count != ''){
			var trs= $('#AAA1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#AAA1 thead tr').html(kq);
			$('#AAA1 tbody tr').first().html('');
			trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${AAAcountDay}">${titleTableAAA}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${AAAcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${AAAdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#AAA1 thead').html(trs1);
			$('#AAA1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}

		var count = '<c:out value="${BBBcountCausedby}"/>';
		if(count != ''){
			var trs= $('#BBB tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#BBB thead tr').html(kq);
			$('#BBB tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${BBBcountCausedby}">${titleTableBBB}</th></tr>';
			trs=trs +'<tr><th colspan="${BBBcountCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${BBBcausedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#BBB thead').html(trs);
		}

		var count = '<c:out value="${BBBcountDay}"/>';
		if(count != ''){
			var trs= $('#BBB1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#BBB1 thead tr').html(kq);
			$('#BBB1 tbody tr').first().html('');
			trs1='<tr>';
			trs1=trs1 + '<th rowspan="3"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th style="text-align:left" colspan="${BBBcountDay}">${titleTableBBB}</th></tr>';
			trs1=trs1 +'<tr><th colspan="${BBBcountDay}"><fmt:message key="baoCaoTongHop.ngay"/></th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${BBBdayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#BBB1 thead').html(trs1);
			$('#BBB1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}
	}
	else{
		var count = '<c:out value="${countCausedby}"/>';
		if(count != ''){
			var trs= $('#item tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#item thead tr').html(kq);
			$('#item tbody tr').first().html('');
			var trs='<tr>';
			trs=trs + '<th rowspan="2"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs=trs + '<th rowspan="2"><fmt:message key="baoCaoTongHop.total"/></th>';
			trs=trs +'<th colspan="${countCausedby}"><fmt:message key="baoCaoTongHop.nguyenNhan"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${causedbyList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.value}</th>';
			</c:forEach>
			trs=trs +'</tr>';
			$('#item thead').html(trs);
		}

		var count = '<c:out value="${countDay}"/>';
		if(count != ''){
			var trs= $('#item1 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#item1 thead tr').html(kq);
			$('#item1 tbody tr').first().html('');
			var trs1='<tr>';
			trs1=trs1 + '<th rowspan="2"><fmt:message key="baoCaoTongHop.dvXuLy"/></th>';
			trs1=trs1 +'<th colspan="${countDay}">${alarmTypeAliasByDay}</th></tr>';
			trs1=trs1 +'<tr>';
			<c:forEach items="${dayList}" var="listOfNames">
				trs1=trs1 +'<th>${listOfNames.causedby}</th>';
			</c:forEach>
			trs1=trs1 +'</tr>'; 
			$('#item1 thead').html(trs1);
			$('#item1 tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
		}
	}
});
</script>