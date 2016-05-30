<%@ page import="com.yqmac.exam.util.SystemContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">

    span.pagernum,span.pagerstr{
        border: 1px solid gray;
        text-align: center;
        padding: 10px 8px;
        border-color: #aaa;
    }
    a.pager_link:link,a.pager_link:visited{
        text-decoration: none;
        color: black;
    }
    span.selected{
        background-color: rgba(199, 243, 255, 0.47);
        border-color: #000000;
        font-weight: bloder;
    }

    span.unselected:hover,span.pagerstr:hover {
        /*background-color: #389fff;*/
        border-color: black;
        cursor: pointer;
    }



</style>
<pg:pager export="curPage=pageNumber"
          items="${param.totalRecord }"
          maxPageItems="<%=SystemContext.getPageSize() %>"
          url="${param.url }">
	<span style="float: left;padding: 6px">
	共
	<pg:last>
        ${pageNumber } 页[${param.totalRecord }条记录],每页显示<%=SystemContext.getPageSize() %>条,
    </pg:last>
	<c:forEach items="${param.params }" var="p">
        <pg:param name="${p }"/>
    </c:forEach>


	</span>
    <span style="float:right;padding:6px;">

    <pg:first>
        <a href="${pageUrl }" class="pager_link"><span class="pagerstr fe">首页</span></a>
    </pg:first>
    <pg:prev>
        <a href="${pageUrl }" class="pager_link"><span class="pagerstr pn">上一页</span></a>
    </pg:prev>
    <pg:pages>
        <c:if test="${curPage eq pageNumber }">
			<span class="pagernum selected"><strong>${pageNumber }</strong></span>
        </c:if>
        <c:if test="${curPage != pageNumber }">
            <a href="${pageUrl }" class="pager_link"><span class="pagernum unselected">${pageNumber }</span></a>
        </c:if>
    </pg:pages>

    <pg:next>
        <a href="${pageUrl }" class="pager_link"><span class="pagerstr pn">下一页</span></a>
    </pg:next>
    <pg:last>
        <a href="${pageUrl }" class="pager_link"><span class="pagerstr fe">尾页</span></a>
    </pg:last>


</pg:pager>
</span>