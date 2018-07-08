<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.alWorkCalendar.formUpload"/></title>
<content tag="heading"><fmt:message key="title.alWorkCalendar.formUpload"/></content>
 	
<form:form method="post" action="upload.htm?region=${region}" enctype="multipart/form-data" >
	<div>
		<input id="startDate" name="startDate" value="${startDate}" type="hidden" />
		<input id="endDate" name="endDate" value="${endDate}" type="hidden" />
	</div>
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b><fmt:message key="qLNguoiDung.file"/></b></td>
			<td><input class="button" type="file" name="file" size="90"/>&nbsp;
			<input class="button" type="submit" class="button" name="save" value="<fmt:message key="global.button.import"/>"/></td>
		</tr>
		<tr>
				<td>
				<b><fmt:message key="qLNguoiDung.dinhDangFile"/></b>
				</td>
				<td>
					<ul style="list-style-type: none;">
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
							&lt;<fmt:message key="qLPhongBan.region"/>&gt;<font color="red">(*)</font>
							&lt;<fmt:message key="alWorkCalendar.email"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alWorkCalendar.day"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alWorkCalendar.shift"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alWorkCalendar.function"/>&gt;,
							&lt;<fmt:message key="alWorkCalendar.emailBefore"/>&gt;,  
							&lt;<fmt:message key="alWorkCalendar.description"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="AlWorkCalendarExample" href="${pageContext.request.contextPath}/upload/example/AlWorkCalendarExample.xls">AlWorkCalendarExample.xls</a>
						</li>
						<li>Chú ý:</li>
						<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu lịch trực ca không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="region" titleKey="qLPhongBan.region" class="NOT_NULL"/>
							<display:column property="email" titleKey="alWorkCalendar.email" class="NOT_NULL"/>
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="alWorkCalendar.day" class="NOT_NULL"/>  
							<display:column property="shift" titleKey="alWorkCalendar.shift" class="NOT_NULL"/>
							<display:column property="function" titleKey="alWorkCalendar.function"/>
							<display:column property="emailBefore" titleKey="alWorkCalendar.emailBefore"/>
							<display:column property="description" titleKey="alWorkCalendar.description" />		
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu lịch trực ca hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="region" titleKey="qLPhongBan.region" class="NOT_NULL"/>
							<display:column property="email" titleKey="alWorkCalendar.email" class="NOT_NULL"/>
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="alWorkCalendar.day" class="NOT_NULL"/>  
							<display:column property="shift" titleKey="alWorkCalendar.shift" class="NOT_NULL"/>
							<display:column property="function" titleKey="alWorkCalendar.function" />
							<display:column property="emailBefore" titleKey="alWorkCalendar.emailBefore"/>
							<display:column property="description" titleKey="alWorkCalendar.description" />		
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm?startDate=${startDate}&endDate=${endDate}&region=${region}"'>
			</td>
		</tr>
	</table>
</form:form>
<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>