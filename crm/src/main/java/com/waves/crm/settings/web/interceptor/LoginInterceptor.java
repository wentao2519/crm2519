package com.waves.crm.settings.web.interceptor;

import com.waves.crm.commons.constants.Constant;
import com.waves.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author huangWenTao
 * @desc
 * @date 2022/6/7 22:08
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在请求到达目标资源之前执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return true 可以继续执行  false  拦截请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 如果用户没有登录成功  则跳转到登录页面
        HttpSession session = httpServletRequest.getSession();
        // 如果登录成功 session中user一定不为空  user==null 说明用户未登录 或登录已过期
        User user = (User) session.getAttribute(Constant.SESSION_USER);

        if (user==null){
            //拦截  重定向到首页
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return false;
        }
        return true;
    }

    /**
     * 在请求到达目标资源之后执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 响应返回之后执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
