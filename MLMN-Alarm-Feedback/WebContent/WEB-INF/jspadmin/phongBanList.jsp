<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="danh-sach.htm">
					<table border="0" cellspacing="3" cellpadding="0">
						<tr>
						
							<td class="wid9 mwid90">
					        	<fmt:message key="qLPhongBan.maPhong"/>
					      	</td>
					      	<td class="wid20">
					      		 <form:input path="deptCode" cssClass="wid90" />
					      	</td>
      	
							<td class="wid12 mwid90"><fmt:message key="qLPhongBan.tenVietTat"/></td>
							<td class="wid20">
								<form:input path="abbreviated" cssClass="wid90"/>
							</td>
							<td></td>			
						</tr>
						<tr>
							<td><fmt:message key="qLPhongBan.tenPhong"/></td>
							<td><form:input path="deptName" cssClass="wid90"/></td>
							
							<td><fmt:message key="qLPhongBan.trangThai"/></td>
							<td>
								<form:select path="isEnable" cssClass="wid90">
									<option value="">--Tất cả--</option>
				   					<c:forEach var="items" items="${trangThaiList}">
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
								value="<fmt:message key="button.search"/>" /></td>  
						</tr>				
					</table>
				</form:form>
				</td>
				
				<td>
				</td> 
		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid60" align="right">
				<a href="form.htm"><fmt:message key="button.add"/></a>&nbsp;
				<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
				</td> 
		</tr>
		
		<tr>
			<td colspan="2">
				<div style="width:100%;overflow:auto; ">
					<display:table name="${departmentList}" class="simple2" id="item" requestURI="" pagesize="50"  export="true" >
						
						
						<display:column property="stt" titleKey="qLNguoiDung.level" class="LEVEL_MENU centerColumnMana"/>
						<display:column property="deptCode" titleKey="qLPhongBan.maPhong" />
						<display:column property="abbreviated" titleKey="qLPhongBan.tenVietTat" />
						<display:column style="max-width:160px;word-wrap: break-word;" property="deptName" titleKey="qLPhongBan.tenPhong" />
						<display:column class="centerColumnMana" property="ordering" titleKey="qLPhongBan.sapXep" />
						<display:column property="phone" titleKey="qLPhongBan.dienThoai" />
						<display:column property="fax"  titleKey="qLPhongBan.fax" />
						<display:column property="address" titleKey="qLPhongBan.diaChi" />
						<display:column property="name" titleKey="qLPhongBan.trangThai" />
						<display:column property="region" titleKey="qLPhongBan.region" />
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
			<td></td>
		</tr>
</table>

<script type="text/javascript">
$(function() {
	${highlight}				
  	});
  	
	function focusIt()
	{
	  var mytext = document.getElementById("deptCode");
	  mytext.focus();
	}

	onload = focusIt;
</script>
