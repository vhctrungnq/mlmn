<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style> 
<title>REPORT BRANCH</title>
<content tag="heading">BRANCH QOS DAILY REPORT</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio/branch/dy/list.htm"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/branch/wk/list.htm"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/radio/branch/mn/list.htm"><span>Báo cáo tháng</span></a></li>
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
			        Chi nhánh
			        <select name="branch" onchange="xl()">
			        	<option value="">Tất cả</option>
				        <c:forEach var="prv" items="${branchList}">
			              <c:choose>
			                <c:when test="${prv.branch == branch}">
			                    <option value="${prv.branch}" selected="selected">${prv.branch}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${prv.branch}">${prv.branch}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
				    </select>
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
	
		<div id="doublescroll">
			<display:table name="${vRpDyBranch}" id="vRpDyBranch" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
				<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
			    <display:column property="region" titleKey="TT" sortable="true"/>
				<display:column property="branch" titleKey="BRANCH" headerClass="hide" class="hide"/>
			    <display:column titleKey="BRANCH" media="html" sortable="true" sortProperty="branch">
			    	<a href="${pageContext.request.contextPath}/report/radio/branch/hr/detail.htm?branch=${vRpDyBranch.branch}&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyBranch.day}"/>">${vRpDyBranch.branch}</a>
			    </display:column>
			    <display:column property="sites" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="SITES" sortable="true"/>
			    <display:column property="cells" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="CELLS" sortable="true"/>
			    <display:column property="trxs" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="TRXS" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="tTraf" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_TRAF" sortable="true"/>
			    <display:column property ="tDrpr" titleKey="T_DRPR" class="T_DRPR" sortable="true"/>
			    <display:column property="tBlkr" titleKey="T_BLKR" class="T_BLKR" sortable="true"/>
			    <display:column property="tHoblkr" titleKey="T_HOBLKR" sortable="true"/>
			    <display:column property ="tEmpdr" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="T_EMPDR" sortable="true"/>
			    <display:column property="cssr" titleKey="CSSR" class="CSSR margin" sortable="true" headerClass="margin"/>
			    <display:column property ="sDef" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="S_DEF" sortable="true"/>
			    <display:column property="sSsr" titleKey="S_SSR" sortable="true"/>
			    <display:column property="sBlkr" titleKey="S_BLKR" class="S_BLKR" sortable="true"/>
			    <display:column property ="sDrpr" titleKey="S_DRPR" sortable="true" class="S_DRPR"/>
			    <display:column property="tAsr" titleKey="T_ASR" sortable="true"/> 
			    <display:column property="haftratePercent" titleKey="HALFRATE" sortable="true" class="margin" headerClass="margin"/>
			    <display:column property="inHoSucr" titleKey="IN_HO_SUCR" sortable="true"/>
			    <display:column property="ogHoSucr" titleKey="OG_HO_SUCR" class="" sortable="true"/>
			    <display:column property="downtime" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="DOWNTIME"  sortable="true"/>
			    <display:column property="dataload" titleKey="DATALOAD" class="margin" headerClass="margin" sortable="true"/>
			    <display:column property ="badCell" titleKey="BAD_CELL" sortable="true"/>
			    <display:column property ="badCellR" titleKey="BAD_CELL_R" class="margin" headerClass="margin" sortable="true"/>
			    <display:column property ="badCellBlk" titleKey="Số CELL nghẽn TCH > 5%" sortable="true"/>
			    <display:column property ="badCellBlkR" titleKey="% CELL nghẽn TCH > 5%" class="margin" headerClass="margin" sortable="true"/>
			    <display:column property ="badCellTraff" titleKey="Số CELL Traff < 10Erl" sortable="true"/>
			    <display:column property ="badCellTraffR" titleKey="% CELL Traff < 10Erl" sortable="true"/>
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
		$("#startDate").datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$("#endDate").datepicker({
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