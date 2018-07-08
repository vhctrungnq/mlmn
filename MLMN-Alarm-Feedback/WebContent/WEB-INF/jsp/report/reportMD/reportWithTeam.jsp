
<%@ include file="/commons/taglibs.jsp"   %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>${title}</title>

<content tag="heading">${title}</content> 	
<style>

.total {
    background-color: #FDF2CF;
    color: #FF0000;
    font-weight: bold;
}

</style>
<div>
<form:form commandName="filter" method="get" action="reportTeam.htm">
	<table>
		<tr>
			<td style="width: 70px;"><fmt:message key="reportWithTeam.TGBD"/></td>
			<td>
				<input type ="text"  value="${startTime}" name="startTime" id="startTime" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseStartDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			<td style="width: 70px;padding-left:10px;"><fmt:message key="reportWithTeam.TGKT"/></td>
			<td>
				<input type ="text"  value="${endTime}" name="endTime" id="endTime" size="17" maxlength="10" style="width:100px">
	   			 <img alt="calendar" title="Click to choose the start date" id="chooseEndDate" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
			</td>
			
			<td colspan="2" style="padding-left: 10px;">		
				<input class="button" type="submit" class="button" value="<fmt:message key="button.search"/>" />
			</td>
		
		</tr>
	</table>
</form:form>
</div>
	
 <%-- <div style="overflow-y: auto; overflow-x: hidden; max-height: 1000px;padding-top:20px;" >
	 <display:table name="${repotList}" class="simple2" id="item" requestURI=""  export="true" defaultsort="1" decorator="org.displaytag.decorator.TotalTableDecorator">
		<display:column property="reportTeam"  titleKey="reportWithTeam.reportTeam" />
		<display:column property="mdUuctSum" format="{0,number,0}" titleKey="reportWithTeam.mdUuctSum" style="color:red;"   total="true"/>
	  	<display:column property="mdUuctC"  format="{0,number,0}" titleKey="reportWithTeam.mdUuctC" total="true" />
		<display:column property="mdUuctY" format="{0,number,0}" titleKey="reportWithTeam.mdUuctY" total="true" />
		<display:column property="mdUuctN"  format="{0,number,0}" titleKey="reportWithTeam.mdUuctN" total="true" />
		<display:column property="mllByMpdUuctSum" format="{0,number,0}" titleKey="reportWithTeam.mllByMpdUuctSum" style="color:red;"  total="true"/>
		<display:column property="mllByMpdUuctY"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctY" total="true"/>
		<display:column property="mllByMpdUuctN"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctY" total="true"/>
		<display:column property="mllByAccuUuctSum"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctSum" style="color:red;"   total="true"/>
		<display:column property="mllByAccuUuctY"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctY" total="true"/>
		<display:column property="mllByAccuUuctN" format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctN" total="true" />
		<display:column property="mchUuctSum"  format="{0,number,0}" titleKey="reportWithTeam.mchUuctSum" style="color:red;"   total="true"/>
		<display:column property="mchUuctY" format="{0,number,0}"  titleKey="reportWithTeam.mchUuctY" total="true" />
		<display:column property="mchUuctN"  format="{0,number,0}" titleKey="reportWithTeam.mchUuctN" total="true"/>
		<display:column property="nodetdSum"  format="{0,number,0}" titleKey="reportWithTeam.nodetdSum" style="color:red;"   total="true"/>
		<display:column property="nodetdUuctY" format="{0,number,0}" titleKey="reportWithTeam.nodetdUuctY" total="true"/>
		<display:column property="nodetdUuctN"  format="{0,number,0}" titleKey="reportWithTeam.nodetdUuctN" total="true"/>
		<display:column property="nodetdMll" format="{0,number,0}" titleKey="reportWithTeam.nodetdMll" total="true"  />
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		<display:footer>
	      <tr>
	        <td colspan="18" style="color:blue;fontWeight: bolder;"><fmt:message key="reportWithTeam.totalFooter"/>${total} </td>
	      </tr>
    </display:footer>
	</display:table> --%>
	<jsp:scriptlet>
	     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
	     totals.setTotalLabel("Full total");
	     totals.setSubtotalLabel("Tổng");
	     pageContext.setAttribute("totals", totals);
	  </jsp:scriptlet>
	<div class="tableStandar">
	<display:table name="${repotList}"  id="item" requestURI=""  export="true"  decorator="totals">
		<display:column property="reportDay"  format="{0,date,dd/MM/yyyy}" titleKey="reportWithTeam.reportDay"  group="1" />
		<display:column property="reportTeam"  titleKey="reportWithTeam.reportTeam" />
		<display:column property="mdUuctSum" format="{0,number,0}" titleKey="reportWithTeam.hearderMD" style="color:red;"   total="true"/>
	  	<display:column property="mllByMpdUuctSum" format="{0,number,0}" titleKey="reportWithTeam.mllByMpdUuctSum" style="color:red;"  total="true"/>
		<display:column property="mllByMpdUuctY"  format="{0,number,0}" titleKey="reportWithTeam.mllByMpd2G" total="true"/>
		<display:column property="mllByMpdUuctN"  format="{0,number,0}" titleKey="reportWithTeam.mllByMpd3G" total="true"/>
		<display:column property="mllByAccuUuctSum"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccuUuctSum" style="color:red;"   total="true"/>
		<display:column property="mllByAccuUuctY"  format="{0,number,0}" titleKey="reportWithTeam.mllByAccu2G" total="true"/>
		<display:column property="mllByAccuUuctN" format="{0,number,0}" titleKey="reportWithTeam.mllByAccu3G" total="true" />
		<display:column property="nodetdSum" format="{0,number,0}" titleKey="reportWithTeam.hearderMDTD" total="true"  />
		
	   	<display:setProperty name="export.csv.include_header" value="true" />
		<display:setProperty name="export.excel.include_header" value="true" />
		<display:setProperty name="export.xml.include_header" value="true" />
		<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
		<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
		<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		<display:footer>
	      <tr>
	        <td colspan="18" style="color:blue;fontWeight: bolder;"><fmt:message key="reportWithTeam.totalFooter"/>${total} </td>
	      </tr>
    </display:footer>
	</display:table>
