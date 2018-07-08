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

<title>BÁO CÁO MẤT ĐIỆN TRẠM</title>
<content tag="heading">BÁO CÁO MẤT ĐIỆN TRẠM</content> 	

<%-- <ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/al_dy_loss_power/list.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/al_dy_loss_power/list.htm?mscid=${msc.mscid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
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
				<td>VENDOR&nbsp;</td>
				<td><input type="text" type="text" value="${vendor}" name="vendor" size="15" />&nbsp;</td>
				<td>SITE&nbsp;</td>
				<td><input type="text" type="text" value="${site}" name="site" size="15" />&nbsp;</td>
				
				<td>CELL&nbsp;</td>
				<td><input type="text" type="text" value="${cell}" name="cell" size="15" />&nbsp;</td>
				
			</tr>
			<tr>
				<td>ALARM&nbsp;</td>
				<td><input type="text" type="text" value="${alarm}" name="alarm" size="15" />&nbsp;</td>
		    	 
				<td>Từ Ngày&nbsp;</td>
				<td><input type="text" type="text" value="${startDate}" name="startDate" id="startDate" size="15" maxlength="10"/>&nbsp;</td>
					
	            <td>Đến Ngày&nbsp;</td>
				<td><input type="text" type="text" value="${endDate}" name="endDate" id="endDate" size="15" maxlength="10"/>&nbsp;</td>
                
                <td>&nbsp;<input type="submit" class="button" name="submit" id="submit" value="<spring:message code="button.search"/>"/></td>
            </tr>	
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${vAlDyLossPower}" id="vAlDyLossPower" requestURI="" pagesize="100" class="simple3" export="true">
			<%-- <display:column property="id" titleKey="ID" sortable="true"/> --%>
		   	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		   	<display:column property="vendor" titleKey="VENDOR" sortable="true"/>
		    <display:column property="system" titleKey="BSC" sortable="true"/>
		    <display:column property="site" titleKey="SITE" sortable="true"/>
		    <display:column property="cell"  titleKey="CELL" sortable="true"/>
		    <display:column property="alarm"  titleKey="ALARM" sortable="true"/>
		    <display:column property="stime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="STIME" sortable="true"/>
		    <display:column property="etime" format="{0,date,dd/MM/yyyy HH:mm}" titleKey="ETIME" sortable="true"/>
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
</script>