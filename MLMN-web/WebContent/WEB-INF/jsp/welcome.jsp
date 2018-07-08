<%@ include file="/includes/taglibs.jsp" %>
<title>Hello</title>
<div class="dock" id="dock2">
  <div class="dock-container2">
	  ${link } 
	 </div>
</div>
<script type="text/javascript">
	
	$(document).ready(
		function()
		{
			$('#dock2').Fisheye(
				{
					maxWidth: 80,
					items: 'a',
					itemsText: 'span',
					container: '.dock-container2',
					itemWidth: 80,
					proximity: 70,
					alignment : 'left',
					valign: 'bottom',
					halign : 'center'
				}
			)
		}
	);

</script>
