<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQWidgets/jqwidgets/jqxwindow.js"></script>
    
<style type="text/css">
    .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
    	.ui-multiselect {
		width:255 !important;
	}
	
	
 .blue
{
color:blue;
}
</style>

<title>${title}</title>
<content tag="heading">${title}</content>

<!-- -------------------------------------------------------------------------
------------------------------------------------------------------------- -->	
<div>
	<div align="left">
		<ul class="ui-tabs-nav" >
		<div align="left" >
		<c:choose>
			<c:when test="${warningTp == 'WOTK_SHIFT'}">
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			
			</c:when>
			<c:when test="${warningTp == '2G'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
		 	<c:when test="${warningTp == '3G'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'PS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'CS_CORE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'IPBB'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'SU_CO_LON'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'ALARM_EXTEND'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
			<c:when test="${warningTp == 'TEMPERATURE_SITE'}">
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:when>
		 	<c:otherwise>
		 		<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=WOTK_SHIFT"><span><fmt:message key="tab.congvieccodinh"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=2G"><span><fmt:message key="tab.canhBao2G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=3G"><span><fmt:message key="tab.canhBao3G"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=CS_CORE"><span><fmt:message key="tab.canhBaoPSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=PS_CORE"><span><fmt:message key="tab.canhBaoCSCore"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=IPBB"><span><fmt:message key="tab.canhBaoIPBB"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=SU_CO_LON"><span><fmt:message key="tab.canhBaoSuCoLon"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=ALARM_EXTEND"><span><fmt:message key="tab.congViecHieuChinhMR"/></span></a></li>
				<li class=""><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=TEMPERATURE_SITE"><span><fmt:message key="tab.gsnhietdoSite"/></span></a></li>
				<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/caTruc/list.htm?warningTp=BAN_GIAO_CA"><span><fmt:message key="tab.bangiaoca"/></span></a></li>
			</c:otherwise>
		</c:choose>
		</div>
		</ul>
	</div>
</div>
<div class="clear"></div>
<c:if test="${warningTp != 'BAN_GIAO_CA'}">	
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
					</c:choose>
		
		</h2></td>
		<td align="right" class="ftsize12">
		<c:if test="${warningTp == 'WOTK_SHIFT' && checkTruongCa==true}">
				<input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/working/form.htm?id=&type=cvcd&note=caTruc"' />
		</c:if>
		<c:if test="${checkCaTruc==true}">	
			<c:choose>
				<c:when test="${warningTp == '2G'}">
					<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=2G&ca=${ca}&ngayTruc=${ngayTruc}"' />
					<input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="form23G.htm?warningTp=2G"' />
				</c:when>
				<c:when test="${warningTp == '3G'}">
					<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>"  onclick='window.location="updateEnd.htm?warningTp=3G&ca=${ca}&ngayTruc=${ngayTruc}"' />
					<input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="form23G.htm?warningTp=3G"' />
				</c:when>
				<c:when test="${warningTp == 'PS_CORE'}">
					<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>"  onclick='window.location="updateEnd.htm?warningTp=PS_CORE&ca=${ca}&ngayTruc=${ngayTruc}"' />
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=PS_CORE&note=caTruc"' />
				</c:when>
				<c:when test="${warningTp == 'CS_CORE'}">
					<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=CS_CORE&ca=${ca}&ngayTruc=${ngayTruc}"' />
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=CS_CORE&note=caTruc"' />
				</c:when>
				<c:when test="${warningTp == 'IPBB'}">
					<input class="button"  type="button" id="btUpdate" value="<fmt:message key="global.button.UpdateEnd"/>" onclick='window.location="updateEnd.htm?warningTp=IPBB&ca=${ca}&ngayTruc=${ngayTruc}"' />
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/warning/form.htm?warningTp=IPBB&note=caTruc"' />  
				</c:when>
				<c:when test="${warningTp == 'SU_CO_LON'}">
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>"  onclick='window.location="form23G.htm?warningTp=SU_CO_LON"' />
				</c:when>
				<c:when test="${warningTp == 'ALARM_EXTEND'}">
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/alarmExtend/form.htm?note=caTruc"' />
		 		</c:when>
				<c:when test="${warningTp == 'TEMPERATURE_SITE'}">
					<input class="button" type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/monitor/temperatureForm.htm?note=caTruc"' />
		 		</c:when>
			</c:choose>
		</c:if>
		</td>
		<td style="width: 30px"><div style="float: right;" id="jqxlistbox"></div></td>
	</tr>
