<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>CELL DAY REPORT</title>
<content tag="heading">CELL LOW CSSR DAILY REPORT</content>

<ul class="ui-tabs-nav">
  <!-- <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/hr.htm?"><span>Báo cáo giờ</span></a></li> -->
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/dy.htm?bscid=${bsc.bscid}&cellid=${cell.cellid}&startDate=${startDate}&endDate=${endDate}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/wk.htm?"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/loss-cssr/cell/mn.htm?"><span>Báo cáo tháng</span></a></li>
</ul>
  
<div class="ui-tabs-panel">
	<form:form method="get" commandName="filter" action="dy.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					BSC <form:input path="bscid" size="10" />
					&nbsp;CELL <form:input path="cellid" size="10" />
	                &nbsp;&nbsp;Từ ngày <input value="${startDate}" name="startDate" id="startDate" size="10" maxlength="10">
	                &nbsp;&nbsp;Tới ngày <input value="${endDate}" name="endDate" id="endDate" size="10" maxlength="10">
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form:form>
	</div>
	<br/>
	
	
<div  style="overflow: auto;">
		<display:table name="${dyCellLossCssr}" id="DyCellLossCssr" requestURI="" pagesize="100" class="simple3" export="true">		    
		    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
		    <display:column property ="bscid"  titleKey="BSC" />
		    <display:column property ="cellid"  titleKey="CELL" />
		    <display:column property ="cellname"  titleKey="CELLNAME" />
		    <display:column property="tDef" titleKey="T_DEF"/>
		    <display:column property="tAvail" titleKey="T_AVAIL"/>
		    <display:column property="tAtts" titleKey="T_ATTS"/>
		    <display:column property="tSeizs" titleKey="T_SEIZS"/>
		    <display:column property="tDrps" titleKey="T_DRPS"/>
		    <display:column property="tBlks" titleKey="T_BLKS"/>
		    <display:column property="sAtts" titleKey="S_ATTS"/>
		    <display:column property="sDef" titleKey="S_DEF"/>
		    <display:column property="sAvail" titleKey="S_AVAIL"/>
		    <display:column property="sSeizs" titleKey="S_SEIZS"/>
		    <display:column property="sDrps" titleKey="S_DRPS"/>
		    <display:column property="sBlks" titleKey="S_BLKS"/>
		    <display:column property="cssr" titleKey="CSSR"/>
		</display:table>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
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

		var cachebsc = {},cachecell = {},
		lastXhrBsc,
		lastXhCell;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachebsc ) {
					response( cachebsc[ term ] );
					return;
				}

				lastXhrBsc = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cachebsc[ term ] = data;
					if ( xhr === lastXhrBsc ) {
						response( data );
					}
				});
			}
		});
		$( "#cellid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cachecell ) {
					response( cachecell[ term ] );
					return;
				}

				lastXhCell = $.getJSON( "${pageContext.request.contextPath}/ajax/getCell.htm", request, function( data, status, xhr ) {
					cachecell[ term ] = data;
					if ( xhr === lastXhCell ) {
						response( data );
					}
				});
			}
		});
	});
</script>
