package cn.tedu.note.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.tedu.note.entity.User;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet Filter implementation class AccessFilter
 */
public class AccessFilter implements Filter {

    public AccessFilter() {
       
    }

	public void destroy() {
		
	}
	private String login="/log_in.html";
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		HttpSession session=req.getSession();
		//放过log_in.html
		String path=req.getRequestURI();
		if(path.endsWith(login)){
			chain.doFilter(request, response);
			return;
		}
		if(path.endsWith("alert_error.html")){
			chain.doFilter(request, response);
			return;
		}
		//检查登录
		User user=(User)session.getAttribute("loginUser");
		//如果登录就放过
		if(user==null){
			//重定向到登录页
			res.sendRedirect(req.getContextPath()+login);
			return;
		}
		//如果没有登录就重定向登录
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
