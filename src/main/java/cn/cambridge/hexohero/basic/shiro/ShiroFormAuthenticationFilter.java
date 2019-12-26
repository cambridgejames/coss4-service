package cn.cambridge.hexohero.basic.shiro;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * shiro请求拦截器，拦截authc请求
 * @author PengJQ
 * @date 2019-12-26
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        String resultJson;
        Subject subject = getSubject(request, response);
        if(subject.getPrincipal() == null) {
            resultJson = mapper.writeValueAsString(CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.TOKEN_NOT_FOUND));
        } else {
            resultJson = mapper.writeValueAsString(CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PERMISSION_DENIED));
        }
        out.println(resultJson);
        out.flush();
        out.close();
        return false;
    }
}
