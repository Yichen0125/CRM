<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>暂缓流失</title>
    <script type="text/javascript">
    	$(function(){
    		$("#save").click(function(){
	    		var no = 1;
    			var targetStr = $("#targetTr").prev("tr").children("th").text();
    			var noStr = targetStr[5];
				
    			if(!parseInt(noStr)) {
    				no = 1;
    			} else {
    				no = parseInt(noStr) + 1;
    			}
    			
    			var delay = $("textarea[name='delay']").val();
    			var url = "${ctp}/drain/delay/${customerDrain.id}";
    			var args = {"delay":delay};
    			$.post(url, args, function(data){
    				if(data == "1") {
    					var $tr = $("<tr><th>暂缓措施-"+no+"</th><td colspan='3'>" + delay + "</td></tr>")
    					$("#targetTr").before($tr);
    					$("textarea[name='delay']").val("")
    				}
    			});
    			return false;
    		});
    	});
    	/*  */

    </script>
  </head>

  <span class="page_title">暂缓流失</span>
  <div class="button_bar">
	<button class="common_button" onclick="javascript:history.go(-1);">返回</button>
	<button class="common_button" onclick="window.location.href='${ctp}/drain/confirm/${customerDrain.id}/${customerDrain.customer.id}'">确认流失</button>
	<button class="common_button" id="save">保存</button>
  </div>
	
  <body class="main">
	  <form action="${ctp}/drain/delay" method="post">
		  	<input type="hidden" name="id" value="${customerDrain.id}"/>
			<table id="table1" class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>编号</th>
					<td>${customerDrain.id}</td>
					<th>客户</th>
					<td>${customerDrain.customer.name}</td>
				</tr>
				<tr id="targer">
					<th>客户经理</th>
					<td>${customerDrain.customer.manager.name}</td>
					<th>最后一次下单时间</th>
					<td><fmt:formatDate value="${customerDrain.lastOrderDate }" pattern="yyyy-MM-dd"/></td>
				</tr>
				<c:forTokens items="${customerDrain.delay}" delims="`" var="delay" varStatus="status">
					<c:if test="${delay != '' }">
						<tr>
							<th>暂缓措施-${status.count }</th>
							<td colspan="3">${delay}</td>
						</tr>
					</c:if>
				</c:forTokens>
				<tr id="targetTr">
					<th>追加暂缓措施</th>
					<td colspan="3"><textarea name="delay" cols="50" rows="6"></textarea>&nbsp;</td>
				</tr>
			</table>
	   </form>	
  </body>
</html>