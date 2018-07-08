<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>Báo cáo tùy chọn bsc qos 3g </title>
<content tag="heading">Báo cáo tùy chọn bsc qos 3g</content>

<style>
	table.simple td, table.simple th {
		max-width: 190px;
	}
	
	.pageSize {
		width: 400px; 
		float: right; 
		text-align: right; 
		padding-bottom: 4px;
	}
	
	#doublescroll { overflow: auto; overflow-y: hidden; }
	#doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }
</style>
 
<div>
	<table style="width:100%" class="form">
		<tr>
		    <td align="left">
			  <form method="get" action="list.htm">
			  
					<fmt:message key="option.cellgprscs.region"/>
		  			<select name="region">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${regionList}">
			              <c:choose>
			                <c:when test="${item.region == region}">
			                    <option value="${item.region}" selected="selected">${item.region}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.region}">${item.region}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
			    	</select>
		            &nbsp;
  
			        RNC
			        <select name="bscid">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${bscList}">
			              <c:choose>
			                <c:when test="${item.bscid == bscid}">
			                    <option value="${item.bscid}" selected="selected">${item.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.bscid}">${item.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
				    &nbsp;
			        
			        <fmt:message key="option.cellgprscs.starttime"/>
			        <input type ="text"  value="${starttime}" name="starttime" id="starttime" size = "20">
	   				<img alt="calendar" title="Click to choose the start time" id="chooseStartTime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			        &nbsp;
			        
			        <fmt:message key="option.cellgprscs.endtime"/>
			        <input type ="text"  value="${endtime}" name="endtime" id="endtime" size = "20">
	   				<img alt="calendar" title="Click to choose the end time" id="chooseEndTime" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			        &nbsp;
			        
					<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
	          </form>
            </td>
        </tr>		
	</table>
	<div id="doublescroll" style="width:100%">
			<display:table name="${optionBsc3gList}" class="simple2" id="item"  export = "true" requestURI="" pagesize="100" >
