<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title> MSC DAY REPORT</title>
<content tag="heading">Msc Vlr Subs CN Daily</content>
<ul class="ui-tabs-nav">
	<li class="ui-tabs-selected"><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dycn.htm"><span>Tổng hợp</span></a></li>
	<li ><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm"><span>Chi tiết</span></a></li>
	<li ><a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/hrlac.htm"><span>Mức giờ</span></a></li>
</ul> 

<form:form commandName="filter" method="post" action="dycn.htm" onSubmit = "return ValidateForm()">
	<div class="ui-tabs-panel">
		<table style = "width:100%;" class="form">
			<tr>
			    <td align="left">
			    	&nbsp;MSC <form:input type="text" path="mscid" size="15"/> 
	                &nbsp;&nbsp;Từ ngày <form:input path="sdate" size="10" maxlength="10"/>
	                &nbsp;&nbsp;Đến <form:input path="edate" size="10" maxlength="10"/>    
	                &nbsp;&nbsp;<input type="submit" class="button" name="submit" id="submit" value="View Report"/>
	            </td>
	        </tr>		
		</table> 
</div> 

<div  style="overflow: auto;">
	<display:table name="${vRpDyMscVlrSubsCn}" id="vRpDyMscVlrSubsCn" requestURI="" pagesize="100" class="simple3" export="true">
	    <display:column property ="day" format="{0,date,dd/MM/yyyy}" titleKey="DAY" />
	    <display:column property ="region"  title="REGION" />
	    <display:column property ="mscid"  titleKey="MSC" /> 
	    
	    <display:column class="rightColumnMana hide" property="subCn1" titleKey="Subcribers CN1" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN1" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn1&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn1}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn1" titleKey="Subcribers Active CN1" />
	    
	    <display:column class="rightColumnMana hide" property="subCn2" titleKey="Subcribers CN2" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN2" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn2&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn2}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn2" titleKey="Subcribers Active CN2" />
	    
	    <display:column class="rightColumnMana hide" property="subCn3" titleKey="Subcribers CN3" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN3" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn3&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn3}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn3" titleKey="Subcribers Active CN3" />
	    
	    <display:column class="rightColumnMana hide" property="subCn4" titleKey="Subcribers CN4" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN4" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn4&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn4}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn4" titleKey="Subcribers Active CN4" />
	    
	    <display:column class="rightColumnMana hide" property="subCn5" titleKey="Subcribers CN5" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN5" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn5&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn5}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn5" titleKey="Subcribers Active CN5" />
		
		<display:column class="rightColumnMana hide" property="subCn6" titleKey="Subcribers CN6" headerClass="hide"/>
	    <display:column class="rightColumnMana" titleKey="Subcribers CN6" media="html">
	   	 	<a href="${pageContext.request.contextPath}/report/msc/vlrSubsCn/dylac.htm?mscid=${vRpDyMscVlrSubsCn.mscid}&cn=cn6&sdate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>&edate=<fmt:formatDate pattern="dd/MM/yyyy" value="${vRpDyMscVlrSubsCn.day}"/>">${vRpDyMscVlrSubsCn.subCn6}</a>
	    </display:column>
	    <display:column class="rightColumnMana" property="subactivCn6" titleKey="Subcribers Active CN6" />
		
		<display:column class="rightColumnMana" property="subTotal" titleKey="Subcribers TOTAL"/>
	    <display:column class="rightColumnMana" property="subactivTotal" titleKey="Subcribers Active TOTAL" />
	
	</display:table> 
</div> 
</form:form> 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/text_date.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		var trs='<tr>';
	    trs=trs +'<th rowspan="2" >DAY</th>';
	    trs=trs +'<th rowspan="2" >REGION</th>';
	    trs=trs +'<th rowspan="2" >MSC</th>'; 
	    trs=trs +'<th colspan="2" >CN1</th>'; 
	    trs=trs +'<th colspan="2" >CN2</th>';
	    trs=trs +'<th colspan="2" >CN3</th>'; 
	    trs=trs +'<th colspan="2" >CN4</th>'; 
	    trs=trs +'<th colspan="2" >CN5</th>';
	    trs=trs +'<th colspan="2" >CN6</th>';
	    trs=trs +'<th colspan="2" >TOTAL</th></tr>';  
	    
	   	trs=trs +'<tr><th >Subcribers</th>';
	   	trs=trs +'<th >Subcribers Active</th>';
	   	
	   	trs=trs +'<th >Subcribers</th>';
	   	trs=trs +'<th >Subcribers Active</th>';
	   	
	   	trs=trs +'<th >Subcribers</th>';
	   	trs=trs +'<th >Subcribers Active</th>';
	   	
	   	trs=trs +'<th >Subcribers</th>';
	   	trs=trs +'<th >Subcribers Active</th>';
	   	
	   	trs=trs +'<th>Subcribers</th>';
	    trs=trs +'<th>Subcribers Active</th>';

	    trs=trs +'<th>Subcribers</th>';
	    trs=trs +'<th>Subcribers Active</th>';
	    
	    trs=trs +'<th>Subcribers</th>';
	    trs=trs +'<th>Subcribers Active</th></tr>'; 
	    
	$('#vRpDyMscVlrSubsCn thead').html(trs);
	});
</script>
 
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
	});
</script>

<script type="text/javascript">
$(function() {
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
