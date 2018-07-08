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

<title>BÁO CÁO LỖI MẤT CẤU HÌNH 2G</title>
<content tag="heading">BÁO CÁO LỖI MẤT CẤU HÌNH 2G</content> 	

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
						<select name="bscid" id="bscid" onchange="focus()">
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
				<td><input type="text" value="${cellid}" name="cellid" size="15" />&nbsp;</td>
				
				<td>ALARM&nbsp;</td>
				<td><input type="text" value="${alarm}" name="alarm" size="15" />&nbsp;</td>
			</tr><tr>	
				<td>LOSS TYPE&nbsp;</td>
				<td><input type="text" value="${lossType}" name="lossType" size="15" />&nbsp;</td>
		    	 
				<td>Từ Ngày&nbsp;</td>
				<td><input type="text" value="${startDate}" name="startDate" id="startDate" size="15" maxlength="10"/>&nbsp;</td>
					
	            <td>Đến Ngày&nbsp;</td>
				<td><input type="text" value="${endDate}" name="endDate" id="endDate" size="15" maxlength="10"/>&nbsp;</td>
                
                <td>&nbsp;<input type="submit" class="button" name="submit" id="submit" value="<spring:message code="button.search"/>"/></td>
	        </tr>		
		</table>
	</form>
	<br/>
</div>
<div  style="overflow: auto;">
		<display:table name="${vAlDyLossConfig2G}" id="vAlDyLossConfig2G" requestURI="" pagesize="100" class="simple3" export="true">
			<%-- <display:column property="id" titleKey="ID" sortable="true"/> --%>
		   	<display:column property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" sortable="true"/>
		    <display:column property="sys" titleKey="BSC" sortable="true"/>
		    <display:column property="cell" titleKey="CELL" sortable="true"/>
		    <display:column property="alarm" titleKey="ALARM" sortable="true"/>
		    <display:column property="stime"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="STIME" sortable="true"/>
		    <display:column property="etime"  format="{0,date,dd/MM/yyyy HH:mm}" titleKey="ETIME" sortable="true"/>
		    <display:column property="causeBy" titleKey="CAUSE_BY" sortable="true"/>
		    <display:column property="lossType" titleKey="LOSS_TYPE" sortable="true"/>
		</display:table>
	</div>

<script type="text/javascript">
	function focus(){
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