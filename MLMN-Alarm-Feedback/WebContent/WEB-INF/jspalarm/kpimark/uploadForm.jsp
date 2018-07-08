<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">   
 	#success { overflow: auto; overflow-y: hidden; }  
    #success p { margin: 0; padding: 1em; white-space: nowrap; }
    
    #failed { overflow: auto; overflow-y: hidden; }  
    #failed p { margin: 0; padding: 1em; white-space: nowrap; }
    .note{color:red;}
</style>

<title>Upload KH chấm điểm KPIs tổ nhóm</title>
<content tag="heading">Upload KH chấm điểm KPIs tổ nhóm</content>
 <form:form  method="post" action="upload.htm" enctype="multipart/form-data">
	<table class="simple2">	
    	<tr style="height:20px;" >
    		<td width="150px"><b><fmt:message key="cautruc.filepath"/> <font color="red">(*)</font></b></td>
    		<td><input type="file" size="110" name="filePath" id="filePath" class="button" />
    			<input type="submit" name="upload" id="upload" class="button" value="<fmt:message key="global.button.import"/>" />
    		</td>
    	</tr>
    	<tr>
    		<td><b>
    			<fmt:message key="global.FileExample"/></b>
    		</td>
    		<td>
				<ul style="list-style-type: none;">
					<li>File import là file (.xls)</li>
					<li>Dữ liệu trong file có dạng: <code> 
					&lt;<fmt:message key="Đài VT"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="Tổ"/>&gt;<font color="red">(*)</font>,  
					&lt;<fmt:message key="Nhóm"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="Số lần MLL tối đa trạm cấp 1"/>&gt;<font color="red">(*)</font>, 
					&lt;<fmt:message key="Số lần MLL tối đa trạm cấp 2"/>&gt;, 
					&lt;<fmt:message key="Số lần MLL tối đa trạm cấp 3"/>&gt;, 
					&lt;<fmt:message key="TGXL MLL ban ngày trạm cấp 1"/>&gt;,  
					&lt;<fmt:message key="TGXL MLL ban ngày trạm cấp 2"/>&gt;, 
					&lt;<fmt:message key="TGXL MLL ban ngày trạm cấp 3"/>&gt;, 
					&lt;<fmt:message key="TGXL MLL ban đêm trạm cấp 1"/>&gt;, 
					&lt;<fmt:message key="TGXL MLL ban đêm trạm cấp 2"/>&gt;, 
					&lt;<fmt:message key="TGXL MLL ban đêm trạm cấp 3"/>&gt;.</code>
					</li>
					<li>File mẫu:&nbsp;<a style="color: blue; " title="KPIsExample" href="${pageContext.request.contextPath}/upload/example/KPIsExample.xls">KPIsExample.xls</a></li>
					<li>Chú ý:</li>
					<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
				</ul>
			</td>
    	</tr>
    	
    </table>
    
    <c:if test="${status != null}" >
    	<div class="error">${status} ${statusExists}</div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu thông tin chấm kiểm KPIs tổ nhóm không hợp lệ( ${failNum}/${totalNum} )</b></div>
    		
    		<div class = "tableStandar">
		    	<display:table name="${failedList}" id="item1" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item1_rowNum}" />
					</display:column> 
					<display:column property="dept" titleKey="Đài VT" />
					<display:column property="team" titleKey="Tổ"/>
					<display:column property="teamGroups" titleKey="Nhóm"/>
					<display:column property="lostContactNum1Kh" titleKey="Số lần MLL tối đa trạm cấp 1"/>
					<display:column property="lostContactNum2Kh" titleKey="Số lần MLL tối đa trạm cấp 2"/>
					<display:column property="lostContactNum3Kh" titleKey="Số lần MLL tối đa trạm cấp 3"/>
					<display:column property="prcessTime1Kh" titleKey="TGXL MLL ban ngày trạm cấp 1"/>
					<display:column property="prcessTime2Kh" titleKey="TGXL MLL ban ngày trạm cấp 2"/>
					<display:column property="prcessTime3Kh" titleKey="TGXL MLL ban ngày trạm cấp 3"/>
					<display:column property="nightPrcessTime1Kh" titleKey="TGXL MLL ban đêm trạm cấp 1"/>
					<display:column property="nightPrcessTime2Kh" titleKey="TGXL MLL ban đêm trạm cấp 2"/>
					<display:column property="nightPrcessTime3Kh" titleKey="TGXL MLL ban đêm trạm cấp 3"/>
					 
					<display:setProperty name="export.csv.include_header" value="true" />
					<display:setProperty name="export.excel.include_header" value="true" />
					<display:setProperty name="export.xml.include_header" value="true" />
					<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
					<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
					<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success" >
    		<div><b>Dữ liệu thông tin chấm kiểm KPIs tổ nhómhợp lệ ( ${successNum}/${totalNum} )</b></div>
    		
    		<div class = "tableStandar">
		    	<display:table name="${successList}"  id="item2" requestURI="" export="false" pagesize="700" >
					<display:column title="STT">
						<c:out value="${item2_rowNum}" />
					</display:column>
					<display:column property="dept" titleKey="Đài VT"/>
					<display:column property="team" titleKey="Tổ"/>
					<display:column property="teamGroups" titleKey="Nhóm"/>
					<display:column property="lostContactNum1Kh" titleKey="Số lần MLL tối đa trạm cấp 1"/>
					<display:column property="lostContactNum2Kh" titleKey="Số lần MLL tối đa trạm cấp 2"/>
					<display:column property="lostContactNum3Kh" titleKey="Số lần MLL tối đa trạm cấp 3"/>
					<display:column property="prcessTime1Kh" titleKey="TGXL MLL ban ngày trạm cấp 1"/>
					<display:column property="prcessTime2Kh" titleKey="TGXL MLL ban ngày trạm cấp 2"/>
					<display:column property="prcessTime3Kh" titleKey="TGXL MLL ban ngày trạm cấp 3"/>
					<display:column property="nightPrcessTime1Kh" titleKey="TGXL MLL ban đêm trạm cấp 1"/>
					<display:column property="nightPrcessTime2Kh" titleKey="TGXL MLL ban đêm trạm cấp 2"/>
					<display:column property="nightPrcessTime3Kh" titleKey="TGXL MLL ban đêm trạm cấp 3"/>
					 </display:table>
			</div>
		</div>
	</c:if>
    
	<table>
		<tr>
			<td>
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css">

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