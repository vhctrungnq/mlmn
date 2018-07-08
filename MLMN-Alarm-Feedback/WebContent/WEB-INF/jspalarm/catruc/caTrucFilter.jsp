<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
    /* .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
    	.ui-multiselect {
		width:255 !important;
	} */
	#caTK {
     visibility: visible !important;
    }
    
</style>

<title>${titleTK}</title>
<content tag="heading">${titleTK}</content>
<div>
<form:form commandName="catruc" method="post" id ="filter" name="filter" action="listFilter.htm?region=${region}"> 	
<input type="hidden" value="${warningTp}" name="warningTp" id="warningTp"> 

<table>
	<tr>

		<td style="width:70px;">
			<fmt:message key="catruc.ngayTKF"/>
		</td>
		<td>
			<input type="text" value="${ngayTK}" name="ngayTK" id="ngayTK" size="10" maxlength="10">
			<img alt="calendar" title="Click to choose the start date" id="choosengayTK" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						
		</td>
		<td style="width:40px;">
			<fmt:message key="catruc.ngayTKTO"/>
		</td>
		<td>
			<input type="text" value="${ngayTKTo}" name="ngayTKTo" id="ngayTKTo" size="10" maxlength="10">
			<img alt="calendar" title="Click to choose the start date" id="choosengayTKTo" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						
		</td>
		<td style="width:50px;padding-left: 10px;">
			<fmt:message key="catruc.caTK"/>
		</td>
		<td>
			<select name="caTK" id="caTK" style="width: 80px;height:20px; padding-top: 4px;">
				<option value=""><fmt:message key="global.shiftAll"/></option>
				<c:forEach var="item" items="${caList}">
					<c:choose>
		                <c:when test="${item.value == caTK}">
		                    <option value="${item.value}" selected="selected">${item.value}</option>
		                </c:when>
						<c:otherwise>
							<option value="${item.value}">${item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select> 
		</td>
		<td style="padding-left: 10px;"><input type="submit" class="button" id = "btTimKiem" value="<fmt:message key="button.search"/>" /></td>
		<td style="padding-left: 30px; font-weight: bold;">
			<span>${caTruongTK}</span>
		</td>
	</tr>
</table>
</form:form>
</div>
<br/>
<div>
	<div align="left">
		<ul class="ui-tabs-nav" >
		<div align="left" >
		<c:choose>
			<c:when test="${warningTp == 'WOTK_SHIFT'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			
			</c:when>
		 	<c:when test="${warningTp == '2G'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
		 	<c:when test="${warningTp == '3G'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when> 
			<c:when test="${warningTp == 'PS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'CS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'IPBB'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'SU_CO_LON'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'SU_CO_KHAC'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'LOI_TRUYEN_DAN'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'ALARM_EXTEND'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'TEMPERATURE_SITE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'NOI_DUNG'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=WOTK_SHIFT&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				 <li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=2G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=3G&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBao3G"/></span></a></li> 
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=PS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=CS_CORE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=IPBB&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_LON&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=SU_CO_KHAC&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoSuCoKhac"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=LOI_TRUYEN_DAN&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.canhBaoLoiTruyenDan"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/listFilter.htm?warningTp=NOI_DUNG&ngayTK=${ngayTK}&ngayTKTo=${ngayTKTo}&caTK=${caTK}&region=${region}"><span><fmt:message key="tab.bangiaokhac"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</div>
		</ul>
	</div>
</div>

<div class="clear"></div>
<table style="width:100%">
	<tr>
		<td align="left"><h2>
				<c:choose>
						<c:when test="${warningTp == 'WOTK_SHIFT'}">
							<fmt:message key="catruc.congvieccodinh"/>
						</c:when>
						<c:when test="${warningTp == '2G'}">
							<fmt:message key="catruc.canhBao2G"/>
						</c:when>
						<c:when test="${warningTp == '3G'}">
							<fmt:message key="catruc.canhBao3G"/>
						</c:when>
						<c:when test="${warningTp == 'PS_CORE'}">
							<fmt:message key="catruc.canhBaoPSCore"/>
						</c:when>
						<c:when test="${warningTp == 'CS_CORE'}">
							<fmt:message key="catruc.canhBaoCSCore"/>
						</c:when>
						<c:when test="${warningTp == 'IPBB'}">
							<fmt:message key="catruc.canhBaoIPBB"/>
						</c:when>
						<c:when test="${warningTp == 'SU_CO_LON'}">
							<fmt:message key="catruc.canhBaoSuCoLon"/>
						</c:when>
						<c:when test="${warningTp == 'ALARM_EXTEND'}">
							<fmt:message key="catruc.congViecHieuChinhMR"/>
						</c:when>
						<c:when test="${warningTp == 'TEMPERATURE_SITE'}">
							<fmt:message key="catruc.gsnhietdoSite"/>
						</c:when>
						<c:when test="${warningTp == 'NOI_DUNG'}">
							<fmt:message key="catruc.bangiaokhac"/>
						</c:when>
						<c:when test="${warningTp == 'LOI_TRUYEN_DAN'}">
							<fmt:message key="catruc.loiTruyenDan"/>
						</c:when>
						<c:when test="${warningTp == 'SU_CO_KHAC'}">
							<fmt:message key="catruc.suCoKhac"/>
						</c:when>
					</c:choose>
		
		</h2></td>
		<td style="width: 30px"><div style="float: right;" id="jqxlistbox"></div></td>
	</tr>
</table>
<div id="grid"></div>
<div id='menujqx'>
            <ul>
            	<%-- <c:if test="${checkCaTruc==true&& warningTp!='NOI_DUNG'}">	
					<li><fmt:message key="global.button.editSelected"/></li>
		   		</c:if> --%>
		   		<c:if test="${warningTp == 'WOTK_SHIFT'&&checkTruongCa==true}">	
		   			<%-- <li><fmt:message key="global.button.deleteSelected"/></li> --%>
		   			<li><fmt:message key="global.button.editSelected"/></li>
				</c:if>
				<c:if test="${checkCaTruc==true&&warningTp == 'WOTK_SHIFT'}">	
					<li><fmt:message key="global.button.updateWorkProgress"/></li>
				</c:if>
            	<c:if test="${checkCaTruc==true&&warningTp != 'WOTK_SHIFT'&&warningTp != 'NOI_DUNG'}">		
            		<%-- <li><fmt:message key="global.button.deleteSelected"/></li>	 --%>	
            		<li><fmt:message key="global.button.editSelected"/></li>
				</c:if>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
	<br/>
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<c:if test="${warningTp == 'WOTK_SHIFT'}">	
<table style="width:100%">
	
	<tr>
		<td align="left"><h2>
				<fmt:message key="catruc.congvieccanlam"/>
		</h2></td>
		<td style="width: 30px"><div style="float: right;" id="jqxlistbox2"></div></td>
	</tr>
</table>
<div id="grid2"></div>
<div id='menujqx2'>
            <ul>
            	<c:if test="${warningTp == 'WOTK_SHIFT'&&checkTruongCa==true}">	
		   			<%-- <li><fmt:message key="global.button.deleteSelected"/></li> --%>
		   			<li><fmt:message key="global.button.editSelected"/></li>
				</c:if>
				<c:if test="${checkCaTruc==true}">		
            		<li><fmt:message key="global.button.updateWorkProgress"/></li>
				</c:if>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
	<br/>


</c:if>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>
	
<script type="text/javascript">
var theme =  getTheme(); 
$('#btTimKiem').jqxButton({ theme: theme });
var warningTp =  "<c:out value='${warningTp}'/>";
var region =  "<c:out value='${region}'/>";
if (warningTp =='WOTK_SHIFT')
{
	${grid2}
	$("#menujqx2").on('itemclick', function (event) {
	    var args = event.args;
	    var exportFileName =  "<c:out value='${exportFileName2}'/>";
	   var rowindex = $("#grid2").jqxGrid('getselectedrowindex');
	    var row = $("#grid2").jqxGrid('getrowdata', rowindex);
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {	
	    		//window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=';   
	    		window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?id='+row.id+'&note=shif&region='+region;    
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.updateWorkProgress"/>')  {
	    	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=&region=cvcd&region='+region;   
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/working/delete.htm?idList='+row.id+'&type=&note=filter&region='+region; 
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#grid2").jqxGrid('exportdata', 'xls', exportFileName);
	    }
	});
}
${grid}

$("#menujqx").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
    var rowindex = $("#grid").jqxGrid('getselectedrowindex');
    var row = $("#grid").jqxGrid('getrowdata', rowindex);
    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    	if (warningTp=='2G'||warningTp=='3G'||warningTp=='SU_CO_LON'||warningTp=='SU_CO_KHAC'||warningTp=='LOI_TRUYEN_DAN')
   		{
   			window.location = 'form23G.htm?id='+row.id+'&warningTp='+warningTp+'&note=filter'+'&region='+region;   
   		}
    	
   		else if (warningTp=='WOTK_SHIFT')
      	{
   		//window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=';   
    		window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?id='+row.id+'&note=shif&region='+region    
   
       	}
    	else if (warningTp=='PS_CORE'||warningTp=='CS_CORE'||warningTp=='IPBB')
   		{
    		window.location = '${pageContext.request.contextPath}/warning/form.htm?id='+row.id+'&warningTp='+warningTp+'&note=filter'+'&region='+region;
   		}
    	else if (warningTp=='ALARM_EXTEND')
   		{
    		window.location = '${pageContext.request.contextPath}/alarmExtend/form.htm?id='+row.id+'&note=filter'+'&region='+region;   
   		}
    	else if (warningTp=='TEMPERATURE_SITE')
   		{
    		window.location = '${pageContext.request.contextPath}/monitor/temperatureForm.htm?id='+row.id+'&note=filter'+'&region='+region;
   		}
   	  	
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
    	if (answer)
    	{
    		if (warningTp=='2G'||warningTp=='3G'||warningTp=='SU_CO_LON'||warningTp=='SU_CO_KHAC'||warningTp=='LOI_TRUYEN_DAN')
       		{
    			window.location = 'delete23G.htm?id='+row.id+'&warningTp='+warningTp+'&note=filter&region='+region;  
       		}
    		else if (warningTp=='PS_CORE'||warningTp=='CS_CORE'||warningTp=='IPBB')
       		{
    			window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp='+warningTp+'&note=filter&region='+region;  
       		}
    		else if (warningTp=='ALARM_EXTEND')
       		{
    			window.location = '${pageContext.request.contextPath}/alarmExtend/delete.htm?id='+row.id+'&note=filter&region='+region;  
       		}
    		else if (warningTp=='TEMPERATURE_SITE')
       		{
    			window.location = '${pageContext.request.contextPath}/monitor/temperaturedelete.htm?idList='+row.id+'&note=filter&region='+region; 
       		}
    	}
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    
    	window.open('${pageContext.request.contextPath}/caTruc/export.htm?warningtype='+warningTp+'&shiftid='+"<c:out value='${caTK}'/>"+
   	 			'&ngayTKTo='+"<c:out value='${ngayTK}'/>"+
   	 			'&ngayTK='+"<c:out value='${ngayTKTo}'/>"+
   	 			'&type='+"<c:out value='${type}'/>"+
   	 			'&exportFileName='+exportFileName+
	 			'&region='+"<c:out value='${region}'/>"
   			 ,'_blank');
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.updateWorkProgress"/>')  {
    	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=cvcl&region='+region;  
    }
});

</script>
<script type="text/javascript">
Calendar.setup({
    inputField		:	"ngayTK",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayTK",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});


Calendar.setup({
    inputField		:	"ngayTKTo",	// id of the input field
    ifFormat		:	"%d/%m/%Y",   	// format of the input field
    button			:   "choosengayTKTo",  	// trigger for the calendar (button ID)
    showsTime		:	true,
    singleClick		:   false					// double-click mode
});
</script>