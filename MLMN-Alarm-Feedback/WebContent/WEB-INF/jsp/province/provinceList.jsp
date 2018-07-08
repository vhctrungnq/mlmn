<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${title}</title>
<content tag="heading">${title}</content> 	


<form:form commandName="filter" method="post" action="list.htm">
	<table style="width:100%;" class="form">
		<tr>
			<td class="wid10 mwid110"><fmt:message key="provincesCode.code"/></td>
			 <td class="wid15">
            	<form:input path="code" id="code" class="wid90"/>
            </td>
            <td class="wid9 mwid110"><fmt:message key="provincesCode.branch"/></td>
			 <td class="wid15">
            	<form:input path="branch" id="branch" class="wid90"/>
            </td>
            <td class="wid9 mwid110"><fmt:message key="provincesCode.location"/></td>
			 <td class="wid15">
            	<form:input path="location" id="location" class="wid90"/>
            </td>
            <td></td>
         </tr>
         <tr>   
           <td><fmt:message key="qLThongTinPhanAnh.phongDai"/></td>
			 <td>
            	<select name="deptCode" id="deptCode" class="wid90">
					<option value=""><fmt:message key="global.All"/></option>
	           		<c:forEach var="item" items="${teamList}">
						<c:choose>
			                <c:when test="${item.deptCode == deptCode}">
			                    <option value="${item.deptCode}" selected="selected">${item.deptCode}</option>
			                </c:when>
							<c:otherwise>
								<option value="${item.deptCode}">${item.deptCode}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> 
            </td>
         	 <td><fmt:message key="provincesCode.province"/></td>
			 <td>
            	<form:input path="province" id="province" class="wid90"/>
             </td>
             <td><fmt:message key="provincesCode.district"/></td>
			 <td>
            	<form:input path="district" id="district" class="wid90"/>
				
            </td>
            <td><input type="submit" class="button" value="<fmt:message key="global.form.timkiem"/>"/></td>
        </tr>		
        <tr>
			<td class="ftsize12" align="right" colspan="7">
	            <a href="form.htm"><fmt:message key="button.add"/></a>
	            <a href="upload.htm"><fmt:message key="button.upload"/></a>
	        </td>
	    </tr>
	</table>
</form:form>
      
<div>
	<display:table name="${provinceList}"  class="simple2" id="province" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
	    <display:column class="centerColumnMana" titleKey="global.list.No" style="width:30px;"> <c:out value="${province_rowNum}"/> </display:column>
	    <display:column property="region" titleKey="provincesCode.region" sortable="true" sortName="REGION" />
	    <display:column property="branch" titleKey="provincesCode.branch" sortable="true" sortName="BRANCH" />
	    <display:column property="location" titleKey="provincesCode.location" sortable="true" sortName="LOCATION"/>
	    <display:column property="code" titleKey="provincesCode.code" sortable="true" sortName="CODE"/>  
	    <display:column property ="province" titleKey="provincesCode.province" sortable="true" sortName="PROVINCE"/>    
	    <display:column property="district" titleKey="provincesCode.district" sortable="true" sortName="DISTRICT"/>
		<display:column property="districtName" titleKey="provincesCode.districtName" sortable="true" sortName="DISTRICT_NAME"/>
		<display:column property="deptCode" titleKey="qLThongTinPhanAnh.phongDai" sortable="true" sortName="DEPT_CODE"/>
		<display:column property="team" titleKey="qLThongTinPhanAnh.toXuLy" sortable="true" sortName="TEAM"/>
	  	<display:column class="rightColumnMana" property ="ordering" titleKey="hWards.ordering" sortable="true" sortName="ORDERING" />
	  	<display:column property="marks" titleKey="provincesCode.marks" sortable="true" sortName="MARKS"/>
	    <display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
				<a href="form.htm?id=${province.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
   				<a href="delete.htm?id=${province.id}"
						onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
   		</display:column>
		<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		</display:table>
</div>

<script type="text/javascript">
function focusIt()
{
  var mytext = document.getElementById("code");
  mytext.focus();
}

onload = focusIt;
</script>