<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Upload dữ liệu LAC</title>
<content tag="heading">Upload dữ liệu LAC</content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b>File</b></td>
			<td class="wid50"><input class="button" type="file" name="file" size="90"/></td>
			<td><input class="button" type="submit" class="button" name="save" value="Import file"/></td>
		</tr>
		<tr>
				<td>
				<b>Định dạng file import</b>
				</td>
				<td colspan="2">
					<ul>
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
								&lt;Region&gt;,
								&lt;Chi nhánh&gt;,
								&lt;Vendor&gt;,
								&lt;BSCID&gt;<font color="red">(*)</font>,
								&lt;LAC&gt;<font color="red">(*)</font>,
								&lt;RAC&gt;
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="BscExample" href="${pageContext.request.contextPath}/upload/H_BSC_LAC.xls">H_BSC_LAC.xls</a>
						</li>
						<li>Chú ý:</li>
						<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">
    	  ${status} <br/>
    	  ${status1} <br/>
    	  ${status2} <br/>
    	 </div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu Bsc không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" id="bsc" requestURI="" pagesize="100" class="simple2" export="false" sort="external" defaultsort="1">				   
					<display:column class="rightColumnMana" title="STT" > <c:out value="${bsc_rowNum}"/> </display:column>
					<display:column property="region" titleKey="Region"/>   
					<display:column property="vendor" titleKey="Vendor"/>   
				    <display:column property="cn" titleKey="Chi nhánh" />
				    <display:column property="bscid" title="BSC"/>  
				    <display:column class="rightColumnMana" property="lac" titleKey="LAC" />
				    <display:column class="rightColumnMana" property="rac" titleKey="RAC" /> 
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu Bsc hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" id="bsc" requestURI="" pagesize="100" class="simple2" export="false" sort="external" defaultsort="1">				   
					<display:column class="rightColumnMana" title="STT" > <c:out value="${bsc_rowNum}"/> </display:column>
					<display:column property="region" titleKey="Region"/>   
					<display:column property="vendor" titleKey="Vendor"/>   
				   <display:column property="cn" titleKey="Chi nhánh" />
				    <display:column property="bscid" title="BSC"/>  
				    <display:column class="rightColumnMana" property="lac" titleKey="LAC" />
				    <display:column class="rightColumnMana" property="rac" titleKey="RAC" /> 
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
 
