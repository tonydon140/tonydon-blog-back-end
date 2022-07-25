package top.tonydon.security;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.tonydon.domain.ResponseResult;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.util.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        authException.printStackTrace();
        ResponseResult<Object> result;
        // 密码错误的异常
        if (authException instanceof BadCredentialsException) {
            result = ResponseResult.error(HttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
        }
        // 用户未登录异常
        else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.error(HttpCodeEnum.NEED_LOGIN);
        }
        // 其他异常情况
        else {
            result = ResponseResult.error(HttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
