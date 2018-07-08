<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title><fmt:message key="title.option.cellgprscs"/></title>
<content tag="heading"><fmt:message key="title.option.cellgprscs"/></content>

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
				<fmt:message key="global.text.hienthi" /> 
				<input type="text" name="pageSize" id="pageSize" value="${pageSize}" style="width:38px; text-align:center;">
				<fmt:message key="global.text.ketqua" />
				<input type="submit" class="button" value="<fmt:message key="global.button.choose" />">
			</form>
		</div>  --%>
	
		<display:table name="${optionCellGprscsList}" class="simple2" id="item" requestURI="" pagesize="100" export="true" >
<%-- 		    <display:column title="STT" class="textCenter"><c:out value="${item_rowNum + startRecord}" /></display:column>
		    <display:column property="sdatetime" titleKey="option.cellgprscs.starttime" headerClass="master-header-1"/>
		    <display:column property="edatetime" titleKey="option.cellgprscs.endtime" class="margin" headerClass="master-header-1"/> --%>
		    
		    <display:column property="region" titleKey="option.cellgprscs.region" sortable="true" sortName="REGION" headerClass="master-header-1"/>
		    <display:column property="province" titleKey="option.cellgprscs.province" sortable="true" sortName="PROVINCE" headerClass="master-header-1"/>
		    <display:column property="mscid" titleKey="option.cellgprscs.mscid" sortable="true" sortName="MSCID" headerClass="master-header-1"/>
		    <display:column property="bscid" titleKey="option.cellgprscs.bscid" sortable="true" sortName="BSCID" headerClass="master-header-1"/>
		    <display:column property="cellid" titleKey="option.cellgprscs.cellid" sortable="true" class="margin" sortName="CELLID" headerClass="master-header-1"/>
		    
		    <display:column property="dlTbfSucc" titleKey="option.cellgprscs.dlTbfSucc" sortable="true" sortName="DL_TBF_SUCC" headerClass="master-header-1"/>
		    <display:column property="ulTbfSucc" titleKey="option.cellgprscs.ulTbfSucc" sortable="true" class= "margin" sortName="UL_TBF_SUCC" headerClass="master-header-1"/>
		    
		    <display:column property="dlTbfSucr" titleKey="option.cellgprscs.dlTbfSucr" sortable="true" sortName="DL_TBF_SUCR" headerClass="master-header-1"/>
		    <display:column property="ulTbfSucr" titleKey="option.cellgprscs.ulTbfSucr" sortable="true" class="margin" sortName="UL_TBF_SUCR" headerClass="master-header-1"/>
		    
		    <display:column property="dlTbfReq" titleKey="option.cellgprscs.dlTbfReq" sortable="true" sortName="DL_TBF_REQ" headerClass="master-header-1"/>
		    <display:column property="ulTbfReq" titleKey="option.cellgprscs.ulTbfReq" sortable="true" class="margin" sortName="UL_TBF_REQ" headerClass="master-header-1"/>
		    
		    <display:column property="eulTraf" titleKey="option.cellgprscs.eulTraf" sortable="true" sortName="EUL_TRAF" headerClass="master-header-1"/>
		    <display:column property="edlTraf" titleKey="option.cellgprscs.edlTraf" sortable="true" sortName="EDL_TRAF" headerClass="master-header-1"/>
		    <display:column property="gulTraf" titleKey="option.cellgprscs.gulTraf" sortable="true" sortName="GUL_TRAF" headerClass="master-header-1"/>
		    <display:column property="gdlTraf" titleKey="option.cellgprscs.gdlTraf" sortable="true" class="margin" sortName="GDL_TRAF" headerClass="master-header-1"/>
		    
		    <display:column property="mcsxLevel1" titleKey="option.cellgprscs.mcsxLevel1" sortable="true" sortName="MCSX_LEVEL1" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel2" titleKey="option.cellgprscs.mcsxLevel2" sortable="true" sortName="MCSX_LEVEL2" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel3" titleKey="option.cellgprscs.mcsxLevel3" sortable="true" sortName="MCSX_LEVEL3" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel4" titleKey="option.cellgprscs.mcsxLevel4" sortable="true" sortName="MCSX_LEVEL4" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel5" titleKey="option.cellgprscs.mcsxLevel5" sortable="true" sortName="MCSX_LEVEL5" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel6" titleKey="option.cellgprscs.mcsxLevel6" sortable="true" sortName="MCSX_LEVEL6" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel7" titleKey="option.cellgprscs.mcsxLevel7" sortable="true" sortName="MCSX_LEVEL7" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel8" titleKey="option.cellgprscs.mcsxLevel8" sortable="true" sortName="MCSX_LEVEL8" headerClass="master-header-1"/> 
		    <display:column property="mcsxLevel9" titleKey="option.cellgprscs.mcsxLevel9" sortable="true" class="margin" sortName="MCSX_LEVEL9" headerClass="master-header-1"/> 
		    
		    <display:column property="csxLevel1" titleKey="option.cellgprscs.csxLevel1" sortable="true" sortName="CSX_LEVEL1" headerClass="master-header-1"/> 
		    <display:column property="csxLevel2" titleKey="option.cellgprscs.csxLevel2" sortable="true" sortName="CSX_LEVEL2" headerClass="master-header-1"/> 
		    <display:column property="csxLevel3" titleKey="option.cellgprscs.csxLevel3" sortable="true" sortName="CSX_LEVEL3" headerClass="master-header-1"/> 
		    <display:column property="csxLevel4" titleKey="option.cellgprscs.csxLevel4" sortable="true" sortName="CSX_LEVEL4" headerClass="master-header-1"/>

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