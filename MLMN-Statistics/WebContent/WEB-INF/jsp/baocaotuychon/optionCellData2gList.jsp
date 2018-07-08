<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>Báo cáo tùy chọn cell data-2g</title>
<content tag="heading">Báo cáo tùy chọn cell data-2g</content>

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
</c:choose>
  --%>
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
		<%-- <div class="pageSize">
			<form class="form">
				Hiển thị
				<input type="text" name="pageSize" id="pageSize" value="${pageSize}" style="width:38px; text-align:center;">
				kết quả/trang
				<input type="submit" class="button" value="Chọn">
			</form>
		</div>  --%>
			<display:table name="${optionCellData2gList}" id="item" requestURI="" pagesize="100" class="simple2" export="true">
<%-- 		    <display:column title="STT" class="textCenter"><c:out value="${item_rowNum}" /></display:column>
 --%><%-- 		<display:column property="sdatetime" titleKey="Khoảng thời gian" headerClass="master-header-1"/>
		    <display:column property="edatetime" titleKey="Khoảng thời gian" class="margin" headerClass="master-header-1"/> --%>
		    
		    <display:column property="region" titleKey="REGION" sortable="true" sortName= "REGION" headerClass="master-header-1"/>
		    <display:column property="province" titleKey="PROVINCE" sortable="true" sortName = "PROVINCE" style="width:120px" headerClass="master-header-1"/>
		    <display:column property="bscid" titleKey="BSC" sortable="true" sortName = "BSCID" headerClass="master-header-1"/>
		    <display:column property="cellid" titleKey="CELL" headerClass="master-header-1" class="margin"  sortable="true" sortName = "CELLID"/>
		    <display:column property="pdchAllocated" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PDCH_ALLOCATED"  sortable="true" sortName = "PDCH_ALLOCATED"/>
			<display:column property="pdchUsed" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PDCH_USED"  sortable="true" sortName = "PDCH_USED"/>
			<display:column property="packetChAllocAtt" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_ATT"  sortable="true" sortName = "PACKET_CH_ALLOC_ATT"/>
			<display:column property="packetChAllocFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_FAIL"  sortable="true" sortName = "PACKET_CH_ALLOC_FAIL"/>
			<display:column property="packetChAllocSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="PACKET_CH_ALLOC_SUCR" class="PACKET_CH_ALLOC_SUCR"  sortable="true" sortName = "PACKET_CH_ALLOC_SUCR"/>
			<display:column property="ulTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_REQ"  sortable="true" sortName="UL_TBF_REQ"/>
			<display:column property="ulTbfFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_FAIL"  sortable="true" sortName = "UL_TBF_FAIL"/>
			<display:column property="ulTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="UL_TBF_SUCR" class="UL_TBF_SUCR"  sortable="true" sortName = "UL_TBF_SUCR"/>
			<display:column property="dlTbfReq" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_REQ"  sortable="true" sortName = "DL_TBF_REQ"/>
			<display:column property="dlTbfFail" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_FAIL"  sortable="true" sortName = "DL_TBF_FAIL"/>
			<display:column property="dlTbfSucr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DL_TBF_SUCR" class="DL_TBF_SUCR"  sortable="true" sortName = "DL_TBF_SUCR"/>
			<display:column property="gprsUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA"  sortable="true" sortName = "GPRS_UL_DATA"/>
			<display:column property="gprsDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA"  sortable="true" sortName = "GPRS_DL_DATA"/>
			<display:column property="edgeUlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA"  sortable="true" sortName = "EDGE_UL_DATA"/>
			<display:column property="edgeDlData" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA"  sortable="true" sortName = "EDGE_DL_DATA"/>
			<display:column property="gprsUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_UL_DATA_THROUGHPUT"  sortable="true" sortName = "GPRS_UL_DATA_THROUGHPUT"/>
			<display:column property="gprsDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="GPRS_DL_DATA_THROUGHPUT"  sortable="true" sortName = "GPRS_DL_DATA_THROUGHPUT"/>
			<display:column property="edgeUlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_UL_DATA_THROUGHPUT"  sortable="true" sortName = "EDGE_UL_DATA_THROUGHPUT"/>
			<display:column property="edgeDlDataThroughput" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="EDGE_DL_DATA_THROUGHPUT" sortable="true" sortName = "EDGE_DL_DATA_THROUGHPUT"/>
		
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