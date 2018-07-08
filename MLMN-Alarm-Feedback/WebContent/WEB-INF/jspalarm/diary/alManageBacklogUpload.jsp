<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.alManageBacklog.formUpload"/></title>
<content tag="heading"><fmt:message key="title.alManageBacklog.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
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
							&lt;<fmt:message key="alManageBacklog.work"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="alManageBacklog.process"/>&gt;, 
							&lt;<fmt:message key="alManageBacklog.startDate"/>&gt;, 
							&lt;<fmt:message key="alManageBacklog.endDate"/>&gt;,
							&lt;<fmt:message key="alManageBacklog.team"/>&gt;<font color="red">(*)</font>,  
							&lt;<fmt:message key="alManageBacklog.description"/>&gt;.
							&lt;<fmt:message key="alManageBacklog.ne"/>&gt;. 
							&lt;<fmt:message key="alManageBacklog.previousConfig"/>&gt;.
							&lt;<fmt:message key="alManageBacklog.laterConfig"/>&gt;.
							&lt;<fmt:message key="alManageBacklog.causeby"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="AlManageBacklogExample" href="${pageContext.request.contextPath}/upload/example/AlManageBacklogExample.xls">AlManageBacklogExample.xls</a>
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
    		<div><b>Dữ liệu sự cố tồn đọng không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="work" titleKey="alManageBacklog.work" class="NOT_NULL"/>
							<display:column property="ne" titleKey="alManageBacklog.ne" />
							<display:column property="process" titleKey="alManageBacklog.process" />
							<display:column property="startDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alManageBacklog.startDate" />  
							<display:column property="endDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alManageBacklog.endDate" />  
							<display:column property="team" titleKey="alManageBacklog.team" class="NOT_NULL"/>
							<display:column property="previousConfig" titleKey="alManageBacklog.previousConfig" />
							<display:column property="laterConfig" titleKey="alManageBacklog.laterConfig" />
							<display:column property="causeby" titleKey="alManageBacklog.causeby" /> 
							<display:column property="description" titleKey="alManageBacklog.description" />		
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu sự cố tồn đọng hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="700">
							<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="work" titleKey="alManageBacklog.work" class="NOT_NULL"/>
							<display:column property="ne" titleKey="alManageBacklog.ne" />
							<display:column property="process" titleKey="alManageBacklog.process" />
							<display:column property="startDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alManageBacklog.startDate" />  
							<display:column property="endDate" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="alManageBacklog.endDate" />  
							<display:column property="team" titleKey="alManageBacklog.team" class="NOT_NULL"/>
							<display:column property="previousConfig" titleKey="alManageBacklog.previousConfig" />
							<display:column property="laterConfig" titleKey="alManageBacklog.laterConfig" />
							<display:column property="causeby" titleKey="alManageBacklog.causeby" /> 
							<display:column property="description" titleKey="alManageBacklog.description" />		
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
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