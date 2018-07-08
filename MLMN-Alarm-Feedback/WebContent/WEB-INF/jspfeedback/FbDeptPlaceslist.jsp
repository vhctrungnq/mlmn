
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<title><fmt:message key="title.toXuLy.tieuDe.toXuLy"/></title>
<body class="section-4" />
	<form></form>
	
<content tag="heading"><fmt:message key="title.toXuLy.tieuDe.toXuLy"/></content>		
<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post"	action="list.htm">
					<table   border="0" cellspacing="1" cellpadding="0">
						<tr> 
							<td class="wid15 mwid110"><fmt:message key="title.toXuLy.phongBanToXuLy"/></td>
							<td class="wid30">
								<form:select path="deptCode" cssClass="wid90">
									<option value="">--Tất cả--</option>
			          				<c:forEach var="items" items="${DepartmentList}">
						              <c:choose>
						                <c:when test="${items.deptCode == departmentCBB}">
						                    <option value="${items.deptCode}" selected="selected">${items.abbreviated}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.deptCode}">${items.abbreviated}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
           						</form:select>
							</td>
							
							<td class="wid9 mwid60"><fmt:message key="qLThongTinPhanAnh.quan"/></td>
							<td class="wid30">
								<form:select path="placesCode" cssClass="wid90">
									<form:option value="">--Tất cả--</form:option>
   									<c:forEach var="items" items="${quanHuyenList}">
						              	<c:choose>
						                <c:when test="${items.code == tinhThanhCBB && items.district == quanHuyenCBB}">
						                    <option value="${items.code}//${items.district}" selected="selected">${items.districtName}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.code}//${items.district}">${items.districtName}</option>
						                </c:otherwise>
						              	</c:choose>
							    	</c:forEach>
								</form:select>
							</td> 
							
							<td><input class="button" type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>
					</table>
				</form:form>
				</td>
				<td></td> 
		</tr>
		<tr>	
			<td colspan="2" align="right">
	            <a href="form.htm"><spring:message code="button.add"/></a>
	        </td>
		</tr> 
		
		<tr>
			<td colspan="2">
				<div style="overflow-y: hidden; overflow-x: auto; width: 100%;">
						<display:table name="${FbDePlace}" id="item" class="simple2" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true" >
						    <display:column class="centerColumnMana" titleKey="global.list.STT"> <c:out value="${item_rowNum}"/> </display:column>
						    <display:column property="abbreviated" titleKey="title.toXuLy.phongBanToXuLy" sortable="true" sortName="ABBREVIATED"/>
		    				<display:column property="districtName" titleKey="qLThongTinPhanAnh.quan" sortable="true" sortName="DISTRICT_NAME"/>
		    				<display:column class="centerColumnMana" property="ordering" titleKey="title.toXuLy.sapXep" sortable="true" sortName="ORDERING"/>        
			    			
			    			<display:column titleKey="title.quanLy" media="html" class="centerColumnMana">
								<a href="form.htm?id=${item.id}"><spring:message code="button.edit"/></a>&nbsp;
			    				<a href="delete.htm?id=${item.id}"
										onclick="return confirm('<spring:message code="messsage.confirm.delete"/>')"><spring:message code="button.delete"/></a>&nbsp;
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
