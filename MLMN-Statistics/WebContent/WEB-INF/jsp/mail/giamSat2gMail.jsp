<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">    #doublescroll { overflow: auto; overflow-y: hidden; }    #doublescroll p { margin: 0; padding: 1em; white-space: nowrap; }</style>
<title>Giám sát KPI ${network}</title>
<content tag="heading">Giám sát KPI ${network}</content>
<ul id ="tab" class="ui-tabs-nav">
  <li id = "tab2g" ><a href="${pageContext.request.contextPath}/giam-sat/kpi/list.htm?network=2G&startDate=${startDate}&endDate=${endDate}"><span>2G</span></a></li>
  <li id = "tab3g"><a href="${pageContext.request.contextPath}/giam-sat/kpi/list.htm?network=3G&startDate=${startDate}&endDate=${endDate}"><span>3G</span></a></li>
</ul>
	<div class="ui-tabs-panel">

	  <form method="get" action="list.htm" name = "frmSample" onSubmit = "return ValidateForm()">
	  <input type = "hidden" id = "network" name = "network" value = "${network}" />
		<table width="100%" class="form">
			<tr>
				<td align="left">
	            	&nbsp;Từ ngày
	                &nbsp;<input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"/>
	                &nbsp;Đến ngày
	                &nbsp;<input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="Tìm kiếm"/>
	            </td>
	        </tr>
	    </table>
	  </form>
	   
		<div id="doublescroll" class="tableStandar">
			<display:table style = "width: 150%"  name="${dataList}" id="item" requestURI="" pagesize="20" export="true" sort="list">
				
      			<display:column class="textCenter" title="STT" style="width:50px;"> <c:out value="${item_rowNum}"/> </display:column>
      		
				<display:column property="day" format="{0,date,dd/MM/yyyy}" title="DAY" style="width:100px" sortable="true" />
			    <display:column property="vendor" title="Vendor" style="width:100px" sortable="true" />
			    <display:column property="bscid" title="BSCID" style="width:100px" sortable="true" />
			    <display:column property="allCell" class ="allCellColor" title="ALL"  style="width:100px" sortable="true"  />
				<display:column property="hour0" class ="hour0Color" title="0" style="width:80px" sortable="true" />
				<display:column property="hour1" class ="hour1Color" title="1" style="width:80px" sortable="true"  />
				<display:column property="hour2" class ="hour2Color" title="2" style="width:80px" sortable="true"  />
				<display:column property="hour3" class ="hour3Color" title="3" style="width:80px" sortable="true"  />
				<display:column property="hour4" class ="hour4Color" title="4" style="width:80px" sortable="true"  />
				<display:column property="hour5" class ="hour5Color" title="5" style="width:80px" sortable="true"  />
				<display:column property="hour6" class ="hour6Color" title="6" style="width:80px" sortable="true"  />
				<display:column property="hour7" class ="hour7Color" title="7" style="width:80px" sortable="true"  />
				<display:column property="hour8" class ="hour8Color" title="8" style="width:80px" sortable="true"  />
				<display:column property="hour9" class ="hour9Color" title="9" style="width:80px" sortable="true"  />
				<display:column property="hour10" class ="hour10Color" title="10" style="width:80px" sortable="true" />
				<display:column property="hour11" class ="hour11Color" title="11" style="width:80px" sortable="true" />
				<display:column property="hour12" class ="hour12Color" title="12" style="width:80px" sortable="true" />
				<display:column property="hour13" class ="hour13Color" title="13" style="width:80px" sortable="true" />
				<display:column property="hour14" class ="hour14Color" title="14" style="width:80px" sortable="true" />
				<display:column property="hour15" class ="hour15Color" title="15" style="width:80px" sortable="true" />
				<display:column property="hour16" class ="hour16Color" title="16" style="width:80px" sortable="true" />
				<display:column property="hour17" class ="hour17Color" title="17" style="width:80px" sortable="true" />
				<display:column property="hour18" class ="hour18Color" title="18" style="width:80px" sortable="true" />
				<display:column property="hour19" class ="hour19Color" title="19" style="width:80px" sortable="true" />
				<display:column property="hour20" class ="hour20Color" title="20" style="width:80px" sortable="true" />
				<display:column property="hour21" class ="hour21Color" title="21" style="width:80px" sortable="true" />
				<display:column property="hour22" class ="hour22Color" title="22" style="width:80px" sortable="true" />
				<display:column property="hour23" class ="hour23Color" title="23" style="width:80px" sortable="true" />
			</display:table>
		</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/exporting.js"></script>

