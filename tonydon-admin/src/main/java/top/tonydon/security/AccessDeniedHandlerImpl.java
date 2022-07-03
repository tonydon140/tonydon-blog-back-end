package top.tonydon.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.tonydon.domain.ResponseResult;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理器
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        accessDeniedException.printStackTrace();
        ResponseResult<Object> result = ResponseResult.error(HttpCodeEnum.NO_OPERATOR_AUTH);
        WebUtils.renderString(response, mapper.writeValueAsString(result));
    }
}
