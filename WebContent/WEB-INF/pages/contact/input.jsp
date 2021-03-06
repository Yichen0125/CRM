<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑联系人</title>
    <script type="text/javascript">
    	$(function(){
    		var val = $("#contactname").val();
    		if(val != null && $.trim(val) != ""){
    			$("#contactname").attr("readonly", "readonly");
    		}
    	})
    </script>
  </head>

  <body class="main">
 	<span class="page_title">编辑联系人</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">返回</button>
		<button class="common_button" onclick="document.forms[1].submit();">保存</button>
	</div>

  	<form:form id="contact" modelAttribute="contact" action="${ctp}/contact/create" method="POST">
  		<input type="hidden" name="customer.id" value="${customerId }">
  		<c:if test="${contact.id != null }">
  			<input type="hidden" name="_method" value="PUT">
  			<input type="hidden" name="id" value="${contact.id }">
  		</c:if>
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>姓名</th>
				<td>
					<form:input path="name"/>
				<th>性别</th>
				<td>
					<% 
						Map<String, String> map = new HashMap<String, String>();
						map.put("男", "男");
						map.put("女", "女");
						request.setAttribute("sex", map);
					%>
					<form:select path="sex" items="${sex }"></form:select>
					<span class="red_star">*</span> 
				</td>
			</tr>
			
			<tr>
				<th>职位</th>
				<td><form:input path="position"/><span class="red_star">*</span></td>
				<th>办公电话</th>
				<td><form:input path="tel"/><span class="red_star">*</span></td>
			</tr>
			<tr>
				<th>手机</th>
				<td><form:input path="mobile"/></td>
				<th>备注</th>
				<td><form:input path="memo"/></td>
			</tr>
		</table>
	</form:form>
	
  </body>
</html>