</table>
<div id="grid"></div>
<div id='menujqx'>
            <ul>
            	<c:if test="${warningTp == 'WOTK_SHIFT'&&checkTruongCa==true}">	
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
		   			<li><fmt:message key="global.button.editSelected"/></li>
				</c:if>
				<c:if test="${checkCaTruc==true&&warningTp == 'WOTK_SHIFT'}">	
					<li><fmt:message key="global.button.updateWorkProgress"/></li>
				</c:if>
            	<c:if test="${checkCaTruc==true&&warningTp != 'WOTK_SHIFT'}">		
            		<li><fmt:message key="global.button.deleteSelected"/></li>		
            		<li><fmt:message key="global.button.editSelected"/></li>
				</c:if>
				<c:if test="${warningTp=='SU_CO_LON'||warningTp=='ALARM_EXTEND'||warningTp=='TEMPERATURE_SITE'}">		
            		<li><fmt:message key="global.button.sendMessage"/></li>
				</c:if>
				<li><fmt:message key="global.button.exportExcel"/></li>
	        </ul>
 </div>
	<br/>

<div id="jqxwindow">
		<div><fmt:message key="alShift.titleSend"/></div>
		<div>
       	<table class="simple2">
       		<tr>
       			<td style="width:70px;"><fmt:message key="alShift.sendTo"/></td>
				<td style="width:120px;">
					<div id="sendToCb" align="left"></div>
				</td>
				<td><input type="text" name="sendTo" id="sendTo"/></td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.title"/></td>
				<td colspan="2">
					 <div id="title"></div>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.content"/></td>
				<td colspan="2">
					<textarea rows="3" cols="85" id="content" name="content" style="width: 100%;"></textarea>
       			</td>
       		</tr>
       		<tr>
       			<td></td>
       			<td colspan="2">
       				<input type="button" class="button" value="Send" id="btSend" />
                    <input type="button" class="button" value="Cancel" id="btCancel" />
                </td>
       		</tr>
       </table>
     </div>
</div>
</c:if>
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<c:if test="${warningTp == 'WOTK_SHIFT'}">	
<table style="width:100%">
	
	<tr>
		<td align="left"><h2>
				<fmt:message key="catruc.congvieccanlam"/>
		</h2></td>
		<td align="right" class="ftsize12">
		<c:if test="${warningTp == 'WOTK_SHIFT' && checkTruongCa==true}">
				<input class="button"  type="button" id="btAdd" value="<fmt:message key="global.button.addNew"/>" onclick='window.location="${pageContext.request.contextPath}/working/form.htm?id=&type=&note=caTruc"' />
		</c:if>
		</td>
		<td style="width: 30px"><div style="float: right;" id="jqxlistbox2"></div></td>
	</tr>
