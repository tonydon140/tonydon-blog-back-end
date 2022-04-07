package club.tonydon.filter;

import club.tonydon.contant.LoginConsts;
import club.tonydon.contant.RedisConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.LoginUser;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.util.RedisUtils;
import club.tonydon.utils.WebUtils;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. 获取请求头中的 token
        String token = request.getHeader(LoginConsts.TOKEN_KEY);

        // 2. 判断 token 是否为空
        if (StrUtil.isBlank(token)) {
            // 如果是请求登陆，直接放行
            if(LoginConsts.LOGIN_PATH.equals(request.getServletPath())){
                filterChain.doFilter(request,response);
            }
            // 非请求登陆，直接拒绝访问
            else WebUtils.renderString(response, JSON.toJSONString(ResponseResult.error(HttpCodeEnum.NEED_LOGIN)));
            return;
        }

        // 3. 根据 token 从 Redis 中查询用户信息
        String key = RedisConsts.LOGIN_PREFIX + token;
        LoginUser userDetails = (LoginUser) redisUtils.getObject(key);
        // 用户不存在，登陆过期
        if(userDetails == null){
            // 提示重新登陆
            WebUtils.renderString(response, JSON.toJSONString(ResponseResult.error(HttpCodeEnum.NEED_LOGIN)));
            return;
        }

        // 4. 刷新 Redis
        redisUtils.expire(key, RedisConsts.LOGIN_TTL);

        // 5. 存入 context
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 6. 放行
        filterChain.doFilter(request, response);
    }
}
