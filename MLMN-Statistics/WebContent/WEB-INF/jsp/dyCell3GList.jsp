<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo Cell QoS 3G</title>

<c:choose>
 	<c:when test="${function=='accessibility' }">
 		<content tag="heading">CELL ACCESSIBILITY DAILY REPORT</content>
 	</c:when>
 	<c:when test="${function=='mobility' }">
  	 	<content tag="heading">CELL MOBILITY DAILY REPORT</content>	
 	</c:when>
 	<c:when test="${function=='retainability' }">
  		<content tag="heading">CELL RETAINABILITY DAILY REPORT</content>	
 	</c:when>
 	<c:when test="${function=='traffic' }">
 	 	<content tag="heading">CELL TRAFFIC DAILY REPORT</content>	
 	</c:when>
 	<c:otherwise>
 		<content tag="heading">CELL QOS DAILY REPORT</content>	
 	</c:otherwise>
 </c:choose>


<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/list.htm?function=${function}"><span>Báo cáo ngày</span></a></li>
	<li><a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/bhList.htm?function=${function}"><span>Báo cáo ngày BH</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name="frmSample" onSubmit="return ValidateForm()">
					Trung tâm 
			  		<select name="region" id="region" onchange="xl()">
			  			<option value="">Tất cả</option>
			              <c:forEach var="items" items="${regionList}">
				              <c:choose>
				                <c:when test="${items.region == region}">
				                    <option value="${items.region}" selected="selected">${items.region}</option>
				                </c:when>
				                <c:otherwise>
				                    <option value="${items.region}">${items.region}</option>
				                </c:otherwise>
				              </c:choose>
						    </c:forEach>
			        </select>
			        &nbsp;PROVINCE 
			        <select name="province" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${provinceList}">
			              <c:choose>
			                <c:when test="${prv.province == province}">
			                    <option value="${prv.province}" selected="selected">${prv.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.province}">${prv.province}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
			        &nbsp;RNC 
			        <select name="bscid" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc== bscid}">
			                    <option value="${bsc}" selected="selected">${bsc}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc}">${bsc}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
			        &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                <input value="${function}" id="function" name="function" type="hidden"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
		
