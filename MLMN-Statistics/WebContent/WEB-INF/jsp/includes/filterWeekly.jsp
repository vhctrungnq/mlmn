<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="wk.htm" name = "frmSample" onSubmit = "return ValidateForm()">			  			
			
					<c:choose> 
					<c:when test="${nodeType=='Routeid'}">
 						Mscid
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
						&nbsp;&nbsp;Routeid <input name="routeid" id="routeid" value="${routeid}" size="10" style="width:150px">
						&nbsp;&nbsp;Routename <input name="routename" id="routename" value="${routename}" size="10"  style="width:150px">
 					</c:when>
 					<c:when test="${nodeType=='s7link'}">
 						Mscid
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
						&nbsp;&nbsp;${node } <input name="linkid" id="linkid" value="${linkid}" size="10" style="width:150px">
						&nbsp;&nbsp;LinksetName <input name="linksetname" id="linksetname" value="${linksetname}" size="10"  style="width:150px">
						&nbsp;&nbsp;Type <input name="type" id="type" value="${type}" size="10" style="width:30px">
 					</c:when>
 					<c:when test="${nodeType=='Mscid'}">
 						Mscid
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
 						</c:when>
 					<c:when test="${nodeType=='ipbb'}">
 						Routeid
	 						<select name="routeid" id="routeid" onchange="xl()">
								<option value="">--Select Route--</option>
						        <c:forEach var="route" items="${routeList}">
						              <c:choose>
						                <c:when test="${route.link == routeid}">
						                    <option value="${route.link}" selected="selected">${route.link}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${route.link}">${route.link}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
							</select>
							Interfaceid
							<input name="interfaceid" id="interfaceid" value="${interfaceid}" size="10" style="width:150px">

 						</c:when>
 					<c:when test="${nodeType=='Sgsnid'}">
 						Sgsnid
 						<select name="sgsnid" id="sgsnid" onchange="xl()">
								<option value="">--Select SGSN--</option>
						        <c:forEach var="sgsn" items="${sgsnList}">
						              <c:choose>
						                <c:when test="${sgsn.sgsnName == sgsnid}">
						                    <option value="${sgsn.sgsnName}" selected="selected">${sgsn.sgsnName}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${sgsn.sgsnName}">${sgsn.sgsnName}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
							</select>
 						</c:when>
 					<c:when test="${nodeType=='Bscid'}">
 						Bscid
 						<select name="bscid" id="bscid" onchange="xl()"> 
								<option value="">--Select BSC--</option>
						        <c:forEach var="bsc" items="${bscList}">
						              <c:choose>
						                <c:when test="${bsc.bscid == bscid}">
						                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${bsc.bscid}">${bsc.bscid}</option>
						                </c:otherwise>
						              </c:choose>
							    </c:forEach>
							</select>
						<c:if test="${objectname=='Y'}">
							&nbsp;&nbsp;ObjectName <input name="objName" id="objName" value="${objName}" size="10"  style="width:150px">
						</c:if> 						</c:when> 
 					<c:otherwise>
 					&nbsp;&nbsp;${nodeType} <input name="nodeid" id="nodeid" value="${nodeid}" size="10"  style="width:150px">
 					</c:otherwise>
 					</c:choose>
	            	&nbsp;Từ tuần <input value="${startWeek}" name="startWeek" id="startWeek" size="2" maxlength="2" onchange ="javascript:checkNumber(document.frmSample.startWeek);">
					<img alt="calendar" title="Click to choose the start week number" id="chooseStartWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${startYear}" name="startYear" id="startYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.startYear);">
	            	&nbsp;Tới tuần <input value="${endWeek}" name="endWeek" id="endWeek" size="2" maxlength="2" onchange="javascript:checkNumber(document.frmSample.endWeek);">
					<img alt="calendar" title="Click to choose the end week number" id="chooseEndWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
	            	&nbsp;Năm <input value="${endYear}" name="endYear" id="endYear" size="4" maxlength="4" onchange ="javascript:checkNumber(document.frmSample.endYear);">
	            	&nbsp;<input type="submit" class="button" name="submit" id="submit"value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
</div>