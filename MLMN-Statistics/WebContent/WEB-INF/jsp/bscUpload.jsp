<%@ include file="/commons/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<title>Upload dữ liệu Bsc</title>
<content tag="heading">Upload dữ liệu Bsc</content>
 	
<form:form method="post" action="upload.htm" enctype="multipart/form-data" >
	
	<table class="simple2">
		<tr>
			<td class="wid10 mwid140"><b>File</b></td>
			<td class="wid50"><input class="button" type="file" name="file" size="90"/></td>
			<td><input class="button" type="submit" class="button" name="save" value="Import file"/></td>
		</tr>
		<tr>
				<td>
				<b>Định dạng file import</b>
				</td>
				<td colspan="2">
					<ul>
						<li>File import là file (.xls)</li>
						<li>Dữ liệu trong file có dạng: 
							<code>
								&lt;BSC&gt;<font color="red">(*)</font>,
								&lt;Vendor&gt;,
								&lt;Dept&gt;,
								&lt;Team&gt;,
								&lt;Sub Team&gt;,
								&lt;Ne Group&gt;,
								&lt;Location Name&gt;,
								&lt;MSC&gt;,
								&lt;SMS CSSR&gt;,
								&lt;SMS DRC&gt;,
								&lt;TRAU&gt;,
								&lt;TRX&gt;,
								&lt;NSEI&gt;
							</code>
						</li>
						<li>File mẫu:&nbsp;<a style="color: blue; " title="BscExample" href="${pageContext.request.contextPath}/upload/BscExample.xls">BscExample.xls</a>
						</li>
						<li>Chú ý:</li>
						<li>&nbsp;&nbsp;- Những thông tin được đánh dấu <font color="red">(*)</font> là thông tin nhập liệu bắt buộc. </li>
					</ul>
			</td>
		</tr>
	</table>
	<c:if test="${status != null}">
    	<div class="error">
    	  ${status} <br/>
    	  ${status1} <br/>
    	  ${status2} <br/>
    	 </div>
    </c:if>
    <c:if test="${fn:length(failedList) gt 0}">
    	<div id="failed">
    		<div><b>Dữ liệu Bsc không hợp lệ  ( ${failNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${failedList}" class="simple2" id="item1" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item1_rowNum}"/> </display:column>
							<display:column property="bscid" title="BSC"  class="NOT_NULL"/>
							<display:column property="vendor" titleKey="Vendor"/> 
							<display:column property="dept" titleKey="Dept"/>   
							<display:column property="team" titleKey="Team"/>	
							<display:column property="subTeam" titleKey="Subteam"/>
							<display:column property="neGroup" title="NE Group" />	     
						    <display:column property="locationName" titleKey="Location name" />
						    <display:column property="mscid" titleKey="MSC"/> 
						    <display:column property="strSmsCssr" titleKey="SMS CSSR" />
						    <display:column property="strSmsDrc" titleKey="SMS DRC" />
						    <display:column property="strTrau" titleKey="TRAU" />
						    <display:column property="strTrx" titleKey="TRX" />	
						    <display:column property="nsei" titleKey="NSEI" />	
				</display:table>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(successList) gt 0}">
    	<div id="success">
    		<div><b>Dữ liệu Bsc hợp lệ  ( ${successNum}/${totalNum} )</b></div>
    		
    		<div style="max-height: 500px; overflow: auto;">
    			<display:table name="${successList}" class="simple2" id="item2" requestURI="" export="false" pagesize="100">
							<display:column class="centerColumnMana" titleKey="global.list.STT" > <c:out value="${item2_rowNum}"/> </display:column>
							<display:column property="bscid" title="BSC"  class="NOT_NULL"/>
							<display:column property="vendor" titleKey="Vendor"/> 
							<display:column property="dept" titleKey="Dept"/>   
							<display:column property="team" titleKey="Team"/>	
							<display:column property="subTeam" titleKey="Subteam"/>
							<display:column property="neGroup" title="NE Group" />	     
						    <display:column property="locationName" titleKey="Location name" />
						    <display:column property="mscid" titleKey="MSC"/> 
						    <display:column property="smsCssr" titleKey="SMS CSSR" />
						    <display:column property="smsDrc" titleKey="SMS DRC" />
						    <display:column property="trau" titleKey="TRAU" />
						    <display:column property="trx" titleKey="TRX" />
						    <display:column property="nsei" titleKey="NSEI" />
				</display:table>
			</div>
		</div>
	</c:if>
		<table>
		<tr>
			<td >
               	<input class="button" type="button" value="<fmt:message key="global.button.back"/>" onClick='window.location="list.htm"'>
			</td>
		</tr>
	</table>
</form:form>

<script type="text/javascript">  
    $(function() {
    	$('#item2>tbody>tr').each(
    	    	 function(){
   					  ${highlight}
   						});

    	$('#item1>tbody>tr').each(
   	    	 function(){
  					  ${highlight}
  					});
		}); 
</script>
