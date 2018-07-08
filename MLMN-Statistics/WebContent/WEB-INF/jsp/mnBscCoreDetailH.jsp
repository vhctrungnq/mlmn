<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC monthly Report</title>
<content tag="heading">BSC MONTHLY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/hr/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/wk/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/mn/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">

	<form method="get" action="detail.htm">
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
	                Tháng <input value="${month}" name="month" id="month" size="2" maxlength="2"/>
	            	Năm <input value="${year}" name="year" id="year" size="4" maxlength="4"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table>
	</form>
	<br/>
	
	<table>
		<tr>
			<td width="20%" valign="top">
			    <table class="simple2">
			        <tr>
			            <th><b>Bsc</b></th>
			            <th><b><i>${bsc.bscid}</i></b></th>
			        </tr>
			        <tr>
			            <th><b>Location</b></th>
			            <th><b><i>${bsc.region}</i></b></th>
			        </tr><tr>
			        <tr>
			            <td>%</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>CSSR (%)</td>
			            <td><a href="#sdcch">${mnBscCore.cssr} %</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="#sdcch">${mnBscCore.dcrs} %</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="#hsr">${mnBscCore.hsr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="#tch">${mnBscCore.dcrt} %</a></td>
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
					<h3><a href="#tch"><b>TCH</b></a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
            					<td><b>TCH Traffic Volume (Erl)</b></td>
           					 	<td>${mnHBscTchQos.f1}</td>
        					</tr>
					        <tr>
            					<td><b>TCH Call Drop Rate (excluding handovers) (%) </b></td>
            					<td>${mnHBscTchQos.f2} %</td>
        					</tr>
       						 <tr>
           						 <td><b>TCH Call Drop Rate (including handovers) (%)</b></td>
           						 <td>${mnHBscTchQos.f3} %</td>
        					</tr>
       						<tr>
           						<td><b>TCH Congestion Rate (TCH Overflow) (%)</b></td>
            					<td>${mnHBscTchQos.f4} %</td>
        					</tr>
        					<tr>
            					<td><b>TCH Define (Num) </b></td>
            					<td>${mnHBscTchQos.f5} </td>
        					</tr>
       						<tr>
            					<td><b>TCH Avail (Num)</b></td>
            					<td>${mnHBscTchQos.f6}</td>
        					</tr>
       						<tr>
            					<td><b>TCH Usability (%) </b></td>
            					<td>${mnHBscTchQos.f7} %</td>
        					</tr>
       						<tr>
            					<td><b>TCH utilization</b></td>
            					<td>${mnHBscTchQos.f8}</td>
        					</tr>
       						<tr>
            					<td><b>Rate of Traffic Volume to Call Drops</b></td>
            					<td>${mnHBscTchQos.f9}</td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#sdcch"><b>SDCCH</b></a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>SDCCH Traffic Volume (Erl)</b></td>
            					<td>${mnHBscSdcchQos.f1}</td>
        					</tr>
        					<tr>
            					<td><b>SDCCH Call Drop Rate (%)</b></td>
            					<td>${mnHBscSdcchQos.f2} %</td>
        					</tr>
       						<tr>
            					<td><b>SDCCH Congestion Rate (%)</b></td>
            					<td>${mnHBscSdcchQos.f3} %</td>
        					</tr>
        					<tr>
           						<td><b>SDCCH Define (Num) </b></td>
            					<td>${mnHBscSdcchQos.f4} </td>
        					</tr>
        					<tr>
            					<td><b>SDCCH Avail (Num)</b></td>
            					<td>${mnHBscSdcchQos.f5} </td>
        					</tr>
       						<tr>
            					<td><b>SDCCH Usability (%)</b></td>
            					<td>${mnHBscSdcchQos.f6} %</td>
        					</tr>
        					<tr>
           						<td><b>Call setup success rate </b></td>
            					<td>${mnHBscSdcchQos.f7} </td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#hsr"><b>HO</b></a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>IntraBSC Handover Success Rate (%) </b></td>
            					<td>${mnHBscHoQos.f1}%</td>
        					</tr>
        					<tr>
            					<td><b>IntraBSC Handover Success Rate Due to Radio Reasons (%) </b></td>
            					<td>${mnHBscHoQos.f2} %</td>
        					</tr>
        					<tr>
           				 		<td><b>OutgoingBSC Handover Success Rate (%)</b></td>
            					<td>${mnHBscHoQos.f3}%</td>
        					</tr>
        					<tr>
            					<td><b>OutgoingBSC Handover Success Rate Due to Radio Reasons (%)</b></td>
            					<td>${mnHBscHoQos.f4} %</td>
        					</tr>
        					<tr>
            					<td><b>IncomingBSC Handover Success Rate(%)</b></td>
            					<td>${mnHBscHoQos.f5} %</td>
        					</tr>
        					<tr>
            					<td><b>IncomingBSC Handover Success Rate Due to Radio Reasons(%)</b></td>
            					<td>${mnHBscHoQos.f6} %</td>
        					</tr>
					    </table>
					</div>	
					<h3><a href="#cpu"><b>CPU</b></a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>Peak percentage of CPU occupation (%)</b></td>
            					<td>${mnHBscCpuQos.f1}%</td>
        					</tr>
					    </table>
					</div>
					<h3><a href="#pch"><b>PCH</b></a></h3>
					<div>
					    <table class="simple2">
					        <tr>
            					<td><b>Circuit paging PCH overloads of ABIS interface</b></td>
            					<td>${mnHBscPchQos.f1}</td>
        					</tr>
					    </table>
					</div>
				</div>
			</td>			
	    	<td width="5%" valign="top"/>
		</tr>
	</table>
	</div>

<script type="text/javascript">
	$(function() {
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
