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
			<div id="doublescroll" class="w50fl">
				 <display:table name="${DOWN_SITE}" class="simple2" style="width:96%" id="DOWN_SITE" requestURI="" export="true">
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
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartDOWN_SITE" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${DOWN_CELL}" class="simple2" style="width:96%" id="DOWN_CELL" requestURI="" export="true">
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
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartDOWN_CELL" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${DOWN_1800}" class="simple2" style="width:96%" id="DOWN_1800" requestURI="" export="true">
				 	<c:forEach var="i" items="${DOWN_1800count}">
						<display:column property="[${i}]" class="centerColumnMana"/> 
					</c:forEach>
						
					<display:setProperty name="export.csv.include_header" value="false" />
					<display:setProperty name="export.excel.include_header" value="false" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartDOWN_1800" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${TRX_ABL}" class="simple2" style="width:96%" id="TRX_ABL" requestURI="" export="true">
				 	<c:forEach var="i" items="${TRX_ABLcount}">
						<display:column property="[${i}]" class="centerColumnMana"/> 
					</c:forEach>
						
					<display:setProperty name="export.csv.include_header" value="false" />
					<display:setProperty name="export.excel.include_header" value="false" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartTRX_ABL" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${TRX_MBL}" class="simple2" style="width:96%" id="TRX_MBL" requestURI="" export="true">
				 	<c:forEach var="i" items="${TRX_MBLcount}">
						<display:column property="[${i}]" class="centerColumnMana"/> 
					</c:forEach>
						
					<display:setProperty name="export.csv.include_header" value="false" />
					<display:setProperty name="export.excel.include_header" value="false" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartTRX_MBL" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${MAT_DIEN}" class="simple2" style="width:96%" id="MAT_DIEN" requestURI="" export="true">
				 	<c:forEach var="i" items="${MAT_DIENcount}">
						<display:column property="[${i}]" class="centerColumnMana"/> 
					</c:forEach>
						
					<display:setProperty name="export.csv.include_header" value="false" />
					<display:setProperty name="export.excel.include_header" value="false" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartMAT_DIEN" style="width: 98%;"></div>
			</div>
		</div>
		<br style="clear:both;"/>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${LOI_TRUYEN_DAN}" class="simple2" style="width:96%" id="LOI_TRUYEN_DAN" requestURI="" export="true">
				 	<c:forEach var="i" items="${LOI_TRUYEN_DANcount}">
						<display:column property="[${i}]" class="centerColumnMana"/> 
					</c:forEach>
						
					<display:setProperty name="export.csv.include_header" value="false" />
					<display:setProperty name="export.excel.include_header" value="false" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" /> 
						
				</display:table>
			</div>
			<div align="center" class="w50fl">
				<div align="center" id="dlDataChartLOI_TRUYEN_DAN" style="width: 98%;"></div>
			</div>
		</div>
		
		<c:choose>
		  <c:when test="${testAAA == 'true'}">
		  	<br style="clear:both;"/>
			<div style="width:100%;">
				<div id="doublescroll" class="w50fl">
					 <display:table name="${AAA}" class="simple2" style="width:96%" id="AAA" requestURI="" export="true">
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
				<div id="doublescroll" class="w50fl">
					 <display:table name="${BBB}" class="simple2" style="width:96%" id="BBB" requestURI="" export="true">
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
				<div align="center" class="w50fl">
					<div align="center" id="dlDataChartBBB" style="width: 98%;"></div>
				</div>
			</div>
		  </c:when>
	  	</c:choose>	
		
	</c:when>
	<c:otherwise>
		<div style="width:100%;">
			<div id="doublescroll" class="w50fl">
				 <display:table name="${tongHop2GList}" class="simple2" style="width:96%" id="item" requestURI="" export="true">
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
${dlDataChartDOWN_1800}
${dlDataChartTRX_ABL}
${dlDataChartTRX_MBL}
${dlDataChartMAT_DIEN}
${dlDataChartLOI_TRUYEN_DAN}
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

		var count = '<c:out value="${DOWN_SITEcountToVT}"/>';
		if(count != ''){
			var trs= $('#DOWN_SITE tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_SITE thead tr').html(kq);
			$('#DOWN_SITE tbody tr').first().html('');
			var trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${DOWN_SITEcountToVT}">${titleTableDOWN_SITE}</th></tr>';
			trs=trs +'<tr><th colspan="${DOWN_SITEcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${DOWN_SITEteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#DOWN_SITE thead').html(trs);
		}

		var count = '<c:out value="${DOWN_CELLcountToVT}"/>';
		if(count != ''){
			var trs= $('#DOWN_CELL tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_CELL thead tr').html(kq);
			$('#DOWN_CELL tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${DOWN_CELLcountToVT}">${titleTableDOWN_CELL}</th></tr>';
			trs=trs +'<tr><th colspan="${DOWN_CELLcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${DOWN_CELLteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#DOWN_CELL thead').html(trs);
		}

		var count = '<c:out value="${DOWN_1800countToVT}"/>';
		if(count != ''){
			var trs= $('#DOWN_1800 tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#DOWN_1800 thead tr').html(kq);
			$('#DOWN_1800 tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${DOWN_1800countToVT}">${titleTableDOWN_1800}</th></tr>';
			trs=trs +'<tr><th colspan="${DOWN_1800countToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${DOWN_1800teamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#DOWN_1800 thead').html(trs);
		}

		
		var count = '<c:out value="${TRX_ABLcountToVT}"/>';
		if(count != ''){
			var trs= $('#TRX_ABL tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#TRX_ABL thead tr').html(kq);
			$('#TRX_ABL tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${TRX_ABLcountToVT}">${titleTableTRX_ABL}</th></tr>';
			trs=trs +'<tr><th colspan="${TRX_ABLcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${TRX_ABLteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#TRX_ABL thead').html(trs);
			}
		
		var count = '<c:out value="${TRX_MBLcountToVT}"/>';
		if(count != ''){
			var trs= $('#TRX_MBL tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#TRX_MBL thead tr').html(kq);
			$('#TRX_MBL tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${TRX_MBLcountToVT}">${titleTableTRX_MBL}</th></tr>';
			trs=trs +'<tr><th colspan="${TRX_MBLcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${TRX_MBLteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#TRX_MBL thead').html(trs);
		}

		var count = '<c:out value="${MAT_DIENcountToVT}"/>';
		if(count != ''){
			var trs= $('#MAT_DIEN tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#MAT_DIEN thead tr').html(kq);
			$('#MAT_DIEN tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${MAT_DIENcountToVT}">${titleTableMAT_DIEN}</th></tr>';
			trs=trs +'<tr><th colspan="${MAT_DIENcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${MAT_DIENteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#MAT_DIEN thead').html(trs);
		}

		var count = '<c:out value="${LOI_TRUYEN_DANcountToVT}"/>';
		if(count != ''){
			var trs= $('#LOI_TRUYEN_DAN tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#LOI_TRUYEN_DAN thead tr').html(kq);
			$('#LOI_TRUYEN_DAN tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${LOI_TRUYEN_DANcountToVT}">${titleTableLOI_TRUYEN_DAN}</th></tr>';
			trs=trs +'<tr><th colspan="${LOI_TRUYEN_DANcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${LOI_TRUYEN_DANteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#LOI_TRUYEN_DAN thead').html(trs);
		}

		var count = '<c:out value="${AAAcountToVT}"/>';
		if(count != ''){
			var trs= $('#AAA tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#AAA thead tr').html(kq);
			$('#AAA tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${AAAcountToVT}">${titleTableAAA}</th></tr>';
			trs=trs +'<tr><th colspan="${AAAcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${AAAteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#AAA thead').html(trs);
		}

		var count = '<c:out value="${BBBcountToVT}"/>';
		if(count != ''){
			var trs= $('#BBB tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#BBB thead tr').html(kq);
			$('#BBB tbody tr').first().html('');
			trs='<tr>';
			trs=trs + '<th rowspan="3"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th style="text-align:left" colspan="${BBBcountToVT}">${titleTableBBB}</th></tr>';
			trs=trs +'<tr><th colspan="${BBBcountToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${BBBteamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
			$('#BBB thead').html(trs);
		}
	}
	else{
		var count = '<c:out value="${countToVT}"/>';
		if(count != ''){
			var trs= $('#item tbody tr').first().html();
			var kq=trs.replace(/td/g,"th"); 
			$('#item thead tr').html(kq);
			$('#item tbody tr').first().html('');
			var trs='<tr>';
			trs=trs + '<th rowspan="2"><fmt:message key="baoCaoTongHop.ngay"/></th>';
			trs=trs +'<th colspan="${countToVT}"><fmt:message key="baoCaoTongHop.dvXuLy"/></th></tr>';
			trs=trs +'<tr>';
			<c:forEach items="${teamProcessList}" var="listOfNames">
				trs=trs +'<th>${listOfNames.teamProcess}</th>';
			</c:forEach>
			trs=trs +'<th><fmt:message key="baoCaoTongHop.total"/></th></tr>';
		    
			$('#item thead').html(trs);
		}
	}
});
</script>
