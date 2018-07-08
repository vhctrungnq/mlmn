<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo Province QoS 3G</title>
<content tag="heading">PROVINCE QOS 3G DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/province/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=><a href="${pageContext.request.contextPath}/report/radio3g/province/mn/list.htm"><span>Báo cáo tháng</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/qr/list.htm"><span>Báo cáo quý</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/yr/list.htm"><span>Báo cáo năm</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
	                &nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div  id="doublescroll">
			<display:table style = "width: 200%" name="${vRpDyProvince}" id="vRpDyProvince" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true" headerClass="master-header-1"/>
			    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
			    <display:column property="province" titleKey="Province" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="Province" media="html" sortable="true" headerClass="master-header-1" sortProperty="province" style = "width: 120px">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/province/hr/detail.htm?province=${vRpDyProvince.province}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyProvince.day}"/>">${vRpDyProvince.province}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" sortable="true"  headerClass="master-header-1"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="Cells" media="html" sortable="true" headerClass="master-header-1 margin" class="margin" sortProperty="cells">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/dy/list.htm?province=${vRpDyProvince.province}&day=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyProvince.day}"/>"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyProvince.cells}"/></a>
			    </display:column>
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" headerClass="master-header-3" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=speechTraff">
		               ${vRpDyProvince.speechTraff}
		           </a>
		        </display:column>
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" headerClass="master-header-3" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=cs64Traff">
		               ${vRpDyProvince.cs64Traff}
		           </a>
		        </display:column>
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true" headerClass="master-header-3" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99UlTraff">
		               ${vRpDyProvince.psr99UlTraff}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true" headerClass="master-header-3" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99DlTraff">
		               ${vRpDyProvince.psr99DlTraff}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" headerClass="master-header-3" media="html" >
		              <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsupaUlTraff">
		               ${vRpDyProvince.hsupaUlTraff}
		           </a>
		        </display:column>   
		        <display:column  decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" headerClass="master-header-3 margin" class="margin" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsdpaDlTraff">
		               ${vRpDyProvince.hsdpaDlTraff}
		           </a>
		        </display:column>       
		        <display:column titleKey="SPEECH_CSSR" sortable="true" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=speechCssr">
		               ${vRpDyProvince.speechCssr}
		           </a>
		        </display:column>       
		        <display:column titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" headerClass="master-header-4" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=cs64Cssr">
		               ${vRpDyProvince.cs64Cssr}
		           </a>
		        </display:column>       
		        <display:column titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" headerClass="master-header-4" media="html" >        
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99Cssr">
		               ${vRpDyProvince.psr99Cssr}
		           </a>
		        </display:column>       
		        <display:column titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" headerClass="master-header-4" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsupaCssr">
		               ${vRpDyProvince.hsupaCssr}
		           </a>
		        </display:column>       
		        <display:column titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" headerClass="master-header-4 margin" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsdpaCssr">
		               ${vRpDyProvince.hsdpaCssr}
		           </a>
		        </display:column>       
		        <display:column titleKey="SPEECH_DRPR" sortable="true" media="html" >
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=speechDrpr">
		               ${vRpDyProvince.speechDrpr}
		           </a>
		        </display:column>       
		
		        
		        <display:column titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5">
		         <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=cs64Drpr">
		             ${vRpDyProvince.cs64Drpr}
		          </a>
		        </display:column>
		        <display:column titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" headerClass="master-header-5" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99Drpr">
		               ${vRpDyProvince.psr99Drpr}
		           </a>
		        </display:column>        
		        <display:column titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" headerClass="master-header-5" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsupaDrpr">
		               ${vRpDyProvince.hsupaDrpr}
		           </a>
		        </display:column>  
		        <display:column titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" headerClass="master-header-5 margin" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsdpaDrpr">
		               ${vRpDyProvince.hsdpaDrpr}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99UlThroughtput">
		               ${vRpDyProvince.psr99UlThroughtput}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=psr99DlThroughtput">
		               ${vRpDyProvince.psr99DlThroughtput}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsupaUlThroughtput">
		               ${vRpDyProvince.hsupaUlThroughtput}
		           </a>
		        </display:column>       
		        <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsdpaDlThroughtput">
		               ${vRpDyProvince.hsdpaDlThroughtput}
		           </a>
		        </display:column>       
		   <%--      <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" headerClass="master-header-2" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=cellDowntime">
		               ${vRpDyProvince.cellDowntime}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" headerClass="master-header-2" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=hsDowntime">
		               ${vRpDyProvince.hsDowntime}
		           </a>
		        </display:column>       
		        <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" headerClass="master-header-2 margin" class="margin" media="html">
		           <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=eulDowntime">
		               ${vRpDyProvince.eulDowntime}
		           </a>
		        </display:column>  --%>      
			    <display:column property ="totBadCell" titleKey="BAD_CELL" sortable="true"/>
			    <display:column property ="badCellR" titleKey="BAD_CELL_R" sortable="true" headerClass="margin" class="margin"/>
                <display:column titleKey="DATALOAD" sortable="true" media="html">
                   <a href="${pageContext.request.contextPath}/report/radio3g/province/dy/chart.htm?province=${vRpDyProvince.province}&kpi=dataload">
                       ${vRpDyProvince.dataload}
                   </a>
                </display:column>
			</display:table>
		</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">
	function xl(){
		var sub= document.getElementById("submit");
		sub.focus();
	}
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
		
		${highlight}
		
		<c:if test="${empty bscid}">
		$($('#vRpDyProvince > tbody tr')[0]).before('<c:forEach var="vRpDyRegion" items="${vRpDyRegionList}"><tr class="${vRpDyRegion.region}"><td><fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyRegion.day}"/></td><td>${vRpDyRegion.region}</td><td/><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.sites}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cells}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.speechTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cs64Traff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99UlTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99DlTraff}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsupaUlTraff}"/></td><td class ="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsdpaDlTraff}"/></td><td>${vRpDyRegion.speechCssr}</td><td>${vRpDyRegion.cs64Cssr}</td><td>${vRpDyRegion.psr99Cssr}</td><td>${vRpDyRegion.hsupaCssr}</td><td class="margin">${vRpDyRegion.hsdpaCssr}</td><td>${vRpDyRegion.speechDrpr}</td><td>${vRpDyRegion.cs64Drpr}</td><td>${vRpDyRegion.psr99Drpr}</td><td>${vRpDyRegion.hsupaDrpr}</td><td class="margin">${vRpDyRegion.hsdpaDrpr}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.psr99UlThroughtput}"/></td><td>${vRpDyRegion.psr99DlThroughtput}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsupaUlThroughtput}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsdpaDlThroughtput}"/></td><td>${vRpDyRegion.csIratHoSr}</td><td>${vRpDyRegion.psIratHoSr}</td><td>${vRpDyRegion.shoSrIn}</td><td class="margin">${vRpDyRegion.shoSrOut}</td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.cellDowntime}"/></td><td><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.hsDowntime}"/></td><td class="margin"><fmt:formatNumber pattern="#,###,###,##0.##" value="${vRpDyRegion.eulDowntime}"/></td><td>${vRpDyRegion.totBadCell}</td><td>${vRpDyRegion.badCellR}</td></tr></c:forEach>');
		</c:if>
	});
</script>

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