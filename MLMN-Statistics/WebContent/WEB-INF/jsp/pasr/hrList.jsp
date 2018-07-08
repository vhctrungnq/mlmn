<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="ui-tabs-panel">
	
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			  <form method="get" action="hrList.htm" name = "frmSample" onSubmit = "return ValidateForm()">
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select RNC--</option>
				        <c:forEach var="bsc" items="${bscList}">
			              <c:choose>
			                <c:when test="${bsc == bscid}">
			                    <option value="${bsc}" selected="selected">${bsc}</option>
			                </c:when>
			                <c:otherwise>
			                    <option value="${bsc}">${bsc}</option>
			                </c:otherwise>
			              </c:choose>
					    </c:forEach>
					</select>
			       &nbsp;CELL <input value="${cellid}" name="cellid" id="cellid" size="10">
	            	&nbsp;Từ <input type = "number" value="${startHour}" name="startHour" id="startHour" size="5" maxlength="5" min = "0" max = "23"> giờ
	            	&nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	            	&nbsp;&nbsp;Tới <input type = "number" value="${endHour}" name="endHour" id="endHour" size="5" maxlength="5" min = "0" max = "23"> giờ
	            	&nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	            	
	            	&nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	          </form>
	            </td>
	        </tr>		
		</table>
	<br/>
<div  style="overflow: auto;" class="tableStandar" id = "doublescroll">
	<display:table name="${hrDataList}" id="item" requestURI="" pagesize="100" class="simple2" export="true" sort="external" defaultsort="1">
	<display:column property="hour" title="Hour" headerClass="master-header-1" sortable="true" sortName = "hour"/>
	<display:column property="day" format="{0,date,dd/MM/yyyy}" title="Date" headerClass="master-header-1" sortable="true" sortName = "day"/>
	<display:column property="bscid" titleKey="RNC" sortable="true" sortName = "bscid"/>
	<display:column property="cellid" decorator="vn.com.vhc.vmsc2.statistics.web.utils.NumberDecorator" titleKey="Cells" sortable="true" sortName = "cellid" />
	<display:column property="pasr" titleKey="PASR" sortable="true" sortName = "pasr"/>		   		    
	</display:table>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript">



$(document).ready(function() {
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