</table>
<div id="grid2"></div>
<div id='menujqx2'>
            <ul>
            	<c:if test="${warningTp == 'WOTK_SHIFT'&&checkTruongCa==true}">	
		   			<li><fmt:message key="global.button.deleteSelected"/></li>
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
<!-- -------------------------------------------------------------------------
-------------------------------------------------------------------------	 -->
<c:if test="${warningTp == 'BAN_GIAO_CA'}">	
<h2><fmt:message key="catruc.chuyenGiaoCaTruc"/></h2>
	<div style="display:none;" id="openCloseIdentifier_giaoCa"></div>
	<div id="giaoCa" >
	<form:form commandName="catruc" method="post" id ="foo" name="foo" action="list.htm?warningTp=BAN_GIAO_CA"> 	
		<form:hidden path="shiftId"/>
		<input type="hidden" value="${xacnhanTC}" name="xacnhanTC" id="xacnhanTC">
		<font color="red">${errorBanGiao}</font>
				<table class="simple2"> 
			      <tr>
			           <td colspan="7">
			           		<b><span><fmt:message key="catruc.banGiaoKhac"/><font color="red">(*)</font></span></b>
			           </td>
			      </tr>
			      <tr>
			      		
			      		<td colspan="7"> 
			      		<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/ckeditor.js"></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/editortext/sample.js"></script>
		           		<textarea style="width:100%; height: 220px" name="noiDung" id="noiDung" value="${noiDung}" maxlength="900" ></textarea>
						 <script type="text/javascript">
							CKEDITOR.replace( 'noiDung',
							{
								toolbar :
									[
										['NewPage','-','Undo','Redo'],
										['Find','Replace','-','SelectAll','RemoveFormat'],
										['Link', 'Unlink', 'Image'],
										['FontSize', 'Bold', 'Italic','Underline'],
										['NumberedList','BulletedList','-','Blockquote'],
										['TextColor', '-', 'Smiley','SpecialChar', '-', 'Maximize']
									]
							});
					  	</script>
		        	</td>
			      </tr>
				  <tr>
				  	 <td colspan="7">
			           		<b><span><fmt:message key="catruc.kyNhanGiaoCa"/></span></b>
			          </td>
				  </tr>
				  <tr>
				  	 <td colspan="4">
			           		<b><span><fmt:message key="catruc.benBanGiao"/></span></b>
			          </td>
			          <td colspan="3">
			           		<b><span><fmt:message key="catruc.benNhanCa"/></span></b>
			          </td>
				  </tr>
				  <tr>
				  	<td><fmt:message key="catruc.giaoUsername"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  	<c:choose>
			                <c:when test="${first==false}">
			                   ${catruc.giaoUsername}<form:hidden path="giaoUsername" maxlength="100"/>
			                </c:when>
							<c:otherwise>
								<form:input path="giaoUsername" name="giaoUsername" id="giaoUsername" maxlength="100" style="width:255px;"/>
						
							</c:otherwise>
					</c:choose>
				  	<%-- 	<form:input path="giaoUsername" maxlength="50" style="width:255px;" value="${giaoUsername}"/> --%>
				  		
				  		<font color="red"><form:errors path="giaoUsername" /></font>
				  	</td>
				  	<td><fmt:message key="catruc.nhanUsername"/><font color="red">(*)</font></td>
				  	<td colspan="2">
						<form:input path="nhanUsername" name="nhanUsername" id="nhanUsername" maxlength="100" style="width:255px;"/>
						<font color="red"><form:errors path="nhanUsername" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.giaoCaVien"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<c:choose>
			                <c:when test="${first==false}">
			                  ${catruc.giaoCaVien}<form:hidden path="giaoCaVien" maxlength="100"/>
			                </c:when>
							<c:otherwise>
								<!-- <select name="giaoCaVien" id="giaoCaVien" multiple="multiple" style="width:100%;"></select>  -->
							<form:input path="giaoCaVien" name="giaoCaVien" id="giaoCaVien" maxlength="100" style="width:255px;"/>
				  		
							</c:otherwise>
						</c:choose>
				  		<font color="red"><form:errors path="giaoCaVien" /></font>
				  	</td>
				  	<td><fmt:message key="catruc.nhanCaVien"/><font color="red">(*)</font></td>
				  	<td colspan="2">
				  		<!-- <select name="nhanCaVien" id="nhanCaVien" multiple="multiple" style="width:250px;"></select>  -->
				  		<form:input path="nhanCaVien" name="nhanCaVien" id="nhanCaVien" maxlength="100" style="width:255px;"/>
				  		<font color="red"><form:errors path="nhanCaVien" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.ca"/><font color="red">(*)</font></td>
				  	
			  		<c:choose>
		                <c:when test="${first==false}">
			                <td colspan="3">
			                   ${catruc.giaoCaTruc}<form:hidden path="giaoCaTruc" maxlength="50"/>,${giaoNgayTruc}<form:hidden path="giaoNgayTruc" name="giaoNgayTruc" id="giaoNgayTruc" maxlength="50"/>
			                </td>
		                </c:when>
						<c:otherwise>
						<td>
							<form:select path="giaoCaTruc"  style="width: 80px;height:20px; padding-top: 4px;">
								<c:forEach var="item" items="${caList}">
									<c:choose>
						                <c:when test="${item.name == giaoCaTruc}">
						                    <option value="${item.name}" selected="selected">${item.value}</option>
						                </c:when>
										<c:otherwise>
											<option value="${item.name}">${item.value}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select> 
							<font color="red"><form:errors path="giaoCaTruc" /></font>
						</td>
						<td colspan="2">
							&nbsp;<input type="text" value="${giaoNgayTruc}" name="giaoNgayTruc" id="giaoNgayTruc" size="25" maxlength="10">
							 <img alt="calendar" title="Click to choose the start date" id="choosegiaoNgayTruc" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
					  		<font color="red">${errorStartTime}<form:errors path="giaoNgayTruc" /></font>
						</td>
						</c:otherwise>
					</c:choose>
					
				  	<td><fmt:message key="catruc.ca"/><font color="red">(*)</font></td>
				  	<td style="width: 80px;">
				  		<form:select path="nhanCaTruc"  style="width: 80px;height:20px; padding-top: 4px;">
							<c:forEach var="item" items="${caList}">
								<c:choose>
					                <c:when test="${item.value == nhanCaTruc}">
					                    <option value="${item.value}" selected="selected">${item.value}</option>
					                </c:when>
									<c:otherwise>
										<option value="${item.value}">${item.value}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select> 
						<font color="red"><form:errors path="nhanCaTruc" /></font>
					</td>
					<td>
						&nbsp;<input type="text" value="${nhanNgayTruc}" name="nhanNgayTruc" id="nhanNgayTruc" onformchange="functionNextShift()"  size="25" maxlength="10">
				  	 	<img alt="calendar" title="Click to choose the start date" id="chooseNhanNgayTruc"  style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
						<font color="red">${errorEndTime}<form:errors path="nhanNgayTruc" /></font>
				  	</td>
				  </tr>
				   <tr>
				  	<td><fmt:message key="catruc.xacnhan"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<input type="password" value="${passwordGiao}" name="passwordGiao" id="passwordGiao" size="40" maxlength="50">
			        </td>
				  	<td><fmt:message key="catruc.xacnhan"/><font color="red">(*)</font></td>
				  	<td colspan="3">
				  		<input type="password" value="${passwordNhan}" name="passwordNhan" id="passwordNhan" size="40" maxlength="50">
				  	</td>
				  </tr>  
			       <tr>
			           <td colspan="8" align="center">
			               	<input type="submit" class="button" id = "btxacnhan"  value="<fmt:message key="button.xacnhan"/>" />
			            	<input type="button" id="btSendSMS" name="btSendSMS" class="button" value="<fmt:message key="button.sendSMS"/>"> 
			            </td>
			       </tr>
			    </table>
			
			</form:form>
		</div>
