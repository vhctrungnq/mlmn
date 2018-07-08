<%@ include file="/includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
        // you can do this as a scriptlet on the page, but i put it into a taglib...
        org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
        subtotals.setGrandTotalDescription("&nbsp;");    // optional, defaults to Grand Total
        subtotals.setSubtotalLabel("&nbsp;", null);
        pageContext.getRequest().setAttribute("subtotaler", subtotals);
%>

<title><fmt:message key="feedback.title.timKiemBaoCaoXLPA"/></title>
<content tag="heading"><fmt:message key="feedback.title.timKiemBaoCaoXLPA"/></content>

<table border="0" width="100%" cellspacing="0" cellpadding="0" class="form">
		<tr>
			<td align="left"><form method="post" action="list.htm">
					<table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td class="wid10 mwid120">
								<fmt:message key="baoCaoXuLyPhanAnh.ngayTaoBaoCao"/>	
							</td>
							<td class="wid15">
								<input type="text" id="startDay" name="startDay" value="${startDay}" class="wid70" maxlength="30"/>
          						<img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid1 mwid40"><fmt:message key="qLThongTinPhanAnh.to"/></td>
							<td class="wid15">
								<input type="text" id="endDay" name="endDay" value="${endDay}" class="wid70" maxlength="30"/>
          						<img alt="calendar" title="Click to choose the end date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
							</td>
							<td class="wid8 mwid110"><spring:message code="qLThongTinPhanAnh.loaiPhanAnh"/></td>
							<td class="wid15">
								<div class="psr ovh select wid90">
									<select name="fbType" class="select" id="fbType">
										<option value="">--Tất cả--</option>
						 				<c:forEach var="items" items="${loaiPAList}">
						              	<c:choose>
						                <c:when test="${items.code == loaiPACBB}">
						                    <option value="${items.code}" selected="selected">${items.name}</option>
						                </c:when>
						                <c:otherwise>
						                    <option value="${items.code}">${items.name}</option>
						                </c:otherwise>
						              	</c:choose>
								    	</c:forEach>
							          </select>
							    </div>
							</td>
							<td class="wid8 mwid100"><spring:message code="qLThongTinPhanAnh.loaiMang"/></td>
							<td class="wid15">
								<div class="psr ovh select wid90">
									<select name="networkType" class="select" id="networkType">
										<option value="">--Tất cả--</option>
				          				<c:forEach var="items" items="${loaiMangList}">
							              	<c:choose>
							                <c:when test="${items.value == loaiMangCBB}">
							                    <option value="${items.value}" selected="selected">${items.name}</option>
							                </c:when>
							                <c:otherwise>
							                    <option value="${items.value}">${items.name}</option>
							                </c:otherwise>
							              	</c:choose>
								    	</c:forEach>
		          					</select>
		          				</div>
							</td>
							
						</tr>
						<tr>
							<td><spring:message code="baoCaoXuLyPhanAnh.nguyenNhan"/></td>
							<td colspan="7">
								<input class="wid98" type="text" id="nguyenNhan" name="nguyenNhan" value="${nguyenNhan}" />
							</td>
							<td class="wid20"><input class="button" type="submit" class="button" name="filter"
								value="<spring:message code="button.search"/>" /></td>
						</tr>				
					</table>
				</form>
			</td>
			<td></td> 
		</tr>
		<tr>
			<td colspan="2">
				
				<div style="width:100%;overflow:auto; ">
					<div style="width:100%;overflow:auto; ">
						<display:table name="${bcXuLyPAList}" class="simple2" id="item" requestURI="" pagesize="50" sort="external" defaultsort="1" decorator="subtotaler" export="true">
							<display:column property="fbName" titleKey="qLThongTinPhanAnh.loaiPhanAnh" total="true" group="1" style="border:0;border-left:1px solid #ddd;" />
							<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="title.xuLyPhanAnh.ngayBaoCao" />
							<display:column class="centerColumnMana" property="networkType" titleKey="qLThongTinPhanAnh.loaiMang"/>
							<display:column class="centerColumnMana" property="totalFb" titleKey="title.xuLyPhanAnh.tongFb" />
							<display:column class="centerColumnMana" property="ngayBaoCao" titleKey="qLThongTinPhanAnh.thoiGianXL" />
							<display:column style="max-width:250px;word-wrap: break-word;" property="causedby" titleKey="title.xuLyPhanAnh.nguyenNhan" />
							<display:column property="responseContent" titleKey="title.xuLyPhanAnh.cachXuLy" />			
							
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
				</div>
				
			</td>
			<td></td>
		</tr>
</table>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
Calendar.setup({
    inputField		:	"startDay",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

Calendar.setup({
    inputField		:	"endDay",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
    singleClick		:   false					// double-click mode
});

function focusIt()
{
  var mytext = document.getElementById("startDay");
  mytext.focus();
}

onload = focusIt;
</script>