<%-- 			<display:column property="sdatetime" titleKey="option.cellgprscs.starttime" headerClass="master-header-1"/>
		    <display:column property="edatetime" titleKey="option.cellgprscs.endtime" class="margin" headerClass="master-header-1"/> --%>
   		   
		    <display:column property="region" titleKey="Center" sortable="true" headerClass="master-header-1"/>
		    <display:column property="bscid" titleKey="RNC" sortable="true" sortName = "bscid"/>
		    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Sites" sortable="true" sortName = "Sites" headerClass="master-header-1"/>
		    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" sortName = "cellid"/>
		    <display:column property="speechTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SPEECH_TRAFF" class="SPEECH_TRAFF"  sortable="true" sortName = "SPEECH_TRAFF" headerClass="master-header-3"/>
		    <display:column property="cs64Traff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CS64_TRAFF" sortable="true" sortName = "CS64_TRAFF" headerClass="master-header-3"/>
		    <display:column property="psr99UlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_TRAFF"  sortable="true"  sortName = "PSR99_UL_TRAFF" headerClass="master-header-3"/>
		    <display:column property="psr99DlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_TRAFF"  sortable="true"  sortName = "PSR99_DL_TRAFF" headerClass="master-header-3"/>
		    <display:column property="hsupaUlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_TRAFF"  sortable="true" sortName = "HSUPA_UL_TRAFF" headerClass="master-header-3"/>
		    <display:column property ="hsdpaDlTraff" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_TRAFF"  sortable="true" sortName = "HSDPA_DL_TRAFF" headerClass="master-header-3 margin" class="margin"/>
		    <display:column property="speechCssr" titleKey="SPEECH_CSSR" sortable="true" sortName = "SPEECH_CSSR"/>
		    <display:column property ="cs64Cssr" titleKey="CS64_CSSR" class="CS64_CSSR" sortable="true" sortName = "CS64_CSSR" headerClass="master-header-4"/>
		    <display:column property="psr99Cssr" titleKey="PSR99_CSSR" class="PSR99_CSSR" sortable="true" sortName = "PSR99_CSSR" headerClass="master-header-4"/>			    
		    <display:column property ="hsupaCssr" titleKey="HSUPA_CSSR" class="HSUPA_CSSR" sortable="true" sortName = "HSUPA_CSSR" headerClass="master-header-4"/>
		    <display:column property="hsdpaCssr" titleKey="HSDPA_CSSR" class="HSDPA_CSSR margin" sortable="true" sortName = "HSDPA_CSSR" headerClass="master-header-4 margin"/>
		    <display:column property="speechDrpr" titleKey="SPEECH_DRPR" sortable="true" sortName = "SPEECH_DRPR"/>
		    <display:column property="cs64Drpr" titleKey="CS64_DRPR" class="CS64_DRPR" sortable="true" headerClass="master-header-5"/>
			<display:column property="psr99Drpr" titleKey="PSR99_DRPR" class="PSR99_DRPR" sortable="true" sortName = "CS64_DRPR" headerClass="master-header-5"/>
			<display:column property ="hsupaDrpr" titleKey="HSUPA_DRPR" class="HSUPA_DRPR" sortable="true" sortName = "HSUPA_DRPR" headerClass="master-header-5"/>
			<display:column property="hsdpaDrpr" titleKey="HSDPA_DRPR" class="HSDPA_DRPR margin" sortable="true" sortName = "HSDPA_DRPR" headerClass="master-header-5 margin"/>
		    <display:column property="psr99UlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_UL_THROUGHTPUT" sortable="true" sortName = "PSR99_UL_THROUGHTPUT" headerClass="master-header-3"/>
		    <display:column property="psr99DlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PSR99_DL_THROUGHTPUT" sortable="true" sortName = "PSR99_DL_THROUGHTPUT" headerClass="master-header-3"/>
		    <display:column property="hsupaUlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSUPA_UL_THROUGHTPUT" sortable="true" sortName = "HSUPA_UL_THROUGHTPUT" headerClass="master-header-3"/>
		    <display:column property="hsdpaDlThroughtput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HSDPA_DL_THROUGHTPUT" sortable="true"  sortName = "HSDPA_DL_THROUGHTPUT" headerClass="master-header-3 margin" class="margin"/>
			<display:column property="csIratHoSr" titleKey="CS_IRAT_HO_SR" sortable="true" sortName = "CS_IRAT_HO_SR" headerClass="master-header-6"/>
			<display:column property ="psIratHoSr" titleKey="PS_IRAT_HO_SR" sortable="true" sortName = "PS_IRAT_HO_SR" headerClass="master-header-6"/>
			<display:column property ="softHsSr" titleKey="SOFT_HS_SR" sortable="true" sortName = "SOFT_HS_SR" headerClass="master-header-6 margin" class="margin"/>
		    <display:column property ="cellDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELL_DOWNTIME" sortable="true" sortName = "CELL_DOWNTIME" headerClass="master-header-2"/>
		    <display:column property ="hsDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="HS_DOWNTIME" sortable="true" sortName = "HS_DOWNTIME" headerClass="master-header-2"/>
		    <display:column property ="eulDowntime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EUL_DOWNTIME" sortable="true" sortName = "EUL_DOWNTIME" headerClass="master-header-2 margin" class="margin"/>
		     	
		    <display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true" />
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		</display:table>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />

<script type="text/javascript">
	Calendar.setup({
	    inputField		:	"starttime",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
	    button			:   "chooseStartTime",  	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	
	Calendar.setup({
	    inputField		:	"endtime",	// id of the input field
	    ifFormat		:	"%d/%m/%Y %H:%M",   	// format of the input field
	    button			:   "chooseEndTime",   	// trigger for the calendar (button ID)
	    showsTime		:	true,
	    singleClick		:   false					// double-click mode
	}); 
	
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
    
    var cacheCell = {}, lastXhrCell;
	$( "#cellid" ).autocomplete({
		minLength: 3,
		source: function( request, response ) {
			var term = request.term;
			if ( term in cacheCell ) {
				response( cacheCell[ term ] );
				return;
			}

			lastXhrCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
				cacheCell[ term ] = data;
				if ( xhr === lastXhrCell ) {
					response( data );
				}
			});
		}
	});
</script>	