<div id="jqxwindow">
		<div><fmt:message key="alShift.titleSend"/></div>
		<div>
       	<table class="simple2">
       		<tr>
       			<td style="width:70px;"><fmt:message key="alShift.sendTo"/></td>
				<td style="width:120px;">
					<div id="sendToCb" align="left"></div>
				</td>
				<td><input type="text" name="sendTo" id="sendTo"/></td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.title"/></td>
				<td colspan="2">
					 <div id="title"></div>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="alShift.content"/></td>
				<td colspan="2">
					<textarea rows="3" cols="85" id="content" name="content" style="width: 100%;"></textarea>
       			</td>
       		</tr>
       		<tr>
       			<td></td>
       			<td colspan="2">
       				<input type="button" class="button" value="Send" id="btSend" />
                    <input type="button" class="button" value="Cancel" id="btCancel" />
                </td>
       		</tr>
       </table>
     </div>
</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadifive/jquery.uploadifive.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/uploadifive/uploadifive.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

	<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"giaoNgayTruc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "choosegiaoNgayTruc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
	Calendar.setup({
	    inputField		:	"nhanNgayTruc",	// id of the input field
	    ifFormat		:	"%d/%m/%Y",   	// format of the input field
	    button			:   "chooseNhanNgayTruc",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	});
	
	</script>
	<script type="text/javascript">

function setAction(value) {
	  var action = document.getElementById("action");
	  action.value = value;

	  return true;
	 }
	 
