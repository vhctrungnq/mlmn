<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<title>BÁO CÁO OPTIMAL THEO NGÀY</title>    
<content tag="heading">BÁO CÁO OPTIMAL THEO NGÀY</content>
<div class="ui-tabs-panel">
  <form method="get" action="dy.htm">
	<table width="40%" class="form">
		<tr>
			<td>Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10"> </td>
			<td> Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10"></td>
			<td >
            	<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
            </td>
        </tr>		
	</table>
  </form>
			
	<div  id="doublescroll">
		<display:table name="${dataList}" id="dataList" requestURI="" pagesize="100" class="simple2" export="true">
		   <display:column  sortable="true" property="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" headerClass="master-header-1"/> 
		   <display:column  sortable="true" property="region" titleKey="REGION" style = "width: 120px" headerClass="master-header-1"/>
		   <display:column  sortable="true" property="province" titleKey="PROVINCE" style = "width: 120px" headerClass="master-header-1"/> 
		   <display:column  sortable="true" property="cssr2g" titleKey="CSSR_2G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="cdr2g" titleKey="CDR_2G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="badcell2g" titleKey="BADCELL_2G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="dkd2g" titleKey="DKD_2G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="pasr3g" titleKey="PASR_3G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="padr3g" titleKey="PADR_3G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="badcell3g" titleKey="BADCELL_3G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="dkd3g" titleKey="DKD_3G" headerClass="master-header-2"/>
		   <display:column  sortable="true" property="sites" titleKey="SITES" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="cells" titleKey="CELLS" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="tTraf" titleKey="T_TRAF" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="nodeb" titleKey="NODEB" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="ucell" titleKey="UCELL" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="amrErl" titleKey="AMR_ERL" headerClass="master-header-3"/>
		   <display:column  sortable="true" property="data3gGb" titleKey="DATA_3G_GB" headerClass="master-header-3"/>
		   
			<display:setProperty name="export.csv.include_header" value="true" />
			<display:setProperty name="export.excel.include_header" value="true" />
			<display:setProperty name="export.xml.include_header" value="true" />
			<display:setProperty name="export.xml.filename" value="${exportFileName}.xml" />
			<display:setProperty name="export.csv.filename" value="${exportFileName}.csv" />
			<display:setProperty name="export.excel.filename" value="${exportFileName}.xls" />
		</display:table>
	</div>

</div>

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
});
</script>