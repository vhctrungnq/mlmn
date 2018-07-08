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

	<form method="get" action="detail.htm" name = "frmSample" onSubmit = "return ValidateForm()">
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
			            <td><a href="#cssr">${dyBscCore.cssr} %</a></td>
			        </tr>
			        <tr>
			            <td>PSR (%)</td>
			            <td><a href="#psr">${dyBscCore.psr} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRS (%)</td>
			            <td><a href="#dcrs">${dyBscCore.dcrs} %</a></td>
			        </tr>
			        <tr>
			            <td>DCRT (%)</td>
			            <td><a href="#dcrt">${dyBscCore.dcrt} %</a></td>
			        </tr>
			        <tr>
			            <td>TRAUCR (%)</td>
			            <td><a href="#traucr">${dyBscCore.traucr} %</a></td>
			        </tr>
			        <tr>
			            <td>HSR (%)</td>
			            <td><a href="#hsr">${dyBscCore.hsr} %</a></td>
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
           					 	<td>${dyBscCssrQos.f1}</td>
        					</tr>
					        <tr>
					            <td>Congestion based on Seizure Attempts (%)</td>
					            <td>${dyBscCssrQos.f2} %</td>
					        </tr>
					        <tr>
					            <td>No. of SDCCH Connections</td>
					            <td>${dyBscCssrQos.f3}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Establishments OL/UL No Congestion (%)</td>
					            <td>${dyBscCssrQos.f4} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Time Congestion UL (%)</td>
					            <td>${dyBscCssrQos.f5} %</td>
					        </tr>
					        <tr>
					            <td>Total Dropped SDCCH Connections</td>
					            <td>${dyBscCssrQos.f6}</td>
					        </tr>
					        <tr>
					            <td>SDCCH Drop (%)</td>
					            <td>${dyBscCssrQos.f7} %</td>
					        </tr>
					        <tr>
					            <td>SDCCH Mean Holding Time OL/UL (Sec.)</td>
					            <td>${dyBscCssrQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Total Assignment Attempts</td>
					            <td>${dyBscCssrQos.f9}</td>
					        </tr>
					        <tr>
					            <td>TCH Assignment Success (%)</td>
					            <td>${dyBscCssrQos.f10} %</td>
					        </tr>
					        <tr>
					            <td>Data Availability (%)</td>
					            <td>${dyBscCssrQos.f11} %</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="psr">PSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
				            <td>No. of Received Paging Messages</td>
				            <td>${dyBscPsrQos.f1}</td>
				        </tr>
				        <tr>
				            <td>Discarded Paging due to Congestion (%)</td>
				            <td>${dyBscPsrQos.f2} %</td>
				        </tr>
				        <tr>
				            <td><b>Paging successful rate (%)</b></td>
				            <td><b>${dyBscPsrQos.f3} %</b></td>
				        </tr>
				        <tr>
				            <td>Data Availability (%)</td>
				            <td>${dyBscPsrQos.f4} %</td>
				        </tr>
					    </table>
					</div>
					<h3><a href="#" name="dcrs">DCRS</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
				            <td>Total No. of Dropped SDCCH Connections</td>
				            <td>${dyBscDcrsQos.f1}</td>
				        </tr>
				        <tr>
				            <td><b>SDCCH Drop (%)</b></td>
				            <td><b>${dyBscDcrsQos.f2} %</b></td>
				        </tr>
				        <tr>
				            <td>SDCCH Erlang Minutes per Drop</td>
				            <td>${dyBscDcrsQos.f3}</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Low SS (%)</td>
				            <td>${dyBscDcrsQos.f4} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Bad Quality (%)</td>
				            <td>${dyBscDcrsQos.f5} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Excessive TA (%)</td>
				            <td>${dyBscDcrsQos.f6} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Other (%)</td>
				            <td>${dyBscDcrsQos.f7} %</td>
				        </tr>
				        <tr>
				            <td>Handover Lost of all SDCCH Drop (%)</td>
				            <td>${dyBscDcrsQos.f8} %</td>
				        </tr>
				        <tr>
				            <td>Data Availability (%)</td>
				            <td>${dyBscDcrsQos.f9} %</td>
				        </tr>
					    </table>
					</div>
					
					
					<h3><a href="#" name="dcrt">DCRT</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
				            <td>Total No. of Dropped TCH Connections</td>
				            <td>${dyBscDcrtQos.f1}</td>
				        </tr>
				        <tr>
				            <td><b>TCH Drop (%)</b></td>
				            <td><b>${dyBscDcrtQos.f2} %</b></td>
				        </tr>
				        <tr>
				            <td>TCH Erlang Minutes per Drop</td>
				            <td>${dyBscDcrtQos.f3}</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Low SS DL (%)</td>
				            <td>${dyBscDcrtQos.f4} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Low SS UL (%)</td>
				            <td>${dyBscDcrtQos.f5} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Low SS UL/DL (%)</td>
				            <td>${dyBscDcrtQos.f6} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Bad Quality DL (%)</td>
				            <td>${dyBscDcrtQos.f7} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Bad Quality UL (%)</td>
				            <td>${dyBscDcrtQos.f8} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Bad Quality UL/DL (%)</td>
				            <td>${dyBscDcrtQos.f9} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Suddenly Lost Connections (%)</td>
				            <td>${dyBscDcrtQos.f10} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Excessive TA (%)</td>
				            <td>${dyBscDcrtQos.f11} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, FER DL (%)</td>
				            <td>${dyBscDcrtQos.f12} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, FER UL (%)</td>
				            <td>${dyBscDcrtQos.f13} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, FER UL/DL (%)</td>
				            <td>${dyBscDcrtQos.f14} %</td>
				        </tr>
				        <tr>
				            <td>Drop Reason, Other (%)</td>
				            <td>${dyBscDcrtQos.f15} %</td>
				        </tr>
					    </table>
					</div>
					<h3><a href="#" name="traucr">TRAUCR</a></h3>
					<div>
					    <table class="simple2">
						  <c:forEach var="dyBscTrapeventQos" items="${dyBscTrapeventQosList}">
					        <tr>
					            <td>TRAU type</td>
					            <td>${dyBscTrapeventQos.type}</td>
					        </tr>
					        <tr>
					            <td>TRAU active resource</td>
					            <td>${dyBscTrapeventQos.f1}</td>
					        </tr>
					        <tr>
					            <td>TRAU idle</td>
					            <td>${dyBscTrapeventQos.f2}</td>
					        </tr>
					        <tr>
					            <td>TRAU Available</td>
					            <td>${dyBscTrapeventQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Number of Transcoder Resource Allocation Attempts</td>
					            <td>${dyBscTrapeventQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Transcoder Resource Congestion</td>
					            <td>${dyBscTrapeventQos.f5}</td>
					        </tr>
					        <tr>
					            <td><b>TRAU congestion rate (%)</b></td>
					            <td><b>${dyBscTrapeventQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Number of TFO establishment attempts</td>
					            <td>${dyBscTrapeventQos.f7}</td>
					        </tr>
					        <tr>
					            <td>Number of successful TFO establishments</td>
					            <td>${dyBscTrapeventQos.f8}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been
					used in a speech call</td>
					            <td>${dyBscTrapeventQos.f9}</td>
					        </tr>
					        <tr>
					            <td>Accumulated number of seconds all TFO capable devices have been in
					TFO operation mode</td>
					            <td>${dyBscTrapeventQos.f10}</td>
					        </tr>
					        <tr><td colspan="2"><br/></td></tr>
						  </c:forEach>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 1</td>
					            <td>${dyBscTrapcomQos.f1}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 2</td>
					            <td>${dyBscTrapcomQos.f2}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Full Rate speech Version 3</td>
					            <td>${dyBscTrapcomQos.f3}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 1</td>
					            <td>${dyBscTrapcomQos.f4}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 2</td>
					            <td>${dyBscTrapcomQos.f5}</td>
					        </tr>
					        <tr>
					            <td>Unsuccessful Transcoder Resource Allocation Attempts due to missing
					transcoder pool for Haft Rate speech Version 3</td>
					            <td>${dyBscTrapcomQos.f6}</td>
					        </tr>
					    </table>
					</div>
					<h3><a href="#" name="hsr">HSR</a></h3>
					<div>
					    <table class="simple2">
					        <tr>
					            <td>Incoming HO Attempts</td>
					            <td>${dyBscHsrQos.f1} </td>
					        </tr>
					        <tr>
					            <td><b>Incoming HO Success (%)</b></td>
					            <td><b>${dyBscHsrQos.f3} %</b></td>
					        </tr>
					        <tr>
					            <td>Outgoing HO Attempts </td>
					            <td>${dyBscHsrQos.f4} </td>
					        </tr>
					        <tr>
					            <td><b>Outgoing HO Success (%)</b></td>
					            <td><b>${dyBscHsrQos.f6} %</b></td>
					        </tr>
					        <tr>
					            <td>Data Availability (%) </td>
					            <td>${dyBscHsrQos.f8} %</td>
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
