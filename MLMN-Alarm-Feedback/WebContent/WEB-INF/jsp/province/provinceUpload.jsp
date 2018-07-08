<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleU}</title>
<content tag="heading">${titleU}</content>
 
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
							&lt;<fmt:message key="provincesCode.region"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="provincesCode.branch"/>&gt;,
							&lt;<fmt:message key="provincesCode.location"/>&gt;,
							&lt;<fmt:message key="provincesCode.code"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="provincesCode.province"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="provincesCode.district"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="provincesCode.districtName"/>&gt;<font color="red">(*)</font>,
							&lt;<fmt:message key="qLThongTinPhanAnh.phongDai"/>&gt;,
							&lt;<fmt:message key="qLThongTinPhanAnh.toXuLy"/>&gt;,
							&lt;<fmt:message key="hWards.ordering"/>&gt;,
							&lt;<fmt:message key="provincesCode.marks"/>&gt;.
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="HProvinceFbExample" href="${pageContext.request.contextPath}/upload/example/HProvinceFb.xls">HProvinceFb.xls</a>
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
    		<div><b>Dữ liệu thông tin quận/huyện không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" >
					<display:column class="centerColumnMana" titleKey="global.list.No" > <c:out value="${item1_rowNum}"/> </display:column>
					<display:column property="region" titleKey="provincesCode.region" class="NOT_NULL"/>
				    <display:column property ="branch" titleKey="provincesCode.branch"  />
				    <display:column property="location" titleKey="provincesCode.location" />
					<display:column property="code" titleKey="provincesCode.code" class="NOT_NULL"/>
				    <display:column property ="province" titleKey="provincesCode.province" class="NOT_NULL" />    
				    <display:column property="district" titleKey="provincesCode.district" class="NOT_NULL" />
				    <display:column property="districtName" titleKey="provincesCode.districtName" class="NOT_NULL" />
					<display:column property="deptCode" titleKey="qLThongTinPhanAnh.phongDai" />
					<display:column property="team" titleKey="qLThongTinPhanAnh.toXuLy" />
					<display:column class="rightColumnMana" property="ordering" titleKey="hWards.ordering" />
					<display:column property="marks" titleKey="provincesCode.marks" />
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu thông tin quận/huyện hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" >
					<display:column class="centerColumnMana" titleKey="global.list.No" > <c:out value="${item2_rowNum}"/> </display:column>
					<display:column property="region" titleKey="provincesCode.region" class="NOT_NULL"/>
				    <display:column property ="branch" titleKey="provincesCode.branch"  />
				    <display:column property="location" titleKey="provincesCode.location" />
					<display:column property="code" titleKey="provincesCode.code" class="NOT_NULL"/>
				    <display:column property ="province" titleKey="provincesCode.province" class="NOT_NULL" />    
				    <display:column property="district" titleKey="provincesCode.district" class="NOT_NULL" />
				    <display:column property="districtName" titleKey="provincesCode.districtName" class="NOT_NULL" />
					<display:column property="deptCode" titleKey="qLThongTinPhanAnh.phongDai" />
					<display:column property="team" titleKey="qLThongTinPhanAnh.toXuLy" />
					<display:column class="rightColumnMana" property="ordering" titleKey="hWards.ordering" />
					<display:column property="marks" titleKey="provincesCode.marks" />	
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
