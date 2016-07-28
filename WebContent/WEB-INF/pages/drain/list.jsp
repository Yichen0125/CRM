<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>客户流失管理</title>
</head>
<body>

	<div class="page_title">
		客户流失管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/drain/list" method="post"> 
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					客户名称
				</th>
				<td>
					<input type="text" name="search_LIKES_customerName" />
				</td>
				<th>
					客户经理
				</th>
				<td>
					<input type="text" name="search_LIKES_customerManagerName" />
				</td>
				<th>
					&nbsp;
				</th>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		<c:if test="${empty page.content }">
			没有相关信息
		</c:if>
	
		<c:if test="${!empty page.content }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>
						编号
					</th>
					<th>
						客户名称
					</th>
					<th>
						客户经理
					</th>
					<th>
						上次下单时间
					</th>
					<th>
						确认流失时间
					</th>
					<th>
						流失原因
					</th>
					<th>
						状态
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach items="${page.content }" var="customerDrain">
					<tr>
						<td class="list_data_number">
							${customerDrain.id }
						</td>
						<td class="list_data_ltext">
							${customerDrain.customer.name }
						</td>
						<td class="list_data_text">
							${customerDrain.customer.manager.name }
						</td>
						<td class="list_data_text">
							<fmt:formatDate value="${customerDrain.lastOrderDate }"/>
						</td>
						<td class="list_data_text">
							<fmt:formatDate value="${customerDrain.drainDate }"/>
						</td>
						<td class="list_data_ltext">
							${customerDrain.reason } 
						</td>
						<td class="list_data_text">
							${customerDrain.status }
						</td>
						<td class="list_data_op">
							<c:if test="${customerDrain.status == '流失预警'}">
								<img onclick="window.location.href=''" 
									title="确认流失" src="${ctp }/static/images/bt_confirm.gif" class="op_button" />
								<img onclick="window.location.href='${ctp}/drain/delay/${customerDrain.id }'" 
									title="暂缓流失" src="${ctp }/static/images/bt_relay.gif" class="op_button" />
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</c:if>
			
	</form>
</body>
</html>
