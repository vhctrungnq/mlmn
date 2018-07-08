<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Stp Hourly Report</title>
<content tag="heading">STP HOURLY REPORT</content>

<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/stp/hr.htm?stpid=${stp.stpid}&day=${day}"><span>Báo cáo giờ</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/dy.htm?stpid=${stp.stpid}"><span>Báo cáo ngày</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/wk.htm?stpid=${stp.stpid}"><span>Báo cáo tuần</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/core/stp/mn.htm?stpid=${stp.stpid}"><span>Báo cáo tháng</span></a></li>
</ul>

<div class="ui-tabs-panel">
	<form method="get" action="hr.htm" name = "frmSample" onSubmit = "return ValidateForm()">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
			    	STP <input name="stpid" id="stpid" value="${stp.stpid}" size="10">
	                Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10"/>
	                Giờ <input value="${hour}" name="hour" id="hour" size="2"/>
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" value="View Report"/>
	            </td>	                
	        </tr>		
		</table>
	</form>
	<br/>
	
	<table>
		<tr>
	    	<td width="25%" valign="top"/>
			<td valign="top">
				<div id="notaccordion" style="width: 800px; margin: 0 auto">
					<h3><a href="#" name="association">ASSOCIATION</a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
					            <td>Number of times an association becomes unavailable for traffic</td>
					            <td>${hrStpAssonciationQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Duration of unavailability of association for traffic (number of seconds)</td>
					            <td>${hrStpAssonciationQos.f2}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="destination">DESTINATION</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of times a destination becomes unreachable</td>
					            <td>${hrStpDestinationQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Duration of inaccessibility of destination (number of seconds)</td>
					            <td>${hrStpDestinationQos.f2}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="utilisation">UTILISATION</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of bytes user data sent by SCTP(variables counts the whole SCTP payload, meaning both M3UA header and payload)</td>
					            <td>${hrStpUtilisationQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Number of bytes user data received by SCTP(variables counts the whole SCTP payload, meaning both M3UA header and payload)</td>
					            <td>${hrStpUtilisationQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Sigtrans utilization RPB-E (%)</td>
					            <td>${hrStpUtilisationQos.f3} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="m3data">M3DATA</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Nr. of Data messages sent</td>
					            <td>${hrStpM3dataQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Nr. of Data messages discarded</td>
					            <td>${hrStpM3dataQos.f2}</td>
					        </tr>
					        <tr>
					            <td>M3UA data message transfer success ratio (%)</td>
					            <td>${hrStpM3dataQos.f3} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="m3perf">M3PERF</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Nr. of occurrences of message discarded due to a routing error</td>
					            <td>${hrStpM3perfQos.f1}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="outofblue">OUTOFBLUE</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of out of blue packets</td>
					            <td>${hrStpOutOfBlueQos.f1}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="checksum">CHECKSUM</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of checksum errors</td>
					            <td>${hrStpChecksumQos.f1}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="retransmition">RETRANSMITION</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of sent data chunks</td>
					            <td>${hrStpRetransmitionQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Number of retransmitted data chunks</td>
					            <td>${hrStpRetransmitionQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Retransmission rate(%)</td>
					            <td>${hrStpRetransmitionQos.f3} %</td>
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
		$( "#stpid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getStp.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>
