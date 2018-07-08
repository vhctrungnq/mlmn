<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">    
    .textrt{
    	text-align: right;
    }
    
    .textct {
    	text-align: center;
    }
</style>

<title>BÁO CÁO BAD CELLS</title>
<content tag="heading">BÁO CÁO BAD CELLS</content> 	

<%-- <ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/al_bad_cell/list.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/al_bad_cell/list.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
</ul> --%>

<div class="ui-tabs-panel">
	<form  method="get" action="list.htm">
		<table style="width: 100%;">
			<tr>			    
	    		<td>BSC&nbsp;</td>
					<td>
						<select name="bscid" id="bscid" onchange="xl()">
							<option value="">Tất cả</option>
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
					</td>
					
					<td>CELL&nbsp;</td>
					<td><input type="text" value="${cellid}" id="cellid" name="cellid" size="15" />&nbsp;</td>
					
					<td>Từ Ngày&nbsp;</td>
					<td><input type="text" type="text" value="${startDate}" name="startDate" id="startDate" size="15" maxlength="10"/>&nbsp;</td>
					
	               	<td>Đến Ngày&nbsp;</td>
					<td><input type="text" value="${endDate}" name="endDate" id="endDate" size="15" maxlength="10"/>&nbsp;</td>
					
	                <td>&nbsp;<input type="submit" class="button" name="submit" id="submit" value="<spring:message code="button.search"/>"/></td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${vAlDyBadCell}" id="vAlDyBadCell" requestURI="" pagesize="100" class="simple3" export="true">
			<%-- <display:column property="id" titleKey="ID" sortable="true"/> --%>
		   	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		    <display:column property="system" titleKey="BSC" sortable="true"/>
		    <display:column property="cell" titleKey="CELL" sortable="true"/>
		    <display:column property="badHours" titleKey="BAD_HOURS" sortable="true"/>
		    <display:column property="teamProcess"  titleKey="TEAM_PROCESS" sortable="true"/>
		    <display:column property="deptCode"  titleKey="DEPT_CODE" sortable="true"/>
		    <display:column property="badCellType" titleKey="BAD_CELL_TYPE" sortable="true"/>
		</display:table>
	</div>

<script type="text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
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
	});
	 $( "#cellid" ).autocomplete({
		minLength: 2,
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
	/*  
	 $("select#bscid").change(function(){
			$.getJSON("/VMSC2-Statistics/ajax/getCellOfBsc.htm",{bscid: $(this).val()}, function(j){
				var options = '<option  value="">--Select Cell--</option>';
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].cellid + '">' + j[i].cellid + '</option>';
				}
				$("#cellid").html(options);
				$('#cellid option:first').attr('selected', 'selected');
			})
		}); */
</script>