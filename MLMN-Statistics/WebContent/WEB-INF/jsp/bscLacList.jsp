<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Danh sách LAC theo BSC</title>
<content tag="heading">Danh sách LAC theo BSC</content>

<form:form commandName="filter" method="post" action="list.htm">
	<table style="width:100%; border:0; cellspacing:3; cellpadding:0;">
		    <tr>
				<td class="mwid70">Vendor</td>
				<td class="wid20">
					<form:select path="vendor" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${vendorList}">
							<c:choose>
								<c:when test="${item.name == vendor}">
									<form:option  value="${item.name}" selected="selected">${item.value}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.name}">${item.value}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
				</td>
				<td class="mwid70">Chi nhánh</td>
				<td class="wid20">
					<form:select path="cn" class="wid90" onchange="xl()"> 
						<form:option  value="">Tất cả</form:option >
						<c:forEach var="item" items="${branchList}">
							<c:choose>
								<c:when test="${item.name == cn}">
									<form:option  value="${item.name}" selected="selected">${item.value}</form:option >
								</c:when>
								<c:otherwise>
									<form:option  value="${item.name}">${item.value}</form:option >
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select> 
				</td>
				<td class="mwid70">BSC</td>
				<td class="wid20">
					<form:input type="text" path="bscid" class="wid90"/>
				</td> 
				<td class="mwid70">LAC</td>
				<td class="wid20">
					<form:input type="text" path="lac" class="wid90"/>
				</td>
				<td>
					<input type="submit" class="button" id="submit" name="submit" value="Search"/>
				</td> 	
				<td align="right" class="wid10 mwid110">
		            <a href="upload.htm">Import</a>&nbsp;
		            <a href="form.htm">Add New</a>&nbsp;
		        </td>
		    </tr>  
	</table> 
	<div  style="overflow: auto;">
		<display:table name="${lacList}" id="bsc" requestURI="" pagesize="100" class="simple2" export="false" sort="external" defaultsort="1">				   
			<display:column class="rightColumnMana" title="STT" > <c:out value="${bsc_rowNum}"/> </display:column>
			<display:column property="region" titleKey="Region"/>   
			<display:column property="vendor" titleKey="Vendor"/>
		    <display:column property="cn" titleKey="Chi nhánh" />
		    <display:column property="bscid" title="BSC"/>  
		    <display:column class="rightColumnMana" property="lac" titleKey="LAC" />
		    <display:column class="rightColumnMana" property="rac" titleKey="RAC" />
		    <display:column titleKey="Quản lý" media="html">
		    	<a href="form.htm?bscid=${bsc.bscid}&lac=${bsc.lac}">Edit</a>&nbsp;
		    	<a href="delete.htm?bscid=${bsc.bscid}&lac=${bsc.lac}"
		    	   onclick="return confirm('Bạn có chắc muốn xóa?')" >Delete</a>&nbsp;
		    </display:column>
		</display:table>
	</div>
</form:form> 
<script type="text/javascript">
function xl(){
	var sub = document.getElementById("filter");
	sub.focus();
}
</script>
