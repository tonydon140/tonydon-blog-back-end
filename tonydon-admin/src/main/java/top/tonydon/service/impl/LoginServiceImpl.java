package top.tonydon.service.impl;

import top.tonydon.domain.entity.LoginUser;
import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.LoginUserVo;
import top.tonydon.domain.vo.UserInfoVo;
import top.tonydon.service.LoginService;
import top.tonydon.constant.RedisConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.util.BeanCopyUtils;
import top.tonydon.util.RedisUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager manager;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 用户登陆
     *
     * @param user 用户
     * @return 返回值
     */
    @Override
    public ResponseResult<Object> login(User user) {
        // 1. 将用户封装为 Authentication
        Authentication beforeAuth =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 2. 认证用户，认证如果不通过会在方法内部直接抛出异常，afterAuth 一定是认证通过的
        Authentication afterAuth = manager.authenticate(beforeAuth);
        LoginUser loginUser = (LoginUser) afterAuth.getPrincipal();

        // 3. 根据 UUID 生成 token，并将 token 存入 LoginUser 中
        String token = UUID.randomUUID().toString().replace("-","");
        loginUser.setToken(token);

        // 4. 将用户消息存入 redis，使用 token 作为 key
        String key = RedisConstants.LOGIN_PREFIX + token;
        redisUtils.setObject(key, loginUser, RedisConstants.LOGIN_TTL);

        // 5. 把 token 和用户消息进行封装，并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        LoginUserVo vo = new LoginUserVo(token, userInfoVo);
        return ResponseResult.success(vo);
    }

    @Override
    public ResponseResult<Object> logout() {
        // 1. 从 context 中获取用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 2. 获取 token
        String token = loginUser.getToken();

        // 3. 从 Redis 中删除用户
        redisUtils.removeObject(RedisConstants.LOGIN_PREFIX + token);
        return ResponseResult.success();
    }
}