<div  id="doublescroll"> 	
        <div class="tableStandar">
            <display:table style="${width}" name="${vRpDyCellList}" id="vRpDyCell" requestURI="" pagesize="100"  sort="external" defaultsort="1" partialList="true" size="${totalSize}">
                <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1" sortable="true" sortName="DAY"/>
                <display:column property="region" titleKey="REGION" headerClass="master-header-1" sortable="true" sortName="REGION"/>
                <display:column property="province" titleKey="Province" sortable="true" headerClass="hide" class="hide"/>
                <display:column titleKey="Province" headerClass="master-header-1" media="html" sortable="true" sortName="PROVINCE" style="width:120px">
                    <a href="${pageContext.request.contextPath}/report/radio3g/province/hr/detail.htm?province=${vRpDyCell.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.province}</a>
                </display:column>
                <display:column property="vendor" titleKey="Vendor" headerClass="master-header-1" sortable="true" sortName="VENDOR" style="width:120px"/>
                <display:column property="bscid" titleKey="RNC" sortable="true" headerClass="hide" class="hide"/>
                <display:column titleKey="RNC" headerClass="master-header-1" media="html" sortable="true" sortName="BSCID">
                    <a href="${pageContext.request.contextPath}/report/radio3g/rnc/hr/detail.htm?bscid=${vRpDyCell.bscid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.bscid}</a>
                </display:column>
                <display:column property="siteName" titleKey ="SITENAME" headerClass="master-header-1" sortable="true"/>
                <display:column property="cellid"  titleKey = "CELLID" headerClass="master-header-1" sortable="true"/>
                <display:column property="cellname"  titleKey = "CELLNAME" headerClass="master-header-1" sortable="true"/>
                
                <c:choose>
                    <c:when test="${function=='accessibility' }">
                        <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2" sortName="CELL_DOWNTIME"/>
                        <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2" sortName="HS_DOWNTIME"/>
                        <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin" sortName="EUL_DOWNTIME"/>
                        <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-5"  media="html" sortName="SPEECH_CSSR">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechCssr}</a>
                        </display:column>
                        
                        <display:column property="cs64Cssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_CSSR" sortable="true" headerClass="master-header-5" sortName="CS64_CSSR"/>
                        <display:column property="psr99Cssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_CSSR" sortable="true" headerClass="master-header-5" sortName="PSR99_CSSR"/>
                        <display:column property="hsupaCssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_CSSR" sortable="true" headerClass="master-header-5" sortName="HSUPA_CSSR"/>
                        <display:column property="hsdpaCssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_CSSR" sortable="true" headerClass="master-header-5" sortName="HSDPA_CSSR"/>
                         
                        
                    </c:when>
                    <c:when test="${function=='retainability' }">
                        <display:column property ="speechDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-5" sortName="SPEECH_DROP"/>
                        <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-5"  media="html" sortName="SPEECH_DRPR">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechDrpr}</a>
                        </display:column>
                        <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-5" sortName="CS64_DROP"/>
                        <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5" sortName="CS64_DRPR"/>
                        <display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-5" sortName="PSR99_DROP"/>
                        <display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5" sortName="PSR99_DRPR"/>
                        <display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-5" sortName="HSUPA_DROP"/>
                        <display:column property="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5" sortName="HSUPA_DRPR"/>
                        <display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-5" sortName="HSDPA_DROP"/>
                        <display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin" sortName="HSDPA_DRPR"/>
                        
                    </c:when>
                    <c:when test="${function=='mobility' }">
                        <display:column property="speechShoSrOut" titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="master-header-6" media="html" sortName="SPEECH_SHO_SR_OUT">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellToCells.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechShoSrOut}</a>
                        </display:column>
                        <display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-6" sortName="SHO_SR_OUT"/>
                        <display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-6" sortName="SHO_SR_IN"/>
                        <display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-6" sortName="CS_IRAT_HO_SR"/>
                        <display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-6 margin" class="margin" sortName="PS_IRAT_HO_SR"/> --%>
                        
                    </c:when>
                    <c:when test="${function=='traffic' }">
                        <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3" sortName="SPEECH_TRAFF"/>
                        <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3" sortName="CS64_TRAFF"/>
                        <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="PSR99_UL_TRAFF"/>
                        <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="PSR99_DL_TRAFF"/>
                        <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="HSUPA_UL_TRAFF"/>
                        <display:column property="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="HSDPA_DL_TRAFF"/>
                        <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="PSR99_UL_THROUGHTPUT"/>
                        <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="PSR99_DL_THROUGHTPUT"/>
                        <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="HSUPA_UL_THROUGHTPUT"/>
                        <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin" sortName="HSDPA_DL_THROUGHTPUT"/>
                        
                    </c:when>
                    <c:otherwise>
                         <display:column property="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2" sortName="CELL_DOWNTIME"/>
                        <display:column property="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2" sortName="HS_DOWNTIME"/>
                        <display:column property="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin" sortName="EUL_DOWNTIME"/>
                        <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3" sortName="SPEECH_TRAFF"/>
                        <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3" sortName="CS64_TRAFF"/>
                        <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="PSR99_UL_TRAFF"/>
                        <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="PSR99_DL_TRAFF"/>
                        <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="HSUPA_UL_TRAFF"/>
                        <display:column property="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF_CELL"  sortable="true" headerClass="master-header-3" sortName="HSDPA_DL_TRAFF"/>
                       <%--  <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="PSR99_UL_THROUGHTPUT"/>
                        <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="PSR99_DL_THROUGHTPUT"/>
                        <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" sortName="HSUPA_UL_THROUGHTPUT"/>
                        <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin" sortName="HSDPA_DL_THROUGHTPUT"/> 
                        --%>  
                        <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" headerClass="hide" class="hide"/>
                        <display:column titleKey="SPEECH_CSSR" class="SPEECH_CSSR" sortable="true" headerClass="master-header-5"  media="html" sortName="SPEECH_CSSR">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/cssr/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechCssr}</a>
                        </display:column>
                        
                       <display:column property="cs64Cssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_CSSR" sortable="true" headerClass="master-header-5" sortName="CS64_CSSR"/>
                        <display:column property="psr99Cssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_CSSR" sortable="true" headerClass="master-header-5" sortName="PSR99_CSSR"/>
                        <display:column property="hsupaCssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_CSSR" sortable="true" headerClass="master-header-5" sortName="HSUPA_CSSR"/>
                        <display:column property="hsdpaCssr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_CSSR" sortable="true" headerClass="master-header-5" sortName="HSDPA_CSSR"/> 
                         
                        <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" headerClass="master-header-4"/>
                        <%-- <display:column titleKey="SPEECH_DRPR" class="SPEECH_DRPR" sortable="true" headerClass="master-header-4" media="html" sortName="SPEECH_DRPR">
                            <a href="${pageContext.request.contextPath}/report/radio3g/cell/drop/dy.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechDrpr}</a>
                        </display:column> --%>
                        <display:column property="pasr3g" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PASR3G" sortable="true" headerClass="master-header-4"/>
                        <display:column property="padr3g" titleKey="PADR3G" sortable="true" headerClass="master-header-4"/>                      
                        <display:column property="speechDrop"  titleKey="SPEECH_DROP" sortable="true" headerClass="master-header-4" sortName="SPEECH_DROP"/>
                        
                         <display:column property="cs64Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_DROP" sortable="true" headerClass="master-header-4" sortName="CS64_DROP"/>
                       <%--  <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5" sortName="CS64_DRPR"/> --%>
                        <display:column property="psr99Drop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DROP" sortable="true" headerClass="master-header-4" sortName="PSR99_DROP"/>
                        <display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5" sortName="PSR99_DRPR"/>
                        <%-- <display:column property="hsupaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_DROP" sortable="true" headerClass="master-header-4" sortName="HSUPA_DROP"/>
                        <display:column property="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5" sortName="HSUPA_DRPR"/>
                         --%>
                         <display:column property="hsdpaDrop" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DROP" sortable="true" headerClass="master-header-4" sortName="HSDPA_DROP"/>
                        <display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR" sortable="true" headerClass="master-header-5" sortName="HSDPA_DRPR"/> 
                        <display:column property="doKhaDung3g" titleKey="DO_KHA_DUNG_3G" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" sortable="true" headerClass="master-header-5 margin" sortName="DO_KHA_DUNG_3G"/>
                    </c:otherwise>
                </c:choose>
                 <display:column property="speechShoSrOut" titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="hide" class="hide"/>
                <display:column titleKey="SPEECH_SHO_SR_OUT" sortable="true" headerClass="master-header-4" media="html" sortName="SPEECH_SHO_SR_OUT">
                    <a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellToCells.htm?bscid=${vRpDyCell.bscid}&cellid=${vRpDyCell.cellid}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyCell.day}"/>">${vRpDyCell.speechShoSrOut}</a>
                </display:column>
                <display:column property="shoSrOut" titleKey="SHO_SR_OUT" sortable="true" headerClass="master-header-4"sortName="SHO_SR_OUT"/>
                <display:column property="shoSrIn" titleKey="SHO_SR_IN" sortable="true" headerClass="master-header-4" sortName="SHO_SR_IN"/>
                <display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" headerClass="master-header-4" sortName="CS_IRAT_HO_SR"/>
                <display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" headerClass="master-header-4" class="margin" sortName="PS_IRAT_HO_SR"/> 
                <display:column property="status" titleKey="BADCELL" style="color:red;" sortable="true" headerClass="margin" class="margin" sortName="STATUS"/>
                <display:column property ="dataload" titleKey="DATALOAD" sortable="true" sortName="DATALOAD"/>
                <display:column property ="siteid" title="Siteid"   class="hide" headerClass="hide"/>
                <display:column title="Map" media="html"  sortable="true">
                    <a href='${pageContext.request.contextPath}/map/list.htm?siteid=${vRpDyCell.siteid}' target="_blank">${vRpDyCell.siteid}</a>&nbsp;
                </display:column>
            </display:table>
        </div>
            
            <div class="exportlinks">Export options:
                    <a href="${pageContext.request.contextPath}/exportExcel/cell3g.htm?region=${region}&startDate=${startDate}&cellid=${cellid}&bscid=${bscid}&province=${province}&endDate=${endDate}"><span class="export excel">Excel </span></a>
            </div>
 </div> 
