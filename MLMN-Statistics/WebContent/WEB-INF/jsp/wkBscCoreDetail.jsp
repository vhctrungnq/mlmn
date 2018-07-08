<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>BSC Weekly Report</title>
<content tag="heading">BSC WEEKLY REPORT</content>
<div class="ui-tabs-panel">
<ul class="ui-tabs-nav">
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/hr/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo giờ</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/dy/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo ngày</span></a></li>
  <li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/core/bsc/wk/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tuần</span></a></li>
  <li class=""><a href="${pageContext.request.contextPath}/report/core/bsc/mn/detail.htm?bscid=${bsc.bscid}"><span>Báo cáo tháng</span></a></li>
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
	                Tuần <input value="${week}" name="week" id="week" size="2" maxlength="2"/>
					<img alt="calendar" title="Click to choose the week number" id="chooseWeek" style="cursor: pointer;" src="${pageContext.request.contextPath}/images/calendar.png"/>
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
			            <td>%</td>
			            <td>N/A</td>
			        </tr>
			        <tr>
			            <td>CSSR (%)</td>
			            <td><a href="#cssr">${wkBscCore.cssr} %</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td><a href="#psr">${wkBscCore.psr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="dcrs">${wkBscCore.dcrs} %</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="#hsr">${wkBscCore.hsr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="#dcrt">${wkBscCore.dcrt} %</a></td>
			        </tr>
			        <tr>
			            <td>TRAUCR (%)</td>
			            <td><a href="#traucr">${wkBscCore.traucr} %</a></td>
			        </tr>
			    </table>
	    	</td>
	    	<td width="5%" valign="top"/>
			<td valign="top">
				<div id="notaccordion" style="width: 800px; margin: 0 auto">
					<h3><a href="#" name="cssr">CSSR</a></h3>				
					<div>			
					    <table class="simple2">
					        <tr>
            					<td>No. of SDCCH Seizure Attempts</td>
           					 	<td>${wkBscCssrQos.f1}</td>
        					</tr>
					        <tr>
					            <td>Congestion based on Seizure Attempts (%)</td>
					            <td>${wkBscCssrQos.f2} %</td>
					        </tr>
					        <tr>
					            <td>No. of SDCCH Connections</td>
					            <td>${wkBscCssrQos.f3}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Establishments OL/UL No Congestion (%)</td>
					            <td>${wkBscCssrQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Time Congestion UL (%)</td>
					            <td>${wkBscCssrQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Total Dropped SDCCH Connections</td>
					            <td>${wkBscCssrQos.f6}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Drop (%)</td>
					            <td>${wkBscCssrQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Mean Holding Time OL/UL (Sec.)</td>
					            <td>${wkBscCssrQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Total Assignment Attempts</td>
					            <td>${wkBscCssrQos.f9}</td>
					        </tr>
					        <tr>
					            <td>TCH Assignment Success (%)</td>
					            <td>${wkBscCssrQos.f10} %</td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${wkBscCssrQos.f11} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="psr">PSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>No. of Received Paging Messages</td>
					            <td>${wkBscPsrQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Discarded Paging due to Congestion (%)</td>
					            <td>${wkBscPsrQos.f2} %</td>
					        </tr>
					        <tr>
					            <td><b>Paging successful rate (%)</b></td>
					            <td><b>${wkBscPsrQos.f3} %</b></td>
					        </tr>
					        <tr>
					            <td>Data Availability (%) </td>
					            <td>${wkBscPsrQos.f4} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="dcrs">DCRS</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Total No. of Dropped SDCCH Connections</td>
					            <td>${wkBscDcrsQos.f1}</td>
					        </tr>
					        <tr>
					            <td><b>SDCCH Drop (%)</b></td>
					            <td><b>${wkBscDcrsQos.f2} %</b></td>
					        </tr>
					        <tr>
					            <td>SDCCH Erlang Minutes per Drop</td>
					            <td>${wkBscDcrsQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS (%)</td>
					            <td>${wkBscDcrsQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality (%)</td>
					            <td>${wkBscDcrsQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Excessive TA (%)</td>
					            <td>${wkBscDcrsQos.f6} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Other (%)</td>
					            <td>${wkBscDcrsQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>Handover Lost of all SDCCH Drop (%)</td>
					            <td>${wkBscDcrsQos.f8} %</td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${wkBscDcrsQos.f9} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="dcrt">DCRT</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Total No. of Dropped TCH Connections</td>
					            <td>${wkBscDcrtQos.f1}</td>
					        </tr>
					        <tr>
					            <td><b>TCH Drop (%)</b></td>
					            <td><b>${wkBscDcrtQos.f2} %</b></td>
					        </tr>
					        <tr>
					            <td>TCH Erlang Minutes per Drop</td>
					            <td>${wkBscDcrtQos.f3} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS DL (%)</td>
					            <td>${wkBscDcrtQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS UL (%)</td>
					            <td>${wkBscDcrtQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Low SS UL/DL (%)</td>
					            <td>${wkBscDcrtQos.f6} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality DL (%)</td>
					            <td>${wkBscDcrtQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality UL (%)</td>
					            <td>${wkBscDcrtQos.f8} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Bad Quality UL/DL (%)</td>
					            <td>${wkBscDcrtQos.f9} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Suddenly Lost Connections (%)</td>
					            <td>${wkBscDcrtQos.f10} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Excessive TA (%)</td>
					            <td>${wkBscDcrtQos.f11} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER DL (%)</td>
					            <td>${wkBscDcrtQos.f12} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER UL (%)</td>
					            <td>${wkBscDcrtQos.f13} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, FER UL/DL (%)</td>
					            <td>${wkBscDcrtQos.f14} %</td>
					        </tr>
					        <tr>
					            <td>Drop Reason, Other (%)</td>
					            <td>${wkBscDcrtQos.f15} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="traucr">TRAUCR</a></h3>
					<div>
					    <table class="simple2">
						  <c:forEach var="wkBscTrapeventQos" items="${wkBscTrapeventQosList}">
					        <tr>
					            <td>TRAU type</td>
					            <td>${wkBscTrapeventQos.type}</td>
					        </tr>
					        <tr>
					            <td>TRAU active resource</td>
					            <td>${wkBscTrapeventQos.f1}</td>
					        </tr>
					        <tr>
					            <td>TRAU idle</td>
					            <td>${wkBscTrapeventQos.f2}</td>
					        </tr>
					        <tr>
					            <td>TRAU Available</td>
					            <td>${wkBscTrapeventQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Number of Transcoder Resource Allocation Attempts</td>
					            <td>${wkBscTrapeventQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Transcoder Resource Congestion</td>
					            <td>${wkBscTrapeventQos.f5}</td>
					        </tr>
					        <tr>
					            <td><b>TRAU congestion rate (%)</b></td>
					            <td><b>${wkBscTrapeventQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Number of TFO establishment attempts</td>
					            <td>${wkBscTrapeventQos.f7}</td>
					        </tr>
					        <tr>
					            <td>Number of successful TFO establishments</td>
					            <td>${wkBscTrapeventQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been
					used in a speech call</td>
					            <td>${wkBscTrapeventQos.f9}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been in
					TFO operation mode</td>
					            <td>${wkBscTrapeventQos.f10}</td>
					        </tr>
					        <tr><td colspan="2"><br/></td></tr>
						  </c:forEach>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 1</td>
					            <td>${wkBscTrapcomQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 2</td>
					            <td>${wkBscTrapcomQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 3</td>
					            <td>${wkBscTrapcomQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 1</td>
					            <td>${wkBscTrapcomQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 2</td>
					            <td>${wkBscTrapcomQos.f5}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 3</td>
					            <td>${wkBscTrapcomQos.f6}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="hsr">HSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Incoming HO Attempts </td>
					            <td>${wkBscHsrQos.f1}</td>
					        </tr>
					        <tr>
					            <td><b>Incoming HO Success (%)</b></td>
					            <td><b>${wkBscHsrQos.f3} %</b></td>
					        </tr>
					        <tr>
					            <td>Outgoing HO Attempts </td>
					            <td>${wkBscHsrQos.f4} </td>
					        </tr>
					        <tr>
					            <td><b>Outgoing HO Success (%)</b></td>
					            <td><b>${wkBscHsrQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${wkBscHsrQos.f8} %</td>
					        </tr>
					    </table>
					</div>
				</div>
			</td>			
	    	<td width="5%" valign="top"/>
		</tr>
	</table>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calendar_setup.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/calendar-blue.css" />


<script type="text/javascript">
    Calendar.setup({
        inputField		:	"week",	// id of the input field
        ifFormat		:	"%W",   	// format of the input field
        button			:   "chooseWeek",  	// trigger for the calendar (button ID)
        singleClick		:   false					// double-click mode
    });
    
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
