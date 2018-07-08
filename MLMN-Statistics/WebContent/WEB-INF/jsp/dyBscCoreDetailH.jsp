<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC Daily Report</title>
<content tag="heading">BSC DAILY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/hr/detail.htm?bscid=${bsc.bscid}&day=${day}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bsc.bscid}&day=${day}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/wk/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/mn/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">

	<form method="get" action="main.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					BSC 
					<select name="bscid" id="bscid" style="width: 163px">
			        <option value="">--Select BSC--</option>
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
	                Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
	
	<table>
		<tr>
			<td width="35%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>Bsc</b></th>
			            <th><b><i><a href="main.htm?bscid=${bsc.bscid}&day=${day}">${bsc.bscid}</a></i></b></th>
			        </tr>
			        <tr>
			            <td>%</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>CSSR (%)</td>
			            <td><a href="#sdcch">${dyBscCore.cssr} %</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="#sdcch">${dyBscCore.dcrs} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="#tch">${dyBscCore.dcrt} %</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="#hsr">${dyBscCore.hsr} %</a></td>
			        </tr>
			        <tr>
			            <td>TRAUCR (%)</td>
			            <td>N/A</td>
			        </tr>
			    </table>
	    	</td>
	    	<td width="5%" valign="top"/>
			<td valign="top">
				<div id="notaccordion" style="width: 800px; margin: 0 auto">
					<h3><a href="#" name="tch">TCH</a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
            					<td><b>TCH Traffic Volume (Erl)</b></td>
           					 	<td>${dyHBscTchQos.f1}</td>
        					</tr>
					        <tr>
            					<td><b>TCH Call Drop Rate (excluding handovers) (%) </b></td>
            					<td>${dyHBscTchQos.f2} %</td>
        					</tr>
       						 <tr>
           						 <td><b>TCH Call Drop Rate (including handovers) (%)</b></td>
           						 <td>${dyHBscTchQos.f3} %</td>
        					</tr>
       						<tr>
           						<td><b>TCH Congestion Rate (TCH Overflow) (%)</b></td>
            					<td>${dyHBscTchQos.f4} %</td>
        					</tr>
        					<tr>
            					<td><b>TCH Define (Num) </b></td>
            					<td>${dyHBscTchQos.f5} </td>
        					</tr>
       						<tr>
            					<td><b>TCH Avail (Num)</b></td>
            					<td>${dyHBscTchQos.f6}</td>
        					</tr>
       						<tr>
            					<td><b>TCH Usability (%) </b></td>
            					<td>${dyHBscTchQos.f7} %</td>
        					</tr>
       						<tr>
            					<td><b>TCH utilization</b></td>
            					<td>${dyHBscTchQos.f8}</td>
        					</tr>
       						<tr>
            					<td><b>Rate of Traffic Volume to Call Drops</b></td>
            					<td>${dyHBscTchQos.f9}</td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#" name="sdcch">SDCCH</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>SDCCH Traffic Volume (Erl)</b></td>
            					<td>${dyHBscSdcchQos.f1}</td>
        					</tr>
        					<tr>
            					<td><b>SDCCH Call Drop Rate (%)</b></td>
            					<td>${dyHBscSdcchQos.f2} %</td>
        					</tr>
       						<tr>
            					<td><b>SDCCH Congestion Rate (%)</b></td>
            					<td>${dyHBscSdcchQos.f3} %</td>
        					</tr>
        					<tr>
           						<td><b>SDCCH Define (Num) </b></td>
            					<td>${dyHBscSdcchQos.f4} </td>
        					</tr>
        					<tr>
            					<td><b>SDCCH Avail (Num)</b></td>
            					<td>${dyHBscSdcchQos.f5} </td>
        					</tr>
       						<tr>
            					<td><b>SDCCH Usability (%)</b></td>
            					<td>${dyHBscSdcchQos.f6} %</td>
        					</tr>
        					<tr>
           						<td><b>Call setup success rate </b></td>
            					<td>${dyHBscSdcchQos.f7} </td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#" name="ho">HO</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>IntraBSC Handover Success Rate (%) </b></td>
            					<td>${dyHBscHoQos.f1} %</td>
        					</tr>
        					<tr>
            					<td><b>IntraBSC Handover Success Rate Due to Radio Reasons (%) </b></td>
            					<td>${dyHBscHoQos.f2} %</td>
        					</tr>
        					<tr>
           				 		<td><b>OutgoingBSC Handover Success Rate (%)</b></td>
            					<td>${dyHBscHoQos.f3} %</td>
        					</tr>
        					<tr>
            					<td><b>OutgoingBSC Handover Success Rate Due to Radio Reasons (%)</b></td>
            					<td>${dyHBscHoQos.f4} %</td>
        					</tr>
        					<tr>
            					<td><b>IncomingBSC Handover Success Rate(%)</b></td>
            					<td>${dyHBscHoQos.f5} %</td>
        					</tr>
        					<tr>
            					<td><b>IncomingBSC Handover Success Rate Due to Radio Reasons(%)</b></td>
            					<td>${dyHBscHoQos.f6} %</td>
        					</tr>
					    </table>
					</div>	
					<h3><a href="#" name="cpu">CPU</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>Peak percentage of CPU occupation (%)</b></td>
            					<td>${dyHBscCpuQos.f1} %</td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#" name="pch">PCH</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>Circuit paging PCH overloads of ABIS interface</b></td>
            					<td>${dyHBscPchQos.f1}</td>
        					</tr>
					    </table>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	$(function() {
		$( "#day" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});

		$("#notaccordion").addClass("ui-accordion ui-widget ui-helper-reset ui-accordion-icons")
		.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-active ui-corner-top ui-state-focus")
			.prepend('<span class="ui-icon ui-icon-triangle-1-s"/>')
			.click(function() {
				$(this).toggleClass("ui-state-active ui-corner-top ui-state-focus ui-state-default ui-corner-all")
				.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s")
				.end().next().toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").toggle();
				return false;
			})
			.next().addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom").show();
		
		var cache = {},
		lastXhr;
		$( "#bscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
