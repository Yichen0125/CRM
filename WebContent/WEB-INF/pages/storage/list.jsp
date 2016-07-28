<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="atguigu" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>库存查询</title>
	<script type="text/javascript">
		$(function(){
			$("img[id^='delete-']").click(function(){
				var id = this.id.split("-")[1];
				var url = "${ctp}/storage/delete/"+id;
				$("#hiddenForm").attr("action",url);
				$("#_method").val("DELETE");
				$("#hiddenForm").submit();
			});
		});
	</script>
</head>
<body>
	<div class="page_title">
		库存管理
	</div>
	<div class="button_bar">
		<button class="common_button"
			onclick="window.location.href='${ctp}/storage/create'">
			库存添加
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/storage/list" method="POST">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					产品
				</th>
				<td>
					<input type="text" name="search_LIKES_productName" />
				</td>
				<th>
					仓库
				</th>
				<td>
					<input type="text" name="search_LIKES_wareHouse" />
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
					产品
				</th>
				<th>
					仓库
				</th>
				<th>
					货位
				</th>
				<th>
					件数
				</th>
				<th>
					备注
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:forEach items="${page.content }" var="storage">
			
				<tr>
					<td class="list_data_number">
						${storage.id }
					</td>
					<td class="list_data_ltext">
						${storage.product.name }
					</td>
					<td class="list_data_ltext">
						${storage.wareHouse }
					</td>
					<td class="list_data_text">
						${storage.stockWare }
					</td>
					<td class="list_data_number">
						${storage.stockCount }
					</td>
					<td class="list_data_ltext">
						${storage.memo }
					</td>
					<td class="list_data_op">
						<img onclick="window.location.href='${ctp}/storage/create/${storage.id }'" 
							title="修改" src="${ctp}/static/images/bt_edit.gif" class="op_button" />
						<img id="delete-${storage.id }" 
							title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />

					</td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
		<atguigu:page page="${page }"/>
	</form>
</body>
</html>