function hiddenClick(id) {
	$("#" + id).slideToggle();

	if ($("#openCloseIdentifier_" + id).is(":hidden")) {
		$("#openCloseIdentifier_" + id).show();
	}
	else {
		$("#openCloseIdentifier_" + id).hide();
	}
}
$(document).ready(function(){
	
	var xacnhanTC =  $("#xacnhanTC").val();
	if (xacnhanTC=='bottom')
	{
		$("html, body").animate({ scrollTop: $(document).height() }, "fast");
	}
	
});
</script>
<script type="text/javascript">
var theme =  getTheme();
	$("#nhanCaTruc").change(function(){
		functionNextShift();
	});

	function functionNextShift()
	{
		
		var nhanNgayTruc = $("#nhanNgayTruc").val();
		var nhanCaTruc = $("#nhanCaTruc").val();
		if (nhanNgayTruc==null)
			{
			nhanNgayTruc='';
			}
		if (nhanCaTruc==null)
		{
			nhanCaTruc='';
		}
		if (nhanNgayTruc != '' &&nhanCaTruc != '') {
			$.getJSON("${pageContext.request.contextPath}/ajax/getNextShift.htm",{ngaytruc : nhanNgayTruc,catruc:nhanCaTruc}, function(j){
				var nhanCatruong= j.catruong;
				var nhanCaVien=j.cavien;
				var nhanUCTT=j.uctt;
				//alert(nhanCatruong+'-'+nhanCaVien+'-'+nhanUCTT);
				$("#nhanUsername").val(nhanCatruong);
				$("#nhanCaVien").val(nhanCaVien);
			
			});
		}
	}
</script>
</c:if>


