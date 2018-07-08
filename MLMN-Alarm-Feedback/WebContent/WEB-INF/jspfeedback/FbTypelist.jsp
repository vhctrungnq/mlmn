<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title><fmt:message key="sidebar.admin.feedbacksTypes"/></title>
<content tag="heading"><fmt:message key="sidebar.admin.feedbacksTypes"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left" colspan="2"><form:form commandName="filter" method="post"	action="list.htm">
					<table border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td class="wid10 mwid90"><fmt:message key="qLLoaiPhanAnh.maPhanAnh"/></td>
							<td class="wid20"><form:input path="code" cssClass="wid90"/></td>
							
							<td class="wid10 mwid90"><fmt:message key="qLLoaiPhanAnh.tenPhanAnh"/></td>
							<td class="wid20"><form:input path="name" cssClass="wid90"/></td>
							
							<td class="wid10 mwid90"><fmt:message key="qLLoaiPhanAnh.trangThai"/></td>
							<td class="wid20">
								<form:select path="isEnable" cssClass="wid90">
									<form:option value="">--Tất cả--</form:option>
   									<c:forEach var="items" items="${sysParameterByCodeList}">
						              <c:choose>
						                <c:when test="${items.value == trangThaiCBB}">
						                    <option value="${items.value}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.name}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
           						</form:select>
							</td>
							 
							<td><input class="button" type="submit" class="button" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>				
					</table>
				</form:form>
				</td>

		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid70" align="right">
				<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
				</td> 
		</tr>
		
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${Fbtype}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
						<display:column class="centerColumnMana" titleKey="global.list.No"> <c:out value="${item_rowNum}"/> </display:column>
						<display:column style="max-width:130px;word-wrap: break-word;" property="code" titleKey="qLLoaiPhanAnh.maPhanAnh" sortable="true" sortName="CODE"/>
						<display:column style="max-width:260px;word-wrap: break-word;" property="name" titleKey="qLLoaiPhanAnh.tenPhanAnh" sortable="true" sortName="NAME"/>
						<display:column class="centerColumnMana" property="ordering" titleKey="qLLoaiPhanAnh.sapXep" sortable="true" sortName="ORDERING"/>
						<display:column property="nameTrangThai" titleKey="qLLoaiPhanAnh.trangThai" sortable="true" sortName="IS_ENABLE"/>
						<display:column style="max-width:240px;word-wrap: break-word;" property="description"  titleKey="qLLoaiPhanAnh.dienGiai" sortable="true" sortName="DESCRIPTION"/>
						<display:column titleKey="global.management" media="html" class="centerColumnMana">
							<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
							<a href="delete.htm?id=${item.id}"
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
			</td>
		</tr>
</table>

<script type="text/javascript">
	
	function focusIt()
	{
	  var mytext = document.getElementById("code");
	  mytext.focus();
	}

	onload = focusIt;
</script>
