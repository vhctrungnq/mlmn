<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>Báo cáo tùy chọn cell qos</title>
<content tag="heading">Báo cáo tùy chọn cell qos</content>

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

<%-- <c:choose>
	<c:when test="${param.pageSize != null}">
		<fmt:parseNumber value="${param.pageSize}" var="pageSize" integerOnly="TRUE" type="NUMBER" scope="session" />
		<c:set var="pageSize" property="pageSize" value="${param.pageSize}" scope="session" />
	</c:when>
	<c:otherwise>
		<c:if test="${empty pageSize }">
			<c:set var="pageSize" property="pageSize" value="50" scope="session" />
		</c:if>
    </c:otherwise>
</c:choose> --%>
 
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
		            
			  		<fmt:message key="option.cellgprscs.province"/>
			        <select name="province">
			        	<option value="">Tất cả</option>
				        <c:forEach var="item" items="${provinceList}">
			              <c:choose>
			                <c:when test="${item.province == province}">
			                    <option value="${item.province}" selected="selected">${item.province}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${item.province}">${item.province}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
				   	&nbsp;
				   		        
			        <fmt:message key="option.cellgprscs.bscid"/> 
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
				    
				    <fmt:message key="option.cellgprscs.cellid"/>
				    <input value="${cellid}" name="cellid" id="cellid" size="10">
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
<%-- 		<div class="pageSize">
			<form class="form">
				Hiển thị
				<input type="text" name="pageSize" id="pageSize" value="${pageSize}" style="width:38px; text-align:center;">
				kết quả/trang
				<input type="submit" class="button" value="Chọn">
			</form>
		</div>  --%>
			<display:table name="${optionCellList}" class="simple2" id="item" requestURI="" pagesize="100" export="true" >
	<%-- 	    <display:column title="STT" class="textCenter"><c:out value="${item_rowNum + startRecord}" /></display:column>
		    <display:column property="sdatetime" titleKey="Khoảng thời gian" headerClass="master-header-1"/>
		    <display:column property="edatetime" titleKey="Khoảng thời gian" class="margin" headerClass="master-header-1"/>
			 --%>
			<display:column property="region" titleKey="TT" sortable="true" sortName="region" headerClass="master-header-1"/>		    
    	    <display:column property="province" titleKey="PROVINCE" headerClass="master-header-1" sortable="true" sortName="province"/>
    	    <display:column property="bscid" titleKey="BSCID" headerClass="master-header-1" sortable="true" sortName="bscid"/>
		    <display:column property="cellid" titleKey="CELLID"  headerClass="master-header-1" sortable="true" sortName="cellid"/>
		    <display:column property ="tDef" titleKey="T_DEF" sortable="true" headerClass="master-header-2" sortName="T_DEF"/>
		    <display:column property ="tAvail" titleKey="T_AVAIL" class="T_AVAIL" sortable="true" headerClass="master-header-2" sortName="T_AVAIL"/>
		    <display:column property ="defAvail" titleKey="DEF_AVAIL" class="margin" headerClass="master-header-1" sortable = "true" sortName = "DEF_AVAIL"/>
		    <display:column property="tAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_ATTS" sortable="true" headerClass="master-header-2" sortName="T_ATTS"/>
		    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true" headerClass="master-header-2" sortName="T_BLKR"/>
		    <display:column property="tBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_BLKS" sortable="true" headerClass="master-header-2" sortName="T_BLKS"/>
		    <display:column property="tHoblkr" titleKey="T_HOBLKR"  sortable="true" headerClass="master-header-2" sortName="T_HOBLKR"/>
		    <display:column property="tHoblks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_HOBLKS" sortable="true" headerClass="master-header-2" sortName="T_HOBLKS"/>
		    <display:column property ="tSeizs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_SEIZS" sortable="true" headerClass="master-header-2" sortName="T_SEIZS"/>
		    <display:column property="cssr" titleKey="CSSR" class="CSSR" sortable="true" headerClass="master-header-2" sortName="cssr"/>
		    <display:column property="tDrpr" titleKey="T_DRPR" sortable = "true" sortName = "T_DRPR"/>
		    <display:column property="tDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Drps" sortable="true" headerClass="master-header-2" sortName="T_DRPS"/>
		    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TCH Traffic" sortable = "true" sortName = "T_TRAF"/>
		    <display:column property ="trafAvail" titleKey="TRAF_AVAIL" sortable = "true" sortName = "TRAF_AVAIL"/>
		    <display:column property="tTrafh" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAFH" class="H_TRAF margin" sortable="true" headerClass="master-header-2 margin" sortName="T_TRAFH"/>			    
		    <display:column property ="trafhTraf" titleKey="TRAFH_TRAF" class="margin" headerClass="master-header-1" sortable = "true" sortName = "TRAFH_TRAF"/>
		    <display:column property="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF"  sortable="true" headerClass="master-header-3" sortName="S_DEF"/>
		    <display:column property="sAtts" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_ATTS"  sortable="true" headerClass="master-header-3" sortName="S_ATTS"/>
		    <display:column property ="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true" headerClass="master-header-3" sortName="S_BLKR"/>
		    <display:column property ="sBlks" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_BLKS" sortable="true" headerClass="master-header-3" sortName="S_BLKS"/>
		    <display:column property="sDrpr" titleKey="S_DRPR" sortable = "true" sortName = "S_DRPR"/>
		    <display:column property="sDrps" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DRPS"  sortable="true" headerClass="master-header-3" sortName="S_DRPS"/>
			<display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true" class="margin" headerClass="master-header-3 margin" sortName="DOWNTIME"/>
			<display:column property="meanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="MEANHOLDTIME"  sortable="true" headerClass="master-header-5" sortName="MEANHOLDTIME"/>
			<display:column property="sdcchmeanholdtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SDCCHMEANHOLDTIME"  sortable="true" class="margin" headerClass="master-header-5 margin" sortName="SDCCHMEANHOLDTIME"/>
			
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