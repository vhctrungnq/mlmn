<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title> MSC DAY REPORT</title>
<content tag="heading">Msc Vlr Subs Lac Daily</content>
<ul class="ui-tabs-nav">
	<li class=""><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dycn.htm"><span>Tổng hợp</span></a></li>
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm"><span>Chi tiết</span></a></li>
	<li class=""><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/hrlac.htm"><span>Mức giờ</span></a></li>
</ul> 

<form:form commandName="filter" method="post" action="dylac.htm" onSubmit = "return ValidateForm()">
	<div class="ui-tabs-panel">
		<table style = "width:100%;" class="form">
			<tr>
			    <td align="left">
			    	&nbsp;CN <form:input type="text" path="cn" size="10"/>
	               	&nbsp;&nbsp;MSC <form:input type="text" path="mscid" size="10"/> 
					&nbsp;&nbsp;BSC <form:input type="text" path="bscid" size="10"/> 
	                &nbsp;&nbsp;Từ ngày <form:input path="sdate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;Đến <form:input path="edate" size="10" maxlength="10"/>  
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table> 
</div>
<div  style="overflow: auto;">
	<display:table name="${vRpDyMscVlrSubsLac}" id="vRpDyMscVlrSubsLac" requestURI="" pagesize="100" class="simple3" export="true">
	    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
	    <display:column property ="cn"  titleKey="CN" />
	    <display:column property ="mscid"  titleKey="MSC" />
	    <display:column property ="bscid"  titleKey="BSC/RNC" />
	    <display:column class="rightColumnMana hide" property ="lac"  titleKey="LAC" headerClass="hide"/>
	    <display:column class="rightColumnMana" title="LAC" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/hrlac.htm?mscid=${vRpDyMscVlrSubsLac.mscid}&cn=${vRpDyMscVlrSubsLac.cn}&bscid=${vRpDyMscVlrSubsLac.bscid}&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsLac.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsLac.day}"/>">${vRpDyMscVlrSubsLac.lac}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="sub" titleKey="Tổng sub" />
	    <display:column class="rightColumnMana" property="subactiv" titleKey="Tổng active" /> 
	</display:table> 
</div>
</form:form> 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>
<script type = "text/javascript">
	function xl(){
		var sub = document.getElementById("submit");
		sub.focus();
	} 
	$(function() {
		$( "#sdate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		$( "#edate" ).datepicker({
			dateFormat: "dd/mm/yy",
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/images/calendar.png",
			buttonImageOnly: true
		});
		 
		var cache = {},
		lastXhr;
		$( "#mscid" ).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getMsc.htm", request, function( data, status, xhr ) {
					cache[ term ] = data;
					if ( xhr === lastXhr ) {
						response( data );
					}
				});
			} 
		});
	});
</script>

<script type="text/javascript">
$(function() {
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

			lastXhr = $.getJSON( "${pageContext.request.contextPath}/ajax/getBsc2g3g.htm", request, function( data, status, xhr ) {
				cache[ term ] = data;
				if ( xhr === lastXhr ) {
					response( data );
				}
			});
		}
	});
});	
</script>
