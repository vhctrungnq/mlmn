<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>cells to cell qos 3g daily report</title>
<content tag="heading">Báo cáo CELLS TO CELL QOS 3G</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellToCells.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ Cell to Cells</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellToCells.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày Cell to Cells</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellsToCell.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ Cells to Cell</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellsToCell.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày Cells to Cell</span></a></li>
</ul>
<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  	  <form method="get" action="dyCellsToCell.htm">
			       TO RNC 
			        <select name="bscid" id="bscid">
						<option value="">--Select RNC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc.bscid == bscid}">
			                    <option value="${bsc.bscid}" selected="selected">${bsc.bscid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc.bscid}">${bsc.bscid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
				    &nbsp;&nbsp;TO CELL 
			        <select name="cellid" id="cellid">
						<option  value="">--Select Cell--</option>
				        <c:forEach var="cell" items="${cellList}">
			              <c:choose>
			                <c:when test="${cell.cellid == cellid}">
			                    <option value="${cell.cellid}" selected="selected">${cell.cellid}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${cell.cellid}">${cell.cellid}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          	  </form>
	            </td>
	        </tr>		
		</table>
		<br/>
	
		<div  id="doublescroll">
			<display:table name="${vRpDyCellToCellQos3G}" id="dyCellToCellQos3G" requestURI="" pagesize="100" class="simple2" export="true" sort="list">
			    <display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="Ngày" sortable="true"/>
			    <display:column property="fromBscid" titleKey="RNC Name" sortable="true"/>	 
			    <display:column property="fromCellid" titleKey="UtranCell" sortable="true" headerClass="hide" class="hide"/>   
			    <display:column titleKey="UtranCell" media="html" sortable="true" sortProperty="fromCellid">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellToCells.htm?bscid=${dyCellToCellQos3G.fromBscid}&cellid=${dyCellToCellQos3G.fromCellid }&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellToCellQos3G.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellToCellQos3G.day}"/>">${dyCellToCellQos3G.fromCellid}</a>
			    </display:column>
			    <display:column property="toCellid" titleKey="UtranRelation" sortable="true" headerClass="hide" class="hide"/>
			    <display:column titleKey="UtranRelation" media="html" sortable="true" sortProperty="toCellid">
			    	<a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellsToCell.htm?bscid=${dyCellToCellQos3G.toBscid}&cellid=${dyCellToCellQos3G.toCellid}&startDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellToCellQos3G.day}"/>&endDate=<fmt:formatDate pattern="dd/MM/yyyy" value="${dyCellToCellQos3G.day}"/>">${dyCellToCellQos3G.toCellid}</a>
			    </display:column>
			    <display:column property="toBscid" titleKey="Adj_RNC Name" sortable="true"/>	   
			    <display:column property ="hoveratt" titleKey="pmRlAddAttemptsBestCellSpeech" sortable="true" headerClass="master-header-2"/>
				<display:column property ="hoversuc" titleKey="pmRlAddSuccessBestCellSpeech" sortable="true" headerClass="master-header-2"/>
				<display:column property ="hoversucr" titleKey="SHO Succ %" sortable="true" headerClass="master-header-2"/>
			</display:table>
		</div>
		<br>
</div>

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

		$("select#bscid").change(function(){
			$.getJSON("${pageContext.request.contextPath}/ajax/getCellOfBsc3G.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			})
		})
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