<c:if test="${warningTp != 'BAN_GIAO_CA'}">	
<script type="text/javascript">
function dateToYMDHMS(date) {
	//alert(date);
    var d = date.getDate();
    var m = date.getMonth()+1;
    var y = date.getFullYear();
    var h = date.getHours();
    var mi = date.getMinutes();
    var s = date.getSeconds();
    //alert(d+'-'+m+'-'+y);
    return '' + (d <= 9 ? '0' + d : d) + '/' + (m<=9 ? '0' + m : m) + '/' + y+ ' ' + (h<=9 ? '0' + h : h)+ ':' + (mi<=9 ? '0' + mi : mi)+ ':' + (s<=9 ? '0' + s : s) ;
}
var warningTp =  "<c:out value='${warningTp}'/>";
var theme =  getTheme(); 
$('#btSend').jqxButton({ theme: theme });
$('#btCancel').jqxButton({ theme: theme });
$('#btSendSMS').jqxButton({ theme: theme });
$('#btxacnhan').jqxButton({ theme: theme });
$('#btAdd').jqxButton({ theme: theme });
$('#btUpdate').jqxButton({ theme: theme });
${grid}
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
	    		window.location = '${pageContext.request.contextPath}/w_working_managements/formContent.htm?id='+row.id+'&note=cvcd';     
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.updateWorkProgress"/>')  {
	    	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=';  
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
	    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
	    	if (answer)
	    	{
	    		window.location = '${pageContext.request.contextPath}/working/delete.htm?idList='+row.id+'&type=&note=caTruc'; 
	    	}
	    }
	    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
	    	$("#grid2").jqxGrid('exportdata', 'xls', exportFileName);
	    }
	});
}
$("#menujqx").on('itemclick', function (event) {
    var args = event.args;
    var exportFileName =  "<c:out value='${exportFileName}'/>";
   var rowindex = $("#grid").jqxGrid('getselectedrowindex');
    var row = $("#grid").jqxGrid('getrowdata', rowindex);
    if ($.trim($(args).text()) == '<fmt:message key="global.button.editSelected"/>') {
    	if (warningTp=='2G'||warningTp=='3G'||warningTp=='SU_CO_LON')
   		{
    		window.location = 'form23G.htm?id='+row.id+'&warningTp='+warningTp;  
   		}
    	
    	else if (warningTp=='PS_CORE'||warningTp=='CS_CORE'||warningTp=='IPBB')
   		{
    		window.location = '${pageContext.request.contextPath}/warning/form.htm?id='+row.id+'&warningTp='+warningTp+'&note=caTruc';
   		}
    	else if (warningTp=='WOTK_SHIFT')
   		{
    		window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=cvcd';   
    	}
    	else if (warningTp=='ALARM_EXTEND')
   		{
    		window.location = '${pageContext.request.contextPath}/alarmExtend/form.htm?id='+row.id+'&note=caTruc';    
   		}
    	else if (warningTp=='TEMPERATURE_SITE')
   		{
    		window.location = '${pageContext.request.contextPath}/monitor/temperatureForm.htm?id='+row.id+'&note=caTruc';
   		}
   	  	
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.updateWorkProgress"/>')  {
    	window.location = '${pageContext.request.contextPath}/w_working_managements/formContentDetails.htm?id='+row.id+'&type=';  
    }
    
    if ($.trim($(args).text()) == '<fmt:message key="global.button.deleteSelected"/>')  {
    	var answer = confirm ('<fmt:message key="messsage.confirm.delete"/>');
    	if (answer)
    	{
    		if (warningTp=='2G'||warningTp=='3G'||warningTp=='SU_CO_LON')
       		{
    			window.location = 'delete23G.htm?id='+row.id+'&warningTp='+warningTp;  
    		}
    		else if (warningTp=='PS_CORE'||warningTp=='CS_CORE'||warningTp=='IPBB')
       		{
    			window.location = '${pageContext.request.contextPath}/warning/delete.htm?id='+row.id+'&warningTp='+warningTp+'&note=caTruc';  
       		}
    		else if (warningTp=='ALARM_EXTEND')
       		{
    			window.location = '${pageContext.request.contextPath}/alarmExtend/delete.htm?id='+row.id+'&note=caTruc';  
       		}
    		else if (warningTp=='TEMPERATURE_SITE')
       		{
    			window.location = '${pageContext.request.contextPath}/monitor/temperaturedelete.htm?idList='+row.id+'&note=caTruc'; 
       		}
    		else if (warningTp=='WOTK_SHIFT')
       		{
    			window.location = '${pageContext.request.contextPath}/working/delete.htm?idList='+row.id+'&type=cvcd&note=caTruc'; 
        	}
    	}
    }
    
    if ($.trim($(args).text()) == '<fmt:message key="global.button.exportExcel"/>')  {
    	$("#grid").jqxGrid('exportdata', 'xls', exportFileName);
    }
    if ($.trim($(args).text()) == '<fmt:message key="global.button.sendMessage"/>')
    {
    	if (warningTp=='SU_CO_LON')
   		{
    		var content=row.alarm+".\n";
        	content+="Thiet bi:"+row.system+".\n";
        	
        	$("#title").val("SU_CO");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
            $("#content").val(content);
		}
		else if (warningTp=='ALARM_EXTEND')
   		{
			var area='';
	    	if (row.area!=null)
	    		{
	    			area=row.area;
	    		}
	    	var content="CV_HCMR: "+row.alarm+"\n";
	    	content+="Element :"+area+"\n";
	    	
	    	$("#title").val("CV_HCMR");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
	        $("#content").val(content);
   		}
		else if (warningTp=='TEMPERATURE_SITE')
   		{
			var temperature='';
	    	if (row.temperature!=null)
	    		{
	    		temperature=row.temperature;
	    		}
	    	var humidity='';
	    	if (row.humidity!=null)
	    		{
	    		humidity=row.humidity;
	    		}
	    	var statusFridge='';
	    	if (row.statusFridge!=null)
	    		{
	    		statusFridge=row.statusFridge;
	    		}
	    	var statusElectricity='';
	    	if (row.statusElectricity!=null)
	    		{
	    		statusElectricity=row.statusElectricity;
	    		}
	    	var checkDate='';
	    	if (row.checkDate!=null)
	    		{
	    		checkDate=dateToYMDHMS(new Date(row.checkDate));
	    		}
	    	var contactUser='';
	    	if (row.contactUser!=null)
	    		{
	    		contactUser=row.contactUser;
	    		}
	    	var content="TINH_HINH_HA_TANG ("+row.site+"):\n";
	    	content+="Nhiet do :"+temperature+"\n";
	    	content+="Do am    :"+humidity+"\n";
	    	content+="May lanh :"+statusFridge+"\n";
	    	content+="Dien luoi:"+statusElectricity+"\n";
	    	content+="Thoi gian:"+checkDate+"\n";
	    	content+="Lien he  :"+contactUser+"\n";
	    	
	    	$("#title").val("GIAM_SAT_NHIET_DO_SITE");
    		$("#sendTo").val("");
        	$("#sendToCb").val("");
	        $("#content").val(content);
	       
   		}
    /* 	var mainDemoContainer = $('#menujqx');
        var offset = mainDemoContainer.offset(); */
    	$('#jqxwindow').jqxWindow({position: { x: 150, y: 200}});
       	$("#jqxwindow").jqxWindow('open'); 
    }
});