</div>
<script type="text/javascript">
    function DoubleScroll(element) {
        var scrollbar= document.createElement('div');
        scrollbar.appendChild(document.createElement('div'));
        scrollbar.style.overflow= 'auto';
        scrollbar.style.overflowY= 'hidden';
        scrollbar.firstChild.style.width= element.scrollWidth+'px';
        scrollbar.firstChild.style.paddingTop= '1px';
        scrollbar.firstChild.appendChild(document.createTextNode('\xA0'));
        scrollbar.onscroll= function() {
            element.scrollLeft= scrollbar.scrollLeft;
        };
        element.onscroll= function() {
            scrollbar.scrollLeft= element.scrollLeft;
        };
        element.parentNode.insertBefore(scrollbar, element);
    }

    DoubleScroll(document.getElementById('doublescroll'));
</script>
<script type="text/javascript"> 
$(function() {
	$( "#startDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
	$( "#endDate" ).datepicker({
		dateFormat: "dd/mm/yy",
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
		buttonImageOnly: true
	});
		var cacheCell = {}, lastXhrCell;
		$("#cellid" ).autocomplete({
			minLength: 3,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cacheCell ) {
					response( cacheCell[ term ] );
					return;
				}

				lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell3G.htm", request, function( data, status, xhr ) {
					cacheCell[ term ] = data;
					if ( xhr === lastXhrCell ) {
						response( data );
					}
				});
			}
		});
		
		${highlight}
	});
</script>
