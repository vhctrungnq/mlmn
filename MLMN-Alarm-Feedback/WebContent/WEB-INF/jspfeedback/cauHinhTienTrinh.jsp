<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title><fmt:message key="title.cauHinhTienTrinh"/></title>
<content tag="heading"><fmt:message key="title.cauHinhTienTrinh"/></content>
<form id="filterController" action="list.htm" method="POST"> 	
	<table border="0" cellspacing="5" cellpadding="0" width="100%">
		<tr>
			<td class="wid30 mwid110" align="right"><fmt:message key="coreServiceFeedback.statusFeedback"/></td>
			<td>
				<c:choose>
					<c:when test="${started == true}">
						<select id="status" name="status" class="wid30" disabled="disabled">
							<option value="">--Tất cả--</option>
		       				<c:forEach var="items" items="${getStatusFbList}">
				              	<c:choose>
				                <c:when test="${items.value == status}">
				                    <option value="${items.value}" selected="selected">${items.name}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.value}">${items.name}</option>
				                </c:otherwise>
				              	</c:choose>
					    	</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<select id="status" name="status" class="wid30">
							<option value="">--Tất cả--</option>
		       				<c:forEach var="items" items="${getStatusFbList}">
				              	<c:choose>
				                <c:when test="${items.value == status}">
				                    <option value="${items.value}" selected="selected">${items.name}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.value}">${items.name}</option>
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
						<input type="text" id="repeats" name="repeats" value="${repeats}" class="wid30" disabled="disabled"/>&nbsp;<fmt:message key="coreServiceFeedback.minutes"/>&nbsp;
					</c:when>
					<c:otherwise>
						<input type="text" id="repeats" name="repeats" value="${repeats}" class="wid30"/>&nbsp;<fmt:message key="coreServiceFeedback.minutes"/>&nbsp;
					</c:otherwise>
				</c:choose>
				<span>(${fromDate}&nbsp;-&nbsp;${toDate})</span>
			</td>
		</tr>
		<tr>
			<td align="right">Thời gian lấy dữ liệu hàng ngày lúc</td>
			<td>
				<c:choose>
					<c:when test="${started == true}">
						<input type="text" id="hourData" name="hourData" value="${hourData}" class="wid10" disabled="disabled"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>,&nbsp;
						Mặc định&nbsp;<input type="text" id="hourDefault" name="hourDefault" value="${hourDefault}" class="wid10" disabled="disabled"/>&nbsp;<fmt:message key="coreServiceFeedback.hourUp"/>&nbsp;
					</c:when>
					<c:otherwise>
						<input type="text" id="hourData" name="hourData" value="${hourData}" class="wid10"/>&nbsp;<fmt:message key="coreServiceFeedback.hour"/>,&nbsp;
						Mặc định&nbsp;<input type="text" id="hourDefault" name="hourDefault" value="${hourDefault}" class="wid10"/>&nbsp;<fmt:message key="coreServiceFeedback.hourUp"/>&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td align="right">
				Tiến trình đang
					<c:choose>
						<c:when test="${started == true }">hoạt động</c:when>
						<c:otherwise>dừng</c:otherwise>
					</c:choose>
			</td>
			<td>	
				<input type="hidden" name="action" id="action" value="${action}">
				<input type="submit" value="${action}" class="button" >
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
setTimeout(function(){
	window.location = '${pageContext.request.contextPath}/feedback/cau-hinh-tien-trinh/list.htm';
}, 
5 * 60 * 1000);
</script>