<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>Báo cáo QoS Province 3G ${province}</title>
<content tag="heading">PROVINCE QOS 3G ${province} REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/hr/detail.htm?province=${province}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/province/dy/detail.htm?province=${province}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/wk/detail.htm?province=${province}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/mn/detail.htm?province=${province}"><span>Báo cáo tháng</span></a></li>
  <%-- <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/dy/bh.htm?province=${province}&endDate=${endDate}"><span>Báo cáo BH ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/wk/bh.htm?province=${province}"><span>Báo cáo BH tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/province/mn/bh.htm?province=${province}"><span>Báo cáo BH tháng</span></a></li> --%>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			<td align="left">
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
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	<table class="form">
		    	<tr>
		    		<td>
		    			<b>Chọn chỉ số hiển thị: </b>
		    		</td>
		    	</tr>
		        <tr>
		        	<td>${checkColumns}</td>
				</tr>
	</table>
<br/>
	<div  id="doublescroll">
<display:table style = "width: 200%" name="${vRpDyProvinceList}" id="vRpDyProvince" requestURI="" pagesize="100" class="simple2" export="true">
		<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Date" sortable="true" headerClass="master-header-1"/>
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
		<display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3" property="psr99UlThroughtput" />
         <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3"  property="psr99DlThroughtput" />
         <display:column decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" headerClass="master-header-3"  property="hsupaUlThroughtput" />
          <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true" headerClass="master-header-3 margin" class="margin" property="hsdpaDlThroughtput" />
         <display:column property="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true"/>
	     <display:column property="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true"/>
	     <display:column property="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true"/>
         <display:column property="dataload" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DATALOAD" sortable="true"/>
	    	
	</display:table>
</div>
	<br/>
	<div id="speechCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cs64CssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psr99CssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsupaCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsdpaCssrChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="speechDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="cs64DrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="psr99DrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsupaDrprChart" style="width: 1000px; margin: 1em auto"></div>
	<br/>
	<div id="hsdpaDrprChart" style="width: 1000px; margin: 1em auto"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>
${speechCssrChart}
${cs64CssrChart}
${psr99CssrChart}
${hsupaCssrChart}
${hsdpaCssrChart}
${speechDrprChart}
${cs64DrprChart}
${psr99DrprChart}
${hsupaDrprChart}
${hsdpaDrprChart}

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