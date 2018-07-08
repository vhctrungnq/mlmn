
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<content tag="heading">${title}</content> 	
<table class="form" style="width:100%;">
		<tr> 
			<td align="left">
			<form:form commandName="filter" method="post"	action="list.htm">
				<input type="hidden" name="region" id="region" value="${region}">
					<table >
						<tr> 
							<td align="left"><fmt:message key="timer.startTime"/></td>
					    	<td>
					    		<input type ="text" value="${startTime}" name="startTime" id="startTime" size="15" maxlength="16" width="60px">
					   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					    	</td>
					    	<td style="width:50px; padding-left:5px;" align="left"><fmt:message key="timer.endTime"/> </td>
					    	<td>
					    		<input type ="text" value="${endTime}" name="endTime" id="endTime" size="15" maxlength="16" width="60px">
					    		<img alt="calendar" title="Click to choose the stop date" id="chooseStopDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
								
							<td style="width:50px; padding-left:5px;" ><fmt:message key="matDien.content"/></td>
							<td>
								<input type ="text" name="alarm" id="alarm" style="width: 200px" value="${alarm}">
							</td>
							<td style="width:50px; padding-left:5px;" ><fmt:message key="matDien.area"/></td>
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
							</td>
							<td style="width:70px; padding-left:5px;"">
								<fmt:message key="matDien.userProcess"/>
							</td>
							<td>
								 <input type ="text" value="${userProcess}" name="userProcess" id="userProcess" style="width: 140px">
								<input class="button" type="submit" class="button"
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
				<display:table name="${CBMatDienList}" class="simple2" id="item" requestURI="" pagesize="15" sort="external" defaultsort="1" export="true">
				  	<display:column class="centerColumnMana" titleKey="global.list.STT" style="width:30px;"> <c:out value="${item_rowNum}"/> </display:column>
					<display:column property="alarm" titleKey="matDien.content" sortable="true" sortName="ALARM" style="min-width:120px;max-width:120px;"/>
				  	<display:column property="area"  titleKey="matDien.area" sortable="true" sortName="AREA" style="min-width:80px;max-width:80px;"/>
					<display:column property="stime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="matDien.startTime" sortable="true" sortName="STIME" style="min-width:115px;max-width:115px;"/>
					<display:column property="etime"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="matDien.endTime" sortable="true" sortName="ETIME" style="min-width:115px;max-width:115px;"/>
					<display:column property="thoiGian"  titleKey="matDien.thoiGian" sortable="true" sortName="THOI_GIAN" style="min-width:40px;max-width:40px;"/>
					<display:column property="resultsProcess"  titleKey="matDien.result" sortable="true" sortName="RESULTS_PROCESS" />
					<display:column property="description"  titleKey="matDien.description" sortable="true" sortName="DESCRIPTION" />
					<display:column property="userProcess"  titleKey="matDien.userProcess" sortable="true" sortName="USER_PROCESS" />
					<c:if test="${checkCaTruc==true}">
						<display:column titleKey="title.quanLy" media="html" class="centerColumnMana" style="min-width:50px;">
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
	function focusIt()
	{
	  var mytext = document.getElementById("content");
	  mytext.focus();
	}
	onload = focusIt;
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