</script>
</c:if>
<script type="text/javascript">
var theme =  getTheme();
$(document).ready(function(){

	// create jqxWindow.
 	
    $("#jqxwindow").jqxWindow({ resizable: true, theme: theme, autoOpen: false, width: 800, minHeight: 250,isModal: true});
  //Create a jqxDropDownList title
   var urlWarningType = "${pageContext.request.contextPath}/ajax/getBrandName.htm";
    // prepare the data
    var sourceWarningType =
    {
        datatype: "json",
        datafields: [
            { name: 'name' },
            { name: 'value' }
        ],
        url: urlWarningType,
        async: false
    };
    var dataAdapterWarningType = new $.jqx.dataAdapter(sourceWarningType);
    $("#title").jqxComboBox({ source: dataAdapterWarningType, displayMember: "value", valueMember: "name", width: '100%',height: 20,theme: theme,autoDropDownHeight: true,autoOpen: true });
    var renderer = function (itemValue, inputValue) {
        var terms = inputValue.split(/,\s*/);
        var value = inputValue;
     
         if (inputValue.indexOf(itemValue) < 0)
	    	{
      
        	 // remove the current input
             terms.pop();
             // add the selected item
	    	 terms.push(itemValue);
	         // add placeholder to get the comma-and-space at the end
	         terms.push("");
	         value = terms.join(",");
	    	}
       
        return value;
    };
  //Create a jqxDropDownList title
   ${groupList}
    $("#sendTo").jqxInput({ height: 20, width: '100%', theme: theme,
        source: function (query, response) {
            var item = query.split(/,\s*/).pop();
            // update the search query.
            $("#sendTo").jqxInput({ query: item });
            response(groupList);
        },
        renderer: renderer
    });
   $("#sendToCb").jqxComboBox({ source: groupList, height: 20, width: '100%', theme: theme ,autoDropDownHeight: true }); 
  //Create a jqxDropDownList title
   $('#sendToCb').on('select', function (event) 
  		{
  			 var itemB= $('#sendTo').val();
  			var item = $("#sendToCb").jqxComboBox('getSelectedItem');
  			if (itemB!='')
  				{
  					if (itemB.indexOf(item.label)==-1)
  						{itemB+=","+item.label ;}
  				}
  			else
  				{
  					itemB=item.label ;
  				} 
  		
  			$("#sendTo").val(itemB);
  			//alert(itemB);
  		});
   
    $("#btSendSMS").click(function (event) {
    	$("#sendTo").val("");
    	$("#sendToCb").val("");
    	$("#title").val("");
    	$("#content").val("");
    	var mainDemoContainer = $('#btSendSMS');
        var offset = mainDemoContainer.offset();
    	$('#jqxwindow').jqxWindow({position: { x: offset.left - 400, y: offset.top - 400}});	
       	$("#jqxwindow").jqxWindow('open');
    });

    $("#btCancel").click(function (event) {
        $("#jqxwindow").jqxWindow('close');  
    });
 

    $("#btSend").click(function (event) {
    	//alert($("#sendToCb").val());
    	var title="";
    	if ($("#title").val()!='')
    		title = $("#title").jqxComboBox('getSelectedItem').value;
    	
    	var content = title+": "+$("#content").val();
    	//alert(content);
    	var smsContents = {};
    	smsContents.isdn       = $("#sendTo").val();
    	smsContents.alarmType  = title;
    	smsContents.message    = content;
    	if (smsContents.message!=null&&smsContents.message!=''&&smsContents.alarmType!=null&&smsContents.alarmType!=''&&smsContents.isdn!=null&&smsContents.isdn!='')
    		{
    			
    						$.ajax({
    				    	    type: "POST",
    				    	    url: "${pageContext.request.contextPath}/caTruc/sendSMS.htm",
    				    	    data: JSON.stringify(smsContents),
    				    	    dataType: 'json',
    				    	    contentType: 'application/json',
    				    	    mimeType: 'application/json',
    				    	    beforeSend: function(){},
    				    	    complete: function(){},
    				    	    success: function(data){
    				    	    	var obj = data.value;
    				    	
    				         	if (obj!='1')
    				    	    		{
    				    	    			alert("Gửi SMS không thành công tới các số điện thoại:"+obj);
    				    	    		}
    				    	    	else
    				    	    		{
	    				    	    		$("#jqxwindow").jqxWindow('close');
		    				               	alert("Gửi SMS thành công");
		    				               	$("#sendTo").val("");
		    				            	$("#sendToCb").val("");
		    				            	$("#title").val("");
		    				            	$("#content").val("");
		    				            	
		    				    	    }
    				    	    }
    				    	});
		    	
		    		}
		    	else
		    		{
		    			alert("Gửi SMS không thành công");
		    		}
		    });
		   
		});
</script>


