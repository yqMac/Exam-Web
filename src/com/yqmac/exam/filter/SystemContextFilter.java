package com.yqmac.exam.filter;

import com.yqmac.exam.util.SystemContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by yqmac on 2016/5/11 0011.
 */
public class SystemContextFilter implements Filter {
    private Integer pageSize;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            pageSize = Integer.parseInt(filterConfig.getInitParameter("pageSize"));
        } catch (NumberFormatException ex) {
            pageSize = 15;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        int offset =0;

        try {
            offset = Integer.parseInt(servletRequest.getParameter("pager.offset"));
        } catch (NumberFormatException e) {
            offset=0;
        }
        try {
            SystemContext.setOrder(servletRequest.getParameter("order"));
            SystemContext.setSort(servletRequest.getParameter("sort"));
            SystemContext.setPageOffset(offset);
            SystemContext.setPageSize(pageSize);
            filterChain.doFilter(servletRequest,servletResponse);
        } finally {
            SystemContext.removeOrder();
            SystemContext.removeSort();
            SystemContext.removePageOffset();
            SystemContext.removePageSize();
            SystemContext.removeRealPath();
        }

    }

    @Override
    public void destroy() {

    }
}