<script type="text/javascript">
function xl(){
	var sub = document.getElementById("submit");
	sub.focus();
}
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
<script type="text/javascript">
$(function() {
	  $('#item>tbody>tr').each( function(){
		   var allCellColor = parseFloat($(this).find(".allCellColor").text());
		   if(allCellColor == 0){
		   		$(this).find('.allCellColor').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		 
		   var hour0Color = parseFloat($(this).find(".hour0Color").text());
		   if(hour0Color == 0){
		   		$(this).find('.hour0Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour1Color = parseFloat($(this).find(".hour1Color").text());
		   if(hour1Color == 0){
		   		$(this).find('.hour1Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour2Color = parseFloat($(this).find(".hour2Color").text());
		   if(hour2Color == 0){
		   		$(this).find('.hour2Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour3Color = parseFloat($(this).find(".hour3Color").text());
		   if(hour3Color == 0 ){
		   		$(this).find('.hour3Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour4Color = parseFloat($(this).find(".hour4Color").text());
		   if(hour4Color == 0 ){
		   		$(this).find('.hour4Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour5Color = parseFloat($(this).find(".hour5Color").text());
		   if(hour5Color == 0 ){
		   		$(this).find('.hour5Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour6Color = parseFloat($(this).find(".hour6Color").text());
		   if(hour6Color == 0 ){
		   		$(this).find('.hour6Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour7Color = parseFloat($(this).find(".hour7Color").text());
		   if(hour7Color == 0 ){
		   		$(this).find('.hour7Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour8Color = parseFloat($(this).find(".hour8Color").text());
		   if(hour8Color == 0 ){
		   		$(this).find('.hour8Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour9Color = parseFloat($(this).find(".hour9Color").text());
		   if(hour9Color == 0 ){
		   		$(this).find('.hour9Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour10Color = parseFloat($(this).find(".hour10Color").text());
		   if(hour10Color == 0 ){
		   		$(this).find('.hour10Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour11Color = parseFloat($(this).find(".hour11Color").text());
		   if(hour11Color == 0 ){
		   		$(this).find('.hour11Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour12Color = parseFloat($(this).find(".hour12Color").text());
		   if(hour12Color == 0 ){
		   		$(this).find('.hour12Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour13Color = parseFloat($(this).find(".hour13Color").text());
		   if(hour13Color == 0 ){
		   		$(this).find('.hour13Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour14Color = parseFloat($(this).find(".hour14Color").text());
		   if(hour14Color == 0 ){
		   		$(this).find('.hour14Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour15Color = parseFloat($(this).find(".hour15Color").text());
		   if(hour15Color == 0 ){
		   		$(this).find('.hour15Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour16Color = parseFloat($(this).find(".hour16Color").text());
		   if(hour16Color == 0 ){
		   		$(this).find('.hour16Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour17Color = parseFloat($(this).find(".hour17Color").text());
		   if(hour17Color == 0 ){
		   		$(this).find('.hour17Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour18Color = parseFloat($(this).find(".hour18Color").text());
		   if(hour18Color == 0 ){
		   		$(this).find('.hour18Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour19Color = parseFloat($(this).find(".hour19Color").text());
		   if(hour19Color == 0 ){
		   		$(this).find('.hour19Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour20Color = parseFloat($(this).find(".hour20Color").text());
		   if(hour20Color == 0 ){
		   		$(this).find('.hour20Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour21Color = parseFloat($(this).find(".hour21Color").text());
		   if(hour21Color == 0 ){
		   		$(this).find('.hour21Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour22Color = parseFloat($(this).find(".hour22Color").text());
		   if(hour22Color == 0 ){
		   		$(this).find('.hour22Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
		   
		   var hour23Color = parseFloat($(this).find(".hour23Color").text());
		   if(hour23Color == 0 ){
		   		$(this).find('.hour23Color').css({'background-color' : 'red', 'font-weight': 'bold'});
		   }
	  });   
});

$(document).ready(function(){
		var tab = "${network}";
		if (tab == "2G") {
			$("#tab2g").addClass("ui-tabs-selected");
		} else {
			$("#tab3g").addClass("ui-tabs-selected");
		}
	});
</script>