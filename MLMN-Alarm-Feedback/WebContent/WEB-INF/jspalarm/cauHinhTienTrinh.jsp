<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.cauHinhTienTrinhMail"/></title>
<content tag="heading"><fmt:message key="title.cauHinhTienTrinhMail"/></content>
<form id="filterController" action="list.htm" method="POST"> 	
	<table border="0" cellspacing="5" cellpadding="0" width="100%">
		<tr>
			<td class="wid30 mwid110" align="right"><fmt:message key="coreServiceFeedback.statusMail"/></td>
			<td>
				<c:choose>
					<c:when test="${started == true}">
						<select id="status" name="status" class="wid30" disabled="disabled">
		       				<c:forEach var="items" items="${getStatusFbList}">
				              	<c:choose>
				                <c:when test="${items.name == status}">
				                    <option value="${items.name}" selected="selected">${items.value}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.name}">${items.value}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<select id="status" name="status" class="wid30">
		       				<c:forEach var="items" items="${getStatusFbList}">
				              	<c:choose>
				                <c:when test="${items.name == status}">
				                    <option value="${items.name}" selected="selected">${items.value}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.name}">${items.value}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
		<tr>
			<td align="right"><fmt:message key="coreServiceFeedback.repeat"/></td>
			<td>
				<c:choose>
					<c:when test="${started == true}">
						<input type="text" id="delay" name="delay" value="${delay}" class="wid30" disabled="disabled"/>&nbsp; Giây &nbsp;
					</c:when>
					<c:otherwise>
						<input type="text" id="delay" name="delay" value="${delay}" class="wid30"/>&nbsp;Giây&nbsp;
					</c:otherwise>
				</c:choose>
				<%-- <span>(${fromDate}&nbsp;-&nbsp;${toDate})</span> --%>
			</td>
		</tr>
		<tr>
			<td align="right">Thời gian gửi email trong ngày:</td>
			<td>
				<c:choose>
					<c:when test="${started == true}">
						<input type="text" id="startHour" name="startHour" value="${startHour}" class="wid10" disabled="disabled"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>,&nbsp;
						-&nbsp;<input type="text" id="endHour" name="endHour" value="${endHour}" class="wid10" disabled="disabled"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>&nbsp;
					</c:when>
					<c:otherwise>
						<input type="text" id="startHour" name="startHour" value="${startHour}" class="wid10"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>,&nbsp;
						-&nbsp;<input type="text" id="endHour" name="endHour" value="${endHour}" class="wid10"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td align="right">
				Tiến trình đang
					<c:choose>
						<c:when test="${started == true }"> hoạt động</c:when>
						<c:otherwise> dừng</c:otherwise>
					</c:choose>
			</td>
			<td style="padding-left: 20px;">	
				<input type="hidden" name="action" id="action" value="${action}">
				<input type="submit" value="${action}" class="button" >
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
setTimeout(function(){
	window.location = '${pageContext.request.contextPath}/sendmail/cau-hinh-tien-trinh/list.htm';
}, 
5 * 60 * 1000);
</script>