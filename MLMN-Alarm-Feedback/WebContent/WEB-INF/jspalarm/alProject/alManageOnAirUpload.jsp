<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">   
    #success p { margin: 0; padding: 1em; white-space: nowrap; } 
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>
<title><fmt:message key="title.alManageOnAir.formUpload"/></title>
<content tag="heading"><fmt:message key="title.alManageOnAir.formUpload"/></content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	<div>
		<input id="projectCode" name="projectCode" value="${projectCode}" type="hidden" />
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
							&lt;<fmt:message key="alManageOnAir.siteid"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageOnAir.siteName"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.ne"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.network"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="alManageCrCdd.cddName"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.clusters"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.projectCode"/>&gt;<font color="red">(*)</font>, 
							&lt;<fmt:message key="alManageOnAir.projectSupervisor"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.intNe"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.onAir"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.noCells"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.noTrxs"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.license"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.note"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.alarmTest"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.callTest"/>&gt;, 
							&lt;<fmt:message key="alManageOnAir.extalm"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.status"/>&gt;,
							&lt;<fmt:message key="alManageOnAir.description"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="AlManageOnAirExample" href="${pageContext.request.contextPath}/upload/example/AlManageOnAirExample.xls">AlManageOnAirExample.xls</a>
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
    		<div><b>Dữ liệu lịch phát sóng không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" >
							<display:column class="centerColumnMana" title="No." > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="siteid" titleKey="alManageOnAir.siteid" class="NOT_NULL"/>
							<display:column property="siteName" titleKey="alManageOnAir.siteName" />  
							<display:column property="ne" titleKey="alManageOnAir.ne" />
							<display:column property="network" titleKey="alManageOnAir.network" class="NOT_NULL"/>
							<display:column property="cddName" titleKey="alManageCrCdd.cddName" />
							<display:column property="clusters" class="rightColumnMana" titleKey="alManageOnAir.clusters" />
							<display:column property="projectCode" titleKey="alManageOnAir.projectCode" class="NOT_NULL"/>
							<display:column property="projectSupervisor" titleKey="alManageOnAir.projectSupervisor" />
							<display:column property="intNe" format="{0,date,dd/MM/yyyy}" titleKey="alManageOnAir.intNe" />
							<display:column property="onAir" format="{0,date,dd/MM/yyyy}" titleKey="alManageOnAir.onAir" />
							<display:column property="noCells" class="rightColumnMana" titleKey="alManageOnAir.noCells" />
							<display:column property="noTrxs" class="rightColumnMana" titleKey="alManageOnAir.noTrxs" />
							<display:column property="license" titleKey="alManageOnAir.license" />
							<display:column property="note" titleKey="alManageOnAir.note"/>
							<display:column property="alarmTest" titleKey="alManageOnAir.alarmTest" />
							<display:column property="callTest" titleKey="alManageOnAir.callTest"/>
							<display:column property="extalm" titleKey="alManageOnAir.extalm"/>
							<display:column property="status" titleKey="alManageOnAir.status"/>
							<display:column property="description" titleKey="alManageOnAir.description"/>
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu lịch phát sóng hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" >
							<display:column class="centerColumnMana" title="No." > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="siteid" titleKey="alManageOnAir.siteid" class="NOT_NULL"/>
							<display:column property="siteName" titleKey="alManageOnAir.siteName" />  
							<display:column property="ne" titleKey="alManageOnAir.ne" />
							<display:column property="network" titleKey="alManageOnAir.network" class="NOT_NULL"/>
							<display:column property="cddName" titleKey="alManageCrCdd.cddName" />
							<display:column property="clusters" class="rightColumnMana" titleKey="alManageOnAir.clusters" />
							<display:column property="projectCode" titleKey="alManageOnAir.projectCode" class="NOT_NULL"/>
							<display:column property="projectSupervisor" titleKey="alManageOnAir.projectSupervisor" />
							<display:column property="intNe" format="{0,date,dd/MM/yyyy}" titleKey="alManageOnAir.intNe" />
							<display:column property="onAir" format="{0,date,dd/MM/yyyy}" titleKey="alManageOnAir.onAir" />
							<display:column property="noCells" class="rightColumnMana" titleKey="alManageOnAir.noCells" />
							<display:column property="noTrxs" class="rightColumnMana" titleKey="alManageOnAir.noTrxs" />
							<display:column property="license" titleKey="alManageOnAir.license" />
							<display:column property="note" titleKey="alManageOnAir.note"/>
							<display:column property="alarmTest" titleKey="alManageOnAir.alarmTest" />
							<display:column property="callTest" titleKey="alManageOnAir.callTest"/>
							<display:column property="extalm" titleKey="alManageOnAir.extalm"/>
							<display:column property="status" titleKey="alManageOnAir.status"/>
							<display:column property="description" titleKey="alManageOnAir.description"/>		
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm<c:if test="${not empty projectCode}">?projectCode=${projectCode}</c:if>"'>
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