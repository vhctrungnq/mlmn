<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style><title>cell to cells 3g hourly report</title>
<content tag="heading">CELL TO CELLS 3G REPORT ${cellid}</content>
<ul class="ui-tabs-nav">
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellToCells.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ Cell to Cells</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellToCells.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày Cell to Cells</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/hrCellsToCell.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo giờ Cells to Cell</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/radio3g/cell/ho-cell-to-cell/dyCellsToCell.htm?bscid=${bscid}&cellid=${cellid}&endDate=${endDate}"><span>Báo cáo ngày Cells to Cell</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="hrCellToCells.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
				<td align="left">
			        FROM RNC 
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
				    &nbsp;&nbsp;FROM CELL 
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
	            	&nbsp;Từ <input value="${startHour}" name="startHour" id="startHour" size="4" maxlength="5"> giờ
	            	&nbsp;<input value="${startDate}"" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới <input value="${endHour}" name="endHour" id="endHour" size="4" maxlength="5"> giờ
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	  </form>
	<br/>
	
	<div  id="doublescroll">
	<display:table name="${vRpHrCellToCellQos3G}" id="vRpHrCellToCellQos3G" requestURI="" pagesize="100" class="simple2" export="true">
	    <display:column property="day" format="{0,date,dd/MM/yyyy}" title="Day" sortable="true"/>
	    <display:column title="Hour" sortable="true"  class="margin" headerClass="margin" sortProperty="hour">
	    	${vRpHrCellToCellQos3G.hour}:00
	    </display:column>
	    <display:column property="fromBscid" title="RNC Name" sortable="true"/>	
	    <display:column property="fromCellid" title="UtranCell" sortable="true"/>	
	    <display:column property="toCellid" title="UtranRelation" sortable="true"/>
	    <display:column property="toBscid" title="Adj_RNC Name" sortable="true"/>
	    <display:column property ="hoveratt" titleKey="pmRlAddAttemptsBestCellSpeech" sortable="true" headerClass="master-header-2"/>
		<display:column property ="hoversuc" titleKey="pmRlAddSuccessBestCellSpeech" sortable="true" headerClass="master-header-2"/>
		<display:column property ="hoversucr" titleKey="SHO Succ %" sortable="true" headerClass="master-header-2"/>
	</display:table>
	</div>
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