</div>
	   
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/calendar_setup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar/chosen.jquery.js" ></script>

<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/chosen.css"/>

<script type="text/javascript"> $(".chzn-select").chosen(); $(".chzn-select-deselect").chosen({allow_single_deselect:true}); </script>
<script type="text/javascript">
    Calendar.setup({
        inputField		:	"startTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseStartDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });

    Calendar.setup({
        inputField		:	"endTime",	// id of the input field
        ifFormat		:	"%d/%m/%Y",   	// format of the input field
        button			:   "chooseEndDate",  	// trigger for the calendar (button ID)
        showsTime		:	true,
        singleClick		:   false					// double-click mode
    });
	
</script>
<script type="text/javascript">
	function focusIt()
	{
	  var mytext = document.getElementById("startTime");
	  mytext.focus();
	}
	onload = focusIt;
	
	
</script>
 
 <!--  <script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs + '<th rowspan="2" ><fmt:message key="reportWithTeam.reportTeam"/></th>';
	    trs=trs +'<th colspan="4" ><fmt:message key="reportWithTeam.hearderMD"/></th>';
	    trs=trs +'<th colspan="3" ><fmt:message key="reportWithTeam.hearderMLLUCTT"/></th>';
	    trs=trs +'<th colspan="3" ><fmt:message key="reportWithTeam.hearderMLLACCUY"/></th>';
	    trs=trs +'<th colspan="3" ><fmt:message key="reportWithTeam.hearderMCH"/></th>';
	    trs=trs +'<th colspan="4" ><fmt:message key="reportWithTeam.hearderMDTD"/></th></tr>';

	   	trs=trs +'<tr><th  ><fmt:message key="reportWithTeam.mdUuctSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mdUuctC"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mdUuctY"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mdUuctN"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mllByMpdUuctSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mllByMpdUuctY"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mllByMpdUuctN"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mllByAccuUuctSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mllByAccuUuctY"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mllByAccuUuctN"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mchUuctSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mchUuctY"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mchUuctN"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.nodetdSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.nodetdUuctY"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.nodetdUuctN"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.nodetdMll"/></th></tr>';
	    
	$('#item thead').html(trs);
	$('#item tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
	 $('table#item tr:last td:first').html('Tổng');
	});
</script> -->

 <script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
		trs=trs + '<th rowspan="2" ><fmt:message key="reportWithTeam.reportDay"/></th>';
	    trs=trs + '<th rowspan="2" ><fmt:message key="reportWithTeam.reportTeam"/></th>';
	    trs=trs +'<th colspan="1" ><fmt:message key="reportWithTeam.hearderMD"/></th>';
	    trs=trs +'<th colspan="3" ><fmt:message key="reportWithTeam.hearderMLLUCTT"/></th>';
	    trs=trs +'<th colspan="3" ><fmt:message key="reportWithTeam.hearderMLLACCUY"/></th>';
	    trs=trs +'<th colspan="1" ><fmt:message key="reportWithTeam.hearderMDTD"/></th></tr>';

	   	trs=trs +'<tr><th  ><fmt:message key="reportWithTeam.mllSum"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mllSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mll2G"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mll3G"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mllSum"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mll2G"/></th>';
	    trs=trs +'<th ><fmt:message key="reportWithTeam.mll3G"/></th>';
	    trs=trs +'<th  ><fmt:message key="reportWithTeam.mllSum"/></th>';
	    trs=trs +'</tr>';
	    
	$('#item thead').html(trs);
	$('#item tbody tr:last td').css({color: 'red', fontWeight: 'bolder'});
	$('table#item tr:last td:first').html('Tổng các ngày');
	});
</script>