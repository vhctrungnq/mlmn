<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<title>${titleSystem}</title>
<content tag="heading">${titleSystem}</content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr> 
			<td align="left"><form:form commandName="filter" method="post" action="danh-sach.htm">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid8 mwid70"><fmt:message key="qLNguoiDung.phongBan"/></td>
							<td class="wid15">
								<select name="maPhong" class="wid90" id="selectMaPhong">
			           				<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${maPhongList}">
						              <c:choose>
						                <c:when test="${items.deptCode == maPhongCBB}">
						                    <option value="${items.deptCode}" selected="selected">${items.deptCode}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.deptCode}">${items.deptCode}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			           			</select>
			           		</td>
							<td class="wid8 mwid90">
								<fmt:message key="qLNguoiDung.taiKhoan"/>
							</td>
							<td class="wid15">
								<form:input path="username" cssClass="wid90" />
							</td>
							
							<td class="wid10 mwid140"><fmt:message key="qLNguoiDung.tenDayDu"/></td>
							<td class="wid15"><form:input path="fullname" cssClass="wid90" /></td>	
							<td class="wid8 mwid70">
								<fmt:message key="qLNguoiDung.dienThoai"/>
							</td>
							<td class="wid15">
								<form:input path="phone" cssClass="wid90" />
							</td>
						</tr>
						<tr>
							<td><fmt:message key="qLNguoiDung.email"/></td>
							<td><form:input path="email" cssClass="wid90" /></td>
							 
							<td>
								<fmt:message key="qLNguoiDung.trangThai"/>
							</td>
							<td>
								<form:select path="isEnable" class="wid90" id="selectIsEnable">
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
							<td>
								<fmt:message key="qLNguoiDung.quyenTaoND"/>
							</td>
							<td>
								<form:select path="rolesAddUsers" class="wid90" id="selectRolesAddUsers">
									<option value="">--Tất cả--</option>
			           				<c:forEach var="items" items="${quyenTaoNDList}">
						              <c:choose>
						                <c:when test="${items.value == quyenTaoNDCBB}">
						                    <option value="${items.value}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.value}">${items.name}</option>
						                </c:otherwise>
						              </c:choose>
								    </c:forEach>
			    				</form:select>
							</td>
							<td></td>
							<td><input class="button" type="submit" name="filter"
								value="<fmt:message key="global.form.timkiem"/>" /></td>
						</tr>			
					</table>
				</form:form>
				</td>
				
				<td>
				</td> 
		</tr>
		<tr>
			<td></td>
			<td class="wid10 mwid140" align="right">
				<c:choose>
				<c:when test="${userLogin.rolesAddUsers == 'Y'}">
					<a href="form.htm"><fmt:message key="global.form.themmoi"/></a>&nbsp;
					<a href="upload.htm"><fmt:message key="global.button.import"/></a>&nbsp;
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>
				</td> 
		</tr>
</table>
<div style="width:100%;overflow:auto; ">
	<display:table name="${usersList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" export="true">
		<display:column class="centerColumnMana" titleKey="global.list.STT">
			<c:out value="${item_rowNum}" />
		</display:column>
		<display:column property="username" titleKey="qLNguoiDung.taiKhoan" sortable="true" sortName="USERNAME"/>
		<display:column property="fullname" titleKey="qLNguoiDung.tenDayDu" sortable="true" sortName="FULLNAME"/>
		<display:column property="phone" titleKey="qLNguoiDung.dienThoai" sortable="true" sortName="PHONE"/>
		<display:column property="email" titleKey="qLNguoiDung.email" sortable="true" sortName="EMAIL"/>
		<%-- <display:column class="centerColumnMana" property="reNameEmail" titleKey="qLNguoiDung.nhanEmail" sortable="true" sortName="RECEIVING_EMAIL"/> --%>
		<display:column class="centerColumnMana" property="reNameSms"  titleKey="qLNguoiDung.nhanSms" sortable="true" sortName="RECEIVING_SMS"/>
		<display:column property="nameTrangThai" titleKey="qLNguoiDung.trangThai" sortable="true" sortName="IS_ENABLE"/>
		<display:column property="quyenTaoNd" titleKey="qLNguoiDung.quyenTaoND" sortable="true" sortName="ROLES_ADD_USERS"/>
		<display:column property="maPhong" titleKey="qLNguoiDung.phongBan" sortable="true" sortName="MA_PHONG"/>
		<display:column property="team" titleKey="qLNguoiDung.team" sortable="true" sortName="TEAM"/>
		<display:column property="subTeam" titleKey="qLNguoiDung.subTeam" sortable="true" sortName="SUB_TEAM"/>
		<c:choose>
			<c:when test="${userLogin.rolesAddUsers == 'Y'}">
			<display:column titleKey="global.management" media="html" class="centerColumnMana">
				<a href="details.htm?id=${item.id}"><fmt:message key="global.form.role"/></a>&nbsp;
				<a href="copyRole.htm?id=${item.id}"><fmt:message key="global.form.copy"/></a>&nbsp;
				<a href="form.htm?id=${item.id}"><fmt:message key="global.form.sua"/></a>&nbsp;
  					<a href="delete.htm?id=${item.id}"
					onclick="return confirm('<fmt:message key="messsage.confirm.delete"/>')"><fmt:message key="global.form.xoa"/></a>&nbsp;
				
  				</display:column>
  				</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
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
	  var mytext = document.getElementById("username");
	  mytext.focus();
	}
	onload = focusIt;
</script>
