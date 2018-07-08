<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Hlr Daily Report</title>
<content tag="heading">HLR DAILY REPORT</content>
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/hr.htm?hlrid=${hlr.hlrid}&day=${day}"><span>Báo cáo giờ</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/hlr/dy.htm?hlrid=${hlr.hlrid}&day=${day}"><span>Báo cáo ngày</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/wk.htm?hlrid=${hlr.hlrid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/hlr/mn.htm?hlrid=${hlr.hlrid}"><span>Báo cáo tháng</span></a></li>
</ul>
<div class="ui-tabs-panel">

	<form method="get" action="dy.htm">
		<table width="100%" class="form">
			<tr>
			    <td align="left">
					HLR <input name="hlrid" id="hlrid" value="${hlr.hlrid}" size="10">
	                Ngày <input value="${day}" name="day" id="day" size="10" maxlength="10"/>
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
										<h3><a href="#" name="authen">Authentication</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Number of authentication data requests in Input - Output Subsystem (IOS) queue</td>
					            <td>${dyHlrAuthenQos.f1} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication parameters received from AUC-IOS.</td>
					            <td>${dyHlrAuthenQos.f2} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication parameters sent to VLR or SGSN</td>
					            <td>${dyHlrAuthenQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Number of times authentication parameters have been reused</td>
					            <td>${dyHlrAuthenQos.f4} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication data requests in Mobile Application Part (MAP) queue</td>
					            <td>${dyHlrAuthenQos.f5} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication parameters received from AUC-MAP</td>
					            <td>${dyHlrAuthenQos.f6}</td>
					        </tr>
					        <tr>
					            <td>Number of triplets generated by AUC</td>
					            <td>${dyHlrAuthenQos.f7}</td>
					        </tr>
					        <tr>
					            <td>Number of failed requests due to IMSI not found in AUC</td>
					            <td>${dyHlrAuthenQos.f8} </td>
					        </tr>
					        <tr>
					            <td>Number of synchronisation failures received from VLR or SGSN</td>
					            <td>${dyHlrAuthenQos.f9} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication data request messages returning GSM authentication vectors</td>
					            <td>${dyHlrAuthenQos.f10} </td>
					        </tr>
					        <tr>
					            <td>Number of authentication data request messages returning UMTS authentication vectors</td>
					            <td>${dyHlrAuthenQos.f11} </td>
					        </tr>
					        <tr>
					            <td>Number of quintets generated by AUC</td>
					            <td>${dyHlrAuthenQos.f12} </td>
					        </tr>
					        <tr>
					            <td>Number of synchronization failures received in AUC.</td>
					            <td>${dyHlrAuthenQos.f13}</td>
					        </tr>
					        <tr>
					            <td>Number of triplets requested to AUC</td>
					            <td>${dyHlrAuthenQos.f14} </td>
					        </tr>
					        <tr>
					            <td>Number of quintets requested to AUC</td>
					            <td>${dyHlrAuthenQos.f15}</td>
					        </tr>
					        <tr>
					            <td>Number of messages answered with 'resource limitation' due to overflow of a high priority queue</td>
					            <td>${dyHlrAuthenQos.f16} </td>
					        </tr>
					    </table>
					</div>
					
					<h3><a href="#" name="subscribers">HLR Subscribers Distribution</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Total number of defined Mobile Subscribers</td>
					            <td>${dyHlrSubscribersQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Total number of defined redundant subscribers</td>
					            <td>${dyHlrSubscribersQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Primary Subscribers Distribution</td>
					            <td>${dyHlrSubscribersQos.f3} </td>
					        </tr>
					        <tr>
					            <td>Redundant Subscribers Distribution </td>
					            <td>${dyHlrSubscribersQos.f4} </td>
					        </tr>
					        <tr>
					            <td>All Subscribers Distribution </td>
					            <td>${dyHlrSubscribersQos.f5} </td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="register">Register HLR Subscribers</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Daily Maximum Mobile Subscribers with known VLR</td>
					            <td>${dyHlrRegisterQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Daily Maximum Mobile Subscribers with known SGSN</td>
					            <td>${dyHlrRegisterQos.f2}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="map">HLR MAP Operations Table</a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
					            <td>Number of total requests for a MAP operation</td>
					            <td>${dyHlrMapQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Number of successful MAP operation executed</td>
					            <td>${dyHlrMapQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Successful MAP operation executed rate(%)</td>
					            <td>${dyHlrMapQos.f3} %</td>
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
		$( "#hlrid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getHlr.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			}
		});
	});
</script>