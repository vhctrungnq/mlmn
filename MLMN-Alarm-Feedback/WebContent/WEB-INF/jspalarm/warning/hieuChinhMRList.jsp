
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title>${title}</title>
<content tag="heading">${title}</content> 	


<table class="form" style="width:100%;">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
			<input type="hidden" name="region" id="region" value="${region}">
				<table >
						<tr> 
						
							<td align="left" ><fmt:message key="timer.startTime"/></td>
					    	<td>
					    		<input type ="text" value="${startTime}" name="startTime" id="startTime" size="15" maxlength="16" width="60px">
					   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					    	</td>
					    	<td style="width:50px; padding-left:5px;" align="left"><fmt:message key="timer.endTime"/> </td>
					    	<td>
					    		<input type ="text" value="${endTime}" name="endTime" id="endTime" size="15" maxlength="16" width="60px">
					    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
							<td style="width:50px; padding-left:5px;"><fmt:message key="alarmExtend.content"/></td>
							<td colspan="3">
								<input type ="text" name="alarm" id="alarm" style="width: 200px" value="${alarm}">
							</td>
							
							<td style="width:50px; padding-left:5px;">
								<fmt:message key="alarmExtend.teamProcess"/>
							</td>
							<td>
								 <select name="teamProcess" id="teamProcess" style="width: 130px;height:20px; padding-top: 4px;">
								 <option value=""><fmt:message key="global.All"/></option>
									<c:forEach var="item" items="${teamList}">
										<c:choose>
							                <c:when test="${item.deptCode == teamProcess}">
							                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
							                </c:when>
											<c:otherwise>
												<option value="${item.deptCode}">${item.deptCode}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
							</td>
							<td style="width:50px; padding-left:5px;" ><fmt:message key="alarmExtend.area"/></td>
							<td>
								<select name="area" id="area" style="width: 140px;height:20px; padding-top: 4px;">
									<option value=""><fmt:message key="global.All"/></option>
									<c:forEach var="item" items="${areaList}">
										<c:choose>
							                <c:when test="${item == area}">
							                    <option value="${item}" selected="selected">${item}</option>
							                </c:when>
											<c:otherwise>
												<option value="${item}">${item}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 	
							<input class="button" type="submit" id="button"
								value="<fmt:message key="button.search"/>" />
							</td>
							
						</tr>
					</table>
				</form:form>
				</td> 
		</tr> 
		<c:if test="${checkCaTruc==true}">
			<tr>
				<td class="wid10 mwid60 ftsize12" align="right" colspan="6">
		            <a href="form.htm?note=&region=${region}"><fmt:message key="button.add"/></a>
		            <a href="upload.htm&region=${region}"><fmt:message key="button.upload"/></a>
		        </td>
		    </tr>
	   	</c:if>
		
	    <tr>
	    	<td>
	    		<div id="doublescroll" >
				<display:table name="${alarmList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
				  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column property="alarm" titleKey="alarmExtend.content" sortable="true" sortName="ALARM"  style="min-width:120px;max-width:120px;"/>
				  	<display:column property="area"  titleKey="alarmExtend.area" sortable="true" sortName="AREA"  style="min-width:80px;max-width:80px;"/>
					<display:column property="stime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alarmExtend.startTime" sortable="true" sortName="STIME"  style="min-width:115px;max-width:115px;"/>
					<display:column property="etime"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alarmExtend.endTime" sortable="true" sortName="ETIME" style="min-width:115px;max-width:115px;"/>
					<display:column property="thoiGian"  titleKey="alarmExtend.thoiGian" sortable="true" sortName="THOI_GIAN" style="min-width:40px;max-width:40px;"/>
					<display:column property="resultsProcess"  titleKey="alarmExtend.result" sortable="true" sortName="RESULTS_PROCESS" style="min-width:100px;"/>
					<display:column property="description"  titleKey="alarmExtend.description" sortable="true" sortName="DESCRIPTION" style="min-width:100px;" />
					<display:column property="teamProcess" titleKey="alarmExtend.teamProcess" sortable="true" sortName="TEAM_PROCESS" style="min-width:100px;"/>
					<display:column property="userProcess" titleKey="alarmExtend.userProcess" sortable="true" sortName="USER_PROCESS" style="min-width:100px;"/>
					<c:if test="${checkCaTruc==true}">
						<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
							<a href="form.htm?id=${item.id}&note=&region=${region}"><fmt:message key="button.edit"/></a>&nbsp;
			   				<a href="delete.htm?id=${item.id}&note=&region=${region}"
									onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="button.delete"/></a>&nbsp;
			   			</display:column>
				   	</c:if>
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		
				</display:table>
			</div>
	    	</td>
	    </tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
        button			:   "chooseStopDate",   	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">
var theme =  getTheme(); 
$('#button').jqxButton({ theme: theme });
	function focusIt()
	{
	  var mytext = document.getElementById("content");
	  mytext.focus();
	}
	onload = focusIt;
	$("#teamProcess").change(function(){
		$.getJSON("${pageContext.request.contextPath}/ajax/getAreaBydeptCode.htm",{team: $(this).val()}, function(j){
			var options = '';
			options = '<option value=""><fmt:message key="global.All"/></option>';
			for (var i = 0; i < j.length; i++) {
				options += '<option value="' + j[i].placesCode + '">' + j[i].placesCode+ '</option>';
			}
			$("#area").html(options);
			$('#area option:first').attr('selected', 'selected');
		});
	});

</script>
<%-- <script type="text/javascript">
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
</script>--%>