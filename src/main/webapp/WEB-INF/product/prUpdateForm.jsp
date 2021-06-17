<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	int myoffset = 2;
	int mywidth = twelve - 2 * myoffset;
	int formleft = 3 ;
	int formright = twelve - formleft ; 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BootStrap Sample</title>
	<style type="text/css">
		.form-group{ margin-bottom : 3px; }
	</style>
</head>
<body>
	<div class="container col-sm-offset-<%=myoffset%> col-sm-<%=mywidth%>">
		<div class="panel panel-default panel-primary">
			<div class="panel-heading"><h4>상품 정보 수정</h4></div>
			<div class="panel-body">
				<c:set var="apppath" value="<%=request.getContextPath()%>" />
				<form:form modelAttribute="product" class="form-horizontal" role="form" action="${apppath}/update.pr"
					method="post" enctype="multipart/form-data">					
					<input type="hidden" name="pageNumber" value="${param.pageNumber}">
					<input type="hidden" name="pageSize" value="${param.pageSize}">
					<input type="hidden" name="mode" value="${param.mode}">
					<input type="hidden" name="keyword" value="${param.keyword}">
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="num">상품 번호</label>
						<div class="col-sm-<%=formright%>">
							<input path="" type="number" class="form-control" name="num" id="num"								
								placeholder="상품 번호" disabled="disabled" value="${bean.num}">
							<input type="hidden" name="num" id="num" value="${bean.num}">								
						</div>
					</div>				
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="name">상품명</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="" type="text" class="form-control" name="name" id="name"								
								placeholder="상품명" value="${bean.name}" />
								<form:errors cssClass="err" path="name" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="company">제조 회사</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="" type="text" class="form-control" name="company" id="company"								
								placeholder="제조 회사" value="${bean.company}" />
								<form:errors cssClass="err" path="company" />
						</div>
					</div>					
					<div class="form-group">
					
						<c:if test="${applicationScope.debugMode == true }">
							이전 이미지 이름 : <br>
							${bean.image} 
						</c:if>
						
						<%-- 이전 이미지 정보를 히든 파라미터로 넘깁니다. --%>
						<%-- 자바의 File 클래스를 이용하여 삭제해야 합니다. --%>
						<input name="image" type="hidden" value="${bean.image}">											
						<input name="oldimage" type="hidden" value="${bean.image}">
					
						<label class="control-label col-sm-<%=formleft%>" for="image">이미지</label>
						<div class="col-sm-<%=formright%>">
							<input type="file" class="form-control" name="abcd"
								id="abcd" placeholder="이미지를 넣어 주셔용^^">
							<form:errors cssClass="err" path="image" />								
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="stock">재고</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="" type="number" class="form-control" name="stock"
								id="stock" placeholder="재고 수량을 넣어 주셔용^^" value="${bean.stock}" />
							<form:errors cssClass="err" path="stock" />
						</div>
					</div>	
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="price">단가</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="" type="number" class="form-control" name="price"
								id="price" placeholder="단가를 넣어 주셔용^^" value="${bean.price}" />
							<form:errors cssClass="err" path="price" />
						</div>
					</div>
					<div class="form-group">
						<label for="category" class="col-xs-<%=formleft%> col-lg-<%=formleft%> control-label">카테고리</label>
	        			<div class="col-xs-<%=formright%> col-lg-<%=formright%>">
							<form:select path="category" class="form-control" name="category" id="category">
								<option value="-" selected="selected">-- 선택하세요
									---------
								<c:if test="${bean.category == 'NORMAL'}">
		        					<option value="NORMAL" selected="selected">NORMAL
		        				</c:if>        			
		            			<c:if test="${bean.category != 'NORMAL'}">
		        					<option value="NORMAL">NORMAL
		        				</c:if>										
								<c:if test="${bean.category == 'HIT'}">
		        					<option value="HIT" selected="selected">인기 상품
		        				</c:if>        			
		            			<c:if test="${bean.category != 'HIT'}">
		        					<option value="HIT">인기 상품
		        				</c:if>	 
								<c:if test="${bean.category == 'NEW'}">
		        					<option value="NEW" selected="selected">신상품
		        				</c:if>        			
		            			<c:if test="${bean.category != 'NEW'}">
		        					<option value="NEW">신상품
		        				</c:if>	
								<c:if test="${bean.category == 'BEST'}">
		        					<option value="BEST" selected="selected">베스트 상품
		        				</c:if>        			
		            			<c:if test="${bean.category != 'BEST'}">
		        					<option value="BEST">베스트 상품
		        				</c:if>
							</form:select>
							<form:errors cssClass="err" path="category" />
 	        			</div>
	        		</div>					
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="contents">상품 설명</label>
						<div class="col-sm-<%=formright%>">
							<form:textarea path="contents" name="contents" id="contents" rows="5" cols="" 
								placeholder="상품 설명" class="form-control" />
							<form:errors cssClass="err" path="contents" />							
						</div>
					</div>					
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="point">포인트</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="point" type="number" class="form-control" name="point"
								id="point" placeholder="포인트 수량을 넣어 주셔용^^" value="${bean.point}" />
							<form:errors cssClass="err" path="point" />
						</div>
					</div>					
					<div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="inputdate">입고 일자</label>
						<div class="col-sm-<%=formright%>">
							<form:input path="inputdate" type="datetime" class="form-control" name="inputdate" id="inputdate"								
								placeholder="입고 일자" value="${bean.inputdate}" />
								<form:errors cssClass="err" path="inputdate" />
						</div>
					</div>					
					<div class="form-group">
						<div align="center" class="col-sm-offset-3 col-sm-6">
							<button class="btn btn-default" type="submit">수정하기</button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-default" type="reset">취소</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	<script>
		$(document).ready(function(){
    		$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	
</body>
</html>